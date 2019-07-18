package com.qa.util;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.*;

public class Amazon {
    private static final String DEFAULT_BUCKET_NAME = "automation-reports.url.com";
    private static final String DEFAULT_SUFFIX = "/";
    private static final String DEFAULT_ACCESS_KEY_ID = "DEFAULT_ACCESS_KEY_ID";
    private static final String DEFAULT_SECRET_ACCESS_KEY = "DEFAULT_SECRET_ACCESS_KEY";
    private static Logger LOG = LoggerFactory.getLogger(Amazon.class);
    private static Parameters testParams = new Parameters();
    private String SUFFIX;
    private String bucketName;
    private String accessKeyId;
    private String secretAccessKey;

    public static S3ClientBuilder builder() {
        return new S3ClientBuilder();
    }


    public static class S3ClientBuilder {
        private String bucketName = DEFAULT_BUCKET_NAME;
        private String SUFFIX = DEFAULT_SUFFIX;
        private String accessKeyId = DEFAULT_ACCESS_KEY_ID;
        private String secretAccessKey = DEFAULT_SECRET_ACCESS_KEY;

        public Amazon build() {
            Amazon s3Client = new Amazon();
            s3Client.bucketName = bucketName;
            s3Client.SUFFIX = SUFFIX;
            s3Client.accessKeyId = accessKeyId;
            s3Client.secretAccessKey = secretAccessKey;
            return s3Client;
        }

    }

    private AmazonS3 s3Client() {
        BasicAWSCredentials credentials = new BasicAWSCredentials(this.accessKeyId, this.secretAccessKey);
        return AmazonS3ClientBuilder.standard().withCredentials(new AWSStaticCredentialsProvider(credentials)).withRegion(Regions.EU_WEST_1).build();
    }

    private void uploadReportFile(AmazonS3 client, String fileNameOnS3, File localFile) {
        LOG.info("Uploading the file=" + localFile + "  and renaming it to=" + fileNameOnS3);
        client.putObject(new PutObjectRequest(bucketName, fileNameOnS3, localFile));
    }

    private static String generateReportFileName() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
        String fileName = "";
        fileName += System.getProperty("env").toUpperCase() + "-";
        fileName += System.getProperty("browser").toUpperCase() + "-";
        fileName += System.getProperty("build") != null ? "API=" + System.getProperty("build") + "-" : "";
        fileName += sdf.format(new Date());
        fileName += ".html";
        LOG.info("report fileName=" + fileName);
        return fileName;
    }

    private static String getReportFilePath() {
        return new File("target/cucumber-reports/advanced-reports/cucumber-html-reports/report-tag_investor-regression.html").getAbsolutePath();
    }

    private static List<File> getEmbeddedFilesToUpload() {
        File folder = new File("target/cucumber-reports/advanced-reports/cucumber-html-reports/embeddings");
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        List<File> files = new ArrayList<>(Arrays.asList(Objects.requireNonNull(folder.listFiles())));
        files.removeIf(next -> !sdf.format(next.lastModified()).equals(sdf.format(new Date())));
        return files;
    }

    public static void main(String[] args) {
        System.setProperty("env", "QA1");
        System.setProperty("browser", "Chrome-Headless");
        System.setProperty("build","feature.INV-2282.9.qa");
        Amazon amazon = Amazon.builder().build();
        amazon.uploadReportFile(amazon.s3Client(), generateReportFileName(), new File(getReportFilePath()));
        List<File> files = getEmbeddedFilesToUpload();
        for (File file : files) {
            amazon.uploadReportFile(amazon.s3Client(), "embeddings/" + file.getName(), file);
        }
    }
}
