# FastCore API Reference 📖

**FastCore** is the unified native loader and foreign function gateway for the FastJava ecosystem. It combines zero-overhead native shared library (`.dll`, `.so`, `.dylib`) extraction with modern **Java 21+ Foreign Function & Memory (FFM)** direct-link downcalls.

---

## 1. Core API (`fastcore.FastCore`)

### Traditional JNI Library Loading

| Method | Return Type | Description |
| :--- | :--- | :--- |
| `loadLibrary(String name)` | `void` | Resolves or extracts the library from the JAR resources and loads it into the JVM process via `System.load`. |
| `loadLibrary(String name, Class<?> ctx)` | `void` | Loads the library using the specific classloader context for resource extraction. |
| `isLibraryLoaded(String name)` | `boolean` | Checks if a library with the given logical name has already been loaded. |
| `getLoadedLibraries()` | `String[]` | Returns an array of all currently loaded library names. |
| `resolveLibraryPath(String name)` | `String` | Resolves the local absolute filesystem path of the native library without calling `System.load`. |

### Modern Java 21+ FFM (Foreign Function & Memory API)

| Method | Return Type | Description |
| :--- | :--- | :--- |
| `getNativeLinker()` | `Linker` | Returns the platform C ABI linker (`Linker.nativeLinker()`). |
| `loadNativeLookup(String name)` | `SymbolLookup` | Loads a native library into an off-heap `Arena` and returns a `SymbolLookup` for symbol resolution. |
| `lookupFunction(String lib, String fn, FunctionDescriptor desc)` | `MethodHandle` | Looks up an exported C function and links it into an invokable, JIT-optimizable `MethodHandle`. |
| `asMemorySegment(long address)` | `MemorySegment` | Converts a 64-bit raw native memory pointer (e.g., from `FastPointer`) into an unbounded `MemorySegment`. |
| `asMemorySegment(long address, long byteSize)` | `MemorySegment` | Converts a 64-bit raw native memory pointer into a bounds-checked `MemorySegment`. |

### Platform Detection & Utilities

| Method | Return Type | Description |
| :--- | :--- | :--- |
| `isWindows()` | `boolean` | Returns `true` if running on Windows. |
| `isLinux()` | `boolean` | Returns `true` if running on Linux. |
| `isMacOS()` | `boolean` | Returns `true` if running on macOS. |
| `isX86_64()` | `boolean` | Returns `true` if CPU architecture is x86_64 / amd64. |
| `isARM64()` | `boolean` | Returns `true` if CPU architecture is AArch64 / Apple Silicon. |
| `getOS()` | `Platform.OS` | Returns the detected `WINDOWS`, `LINUX`, `MACOS`, or `UNKNOWN`. |
| `getArch()` | `Platform.Arch` | Returns the detected `X86_64`, `ARM64`, `X86`, or `UNKNOWN`. |
| `getPlatformInfo()` | `String` | Formats OS, architecture, and Java runtime version into a single debug string. |
| `getLibraryFileName(String name)` | `String` | Returns platform-native file name (e.g. `name.dll` on Windows, `libname.so` on Linux). |
| `validatePlatform()` | `void` | Throws `UnsupportedOperationException` if OS or architecture is unrecognized. |
| `getVersion()` | `String` | Returns current FastCore version (`"0.1.1"`). |

---

## 2. Extraction Order & Resolution Mechanics

When `loadLibrary` or `resolveLibraryPath` is called, FastCore probes in strict priority order:

1. **Explicit Directories**:
   - `System.getProperty("app.dir")`
   - `System.getProperty("fastjava.native.dir")`
   - `./` (working directory), `./dll`, `./bin`, `./native`
2. **System Library Path**:
   - `java.library.path` and OS environment `PATH` / `LD_LIBRARY_PATH`.
3. **Embedded JAR Extraction**:
   - Extracted from `/native/<filename>` to `~/.fastcore/native/<libname>/<filename>`.
   - Re-used if already present on disk (avoids antivirus/WDAC lockups).

---

## 3. Platform & CPU Matrix

| OS / Architecture | Status | JNI Loading | FFM Downcalls |
| :--- | :--- | :--- | :--- |
| **Windows 10/11 (x64)** | ✅ Supported | Full | Full (MSVC ABI) |
| **Windows 11 (ARM64)** | 🚧 Planned | In Development | In Development |
| **Linux (x86_64 / glibc)** | 🚧 Planned | In Development | In Development (System V AMD64) |
| **macOS (Apple Silicon / M1-M4)** | 🚧 Planned | In Development | In Development (Darwin ARM64) |

---

*Part of the **FastJava** Ecosystem — Making the JVM faster. Minimalist. Deterministic. Native-First.*
