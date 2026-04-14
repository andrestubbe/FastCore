package fastcore;

/**
 * FastJava Core - Unified JNI loader and platform abstraction.
 * 
 * <p>FastCore is the foundation for all FastJava modules, providing:
 * <ul>
 *   <li><b>Unified JNI Loading</b> - Load native libraries (.dll/.so/.dylib) dynamically</li>
 *   <li><b>Cross-Platform Support</b> - Windows, Linux, macOS detection</li>
 *   <li><b>Resource Management</b> - Automatic temp file cleanup</li>
 *   <li><b>Error Translation</b> - Native errors to Java exceptions</li>
 * </ul>
 * 
 * <p><b>Usage:</b>
 * <pre>
 * // Load a native library
 * FastCore.loadLibrary("fastrobot");
 * 
 * // Check platform
 * if (FastCore.isWindows()) {
 *     // Windows-specific code
 * }
 * 
 * // Get platform info
 * System.out.println(FastCore.getPlatformInfo());
 * </pre>
 * 
 * @author FastJava Team
 * @version 1.0.0
 */
public final class FastCore {
    
    public static final String VERSION = "1.0.0";
    
    private FastCore() {
        // Utility class - cannot instantiate
    }
    
    /**
     * Loads a native library by name.
     * 
     * <p>This is the main entry point for all FastJava modules.
     * The library name should be without prefix or extension:
     * <ul>
     *   <li>Windows: "fastrobot" → fastrobot.dll</li>
     *   <li>Linux: "fastrobot" → libfastrobot.so</li>
     *   <li>macOS: "fastrobot" → libfastrobot.dylib</li>
     * </ul>
     * 
     * <p>Libraries are loaded in the following order:
     * <ol>
     *   <li>Try system library path (java.library.path)</li>
     *   <li>Extract from classpath JAR to temp directory</li>
     *   <li>Load from temp location</li>
     * </ol>
     *
     * @param libraryName the library name without prefix/extension (e.g., "fastrobot")
     * @throws UnsatisfiedLinkError if the library cannot be found or loaded
     * @throws UnsupportedOperationException if the operating system is not supported
     * @see Platform for OS/architecture detection
     */
    public static void loadLibrary(String libraryName) {
        LibraryLoader.load(libraryName);
    }
    
    /**
     * Checks if a library has been loaded.
     *
     * @param libraryName the library name
     * @return true if the library is already loaded
     */
    public static boolean isLibraryLoaded(String libraryName) {
        return LibraryLoader.isLoaded(libraryName);
    }
    
    /**
     * Gets all loaded library names.
     *
     * @return array of loaded library names
     */
    public static String[] getLoadedLibraries() {
        return LibraryLoader.getLoadedLibraries();
    }
    
    /**
     * Checks if running on Windows.
     */
    public static boolean isWindows() {
        return Platform.isWindows();
    }
    
    /**
     * Checks if running on Linux.
     */
    public static boolean isLinux() {
        return Platform.isLinux();
    }
    
    /**
     * Checks if running on macOS.
     */
    public static boolean isMacOS() {
        return Platform.isMacOS();
    }
    
    /**
     * Checks if running on x86_64 (AMD64) architecture.
     */
    public static boolean isX86_64() {
        return Platform.isX86_64();
    }
    
    /**
     * Checks if running on ARM64 (AArch64) architecture.
     */
    public static boolean isARM64() {
        return Platform.isARM64();
    }
    
    /**
     * Gets the current operating system type.
     */
    public static Platform.OS getOS() {
        return Platform.getOS();
    }
    
    /**
     * Gets the current architecture type.
     */
    public static Platform.Arch getArch() {
        return Platform.getArch();
    }
    
    /**
     * Gets platform information for debugging.
     * 
     * @return string containing OS, architecture, and Java version
     */
    public static String getPlatformInfo() {
        return Platform.getPlatformInfo();
    }
    
    /**
     * Gets the native library filename for the current platform.
     * 
     * @param libraryName base library name (e.g., "fastrobot")
     * @return platform-specific filename (e.g., "fastrobot.dll" or "libfastrobot.so")
     */
    public static String getLibraryFileName(String libraryName) {
        return Platform.getLibraryFileName(libraryName);
    }
    
    /**
     * Validates that the current platform is supported.
     * 
     * @throws UnsupportedOperationException if platform is not supported
     */
    public static void validatePlatform() {
        Platform.validatePlatform();
    }
    
    /**
     * Gets the FastCore version.
     */
    public static String getVersion() {
        return VERSION;
    }
    
    /**
     * Main entry point for command-line usage.
     * Prints platform information.
     */
    public static void main(String[] args) {
        System.out.println("FastJava Core " + VERSION);
        System.out.println("===================");
        System.out.println(getPlatformInfo());
        System.out.println();
        System.out.println("Supported platforms:");
        System.out.println("  - Windows (x86_64, ARM64)");
        System.out.println("  - Linux (x86_64, ARM64)");
        System.out.println("  - macOS (x86_64, ARM64)");
        System.out.println();
        System.out.println("Usage:");
        System.out.println("  FastCore.loadLibrary(\"fastrobot\");");
    }
}
