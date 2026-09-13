package fastcore;

/**
 * <h1>Platform — Hardware and Operating System Diagnostics</h1>
 *
 * <p>Lightweight, zero-allocation runtime diagnostics identifying the host operating system,
 * CPU architecture, and shared library file naming conventions for the FastJava ecosystem.</p>
 *
 * <h2>Capabilities:</h2>
 * <ul>
 *   <li><b>Operating System Detection:</b> Windows, Linux, and macOS.</li>
 *   <li><b>Architecture Identification:</b> x86_64 / amd64, ARM64 / AArch64, x86.</li>
 *   <li><b>Library Conventions:</b> Maps logical names to OS-native binaries (e.g. {@code .dll}, {@code .so}, {@code .dylib}).</li>
 * </ul>
 *
 * @author Andre Stubbe
 * @version 0.1.1
 * @since 0.1.0
 */
public final class Platform {

    private static final String OS_NAME = System.getProperty("os.name").toLowerCase();
    private static final String OS_ARCH = System.getProperty("os.arch").toLowerCase();
    private static final String JAVA_VERSION = System.getProperty("java.version");

    private Platform() {
        // Non-instantiable
    }

    /**
     * Supported operating system families.
     */
    public enum OS {
        WINDOWS, LINUX, MACOS, UNKNOWN
    }

    /**
     * Supported CPU architectures.
     */
    public enum Arch {
        X86_64, ARM64, X86, UNKNOWN
    }

    /**
     * Identifies the current host operating system family.
     *
     * @return {@link OS} enum value
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
     * Identifies the host CPU architecture.
     *
     * @return {@link Arch} enum value
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
     * Returns {@code true} if running on Windows.
     */
    public static boolean isWindows() {
        return getOS() == OS.WINDOWS;
    }

    /**
     * Returns {@code true} if running on Linux.
     */
    public static boolean isLinux() {
        return getOS() == OS.LINUX;
    }

    /**
     * Returns {@code true} if running on macOS.
     */
    public static boolean isMacOS() {
        return getOS() == OS.MACOS;
    }

    /**
     * Returns {@code true} if the CPU architecture is x86_64 / amd64.
     */
    public static boolean isX86_64() {
        return getArch() == Arch.X86_64;
    }

    /**
     * Returns {@code true} if the CPU architecture is ARM64 / AArch64.
     */
    public static boolean isARM64() {
        return getArch() == Arch.ARM64;
    }

    /**
     * Returns the native shared library file extension for the current OS.
     *
     * @return {@code ".dll"} on Windows, {@code ".dylib"} on macOS, {@code ".so"} on Linux
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
     * Returns the standard library prefix (e.g. {@code "lib"} on UNIX, empty on Windows).
     */
    public static String getLibraryPrefix() {
        return isWindows() ? "" : "lib";
    }

    /**
     * Computes the full file name of a shared library for the host platform.
     *
     * @param libraryName Base name
     * @return Fully formatted filename
     */
    public static String getLibraryFileName(String libraryName) {
        return getLibraryPrefix() + libraryName + getLibraryExtension();
    }

    /**
     * Computes the standard classpath resource path where the native binary is stored.
     *
     * @param libraryName Base name
     * @return Classpath resource location
     */
    public static String getLibraryResourcePath(String libraryName) {
        return "/native/" + getLibraryFileName(libraryName);
    }

    /**
     * Returns a formatted diagnostic string detailing the OS, architecture, and Java version.
     */
    public static String getPlatformInfo() {
        return String.format("OS: %s (%s), Arch: %s, Java: %s", getOS(), OS_NAME, getArch(), JAVA_VERSION);
    }

    /**
     * Verifies that the host operating system and architecture are recognized.
     *
     * @throws UnsupportedOperationException If unrecognized
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
