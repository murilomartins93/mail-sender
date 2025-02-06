package org.jfm.test;

import org.jfm.domain.EmailRequest;
import org.jfm.application.SqsConsumerService;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.Message;
import software.amazon.awssdk.services.sqs.model.ReceiveMessageResponse;
import jakarta.inject.Inject;
import java.util.List;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@QuarkusTest
public class SqsConsumerServiceTest {

    @Inject
    SqsConsumerService sqsConsumerService;

    @Mock
    SqsClient sqsClient;

    @Test
    void testProcessMessages() {
        // Mock SQS response
        Message message = Message.builder()
                .body("{\"to\":\"test@example.com\",\"subject\":\"Test\",\"body\":\"Hello\"}")
                .receiptHandle("dummy-receipt-handle")
                .build();
        when(sqsClient.receiveMessage(any()))
                .thenReturn(ReceiveMessageResponse.builder()
                        .messages(message)
                        .build());

        // Test message processing
        sqsConsumerService.processMessages();

        // Verify message deletion
        verify(sqsClient).deleteMessage(any());
    }
}