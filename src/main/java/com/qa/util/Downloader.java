package com.qa.util;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.params.ClientPNames;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.BasicHttpContext;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

public class Downloader {
    private static Logger LOG = LoggerFactory.getLogger(Downloader.class);

    public static String downloader(WebElement element, String attribute) throws URISyntaxException, IOException {
        boolean followRedirects = true;
        int statusOfLastDownloadAttempt;
        String fileToDownloadLocation = element.getAttribute(attribute);
        if (fileToDownloadLocation.trim().equals("")) throw new NullPointerException("The specified link is empty");
        URL fileToDownload = new URL(fileToDownloadLocation);
        File downloadedFile = new File(System.getProperty("user.dir") + "/downloads/" + fileToDownload.getFile().replaceFirst("[/\\\\]", ""));
        if (!downloadedFile.canWrite()) downloadedFile.setWritable(true);
        HttpClient client = new DefaultHttpClient();
        BasicHttpContext localContext = new BasicHttpContext();
        HttpGet httpget = new HttpGet(fileToDownload.toURI());
        HttpParams httpRequestParameters = httpget.getParams();
        httpRequestParameters.setParameter(ClientPNames.HANDLE_REDIRECTS, followRedirects);
        httpget.setParams(httpRequestParameters);
        LOG.info("Sending GET request for: " + httpget.getURI());
        HttpResponse response = client.execute(httpget, localContext);
        statusOfLastDownloadAttempt = response.getStatusLine().getStatusCode();
        LOG.info("HTTP request status: " + statusOfLastDownloadAttempt);
        LOG.info("Downloading file: " + downloadedFile.getName());
        org.apache.commons.io.FileUtils.copyInputStreamToFile(response.getEntity().getContent(), downloadedFile);
        response.getEntity().getContent().close();
        String downloadedFileAbsolutePath = downloadedFile.getAbsolutePath();
        LOG.info("File downloaded to " + downloadedFileAbsolutePath + "'");
        return downloadedFileAbsolutePath;
    }
}
