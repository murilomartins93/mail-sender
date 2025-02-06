package org.jfm.presentation;

import io.quarkus.scheduler.Scheduled;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.jfm.application.SqsConsumerService;

@ApplicationScoped
public class SqsScheduler {

    @Inject
    SqsConsumerService sqsConsumerService;

    @Scheduled(every = "1s") 
    void pollSqsQueue() {
        sqsConsumerService.processMessages();
    }
}