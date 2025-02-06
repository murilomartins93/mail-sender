package org.jfm.application;

import com.google.gson.Gson;
import org.jboss.logging.Logger;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.Message;
import software.amazon.awssdk.services.sqs.model.ReceiveMessageRequest;
import software.amazon.awssdk.services.sqs.model.DeleteMessageRequest;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.util.List;
import org.jfm.config.SqsConfig;

@ApplicationScoped
public class SqsConsumerService {

    @Inject
    SqsClient sqsClient;

    @Inject
    EmailService emailService;

    @Inject
    SqsConfig sqsConfig;

    private static final Logger LOG = Logger.getLogger(SqsConsumerService.class);

    public void processMessages() {

        String queueUrl = sqsConfig.queueUrl();
        List<Message> messages = sqsClient.receiveMessage(ReceiveMessageRequest.builder()
                .queueUrl(queueUrl)
                .maxNumberOfMessages(10)
                .waitTimeSeconds(5)
                .build())
                .messages();

        for (Message message : messages) {
            try {
                String[] parts = message.body().split("\\.", 2);
                if (parts.length == 2) {
                    String videoId = parts[0];
                    String userEmail = parts[1];
                    emailService.sendEmail(userEmail, videoId);  // Updated method
                    sqsClient.deleteMessage(DeleteMessageRequest.builder()
                            .queueUrl(queueUrl)
                            .receiptHandle(message.receiptHandle())
                            .build());
                } else {
                    LOG.errorf("Invalid message: %s", message.body());
                }
            } catch (Exception e) {
                LOG.error("Failed to process message", e);
            }
        }
    }
}