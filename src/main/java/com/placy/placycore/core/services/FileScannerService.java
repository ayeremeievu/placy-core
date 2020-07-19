package com.placy.placycore.core.services;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.io.filefilter.FileFilterUtils;
import org.apache.commons.io.filefilter.TrueFileFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author ayeremeiev@netconomy.net
 */
public class FileScannerService {
    private static final Logger LOG = LoggerFactory.getLogger(FileScannerService.class);

    private ClassLoader classLoader = this.getClass().getClassLoader();

    @Autowired
    private ResourceLoader resourceLoader;

//    public List<File> getAllResourceFilesInDirectoriesNested(String resourceFolderPath, String suffix) {
//        LOG.info(classLoader == null ? "class loader is null" : "class loader is not null");
//        LOG.info("getCurrentResourcesBasePath : {} ", getCurrentResourcesBasePath());
//        LOG.info("Resource folder path : {} ", resourceFolderPath);
//
//        Resource resourceFolder = resourceLoader.getResource("classpath:" + resourceFolderPath);
//
//        if(resourceFolderPath == null) {
//            throw new IllegalArgumentException(
//                String.format("Could not find resource folder : '%s' ", resourceFolderPath)
//            );
//        }
//
//        File file = null;
//        try {
//            file = resourceFolder.getFile();
//        } catch (IOException e) {
//            throw new IllegalArgumentException(e);
//        }
//
//        return getAllFilesInDirectoriesNested(file, suffix);
//    }

    public List<Resource> getAllResourceFilesInDirectoriesNested(String resourceFolderPath, String suffix) {
        LOG.info(classLoader == null ? "class loader is null" : "class loader is not null");
        LOG.info("getCurrentResourcesBasePath : {} ", getCurrentResourcesBasePath());
        LOG.info("Resource folder path : {} ", resourceFolderPath);

        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();

        List<Resource> resources = new ArrayList<>();
        try {
            resources.addAll(
                Arrays.asList(
                    resolver.getResources(
                        String.format("classpath*:%s/**/*%s", resourceFolderPath, suffix)
                    )
                )
            );
        } catch (IOException ex) {
            throw new IllegalArgumentException("error happend during resourcee scanning : ", ex);
        }

        return resources;
    }

    private URI getCurrentResourcesBasePath() {
        LOG.info(System.getProperty("java.class.path"));
        try {
            return FileScannerService.class.getProtectionDomain().getCodeSource().getLocation()
                                         .toURI();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        return null;
    }

    public List<File> getAllFilesInDirectoriesNested(File file, String suffix) {
        LOG.info("Is directory : " + file.isDirectory());
        LOG.info("Is file : " + file.isFile());
        LOG.info("Files " + Arrays.toString(file.listFiles()));

        return new ArrayList<>(
            FileUtils.listFiles(file, FileFilterUtils.suffixFileFilter(suffix), TrueFileFilter.INSTANCE)
        );
    }

    public List<String> getAllResourcesFilesPaths(String classesFilePath, List<File> files) {
        return files.stream()
             .map(file -> getResourceFilePath(classesFilePath, file))
             .collect(Collectors.toList());
    }

    public String readFileCompletely(File file) {
        try {
            return IOUtils.toString(new FileReader(file));
        } catch (IOException ex) {
            throw new IllegalArgumentException(
                String.format("File %s was not found or another exception happened", file), ex
            );
        }
    }

    public String readFileCompletely(InputStream inputStream) {
        try {
            return IOUtils.toString(inputStream);
        } catch (IOException ex) {
            throw new IllegalArgumentException("Exception occurred during reaad of a stream", ex);
        }
    }

    public String getFileData(Resource resource) {
        try {
            return readFileCompletely(resource.getInputStream());
        } catch (IOException ex) {
            throw new IllegalArgumentException("Error happened by resource stream retrieval at resource " + resource, ex);
        }
    }

    public String getChecksum(File file) {
        try (InputStream is = new FileInputStream(file)) {
            return DigestUtils.md5Hex(is);
        } catch (FileNotFoundException e) {
            throw new IllegalArgumentException(
                String.format("File %s was not found", file)
            );
        } catch (IOException e) {
            throw new IllegalArgumentException(
                String.format("Error occurred during processing of %s", file)
            );
        }
    }

    public String getChecksum(String value) {
        return DigestUtils.md5Hex(value);
    }

    private String getResourceFilePath(String classesFilePath, File file) {
        String canonicalPath;

        try {
            canonicalPath = file.getCanonicalPath();
        } catch (IOException ex) {
            throw new IllegalStateException("Exception occured during caanoncal path getting.", ex);
        }

        return prependRootSlash(classesFilePath, canonicalPath);
    }

    private String prependRootSlash(String classesFilePath, String canonicalPath) {
        return File.separator + trimResourceFilePath(canonicalPath, classesFilePath);
    }

    private String trimResourceFilePath(String filepath, String classesFilePath) {
        if(!filepath.startsWith(classesFilePath)) {
            throw new IllegalArgumentException(
                String.format("The file %s is not in current class path classes base path %s", filepath, classesFilePath)
            );
        }

        return filepath.substring(classesFilePath.length());
    }
}
