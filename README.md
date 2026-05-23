# FastCore â€” Native Library Loader & JNI Utilities for Java [v0.1.0]

**The ultra-minimal foundation powering the entire FastJava ecosystem.**

[![Build](https://img.shields.io/github/actions/workflow/status/andrestubbe/FastCore/build.yml?branch=main)](https://github.com/andrestubbe/FastCore/actions)
[![Java](https://img.shields.io/badge/Java-17+-blue.svg)](https://www.java.com)
[![Platform](https://img.shields.io/badge/Platform-Windows%2010+-lightgrey.svg)]()
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)
[![JitPack](https://jitpack.io/v/andrestubbe/FastCore.svg)](https://jitpack.io/#andrestubbe/FastCore)

FastCore provides the mandatory **native library extraction and loading engine** for the FastJava ecosystem. It ensures that bundled DLLs are safely deployed and loaded across different environments with zero overhead.

```java
// Quick Start â€” Loading a native library
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

- **ðŸš€ Smart Extraction** â€” Automatically handles temporary file deployment for JNI DLLs.
- **âš¡ Zero Overhead** â€” Minimalist design focused on JVM startup acceleration.
- **ðŸ“¦ Ecosystem Base** â€” Required dependency for all FastJava modules.

---

## Performance

FastCore is designed to be the fastest way to bridge Java and Native code during the initialization phase.

| Operation | FastCore | Standard System.load | Benefit |
|-----------|---------|----------------------|---------|
| Library Extraction | < 5 ms | N/A (Manual) | **Automated** |
| JNI Mapping | Instant | Instant | **Stable** |

---

## Installation

### Option 1: Maven (Recommended)
Add the JitPack repository and the dependencies to your `pom.xml`:

```xml
<repositories>
    <repository>
        <id>jitpack.io</id>
        <url>https://jitpack.io</url>
    </repository>
</repositories>

<dependencies>
    <!-- FastCore Library -->
    <dependency>
        <groupId>com.github.andrestubbe</groupId>
        <artifactId>fastcore</artifactId>
        <version>v0.1.0</version>
    </dependency>
</dependencies>
```

### Option 2: Gradle (via JitPack)
```groovy
repositories {
    maven { url 'https://jitpack.io' }
}

dependencies {
    implementation 'com.github.andrestubbe:fastcore:v0.1.0'
}
```

### Option 3: Direct Download (No Build Tool)
Download the latest JARs directly to add them to your classpath:

1. 📦 **[fastcore-v0.1.0.jar](https://github.com/andrestubbe/FastCore/releases/download/v0.1.0/fastcore-v0.1.0.jar)** (The Core Library)


## API Reference

| Method | Description |
|--------|-------------|
| `void loadLibrary(String name)` | Extracts and loads a native library from the JAR resources. |

---

## Platform Support

| Platform | Status |
|----------|--------|
| Windows 10/11 (x64) | âœ… Fully Supported |
| Linux | ðŸš§ Planned |
| macOS | ðŸš§ Planned |

---

## Building from Source

For detailed instructions on compiling the native parts and building the Maven artifact, see [COMPILE.md](COMPILE.md).

---

## License
MIT License â€” See [LICENSE](LICENSE) file for details.

---

## Related Projects
- [FastFileIndex](https://github.com/andrestubbe/FastFileIndex) â€” Ultra-fast filesystem scanner
- [FastTheme](https://github.com/andrestubbe/FastTheme) â€” High-performance native window styling
- [FastThumb](https://github.com/andrestubbe/FastThumb) â€” Native Shell Image Engine

---
**Made with âš¡ by Andre Stubbe**

<!-- 
SEO Keywords: java, jni, native, fastjava, library loader, performance
-->
