package fastcore;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public final class LibraryLoader {
    
    private static final Map<String, Boolean> loadedLibraries = new ConcurrentHashMap<>();
    
    private LibraryLoader() {
    }
    
    public static synchronized void load(String libraryName) {
        load(libraryName, null);
    }

    public static synchronized void load(String libraryName, Class<?> contextClass) {
        if (loadedLibraries.containsKey(libraryName)) {
            return;
        }
        
        Platform.validatePlatform();
        
        // Priority 1: Check local application directory and common native subfolders
        String fileName = Platform.getLibraryFileName(libraryName);
        String[] candidateDirs = {
            System.getProperty("app.dir"),
            System.getProperty("fastjava.native.dir"),
            ".",
            "dll",
            "bin",
            "native"
        };

        for (String dir : candidateDirs) {
            if (dir != null && !dir.isBlank()) {
                File candidate = Path.of(dir, fileName).toFile();
                if (candidate.exists() && candidate.isFile()) {
                    try {
                        System.load(candidate.getAbsolutePath());
                        loadedLibraries.put(libraryName, true);
                        return;
                    } catch (Throwable ignored) {
                        // Fallthrough to next candidate or standard loading
                    }
                }
            }
        }

        // Priority 2: System library path (java.library.path / PATH)
        try {
            System.loadLibrary(libraryName);
            loadedLibraries.put(libraryName, true);
            return;
        } catch (UnsatisfiedLinkError ignored) {
        }

        // Priority 3: Fallback extraction from JAR classpath into user cache
        try {
            String libraryPath = extractLibrary(libraryName, contextClass);
            System.load(libraryPath);
            loadedLibraries.put(libraryName, true);
        } catch (Exception e2) {
            throw new UnsatisfiedLinkError("Failed to load native library '" + libraryName + "': " + e2.getMessage());
        }
    }
    
    public static boolean isLoaded(String libraryName) {
        return loadedLibraries.containsKey(libraryName);
    }
    
    public static String[] getLoadedLibraries() {
        return loadedLibraries.keySet().toArray(new String[0]);
    }
    
    private static String extractLibrary(String libraryName, Class<?> contextClass) throws Exception {
        String fileName = Platform.getLibraryFileName(libraryName);
        String resourcePath = Platform.getLibraryResourcePath(libraryName);
        
        Path cacheDir = Path.of(System.getProperty("user.home", "."), ".fastcore", "native", libraryName);
        Files.createDirectories(cacheDir);
        File libraryFile = cacheDir.resolve(fileName).toFile();
        
        InputStream in = null;
        if (contextClass != null) {
            in = contextClass.getResourceAsStream(resourcePath);
        }
        if (in == null) {
            ClassLoader tcl = Thread.currentThread().getContextClassLoader();
            if (tcl != null) {
                in = tcl.getResourceAsStream(resourcePath);
            }
        }
        if (in == null) {
            in = LibraryLoader.class.getResourceAsStream(resourcePath);
        }

        if (in == null) {
            throw new RuntimeException("Native library not found in classpath: " + resourcePath);
        }

        // Only extract if the file does not already exist in cache.
        // This prevents WDAC / Defender from blocking a freshly overwritten DLL.
        if (!libraryFile.exists()) {
            try (InputStream inToUse = in;
                 FileOutputStream out = new FileOutputStream(libraryFile)) {
                byte[] buffer = new byte[8192];
                int read;
                while ((read = inToUse.read(buffer)) != -1) {
                    out.write(buffer, 0, read);
                }
            }
        } else {
            in.close();
        }

        if (!Platform.isWindows()) {
            libraryFile.setExecutable(true);
        }

        return libraryFile.getAbsolutePath();
    }
    
    /**
     * Resolves or extracts the library file on the filesystem without calling System.load.
     * Useful for FFM SymbolLookup.libraryLookup.
     */
    public static synchronized String resolveLibraryPath(String libraryName, Class<?> contextClass) throws Exception {
        Platform.validatePlatform();
        String fileName = Platform.getLibraryFileName(libraryName);
        String[] candidateDirs = {
            System.getProperty("app.dir"),
            System.getProperty("fastjava.native.dir"),
            ".",
            "dll",
            "bin",
            "native"
        };

        for (String dir : candidateDirs) {
            if (dir != null && !dir.isBlank()) {
                File candidate = Path.of(dir, fileName).toFile();
                if (candidate.exists() && candidate.isFile()) {
                    return candidate.getAbsolutePath();
                }
            }
        }

        return extractLibrary(libraryName, contextClass);
    }

    public static void clearCache() {
        loadedLibraries.clear();
    }
}
