/*
 * Copyright 2009 the original author or authors.
 */

package cz.silesnet.sis.util;

import java.io.File;
import java.io.IOException;

import org.joda.time.DateTime;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;

/**
 * Resource factory creating new files within work directory prefixed with the
 * same time stamp (this instance creation time).
 * 
 * @author rsi
 * 
 */
public class TimestampWorkdirResourceFactory implements ResourceFactory {

    private final String stamp = (new DateTime()).toString("yyyyMMdd_HHmmss");
    private String filePrefix;

    public TimestampWorkdirResourceFactory() {
        setWorkDir(new FileSystemResource("."));
    }

    public void setWorkDir(Resource workDir) {
        if (!workDir.exists()) {
            try {
                workDir.getFile().mkdirs();
            } catch (IOException e) {
                throw new IllegalArgumentException(e);
            }
        }
        String path;
        try {
            path = workDir.getFile().getCanonicalPath();
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
        filePrefix = path + File.separator + stamp + "_";
    }

    public Resource createInstance(String fileSuffix) {
        return new FileSystemResource(filePrefix + fileSuffix);
    }
}
