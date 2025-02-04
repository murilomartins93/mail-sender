package org.jfm.infrastructure;

import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sqs.SqsClient;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;

@ApplicationScoped
public class SqsClientProducer {

    @Produces
    public SqsClient sqsClient() {
        return SqsClient.builder()
                .region(Region.of("your-region")) // Replace with your AWS region
                .credentialsProvider(DefaultCredentialsProvider.create()) // Use DefaultCredentialsProvider
                .build();
    }
}