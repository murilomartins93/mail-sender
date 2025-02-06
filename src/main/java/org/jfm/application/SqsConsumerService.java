package org.jfm.application;

import com.google.gson.Gson;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.Message;
import software.amazon.awssdk.services.sqs.model.ReceiveMessageRequest;
import software.amazon.awssdk.services.sqs.model.DeleteMessageRequest;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.util.List;

@ApplicationScoped
public class SqsConsumerService {

    @Inject
    SqsClient sqsClient;

    @Inject
    EmailService emailService;

    private final Gson gson = new Gson(); // Initialize Gson
    private final String queueUrl = "https://sqs.us-east-1.amazonaws.com/520576585750/framer-notification";

    public void processMessages() {
        List<Message> messages = sqsClient.receiveMessage(ReceiveMessageRequest.builder()
                .queueUrl(queueUrl)
                .maxNumberOfMessages(10)
                .waitTimeSeconds(5)
                .build())
                .messages();

        for (Message message : messages) {
            try {
                String[] parts = message.body().split("\\.");
                if (parts.length == 2) {
                    String email = parts[1];

                    // Send the "Success" message
                    emailService.sendEmail(email, "Success! Your video is ready!");

                    // Delete the message from the queue
                    sqsClient.deleteMessage(DeleteMessageRequest.builder()
                            .queueUrl(queueUrl)
                            .receiptHandle(message.receiptHandle())
                            .build());
                } else {
                    System.err.println("Invalid message format: " + message.body());
                }
            } catch (Exception e) {
                System.err.println("Failed to process message: " + e.getMessage());
            }
        }
    }
}