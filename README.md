# FastCore — Unified JNI Loader for FastJava

> **Cross-platform native library loading** for Java 25+ — Windows, Linux, macOS

[![Java](https://img.shields.io/badge/Java-25+-blue.svg)](https://www.java.com)
[![Maven](https://img.shields.io/badge/Maven-3.9+-orange.svg)](https://maven.apache.org)
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)
[![Maven Central](https://img.shields.io/badge/Maven%20Central-planned-green.svg)](https://search.maven.org)

---

## Quick Start

```bash
# Clone repository
git clone https://github.com/andrestubbe/FastCore.git
cd FastCore

# Build
mvn clean package

# Run platform info
mvn compile exec:java
```

## What is FastCore?

FastCore is the foundation of the FastJava ecosystem. It provides unified native library loading across Windows, Linux, and macOS — eliminating boilerplate code for JNI-based modules.

**Key Features:**
- **Cross-platform support** — Windows (.dll), Linux (.so), macOS (.dylib)
- **Automatic extraction** — Native libraries from JAR to temp
- **Smart loading** — System path first, fallback to extracted
- **Resource cleanup** — Automatic temp file removal
- **Zero dependencies** — Pure Java, no external libraries

---

## Installation

### Maven Central (Recommended)

```xml
<dependency>
    <groupId>io.github.andrestubbe</groupId>
    <artifactId>fastcore</artifactId>
    <version>1.0.0</version>
</dependency>
```

### JitPack (Alternative)

Add repository:
```xml
<repositories>
    <repository>
        <id>jitpack.io</id>
        <url>https://jitpack.io</url>
    </repository>
</repositories>
```

Dependency:
```xml
<dependency>
    <groupId>com.github.andrestubbe</groupId>
    <artifactId>fastcore</artifactId>
    <version>1.0.0</version>
</dependency>
```

### Gradle

```groovy
dependencies {
    implementation 'io.github.andrestubbe:fastcore:1.0.0'
}
```

---

## Project Structure

```
fastcore/
├── .github/                    # GitHub workflows
│   └── workflows/
│       └── build.yml           # CI build & test
├── docs/                       # Documentation assets
├── examples/                   # ⭐ Usage examples
│   └── 00-basic-usage/         # Platform detection demo
│       ├── pom.xml             # Own Maven config
│       └── src/main/java/...   # Example code
├── src/
│   └── main/java/fastcore/     # Main library code
│       ├── FastCore.java       # Main API
│       ├── LibraryLoader.java  # Native library loader
│       └── Platform.java       # OS/Arch detection
├── pom.xml                     # Maven configuration
├── LICENSE                     # MIT License
├── .gitignore                  # Git ignore rules
└── README.md                   # Main documentation
```

**Optional folders** (add when needed):
- `src/test/java/` - JUnit tests (Maven recognizes automatically)

**Optional markdown docs** (root level, as needed):
- `BENCHMARK.md` - Performance results
- `TODO.md` - Development roadmap
- `DEPLOYMENT.md` - Release guide
- `PROMOTION.md` - Social media content

**Why `examples/` on root level?**
- Not part of the library → separate mini-projects
- Not tests → tutorials for users
- Each example has its own `pom.xml` → runnable standalone
- Copy-paste friendly → users can use as starter template

---

## Building from Source

### Prerequisites
- JDK 25+
- Maven 3.9+

### Build Commands

```bash
# Standard Maven project (pure Java, no native build needed)
mvn clean package

# Skip tests (fast build)
mvn clean package -DskipTests

# Show platform info
mvn compile exec:java

# Run example (separate mini-project)
cd examples/00-basic-usage
mvn compile exec:java
```

### Running Examples

All runnable code (demos, examples) is in `examples/` - **never in `src/main/java`**.

The `src/main/java` folder contains **only API/library code** that users import and use.

```bash
# Basic usage example - Platform detection demo
cd examples/00-basic-usage
mvn compile exec:java

# Create your own example
cp -r examples/00-basic-usage examples/10-my-advanced-example
# Edit pom.xml and Java files
```

**Naming convention for examples:**
- `00-*` - Basic usage examples
- `10-*` - Advanced usage
- `20-*` - Integration examples (loading other libraries)

---

## Release Checklist

- [ ] Version updated in `pom.xml`
- [ ] `CHANGELOG.md` updated
- [ ] All tests passing: `mvn clean test`
- [ ] Git tag created: `git tag -a v1.0.0 -m "Release 1.0.0"`
- [ ] GitHub Release created
- [ ] Maven Central deployed: `mvn clean deploy -P release`

---

## License

MIT License — See [LICENSE](LICENSE) for details.

---

---

## Usage Example

```java
import fastcore.FastCore;

public class MyApp {
    public static void main(String[] args) {
        // Load a native library (e.g., fastrobot.dll, libfastrobot.so, libfastrobot.dylib)
        FastCore.loadLibrary("fastrobot");
        
        // Check platform
        if (FastCore.isWindows()) {
            System.out.println("Running on Windows");
        }
        
        // Get platform info
        System.out.println(FastCore.getPlatformInfo());
        // Output: OS: WINDOWS (windows 11), Arch: X86_64, Java: 25.0.1
    }
}
```

---

**Part of the FastJava Ecosystem** — *Making the JVM faster.*
