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
        
        try {
            System.loadLibrary(libraryName);
            loadedLibraries.put(libraryName, true);
        } catch (UnsatisfiedLinkError e1) {
            try {
                String libraryPath = extractLibrary(libraryName, contextClass);
                System.load(libraryPath);
                loadedLibraries.put(libraryName, true);
            } catch (Exception e2) {
                throw new UnsatisfiedLinkError("Failed to load native library '" + libraryName + "': " + e2.getMessage());
            }
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
        
        Path tempDir = Files.createTempDirectory("fastcore-" + libraryName + "-");
        File libraryFile = tempDir.resolve(fileName).toFile();
        
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

        try (InputStream inToUse = in;
             FileOutputStream out = new FileOutputStream(libraryFile)) {
            byte[] buffer = new byte[8192];
            int read;
            while ((read = inToUse.read(buffer)) != -1) {
                out.write(buffer, 0, read);
            }
        }
        
        if (!Platform.isWindows()) {
            libraryFile.setExecutable(true);
        }
        
        registerCleanupHook(libraryName, libraryFile, tempDir);
        
        return libraryFile.getAbsolutePath();
    }
    
    private static void registerCleanupHook(String libraryName, File libraryFile, Path tempDir) {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try {
                Files.deleteIfExists(libraryFile.toPath());
                Files.deleteIfExists(tempDir);
            } catch (Exception e) {
            }
        }, "fastcore-cleanup-" + libraryName));
    }
    
    public static void clearCache() {
        loadedLibraries.clear();
    }
}
