# Changelog

All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.1.0/).

## [0.1.1] - 2026-09-13

### Added
- **Java 21+ Foreign Function & Memory (FFM) API**:
  - `FastCore.getNativeLinker()` for direct system C ABI downcalls.
  - `FastCore.loadNativeLookup()` for registering off-heap native library symbols.
  - `FastCore.lookupFunction()` producing zero-overhead, inlinable `MethodHandle` instances.
  - `FastCore.asMemorySegment()` for zero-copy conversion of 64-bit native pointers (e.g. `FastPointer`).
- **Filesystem Path Resolution**:
  - `LibraryLoader.resolveLibraryPath()` to locate or extract native DLLs without forcing `System.load`.
- **Hero Demo & Automation**:
  - Standalone runner `run-demo.bat` for quick verification.
  - Enhanced `Demo.java` testing both traditional JNI and Java 21+ FFM Linkers.
- **Architectural Roadmap**:
  - Added hidden `.TODO.md` outlining the FastJava FFM migration path.

### Changed
- Upgraded Java language baseline from 17 to **21 LTS** (`--enable-preview`).
- Overhauled documentation (`README.md`, `REFERENCE.md`, `PHILOSOPHY.md`, `ROADMAP.md`, `COMPILE.md`) to reflect FastJava Blueprint standards.

## [0.1.0] - 2026-08-24

### Added
- Initial project release.
- Core JNI native extraction and loading engine (`LibraryLoader`).
- Cross-platform architecture and OS validation (`Platform`).
