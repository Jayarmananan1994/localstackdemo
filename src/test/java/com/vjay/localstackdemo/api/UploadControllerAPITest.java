package com.vjay.localstackdemo.api;

import com.amazonaws.services.s3.AmazonS3;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.testcontainers.containers.localstack.LocalStackContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.http.HttpMethod.POST;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
public class UploadControllerAPITest {
    public static final String BUCKET_NAME = "testbucket";
    @LocalServerPort
    private int port;

    @Container
    private static final LocalStackContainer localStackContainer = new LocalStackContainer(DockerImageName.parse("localstack/localstack:0.11.3"))
            .withServices(LocalStackContainer.Service.S3);

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private AmazonS3 s3Client;

    @BeforeAll
    static void beforeAll() {
        System.setProperty("spring.cloud.aws.credentials.access-key", localStackContainer.getAccessKey());
        System.setProperty("spring.cloud.aws.credentials.secret-key", localStackContainer.getSecretKey());
        System.setProperty("spring.cloud.aws.s3.region", localStackContainer.getRegion());
        System.setProperty("cloud.aws.s3.endpoint", localStackContainer.getEndpointOverride(LocalStackContainer.Service.S3).toString());
    }

    @BeforeEach
    void setUp() {
        s3Client.createBucket(BUCKET_NAME);
    }

    @Test
    void shouldUploadFileSuccessFullyToS3() {
        String fileUploadURI = "/api/upload";
        LinkedMultiValueMap<String, Object> parameters = new LinkedMultiValueMap<>();
        parameters.add("file", new ClassPathResource("sample.txt"));
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        HttpEntity<LinkedMultiValueMap<String, Object>> entity = new HttpEntity<>(parameters, headers);
        String expected = "{\"status\":\"success\", \"url\":\"/testbucket/sample.txt\"}";

        ResponseEntity<String> responseEntity = restTemplate.exchange("http://localhost:" + port + fileUploadURI, POST, entity, String.class);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(expected, responseEntity.getBody());

    }

}
