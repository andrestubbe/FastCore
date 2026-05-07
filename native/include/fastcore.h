#ifndef FASTCORE_H
#define FASTCORE_H

#include <jni.h>
#include <cstdint>
#include <string>
#include <vector>

// FileIndex handle
struct FileIndexHandle {
    uint64_t id;
    void* data;
    int64_t entryCount;
};

// SearchEngine handle
struct SearchEngineHandle {
    uint64_t id;
    void* data;
    FileIndexHandle* index;
};

// WatchService handle
struct WatchServiceHandle {
    uint64_t id;
    void* data;
    JavaVM* jvm;
    jobject callback;
};

// FileIndex JNI functions
extern "C" {
    JNIEXPORT jlong JNICALL Java_fastfileindex_FileIndex_build(
        JNIEnv* env, jclass clazz, jobjectArray roots, jobject options);
    JNIEXPORT jlong JNICALL Java_fastfileindex_FileIndex_getEntryCount(
        JNIEnv* env, jobject obj);
    JNIEXPORT jobject JNICALL Java_fastfileindex_FileIndex_get(
        JNIEnv* env, jobject obj, jlong id);
    JNIEXPORT void JNICALL Java_fastfileindex_FileIndex_close(
        JNIEnv* env, jobject obj);
}

// SearchEngine JNI functions
extern "C" {
    JNIEXPORT jobject JNICALL Java_fastfilesearch_FastFileSearch_fromIndex(
        JNIEnv* env, jclass clazz, jobject index, jobject options);
    JNIEXPORT void JNICALL Java_fastfilesearch_FastFileSearch_close(
        JNIEnv* env, jobject obj);
    JNIEXPORT jobjectArray JNICALL Java_fastfilesearch_FastFileSearch_prefix(
        JNIEnv* env, jobject obj, jobject query, jobject options);
    JNIEXPORT jobjectArray JNICALL Java_fastfilesearch_FastFileSearch_fuzzy(
        JNIEnv* env, jobject obj, jobject query, jobject options);
    JNIEXPORT jobjectArray JNICALL Java_fastfilesearch_FastFileSearch_exact(
        JNIEnv* env, jobject obj, jobject query, jobject options);
    JNIEXPORT void JNICALL Java_fastfilesearch_FastFileSearch_applyUpdate(
        JNIEnv* env, jobject obj, jobject update);
}

// WatchService JNI functions
extern "C" {
    JNIEXPORT jobject JNICALL Java_fastfilewatch_FastFileWatch_start(
        JNIEnv* env, jclass clazz, jobjectArray roots, jobject callback);
    JNIEXPORT void JNICALL Java_fastfilewatch_FastFileWatch_stop(
        JNIEnv* env, jobject obj);
    JNIEXPORT jboolean JNICALL Java_fastfilewatch_FastFileWatch_isRunning(
        JNIEnv* env, jobject obj);
}

#endif // FASTCORE_H
