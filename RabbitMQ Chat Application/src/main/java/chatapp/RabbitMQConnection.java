package chatapp;
import com.rabbitmq.client.*;

public class RabbitMQConnection {
    // Name of the queue where messages will be sent/received
    private static final String QUEUE_NAME = "chatQueue"; 
    
    // RabbitMQ connection and channel objects
    private static Connection connection;
    private static Channel channel;

    // Returns a channel connected to RabbitMQ
    public static Channel getChannel() throws Exception {
        // If the channel is not created or is closed, create a new one
        if (channel == null || !channel.isOpen()) {
            // Create a factory to connect to RabbitMQ
            ConnectionFactory factory = new ConnectionFactory();
            factory.setHost("localhost"); // RabbitMQ server is on local machine
            
            // Create a new connection and channel
            connection = factory.newConnection();
            channel = connection.createChannel();
            
            // Declare the queue (create if it doesn't exist)
            channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        }
        return channel;
    }

    // Closes the channel and connection safely
    public static void close() throws Exception {
        // Close the channel if it's open
        if (channel != null && channel.isOpen()) channel.close();
        // Close the connection if it's open
        if (connection != null && connection.isOpen()) connection.close();
    }
}
