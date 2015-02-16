/*
 * Copyright (c) 2008-2013 Haulmont. All rights reserved.
 * Use is subject to license terms, see http://www.cuba-platform.com/license for details.
 */
package com.haulmont.javacl;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

class SourceProvider {
    public static final String JAVA_EXT = ".java";
    private String rootDir;

    public SourceProvider(String rootDir) {
        this.rootDir = rootDir;
    }

    public String getSourceString(String name) throws IOException {
        File srcFile = getSourceFile(name);
        if (!srcFile.exists()) {
            throw new FileNotFoundException("Java source for " + name + " not found");
        }
        return FileUtils.readFileToString(srcFile);
    }

    public File getSourceFile(String name) {
        String path = name.replace(".", "/");
        File srcFile = new File(rootDir, path + JAVA_EXT);
        return srcFile;
    }

    public boolean sourceExistsInFileSystem(String className) {
        String path = className.replace('.', '/');
        File file = new File(rootDir, path + JAVA_EXT);
        return file.exists();
    }

    public boolean directoryExistsInFileSystem(String packageName) {
        String path = packageName.replace('.', '/');
        File dir = new File(rootDir, path);
        return dir.exists();
    }

    public List<String> getAllClassesFromPackage(String packageName) {
        String path = packageName.replace(".", "/");
        File srcDir = new File(rootDir, path);
        String[] fileNames = srcDir.list();
        List<String> classNames = new ArrayList<>();
        for (String fileName : fileNames) {
            if (fileName.endsWith(JAVA_EXT)) {
                classNames.add(packageName + "." + fileName.replace(JAVA_EXT, ""));
            }
        }
        return classNames;
    }
}
