# FastCore v0.1.0 — The Foundation 🏗️

## 🎉 Version 0.1.0: Blueprint Overhaul
This release marks the official standardization of **FastCore** as the mandatory foundation for the entire FastJava ecosystem. It provides the unified engine for native library deployment and JNI loading.

---

## ✨ Features

### 🚀 Unified JNI Loading
- Automated extraction and loading of native libraries (`.dll`, `.so`, `.dylib`) from JAR resources.
- Smart path resolution: Attempts to load from system paths first, with a seamless fallback to the internal deployment engine.

### 💻 Platform Abstraction
- Integrated platform detection for Windows, Linux, and macOS.
- Unified API to retrieve OS architecture and versioning info at runtime.

### 📦 Zero-Dependency Architecture
- Pure Java implementation with no external dependencies.
- Minimal footprint to ensure zero overhead during JVM startup.

---

## 📦 Installation (JitPack)

### Maven
```xml
<repositories>
    <repository>
        <id>jitpack.io</id>
        <url>https://jitpack.io</url>
    </repository>
</repositories>

<dependency>
    <groupId>com.github.andrestubbe</groupId>
    <artifactId>fastcore</artifactId>
    <version>0.1.0</version>
</dependency>
```

### Gradle
```groovy
repositories {
    maven { url 'https://jitpack.io' }
}

dependencies {
    implementation 'com.github.andrestubbe:fastcore:0.1.0'
}
```

---

## 🔧 Technical Details
- **Module Name:** `fastcore`
- **Minimum Java Version:** 17
- **License:** MIT

---

## 🙏 Credits
Developed with ⚡ by **Andre Stubbe**.

**Part of the FastJava Ecosystem** — *Making the JVM faster.*
