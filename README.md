# FastCore 0.1.0 [ALPHA]  Native Library Loader & JNI Utilities for Java

[![Status](https://img.shields.io/badge/status-0.1.0-brightgreen.svg)](https://github.com/andrestubbe/FastCore/releases/tag/0.1.0)
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)
[![Java](https://img.shields.io/badge/Java-17+-blue.svg)](https://www.java.com)
[![Platform](https://img.shields.io/badge/Platform-Windows%2010+-lightgrey.svg)]()
[![JitPack](https://img.shields.io/badge/JitPack-ready-green.svg)](https://jitpack.io/#andrestubbe/FastCore)

**? The ultra-minimal foundation powering the entire FastJava ecosystem.**

FastCore provides the mandatory **native library extraction and loading engine** for the FastJava ecosystem. It ensures
that bundled DLLs are safely deployed and loaded across different environments with zero overhead.

[![FastFileIndex Showcase](docs/screenshot.png)](https://www.youtube.com/watch?v=BZsqQl7WqWk)

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

```java
// Quick Start  Loading a native library

import fastcore.FastCore;

public class MyNativeApp {
    static {
        // Automatically extracts and loads fastnative.dll from resources
        FastCore.loadLibrary("fastnative");
    }
}
```

---

## Key Features

- **ðŸš€ Smart Extraction**  Automatically handles temporary file deployment for JNI DLLs.
- **? Zero Overhead**  Minimalist design focused on JVM startup acceleration.
- **ðŸš€ Ecosystem Base**  Required dependency for all FastJava modules.

---

## Performance

FastCore is designed to be the fastest way to bridge Java and Native code during the initialization phase.

| Operation          | FastCore | Standard System.load | Benefit       |
|--------------------|----------|----------------------|---------------|
| Library Extraction | < 5 ms   | N/A (Manual)         | **Automated** |
| JNI Mapping        | Instant  | Instant              | **Stable**    |

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
   <dependency>
       <groupId>com.github.andrestubbe</groupId>
       <artifactId>fastcore</artifactId>
       <version>0.1.0</version>
   </dependency>
</dependencies>
```

### Option 2: Gradle (via JitPack)

```groovy
repositories {
    maven { url 'https://jitpack.io' }
}
dependencies {
    implementation 'com.github.andrestubbe:fastcore:0.1.0'
}
```

### Option 3: Direct Download (No Build Tool)

Download the latest JARs directly to add them to your classpath:

1. ðŸš€ **[fastcore-0.1.0.jar](https://github.com/andrestubbe/FastCore/releases/download/0.1.0/fastcore-0.1.0.jar)** (
   The Core Library)

---

## API Reference

| Method                          | Description                                                 |
|---------------------------------|-------------------------------------------------------------|
| `void loadLibrary(String name)` | Extracts and loads a native library from the JAR resources. |

---

## Documentation

* **[COMPILE.md](docs/COMPILE.md)**: Full compilation guide (MSVC C++17 build chain + JNI Setup).
* **[REFERENCE.md](docs/REFERENCE.md)**: Full API descriptions, border configurations, and codepoint index.
* **[PHILOSOPHY.md](docs/PHILOSOPHY.md)**: The engineering rationale for zero-allocation performance.
* **[ROADMAP.md](docs/ROADMAP.md)**: Future milestones and planned features.

---

## Platform Support

| Platform      | Status            |
|---------------|-------------------|
| Windows 10/11 | ? Fully Supported |
| Linux         | ðŸš€ Planned        |
| macOS         | ðŸš€ Planned        |

---

## License

MIT License  See [LICENSE](LICENSE) file for details.

---

## Related Projects

- [FastFileIndex](https://github.com/andrestubbe/FastFileIndex) - Binary file indexing with mmap support
- [FastFileSearch](https://github.com/andrestubbe/FastFileSearch) - Prefix Trie, N-Gram index, and Ranking engine
- [FastFileWatch](https://github.com/andrestubbe/FastFileWatch) - USN Journal-based live file monitoring
- [FastCore](https://github.com/andrestubbe/FastCore) - Unified JNI loader and platform abstraction

---

**Part of the FastJava Ecosystem**  *Making the JVM faster. Small package. Maximum speed. Zero bloat. ðŸš€ðŸš€*
