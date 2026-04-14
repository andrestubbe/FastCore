package fastcore;

/**
 * FastCore Demo — Platform detection and library loading showcase.
 * 
 * Run with: mvn compile exec:java
 */
public class Demo {
    
    public static void main(String[] args) {
        printBanner();
        
        // Show platform info
        System.out.println("Platform Information:");
        System.out.println("  " + FastCore.getPlatformInfo());
        System.out.println();
        
        // Detect OS
        System.out.println("Operating System:");
        System.out.println("  Windows: " + FastCore.isWindows());
        System.out.println("  Linux: " + FastCore.isLinux());
        System.out.println("  macOS: " + FastCore.isMacOS());
        System.out.println();
        
        // Detect Architecture
        System.out.println("Architecture:");
        System.out.println("  x86_64: " + FastCore.isX86_64());
        System.out.println("  ARM64: " + FastCore.isARM64());
        System.out.println();
        
        // Show library naming
        System.out.println("Library Naming Convention:");
        System.out.println("  fastrobot → " + FastCore.getLibraryFileName("fastrobot"));
        System.out.println("  fasthotkey → " + FastCore.getLibraryFileName("fasthotkey"));
        System.out.println();
        
        // Show loaded libraries (initially empty)
        System.out.println("Loaded Libraries: " + java.util.Arrays.toString(FastCore.getLoadedLibraries()));
        System.out.println();
        
        System.out.println("✅ Demo complete!");
        System.out.println();
        System.out.println("To load a native library in your code:");
        System.out.println("  FastCore.loadLibrary(\"fastrobot\");");
    }
    
    private static void printBanner() {
        System.out.println("╔══════════════════════════════════════════╗");
        System.out.println("║      FastCore Demo v" + FastCore.getVersion() + "                 ║");
        System.out.println("║      Unified JNI Loader                  ║");
        System.out.println("╚══════════════════════════════════════════╝");
        System.out.println();
    }
}
