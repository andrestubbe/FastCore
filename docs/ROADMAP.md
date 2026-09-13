# FastCore Roadmap 🗺️

**Vision:** To provide the fastest, zero-overhead native primitives and modern C ABI downcalls for the entire FastJava ecosystem.

## 🟢 v0.1.0: Initial JNI Extraction Core
- [x] **Smart Library Extraction**: Automated off-heap unpacking from JAR resources.
- [x] **Cross-Platform Path Resolver**: Windows, Linux, and macOS file conventions.
- [x] **Deduplicated Loading Cache**: Thread-safe registry preventing double-loading.

## 🟢 v0.1.1: Java 21+ FFM & Foreign Function Architecture (Current)
- [x] **Java 21 LTS Target**: Baseline upgrade to modern LTS with native memory support.
- [x] **FFM SymbolLookup & Linker**: Direct zero-JNI downcall handles (`lookupFunction`).
- [x] **FastPointer to MemorySegment Bridge**: Zero-copy conversions (`asMemorySegment`).
- [x] **Executable Demo & Benchmark**: Standalone launcher (`run-demo.bat`).

## 🟡 v0.2.0: Modular Native Backends & Auto-Discovery
- [ ] **Multi-Platform Native Matrix**: Prebuilt `.so` and `.dylib` cross-compilation in CI.
- [ ] **Native Function Registry**: Declarative annotations for automatic FFM function binding.
- [ ] **Off-Heap Allocator Hints**: Direct NUMA-aware allocation and large page checks.

## 🟠 v0.5.0: Native Memory Interop & Ecosystem Expansion
- [ ] **Universal FastPointer Integration**: Native struct layout mapping.
- [ ] **Direct Syscall Dispatcher**: High-precision timers, IO completion ports, and memory barriers.

## 🔴 v1.0.0: Production Hardening
- [ ] **Complete JVM Multi-Release Packaging**: Java 21+ and Java 25 LTS certified.
- [ ] **Enterprise Security & WDAC Certification**: Code-signing pipeline for all bundled shared libraries.

---
**Focus:** Performance is our USP. We optimize where standard Java stops. 🚀
