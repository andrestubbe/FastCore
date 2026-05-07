package fastcore;

public final class Platform {
    
    private static final String OS_NAME = System.getProperty("os.name").toLowerCase();
    private static final String OS_ARCH = System.getProperty("os.arch").toLowerCase();
    private static final String JAVA_VERSION = System.getProperty("java.version");
    
    private Platform() {
    }
    
    public enum OS {
        WINDOWS, LINUX, MACOS, UNKNOWN
    }
    
    public enum Arch {
        X86_64, ARM64, X86, UNKNOWN
    }
    
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
    
    public static boolean isWindows() {
        return getOS() == OS.WINDOWS;
    }
    
    public static boolean isLinux() {
        return getOS() == OS.LINUX;
    }
    
    public static boolean isMacOS() {
        return getOS() == OS.MACOS;
    }
    
    public static boolean isX86_64() {
        return getArch() == Arch.X86_64;
    }
    
    public static boolean isARM64() {
        return getArch() == Arch.ARM64;
    }
    
    public static String getLibraryExtension() {
        return switch (getOS()) {
            case WINDOWS -> ".dll";
            case MACOS -> ".dylib";
            case LINUX -> ".so";
            default -> throw new UnsupportedOperationException("Unsupported OS: " + OS_NAME);
        };
    }
    
    public static String getLibraryPrefix() {
        return isWindows() ? "" : "lib";
    }
    
    public static String getLibraryFileName(String libraryName) {
        return getLibraryPrefix() + libraryName + getLibraryExtension();
    }
    
    public static String getLibraryResourcePath(String libraryName) {
        return "/native/" + getLibraryFileName(libraryName);
    }
    
    public static String getPlatformInfo() {
        return String.format("OS: %s (%s), Arch: %s, Java: %s", getOS(), OS_NAME, getArch(), JAVA_VERSION);
    }
    
    public static void validatePlatform() {
        if (getOS() == OS.UNKNOWN) {
            throw new UnsupportedOperationException("Unsupported operating system: " + OS_NAME);
        }
        if (getArch() == Arch.UNKNOWN) {
            throw new UnsupportedOperationException("Unsupported architecture: " + OS_ARCH);
        }
    }
}
