cmake_minimum_required(VERSION 3.15)
project(FastCore CXX)

set(CMAKE_CXX_STANDARD 17)
set(CMAKE_CXX_STANDARD_REQUIRED ON)

# Find JNI
find_package(JNI REQUIRED)
include_directories(${JNI_INCLUDE_DIRS})

# Source files
set(SOURCES
    src/fastcore.cpp
    src/index.cpp
    src/search.cpp
    src/watch.cpp
)

# Create shared library (DLL)
add_library(fastcore SHARED ${SOURCES})

# Set output name
set_target_properties(fastcore PROPERTIES
    PREFIX ""
    OUTPUT_NAME "fastcore"
)

# Link JNI
target_link_libraries(fastcore ${JNI_LIBRARIES})

# Windows specific settings
if(WIN32)
    target_compile_definitions(fastcore PRIVATE _CRT_SECURE_NO_WARNINGS)
endif()
