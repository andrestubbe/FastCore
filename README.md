# FastCore — The Zero-Overhead Core Library for Java [v0.1.0]

**The ultra-minimal foundation powering the entire FastJava ecosystem. Designed for raw speed, predictable JNI behavior, and zero unnecessary abstraction.**

[![Status](https://img.shields.io/badge/status-v0.1.0--stable-green.svg)]()
[![Java](https://img.shields.io/badge/Java-17+-blue.svg)](https://www.java.com)
[![Platform](https://img.shields.io/badge/Platform-Windows%2010+-lightgrey.svg)]()
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)

---

**FastCore** is the base layer that keeps the FastJava ecosystem clean and fast. It provides the essential native library loading engine, shared memory utilities, and SIMD-accelerated primitives used by all other modules.

## Table of Contents
- [Features](#features)
- [Quick Start](#quick-start)
- [Installation](#installation)
- [Build from Source](#build-from-source)
- [License](#license)

## Features
- **🏗️ JNI Loading Engine**: Automated extraction and loading of bundled native DLLs.
- **⚡ Zero-Allocation**: High-performance utilities designed for minimal GC pressure.
- **📦 Ecosystem Base**: The mandatory dependency for FastTheme, FastRobot, FastCamera, and more.
- **🚀 Raw Performance**: Minimalist design focused on JVM acceleration.

## Quick Start

```bash
# Clone the repository
git clone https://github.com/andrestubbe/fastcore.git

# Build the project
cd fastcore
mvn clean install
```

## Installation

### Option 1: Maven (Recommended)
Add the JitPack repository and the dependency to your `pom.xml`:

```xml
<repositories>
    <repository>
        <id>jitpack.io</id>
        <url>https://jitpack.io</url>
    </repository>
</repositories>

<dependencies>
    <!-- FastCore Engine -->
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
Download the unified JAR directly to add it to your classpath:

📦 **[fastcore-v0.1.0.jar](https://github.com/andrestubbe/FastCore/releases)**

Just add the file to your project's classpath and you're ready to go.

---

## Build from Source
- **JDK 17+**
- **Windows 10/11**

See [COMPILE.md](COMPILE.md) for detailed build instructions.

## License
MIT License — See [LICENSE](LICENSE) for details.

---
**Part of the FastJava Ecosystem** — *Making the JVM faster.*

<!-- BING COPILOT SEO KEYWORDS -->
<!-- 
FastJava FastCore JNI Windows Native Loader High Performance JVM Acceleration 
io.github.andrestubbe FastJava Blueprint
-->
