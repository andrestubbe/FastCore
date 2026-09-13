# Building FastCore ⚙️

This document describes how to compile, test, and release FastCore from source.

## Prerequisites

- **JDK 21+ LTS** (OpenJDK, Oracle JDK, or Temurin)
- **Maven 3.9+**
- *(Optional for C++ native builds)*: MSVC v143+ (Visual Studio 2022/2026) or GCC/Clang on Linux/macOS.

## Build Commands

```bash
# Clean and Compile with Java 21 preview features
mvn clean compile

# Build JAR and install to local Maven repository
mvn clean install -DskipTests

# Run Platform & FFM Linker Showcase
run-demo.bat
```

## Running the Showcase Demo

```bash
cd examples/Demo
mvn clean compile
run-demo.bat
```

## Release Process

FastCore releases are automatically built and published via GitHub Actions.

### Creating a Release Tag

```bash
# 1. Update version in pom.xml (e.g. 0.1.1)
# 2. Commit changes
git add .
git commit -m "Release FastCore 0.1.1"

# 3. Create and push tag
git tag -a 0.1.1 -m "FastCore 0.1.1 — Java 21 FFM & JNI Base"
git push origin 0.1.1
```

Once pushed, GitHub Actions triggers `.github/workflows/release.yml`, builds the artifact, and creates the GitHub Release for JitPack ingestion.
