package com.dcrunch.myapp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import software.amazon.awssdk.awscore.exception.AwsServiceException;
import software.amazon.awssdk.core.exception.SdkClientException;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;

public class App {

	private static final Logger LOGGER = LoggerFactory.getLogger(App.class);

	public static void main(String[] args) throws IOException {
		LOGGER.info("Starting...");
		Region region = Region.US_EAST_2;
		LOGGER.info("Building S3 Client for {}", Region.US_EAST_2.toString());
		S3Client s3 = S3Client.builder().region(region).build();
		String line;
		String bucket = "dshailender-role-test-bucket";
		String key = "file.txt";
		LOGGER.info("Attempting to get {}, from bucket: {}", key, bucket);
		try {
			GetObjectRequest getObjectRequest = GetObjectRequest.builder().bucket(bucket).key(key).build();
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(s3.getObject(getObjectRequest)));
			while ((line = bufferedReader.readLine()) != null) {
				LOGGER.info("content from {}, {}", key, line);
			}
		} catch (AwsServiceException | SdkClientException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			LOGGER.info("Closing the connection to {S3}");
			s3.close();
			LOGGER.info("Connection closed");
			LOGGER.info("Exiting...");
		}

	}
}
