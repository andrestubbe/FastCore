package fastcore;

import java.lang.foreign.Arena;
import java.lang.foreign.FunctionDescriptor;
import java.lang.foreign.Linker;
import java.lang.foreign.MemorySegment;
import java.lang.foreign.SymbolLookup;
import java.lang.invoke.MethodHandle;
import java.nio.file.Path;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public final class FastCore {
    
    public static final String VERSION = "0.1.1";
    
    private static final Map<String, SymbolLookup> FFM_LOOKUPS = new ConcurrentHashMap<>();
    private static final Linker NATIVE_LINKER = Linker.nativeLinker();
    
    private FastCore() {
    }
    
    // --- Traditional JNI Loading ---

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

    public static String resolveLibraryPath(String libraryName, Class<?> contextClass) throws Exception {
        return LibraryLoader.resolveLibraryPath(libraryName, contextClass);
    }

    public static String resolveLibraryPath(String libraryName) throws Exception {
        return LibraryLoader.resolveLibraryPath(libraryName, null);
    }

    // --- Modern Java 21+ FFM (Foreign Function & Memory API) Support ---

    /**
     * Obtains the global native linker for C ABI downcalls.
     */
    public static Linker getNativeLinker() {
        return NATIVE_LINKER;
    }

    /**
     * Resolves and registers an FFM SymbolLookup for a native shared library (.dll, .so, .dylib).
     */
    public static SymbolLookup loadNativeLookup(String libraryName, Class<?> contextClass, Arena arena) throws Exception {
        SymbolLookup existing = FFM_LOOKUPS.get(libraryName);
        if (existing != null) {
            return existing;
        }
        String pathStr = resolveLibraryPath(libraryName, contextClass);
        Path path = Path.of(pathStr);
        SymbolLookup lookup = SymbolLookup.libraryLookup(path, arena != null ? arena : Arena.global());
        FFM_LOOKUPS.put(libraryName, lookup);
        return lookup;
    }

    public static SymbolLookup loadNativeLookup(String libraryName) throws Exception {
        return loadNativeLookup(libraryName, null, Arena.global());
    }

    /**
     * Looks up and links a native C function directly into a high-performance MethodHandle using FFM.
     */
    public static MethodHandle lookupFunction(String libraryName, String functionName,
                                              FunctionDescriptor descriptor, Class<?> contextClass) throws Exception {
        SymbolLookup lookup = loadNativeLookup(libraryName, contextClass, Arena.global());
        var symbolOpt = lookup.find(functionName);
        if (symbolOpt.isEmpty()) {
            throw new NoSuchMethodException("Native function '" + functionName + "' not found in library '" + libraryName + "'");
        }
        return NATIVE_LINKER.downcallHandle(symbolOpt.get(), descriptor);
    }

    public static MethodHandle lookupFunction(String libraryName, String functionName,
                                              FunctionDescriptor descriptor) throws Exception {
        return lookupFunction(libraryName, functionName, descriptor, null);
    }

    /**
     * Zero-overhead conversion of a 64-bit native memory address (e.g. from FastPointer) into a MemorySegment.
     */
    public static MemorySegment asMemorySegment(long address) {
        return MemorySegment.ofAddress(address);
    }

    /**
     * Zero-overhead conversion of a 64-bit native memory address with a bounded byte size into a MemorySegment.
     */
    public static MemorySegment asMemorySegment(long address, long byteSize) {
        return MemorySegment.ofAddress(address).reinterpret(byteSize);
    }
    
    // --- Platform Query Utilities ---

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
        System.out.println("Usage (JNI):");
        System.out.println("  FastCore.loadLibrary(\"fastnative\");");
        System.out.println("Usage (FFM):");
        System.out.println("  MethodHandle mh = FastCore.lookupFunction(\"fastnative\", \"func\", descriptor);");
    }
}
