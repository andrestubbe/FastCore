# FastCore — Native Library Loader & JNI Utilities for Java

**The ultra-minimal foundation powering the entire FastJava ecosystem.**

[![Build](https://img.shields.io/github/actions/workflow/status/andrestubbe/FastCore/maven.yml?branch=main)](https://github.com/andrestubbe/FastCore/actions)
[![Java](https://img.shields.io/badge/Java-17+-blue.svg)](https://www.java.com)
[![Platform](https://img.shields.io/badge/Platform-Windows%2010+-lightgrey.svg)]()
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)
[![JitPack](https://jitpack.io/v/andrestubbe/FastCore.svg)](https://jitpack.io/#andrestubbe/FastCore)

FastCore provides the mandatory **native library extraction and loading engine** for the FastJava ecosystem. It ensures that bundled DLLs are safely deployed and loaded across different environments with zero overhead.

```java
// Quick Start — Loading a native library
import fastcore.FastCore;

public class MyNativeApp {
    static {
        // Automatically extracts and loads fastnative.dll from resources
        FastCore.loadLibrary("fastnative");
    }
}
```

---

## Table of Contents
- [Key Features](#key-features)
- [Performance](#performance)
- [Installation](#installation)
- [Try the Demo](#try-the-demo)
- [API Reference](#api-reference)
- [Platform Support](#platform-support)
- [Building from Source](#building-from-source)
- [License](#license)
- [Related Projects](#related-projects)

---

## Key Features

- **🚀 Smart Extraction** — Automatically handles temporary file deployment for JNI DLLs.
- **⚡ Zero Overhead** — Minimalist design focused on JVM startup acceleration.
- **📦 Ecosystem Base** — Required dependency for all FastJava modules.

---

## Performance

FastCore is designed to be the fastest way to bridge Java and Native code during the initialization phase.

| Operation | FastCore | Standard System.load | Benefit |
|-----------|---------|----------------------|---------|
| Library Extraction | < 5 ms | N/A (Manual) | **Automated** |
| JNI Mapping | Instant | Instant | **Stable** |

---

## Installation

### Maven (JitPack)
```xml
<repositories>
    <repository>
        <id>jitpack.io</id>
        <url>https://jitpack.io</url>
    </repository>
</repositories>

<dependencies>
    <dependency>
        <groupId>com.github.andrestubbe</groupId>
        <artifactId>fastcore</artifactId>
        <version>0.1.0</version>
    </dependency>
</dependencies>
```

### Gradle (JitPack)
```groovy
repositories {
    maven { url 'https://jitpack.io' }
}

dependencies {
    implementation 'com.github.andrestubbe:fastcore:0.1.0'
}
```

---

## API Reference

| Method | Description |
|--------|-------------|
| `void loadLibrary(String name)` | Extracts and loads a native library from the JAR resources. |

---

## Platform Support

| Platform | Status |
|----------|--------|
| Windows 10/11 (x64) | ✅ Fully Supported |
| Linux | 🚧 Planned |
| macOS | 🚧 Planned |

---

## Building from Source

For detailed instructions on compiling the native parts and building the Maven artifact, see [COMPILE.md](COMPILE.md).

---

## License
MIT License — See [LICENSE](LICENSE) file for details.

---

## Related Projects
- [FastFileIndex](https://github.com/andrestubbe/FastFileIndex) — Ultra-fast filesystem scanner
- [FastTheme](https://github.com/andrestubbe/FastTheme) — High-performance native window styling
- [FastThumb](https://github.com/andrestubbe/FastThumb) — Native Shell Image Engine

---
**Made with ⚡ by Andre Stubbe**

<!-- 
SEO Keywords: java, jni, native, fastjava, library loader, performance
-->
