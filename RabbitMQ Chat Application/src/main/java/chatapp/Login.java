package chatapp;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Cursor;
import javax.swing.JPasswordField;
import java.awt.Color;

public class Login extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField username;
	private JButton loginButton;
	private JPasswordField password;
	// two different users
	public String user1 = "Ugur";
	public String password1 = "ugur123";
	public String user2 = "Hasan";
	public String password2 = "hasan123";
	private String currentUser = "";

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Login frame = new Login();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Login() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 265, 163);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(248, 244, 225));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		username = new JTextField();
		username.setBounds(95, 23, 144, 20);
		contentPane.add(username);
		username.setColumns(10);
		
		loginButton = new JButton("Connect");
		loginButton.setForeground(new Color(116, 81, 45));
		loginButton.setFont(new Font("Tahoma", Font.BOLD, 15));
		loginButton.setBackground(new Color(254, 186, 23));
		
		loginButton.setFocusPainted(false);
		loginButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		loginButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// start chat application
				if (username.getText().equals(user1) && password.getText().equals(password1)) {
					currentUser = user1;
					new Application(currentUser, Login.this).setVisible(true); 
					setVisible(false); 
				}
				else if (username.getText().equals(user2) && password.getText().equals(password2)) {
					currentUser = user2;
					new Application(currentUser, Login.this).setVisible(true); 
					setVisible(false);
				}
			}
		});
		loginButton.setBounds(77, 90, 95, 23);
		contentPane.add(loginButton);
		
		JLabel label1 = new JLabel("Username:");
		label1.setFont(new Font("Tahoma", Font.BOLD, 14));
		label1.setBounds(10, 24, 75, 14);
		contentPane.add(label1);
		
		JLabel lblPassword = new JLabel("Password:");
		lblPassword.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblPassword.setBounds(10, 57, 75, 14);
		contentPane.add(lblPassword);
		
		password = new JPasswordField();
		password.setBounds(95, 54, 144, 20);
		contentPane.add(password);
	}
}
