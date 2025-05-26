package chatapp;

import com.rabbitmq.client.*;

public class MessageProducer {

    // Name of the queue to send messages to
    private static final String QUEUE_NAME = "chatQueue";

    // Sends a message to the queue
    public static boolean sendMessage(String message) {
        try (Channel channel = RabbitMQConnection.getChannel()) {
            // Send the message to the queue
            channel.basicPublish("", QUEUE_NAME, null, message.getBytes());

            // Print confirmation to console
            System.out.println("Message sent: " + message);

            return true; // Message sent successfully
        } catch (Exception e) {
            e.printStackTrace();
            return false; // Message failed to send
        }
    }
}
