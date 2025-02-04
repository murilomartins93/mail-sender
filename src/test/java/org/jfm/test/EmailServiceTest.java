// package org.jfm.test;

// import org.jfm.domain.EmailRequest;
// import org.jfm.application.EmailService;
// import io.quarkus.mailer.MockMailbox;
// import io.quarkus.test.junit.QuarkusTest;
// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.Test;
// import jakarta.inject.Inject;
// import static org.junit.jupiter.api.Assertions.assertEquals;

// @QuarkusTest
// public class EmailServiceTest {

//     @Inject
//     EmailService emailService;

//     @Inject
//     MockMailbox mockMailbox;

//     @BeforeEach
//     void setUp() {
//         mockMailbox.clear();
//     }

//     @Test
//     void testSendEmail() {
//         EmailRequest request = new EmailRequest();
//         request.setTo("test@example.com");
//         request.setSubject("Test Subject");
//         request.setBody("Test Body");

//         emailService.sendEmail(request);

//         assertEquals(1, mockMailbox.getMessagesSentTo("test@example.com").size());
//     }
// }