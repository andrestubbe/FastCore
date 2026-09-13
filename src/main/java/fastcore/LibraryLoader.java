package fastcore;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * <h1>LibraryLoader — Native Binary Resolution and Deployment Engine</h1>
 *
 * <p>Automates the extraction, disk caching, and execution loading of native shared libraries
 * ({@code .dll}, {@code .so}, {@code .dylib}) embedded within FastJava application JARs.</p>
 *
 * <h2>Resolution Sequence:</h2>
 * <ol>
 *   <li><b>Explicit Candidate Directories:</b> Checks {@code System.getProperty("app.dir")},
 *       {@code System.getProperty("fastjava.native.dir")}, {@code ./dll}, {@code ./bin}, and {@code ./native}.</li>
 *   <li><b>System Environment:</b> Probes {@code java.library.path} and OS {@code PATH} / {@code LD_LIBRARY_PATH}.</li>
 *   <li><b>Embedded JAR Extraction:</b> Unpacks the resource to {@code ~/.fastcore/native/<libname>/}
 *       with idempotent file existence checks to prevent WDAC or antivirus locks.</li>
 * </ol>
 *
 * @author Andre Stubbe
 * @version 0.1.1
 * @since 0.1.0
 */
public final class LibraryLoader {

    private static final Map<String, Boolean> loadedLibraries = new ConcurrentHashMap<>();

    private LibraryLoader() {
        // Non-instantiable
    }

    /**
     * Loads a native library into the current process using the default classloader.
     *
     * @param libraryName Base name of the library
     */
    public static synchronized void load(String libraryName) {
        load(libraryName, null);
    }

    /**
     * Loads a native library into the current process using a specific context class.
     *
     * @param libraryName  Base name of the library
     * @param contextClass Calling class used for resource lookup
     */
    public static synchronized void load(String libraryName, Class<?> contextClass) {
        if (loadedLibraries.containsKey(libraryName)) {
            return;
        }

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
                    try {
                        System.load(candidate.getAbsolutePath());
                        loadedLibraries.put(libraryName, true);
                        return;
                    } catch (Throwable ignored) {
                        // Fallthrough to next candidate
                    }
                }
            }
        }

        // Priority 2: System library path
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

    /**
     * Checks if the specified library is already loaded.
     */
    public static boolean isLoaded(String libraryName) {
        return loadedLibraries.containsKey(libraryName);
    }

    /**
     * Returns an array of loaded library names.
     */
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
     * Resolves or extracts the library file on the filesystem without invoking {@link System#load}.
     * Essential for modern Foreign Function &amp; Memory (FFM) SymbolLookup.
     *
     * @param libraryName  Logical library name
     * @param contextClass Context class for resource extraction
     * @return Absolute file path to the native binary
     * @throws Exception If resolution or extraction fails
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

    /**
     * Clears internal state tracking of loaded libraries.
     */
    public static void clearCache() {
        loadedLibraries.clear();
    }
}
