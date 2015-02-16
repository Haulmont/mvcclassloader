package com.haulmont.javacl;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.util.HashSet;

class CompilationScope {
    final HashSet<String> compilationNeeded = new HashSet<String>();

    private final HashSet<String> processed = new HashSet<String>();
    private final JavaClassLoader javaClassLoader;
    private final String rootClassName;
    private final SourceProvider sourceProvider;

    public CompilationScope(JavaClassLoader javaClassLoader, String rootClassName) {
        this.javaClassLoader = javaClassLoader;
        this.sourceProvider =  javaClassLoader.sourceProvider;
        this.rootClassName = rootClassName;
    }

    public boolean compilationNeeded() throws ClassNotFoundException {
        collectInformation(rootClassName);
        return !CollectionUtils.isEmpty(compilationNeeded);
    }

    private void collectInformation(String rootClassName) throws ClassNotFoundException {
        if (processed.contains(rootClassName)) {
            return;
        }

        File srcFile = sourceProvider.getSourceFile(rootClassName);
        processed.add(rootClassName);

        TimestampClass timeStampClazz = javaClassLoader.getTimestampClass(rootClassName);
        if (timeStampClazz != null) {
            if (FileUtils.isFileNewer(srcFile, timeStampClazz.timestamp)) {
                compilationNeeded.add(rootClassName);
            } else if (!srcFile.exists()) {
                throw new ClassNotFoundException("Class " + rootClassName + " not found. No sources found in file system.");
            }

            for (String dependencyName : timeStampClazz.dependencies) {
                collectInformation(dependencyName);
            }
        } else {
            compilationNeeded.add(rootClassName);
        }
    }
}
