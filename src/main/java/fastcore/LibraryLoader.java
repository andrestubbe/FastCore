package fastcore;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Unified native library loader for all FastJava modules.
 * 
 * Features:
 * - Multi-platform support (Windows, Linux, macOS)
 * - Automatic library extraction from classpath
 * - Version checking
 * - Resource cleanup
 * - Singleton pattern per library
 * 
 * Usage:
 * <pre>
 * FastCore.loadLibrary("fastrobot");
 * FastCore.loadLibrary("fasthotkey");
 * </pre>
 */
public final class LibraryLoader {
    
    private static final Map<String, Boolean> loadedLibraries = new ConcurrentHashMap<>();
    
    private LibraryLoader() {
        // Utility class
    }
    
    /**
     * Loads a native library by name.
     * 
     * @param libraryName the library name without prefix/extension (e.g., "fastrobot")
     * @throws UnsatisfiedLinkError if the library cannot be loaded
     * @throws UnsupportedOperationException if the platform is not supported
     */
    public static synchronized void load(String libraryName) {
        if (loadedLibraries.containsKey(libraryName)) {
            return; // Already loaded
        }
        
        // Validate platform first
        Platform.validatePlatform();
        
        try {
            // Try system path first
            System.loadLibrary(libraryName);
            loadedLibraries.put(libraryName, true);
        } catch (UnsatisfiedLinkError e1) {
            // Extract and load from classpath
            try {
                String libraryPath = extractLibrary(libraryName);
                System.load(libraryPath);
                loadedLibraries.put(libraryName, true);
            } catch (Exception e2) {
                throw new UnsatisfiedLinkError(
                    "Failed to load native library '" + libraryName + "': " + e2.getMessage());
            }
        }
    }
    
    /**
     * Checks if a library is already loaded.
     */
    public static boolean isLoaded(String libraryName) {
        return loadedLibraries.containsKey(libraryName);
    }
    
    /**
     * Gets the set of loaded library names.
     */
    public static String[] getLoadedLibraries() {
        return loadedLibraries.keySet().toArray(new String[0]);
    }
    
    /**
     * Extracts the native library from classpath to a temporary location.
     */
    private static String extractLibrary(String libraryName) throws Exception {
        String fileName = Platform.getLibraryFileName(libraryName);
        String resourcePath = Platform.getLibraryResourcePath(libraryName);
        
        // Create temp directory
        Path tempDir = Files.createTempDirectory("fastcore-" + libraryName + "-");
        File libraryFile = tempDir.resolve(fileName).toFile();
        
        // Extract from classpath
        try (InputStream in = LibraryLoader.class.getResourceAsStream(resourcePath)) {
            if (in == null) {
                throw new RuntimeException("Native library not found in classpath: " + resourcePath);
            }
            
            try (FileOutputStream out = new FileOutputStream(libraryFile)) {
                byte[] buffer = new byte[8192];
                int read;
                while ((read = in.read(buffer)) != -1) {
                    out.write(buffer, 0, read);
                }
            }
        }
        
        // Make executable on Unix
        if (!Platform.isWindows()) {
            libraryFile.setExecutable(true);
        }
        
        // Register cleanup hook
        registerCleanupHook(libraryName, libraryFile, tempDir);
        
        return libraryFile.getAbsolutePath();
    }
    
    /**
     * Registers a shutdown hook to clean up the temporary library file.
     */
    private static void registerCleanupHook(String libraryName, File libraryFile, Path tempDir) {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try {
                Files.deleteIfExists(libraryFile.toPath());
                Files.deleteIfExists(tempDir);
            } catch (Exception e) {
                // Ignore cleanup errors
            }
        }, "fastcore-cleanup-" + libraryName));
    }
    
    /**
     * Clears the loaded library cache.
     * Useful for testing or when libraries need to be reloaded.
     */
    public static void clearCache() {
        loadedLibraries.clear();
    }
}
