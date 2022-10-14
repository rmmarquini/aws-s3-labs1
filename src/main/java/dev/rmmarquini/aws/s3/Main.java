package dev.rmmarquini.aws.s3;

import dev.rmmarquini.aws.s3.utils.ConfigProperties;
import dev.rmmarquini.aws.s3.utils.LoadLogger;
import org.apache.logging.log4j.Logger;

import java.util.Properties;

public class Main {

    private static final Logger LOGGER = LoadLogger.getLogger(Main.class.getCanonicalName());

    public static void main(String[] args) {

        LOGGER.info(new String(new char[50]).replace("/0", "-"));
        LOGGER.info("----- AWS S3 Laboratory - START -----");

        LOGGER.info("Load props...");
        Properties prop = new ConfigProperties().load();

        /*LOGGER.info("Load credentials provider...");
        ProfileCredentialsProvider credentialsProvider = ProfileCredentialsProvider.create();

        LOGGER.info("Instantiate S3 Client...");
        S3Client s3Client = S3Client.builder()
                .region(Region.US_EAST_1)
                .credentialsProvider(credentialsProvider)
                .build();

        LOGGER.info("Instantiate a CustomBucket...");
        CustomBucket bucket = new CustomBucket(s3Client, prop.getProperty("bucketName"));

        LOGGER.info("Create a bucket...");
        bucket.create();

        LOGGER.info("Put some object into bucket...");
        String objectPutted = bucket.put(prop.getProperty("objectKey"), prop.getProperty("objectPath"));
        LOGGER.info("Object putted: " + objectPutted);*/


        LOGGER.info("----- AWS S3 Laboratory - END -----");
        LOGGER.info(new String(new char[50]).replace("/0", "-"));

    }

}
