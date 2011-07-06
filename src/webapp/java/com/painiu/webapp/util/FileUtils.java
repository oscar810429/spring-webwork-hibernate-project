/*
 * @(#)FileUtils.java Dec 13, 2009
 * 
 * Copyright 2008 Painiu. All rights reserved.
 */
package com.painiu.webapp.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <p>
 * <a href="FileUtils.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author Zhang Songfu
 * @version $Id: FileUtils.java 36 2010-06-01 02:14:52Z zhangsf $
 */
public class FileUtils {
	
	private static final Log log = LogFactory.getLog(FileUtils.class);
	
    public static void copyDirectory(File srcDir, File destDir) throws IOException {
        copyDirectory(srcDir, destDir, false);
    }

    public static void copyDirectory(File srcDir, File destDir, boolean overwrite) throws IOException {
        File files[] = srcDir.listFiles();
        if (!destDir.exists())
            destDir.mkdirs();
        else
            log.debug(destDir.getAbsolutePath() + " already exists");
        for (int i = 0; i < files.length; i++) {
            File file = files[i];
            File dest = new File(destDir, file.getName());
            if (file.isFile())
                copyFile(new FileInputStream(file), dest, overwrite);
            else
                copyDirectory(file, dest, overwrite);
        }
    }

    public static void copyFile(File srcFile, File destFile) throws IOException {
        InputStream input = new FileInputStream(srcFile);
        copyFile(input, destFile);
    }

    public static void copyFile(InputStream srcStream, File destFile) throws IOException {
        copyFile(srcStream, destFile, false);
    }

    public static void copyFile(InputStream srcStream, File destFile, boolean overwrite) throws IOException {
        File parentFile = destFile.getParentFile();
        if (!parentFile.isDirectory()) {
            parentFile.mkdirs();
        }
        if (destFile.exists()) {
            if (overwrite) {
                log.debug("Overwriting file at: " + destFile.getAbsolutePath());
                writeStreamToFile(srcStream, destFile);
            } else {
                log.warn(destFile.getAbsolutePath() + " already exists");
            }
        } else {
            destFile.createNewFile();
            writeStreamToFile(srcStream, destFile);
        }
    }
    
    public static void writeStreamToFile(InputStream srcStream, File destFile) throws IOException {
        OutputStream output;
        InputStream input = null;
        output = null;
        try {
            input = new BufferedInputStream(srcStream);
            output = new BufferedOutputStream(new FileOutputStream(destFile));
            int ch;
            while ((ch = input.read()) != -1) output.write(ch);
        } catch (IOException e) {
            log.error("Error writing stream to file: " + destFile.getAbsolutePath());
            throw e;
        } finally {
            input.close();
        }
        output.close();
    }
    
    public static boolean deleteDir(File dir) {
        if (dir == null)
            return false;
        File candir;
        try {
            candir = dir.getCanonicalFile();
        } catch (IOException e) {
            return false;
        }
        if (!candir.equals(dir.getAbsoluteFile()))
            return false;
        File files[] = candir.listFiles();
        if (files != null) {
            for (int i = 0; i < files.length; i++) {
                File file = files[i];
                boolean deleted = !file.delete();
                if (deleted && file.isDirectory())
                    deleteDir(file);
            }

        }
        return dir.delete();
    }
}
