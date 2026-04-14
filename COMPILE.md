# Building FastCore

This document is for developers who want to build FastCore from source.

## Prerequisites

- JDK 25+
- Maven 3.9+

## Build Commands

```bash
# Compile
mvn clean compile

# Build JAR
mvn clean package

# Skip tests (fast build)
mvn clean package -DskipTests

# Run platform info
mvn compile exec:java
```

## Release Process

Releases are automated via GitHub Actions.

### Creating a Release

```bash
# 1. Ensure version is updated in pom.xml
# 2. Commit all changes
git add .
git commit -m "Prepare v1.0.0"

# 3. Create and push tag
git tag -a v1.0.0 -m "FastCore 1.0.0"
git push origin v1.0.0
```

GitHub Actions will automatically:
- Build the JAR with JDK 25
- Create a GitHub Release
- Upload the JAR as an asset

### After Release

The release will be available at:
```
https://github.com/andrestubbe/FastCore/releases
```

JitPack will automatically make it available as:
```xml
<version>v1.0.0</version>
```

## GitHub Actions

The release workflow is defined in `.github/workflows/release.yml`:

- Triggers on tags starting with `v`
- Runs on Ubuntu with JDK 25
- Builds JAR and uploads to GitHub Releases
