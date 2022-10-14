package dev.rmmarquini.aws.s3.actions;

import dev.rmmarquini.aws.s3.utils.FileUtils;
import dev.rmmarquini.aws.s3.utils.LoadLogger;
import org.apache.logging.log4j.Logger;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.core.waiters.WaiterResponse;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;
import software.amazon.awssdk.services.s3.waiters.S3Waiter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CustomBucket {

    private static final Logger LOGGER = LoadLogger.getLogger(CustomBucket.class.getCanonicalName());

    S3Client s3Client;
    String bucketName;

    public CustomBucket(S3Client s3Client, String bucketName) {
        super();
        this.s3Client = s3Client;
        this.bucketName = bucketName;
    }

    public boolean create() {

        try {

            CreateBucketRequest request = CreateBucketRequest.builder()
                    .bucket(bucketName)
                    .build();

            s3Client.createBucket(request);

            return isBucketExistsOrReady();

        } catch (S3Exception e) {
            System.err.println(e.awsErrorDetails().errorMessage());
            System.exit(1);
            return false;
        }

    }

    public boolean isBucketExistsOrReady() {
        S3Waiter s3Waiter = s3Client.waiter();
        HeadBucketRequest wait = HeadBucketRequest.builder()
                .bucket(bucketName)
                .build();
        WaiterResponse<HeadBucketResponse> response = s3Waiter.waitUntilBucketExists(wait);
        return response.matched().response().isPresent();
    }

    public String put(String objectKey, String objectPath) {

        try {

            Map<String, String> metadata = new HashMap<>();
            metadata.put("x-amz-meta-description", "test");

            PutObjectRequest request = PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(objectKey)
                    .metadata(metadata)
                    .build();

            PutObjectResponse response = s3Client.putObject(request, RequestBody.fromBytes(FileUtils.getObjectFile(objectPath)));
            return response.eTag();

        } catch (S3Exception e) {
            LOGGER.error(e.getMessage());
            System.exit(1);
            return null;
        }

    }

    public List<S3Object> listBucketObjects() {

        try {

            ListObjectsRequest request = ListObjectsRequest.builder()
                    .bucket(bucketName)
                    .build();

            ListObjectsResponse response = s3Client.listObjects(request);
            System.out.println(response.contents());
            return response.contents();

        } catch (S3Exception e) {
            LOGGER.error(e.awsErrorDetails().errorMessage());
            System.exit(1);
            return null;
        }

    }

}
