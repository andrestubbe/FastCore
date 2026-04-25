package fastcore;

/**
 * Demo - Jitpack dependency test for FastCore.
 *
 * <p>This demo validates that FastCore can be loaded via Jitpack Maven dependency.
 * It tests the core functionality:
 * <ul>
 *   <li>Platform detection (OS, architecture)</li>
 *   <li>Library filename generation</li>
 *   <li>Platform validation</li>
 *   <li>Version information</li>
 * </ul>
 *
 * <p><b>Usage:</b> Run with Maven to verify Jitpack dependency works.</p>
 *
 * @see FastCore
 * @version 1.0.0
 */
public class Demo {
    public static void main(String[] args) {
        System.out.println("FastCore Jitpack Dependency Test");
        System.out.println("================================");
        System.out.println();

        // Test version
        System.out.println("Version: " + FastCore.getVersion());
        System.out.println();

        // Test platform detection
        System.out.println("Platform Detection:");
        System.out.println("  OS: " + FastCore.getOS());
        System.out.println("  Architecture: " + FastCore.getArch());
        System.out.println("  Is Windows: " + FastCore.isWindows());
        System.out.println("  Is Linux: " + FastCore.isLinux());
        System.out.println("  Is macOS: " + FastCore.isMacOS());
        System.out.println("  Is x86_64: " + FastCore.isX86_64());
        System.out.println("  Is ARM64: " + FastCore.isARM64());
        System.out.println();

        // Test platform info
        System.out.println("Platform Info:");
        System.out.println("  " + FastCore.getPlatformInfo());
        System.out.println();

        // Test library filename generation
        System.out.println("Library Filename Generation:");
        System.out.println("  fastrobot -> " + FastCore.getLibraryFileName("fastrobot"));
        System.out.println("  fastdisplay -> " + FastCore.getLibraryFileName("fastdisplay"));
        System.out.println();

        // Test platform validation
        System.out.println("Platform Validation:");
        try {
            FastCore.validatePlatform();
            System.out.println("  [OK] Platform is supported");
        } catch (UnsupportedOperationException e) {
            System.out.println("  [FAIL] Platform not supported: " + e.getMessage());
        }
        System.out.println();

        // Test library loading status
        System.out.println("Library Loading Status:");
        System.out.println("  Loaded libraries: " + FastCore.getLoadedLibraries().length);
        System.out.println();

        System.out.println("================================");
        System.out.println("Jitpack dependency test complete!");
        System.out.println("If you see this message, Jitpack Maven dependency works correctly.");
    }
}
