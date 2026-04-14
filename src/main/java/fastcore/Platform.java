package fastcore;

/**
 * Platform detection utilities for Windows, Linux, and macOS.
 * Provides OS and architecture information for native library loading.
 */
public final class Platform {
    
    private static final String OS_NAME = System.getProperty("os.name").toLowerCase();
    private static final String OS_ARCH = System.getProperty("os.arch").toLowerCase();
    private static final String JAVA_VERSION = System.getProperty("java.version");
    
    private Platform() {
        // Utility class
    }
    
    /**
     * Operating System types.
     */
    public enum OS {
        WINDOWS, LINUX, MACOS, UNKNOWN
    }
    
    /**
     * CPU Architecture types.
     */
    public enum Arch {
        X86_64, ARM64, X86, UNKNOWN
    }
    
    /**
     * Gets the current operating system.
     */
    public static OS getOS() {
        if (OS_NAME.contains("win")) {
            return OS.WINDOWS;
        } else if (OS_NAME.contains("mac")) {
            return OS.MACOS;
        } else if (OS_NAME.contains("nix") || OS_NAME.contains("nux") || OS_NAME.contains("aix")) {
            return OS.LINUX;
        }
        return OS.UNKNOWN;
    }
    
    /**
     * Gets the current architecture.
     */
    public static Arch getArch() {
        if (OS_ARCH.contains("amd64") || OS_ARCH.contains("x86_64")) {
            return Arch.X86_64;
        } else if (OS_ARCH.contains("aarch64") || OS_ARCH.contains("arm64")) {
            return Arch.ARM64;
        } else if (OS_ARCH.contains("x86")) {
            return Arch.X86;
        }
        return Arch.UNKNOWN;
    }
    
    /**
     * Checks if running on Windows.
     */
    public static boolean isWindows() {
        return getOS() == OS.WINDOWS;
    }
    
    /**
     * Checks if running on Linux.
     */
    public static boolean isLinux() {
        return getOS() == OS.LINUX;
    }
    
    /**
     * Checks if running on macOS.
     */
    public static boolean isMacOS() {
        return getOS() == OS.MACOS;
    }
    
    /**
     * Checks if running on 64-bit x86 architecture.
     */
    public static boolean isX86_64() {
        return getArch() == Arch.X86_64;
    }
    
    /**
     * Checks if running on ARM64 architecture.
     */
    public static boolean isARM64() {
        return getArch() == Arch.ARM64;
    }
    
    /**
     * Gets the native library file extension for the current platform.
     */
    public static String getLibraryExtension() {
        return switch (getOS()) {
            case WINDOWS -> ".dll";
            case MACOS -> ".dylib";
            case LINUX -> ".so";
            default -> throw new UnsupportedOperationException("Unsupported OS: " + OS_NAME);
        };
    }
    
    /**
     * Gets the native library prefix for the current platform.
     * (e.g., "lib" on Unix systems, "" on Windows)
     */
    public static String getLibraryPrefix() {
        return isWindows() ? "" : "lib";
    }
    
    /**
     * Gets the full native library name for the given module.
     * e.g., "fastrobot" → "fastrobot.dll" (Windows) or "libfastrobot.so" (Linux)
     */
    public static String getLibraryFileName(String libraryName) {
        return getLibraryPrefix() + libraryName + getLibraryExtension();
    }
    
    /**
     * Gets the resource path for the native library.
     */
    public static String getLibraryResourcePath(String libraryName) {
        return "/native/" + getLibraryFileName(libraryName);
    }
    
    /**
     * Gets platform information for debugging.
     */
    public static String getPlatformInfo() {
        return String.format("OS: %s (%s), Arch: %s, Java: %s", 
            getOS(), OS_NAME, getArch(), JAVA_VERSION);
    }
    
    /**
     * Validates that the current platform is supported.
     * @throws UnsupportedOperationException if platform is not supported
     */
    public static void validatePlatform() {
        if (getOS() == OS.UNKNOWN) {
            throw new UnsupportedOperationException("Unsupported operating system: " + OS_NAME);
        }
        if (getArch() == Arch.UNKNOWN) {
            throw new UnsupportedOperationException("Unsupported architecture: " + OS_ARCH);
        }
    }
}
