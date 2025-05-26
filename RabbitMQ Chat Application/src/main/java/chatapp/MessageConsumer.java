package chatapp;

import com.rabbitmq.client.*;

public class MessageConsumer {

    // Name of the queue to receive messages from
    private static final String QUEUE_NAME = "chatQueue";

    // Functional interface to handle incoming messages
    public interface MessageHandler {
        void handleMessage(String message); // Method to define what to do with the received message
    }

    // Starts listening for messages from the queue
    public static void startListening(MessageHandler handler) {
        // Run listening logic in a new thread so it doesn't block the GUI
        new Thread(() -> {
            try {
                // Get a channel to the RabbitMQ server
                Channel channel = RabbitMQConnection.getChannel();

                // Define what happens when a message is received
                DeliverCallback deliverCallback = (consumerTag, delivery) -> {
                    // Convert the received byte array into a String
                    String message = new String(delivery.getBody(), "UTF-8");

                    // Pass the message to the handler
                    handler.handleMessage(message);
                };

                // Start consuming messages from the queue
                channel.basicConsume(QUEUE_NAME, true, deliverCallback, consumerTag -> {});

            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }
}
