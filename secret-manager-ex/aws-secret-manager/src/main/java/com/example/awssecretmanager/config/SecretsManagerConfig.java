package com.example.awssecretmanager.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.secretsmanager.SecretsManagerClient;
import software.amazon.awssdk.services.secretsmanager.model.GetSecretValueRequest;
import software.amazon.awssdk.services.secretsmanager.model.GetSecretValueResponse;
import software.amazon.awssdk.services.secretsmanager.model.ResourceNotFoundException;

@Configuration
public class SecretsManagerConfig {

    @Value("${aws.secretsmanager.secretName}")
    private String secretName;

    @Value("${aws.secretsmanager.region}")
    private String region;

    @Bean
    public SecretsManagerClient secretsManagerClient() {
        return SecretsManagerClient.builder()
                .region(region != null ? Region.of(region) : Region.US_EAST_1)
                .credentialsProvider(DefaultCredentialsProvider.create())
                .build();
    }

//    @Bean
//    public String getSecretValue(SecretsManagerClient secretsManagerClient) {
//        try {
//            GetSecretValueRequest getSecretValueRequest = GetSecretValueRequest.builder()
//                    .secretId(secretName)
//                    .build();
//            GetSecretValueResponse getSecretValueResponse = secretsManagerClient.getSecretValue(getSecretValueRequest);
//            return getSecretValueResponse.secretString();
//        } catch (ResourceNotFoundException e) {
//            throw new RuntimeException("Secret not found: " + secretName, e);
//        }
//    }

}
