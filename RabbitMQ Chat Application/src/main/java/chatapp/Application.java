package chatapp;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionListener;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.ActionEvent;

public class Application extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField message;
    private JButton quitButton;
    private Login loginFrame;
    private static List<String> messageHistory = new ArrayList<>(); // to store all chat messages
    private JTextPane chat;

    // Main chat window constructor
    public Application(String currentUser, Login loginFrame) {
        this.loginFrame = loginFrame;
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 450, 300);
        contentPane = new JPanel();
        contentPane.setBackground(new Color(248, 244, 225));
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(10, 11, 414, 203);
        contentPane.add(scrollPane);

        chat = new JTextPane();
        chat.setEditable(false);
        scrollPane.setViewportView(chat);
        chat.setText(String.join("\n", messageHistory));

        // Start a new thread to listen for incoming messages from RabbitMQ
        new Thread(() -> {
            // Start listening using MessageConsumer
            MessageConsumer.startListening(newMessage -> {
                // Update the chat UI with the new message
                SwingUtilities.invokeLater(() -> {
                    // Add message only if it's not already in history
                    if (!messageHistory.contains(newMessage)) {
                        messageHistory.add(newMessage);
                        chat.setText(String.join("\n", messageHistory));
                    }
                });
            });
        }).start();

        message = new JTextField();
        message.setBounds(10, 225, 247, 23);
        contentPane.add(message);
        message.setColumns(10);

        JButton sendButton = new JButton("SEND");
        sendButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        sendButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String text = message.getText();
                if (text.trim().isEmpty()) return;

                // Add time and username to the message
                String time = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm"));
                String fullMessage = "[" + time + "] " + currentUser + " : " + text;

                // Send message using RabbitMQ
                if (MessageProducer.sendMessage(fullMessage)) {
                    // If message sent successfully, update the chat UI
                    SwingUtilities.invokeLater(() -> {
                        messageHistory.add(fullMessage);
                        chat.setText(String.join("\n", messageHistory));
                    });
                }

                // Clear the input box
                message.setText("");
            }
        });

        sendButton.setForeground(new Color(116, 81, 45));
        sendButton.setFont(new Font("Tahoma", Font.BOLD, 14));
        sendButton.setBackground(new Color(254, 186, 23));
        sendButton.setBounds(267, 225, 74, 23);
        contentPane.add(sendButton);

        quitButton = new JButton("QUIT");
        quitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new Login().setVisible(true);
                dispose();
            }
        });
        quitButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        quitButton.setForeground(new Color(116, 81, 45));
        quitButton.setFont(new Font("Tahoma", Font.BOLD, 14));
        quitButton.setBackground(new Color(254, 186, 23));
        quitButton.setBounds(350, 225, 74, 23);
        contentPane.add(quitButton);
    }
}
