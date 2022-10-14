import dev.rmmarquini.aws.s3.Main;
import dev.rmmarquini.aws.s3.actions.CustomBucket;
import org.junit.jupiter.api.*;
import software.amazon.awssdk.auth.credentials.ProfileCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.S3Object;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3control.S3ControlClient;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class MainTest {

    private static S3Client s3;
    private static S3Presigner presigner;
    private static S3ControlClient s3ControlClient;

    // Define the data members required for the tests
    private static String bucketName = "";
    private static String objectKey = "";
    private static String objectPath = "";
    private static String toBucket = "";
    private static String policyText = "";
    private static String id = "";
    private static String presignKey="";
    private static String presignBucket="";
    private static String path="";
    private static String bucketNamePolicy="";
    private static String accountId="";
    private static String accessPointName="";

    //Used for the Encryption test
    private static String encryptObjectName="";
    private static String encryptObjectPath="";
    private static String encryptOutPath="";
    private static String keyId="";

    private static CustomBucket bucket;

    @BeforeAll
    public static void setUp() throws IOException {

        // Run tests on Real AWS Resources
        ProfileCredentialsProvider credentialsProvider = ProfileCredentialsProvider.create();
        s3 = S3Client.builder()
                .region(Region.US_EAST_1)
                .credentialsProvider(credentialsProvider)
                .build();

        presigner = S3Presigner.builder()
                .region(Region.US_EAST_1)
                .credentialsProvider(credentialsProvider)
                .build();

        s3ControlClient = S3ControlClient.builder()
                .region(Region.US_EAST_1)
                .credentialsProvider(credentialsProvider)
                .build();

        try (InputStream input = Main.class.getClassLoader().getResourceAsStream("config.properties")) {

            Properties prop = new Properties();

            if (input == null) {
                System.out.println("Sorry, unable to find config.properties");
                return;
            }

            //load a properties file from class path, inside static method
            prop.load(input);

            // Populate the data members required for all tests
            bucketName = prop.getProperty("bucketName");
            objectKey = prop.getProperty("objectKey");
            objectPath= prop.getProperty("objectPath");
            toBucket = prop.getProperty("toBucket");
            policyText = prop.getProperty("policyText");
            id  = prop.getProperty("id");
            presignKey = prop.getProperty("presignKey");
            presignBucket= prop.getProperty("presignBucket");
            path = prop.getProperty("path");
            bucketNamePolicy = prop.getProperty("bucketNamePolicy");
            accountId = prop.getProperty("accountId");
            accessPointName = prop.getProperty("accessPointName");
            encryptObjectName = prop.getProperty("encryptObjectName");
            encryptObjectPath = prop.getProperty("encryptObjectPath");
            encryptOutPath = prop.getProperty("encryptOutPath");
            keyId = prop.getProperty("keyId");

        } catch (IOException ex) {
            ex.printStackTrace();
        }

        bucket = new CustomBucket(s3, bucketName);
    }

    @Test
    @Order(1)
    public void whenInitializingAWSS3Service_thenNotNull() {
        assertNotNull(s3);
        System.out.println("Test 1 passed.");
    }

    @Test
    @Order(2)
    public void createBucket() {
        assertTrue(bucket.create());
        System.out.println("Test 2 passed.");
    }

    @Test
    @Order(3)
    public void isBucketExistsOrReady() {
        assertTrue(bucket.isBucketExistsOrReady());
        System.out.println("Test 3 passed.");
    }

    @Test
    @Order(4)
    public void putObject() {
        if (bucket.isBucketExistsOrReady()) {
            String result = bucket.put(objectKey, objectPath);
            assertFalse(result.isEmpty());
            System.out.println("Test 4 passed.");
        } else {
            System.out.println("Bucket not ready. Test failed.");
            assertTrue(bucket.isBucketExistsOrReady());
        }
    }

    @Test
    @Order(5)
    public void listBucketObjects() {
        if (bucket.isBucketExistsOrReady()) {
            List<S3Object> lstBucketObjects = bucket.listBucketObjects();
            assertTrue(lstBucketObjects.size() > 0);
            System.out.println("Test 5 passed.");
        } else {
            System.out.println("Bucket not ready. Test failed.");
            assertTrue(bucket.isBucketExistsOrReady());
        }
    }

}
