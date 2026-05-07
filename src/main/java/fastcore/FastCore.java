package fastcore;

public final class FastCore {
    
    public static final String VERSION = "1.0.0";
    
    private FastCore() {
    }
    
    public static void loadLibrary(String libraryName, Class<?> contextClass) {
        LibraryLoader.load(libraryName, contextClass);
    }

    public static void loadLibrary(String libraryName) {
        LibraryLoader.load(libraryName, null);
    }
    
    public static boolean isLibraryLoaded(String libraryName) {
        return LibraryLoader.isLoaded(libraryName);
    }
    
    public static String[] getLoadedLibraries() {
        return LibraryLoader.getLoadedLibraries();
    }
    
    public static boolean isWindows() {
        return Platform.isWindows();
    }
    
    public static boolean isLinux() {
        return Platform.isLinux();
    }
    
    public static boolean isMacOS() {
        return Platform.isMacOS();
    }
    
    public static boolean isX86_64() {
        return Platform.isX86_64();
    }
    
    public static boolean isARM64() {
        return Platform.isARM64();
    }
    
    public static Platform.OS getOS() {
        return Platform.getOS();
    }
    
    public static Platform.Arch getArch() {
        return Platform.getArch();
    }
    
    public static String getPlatformInfo() {
        return Platform.getPlatformInfo();
    }
    
    public static String getLibraryFileName(String libraryName) {
        return Platform.getLibraryFileName(libraryName);
    }
    
    public static void validatePlatform() {
        Platform.validatePlatform();
    }
    
    public static String getVersion() {
        return VERSION;
    }
    
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
