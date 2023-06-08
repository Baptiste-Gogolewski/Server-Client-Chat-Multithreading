package source;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SignUp implements ActionListener
{
    JFrame Frame = new JFrame();
    JLabel MessageLabel = new JLabel();
    JLabel UserIdLabel = new JLabel("UserID :");
    JLabel UserPasswordLabel = new JLabel("Password :");
    JLabel UserPasswordBisLabel = new JLabel("Repeat password :");
    JTextField UserIdTextField = new JTextField();
    JPasswordField UserPasswordField = new JPasswordField();
    JPasswordField UserPasswordBisField = new JPasswordField();
    JButton SignUpButton = new JButton();

    final static private int WIDTH = 420;
    final static private int HEIGHT = 420;

    // Recupère les dimension de l'écran
    private final Dimension MonitorSize = Toolkit.getDefaultToolkit().getScreenSize();
    private int MONITORWIDTH = MonitorSize.width;
    private int MONITORHEIGHT = MonitorSize.height;

    final static private Color TextColor = new Color(255, 255, 255);
    final static private Color BackGroundFrameColor = new Color(27, 38, 49);
    final static private Color BackGroundButtonColor = new Color(66, 73, 73);

    public SignUp()
    {
        Frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Frame.setLayout(null);
        Frame.setSize(WIDTH, HEIGHT);
        Frame.setTitle("Sign Up");
        Frame.getContentPane().setBackground(BackGroundFrameColor);
        Frame.setLocation((MONITORWIDTH / 2) - (WIDTH / 2), (MONITORHEIGHT / 2) - (HEIGHT / 2));
        Frame.setResizable(false);

        MessageLabel.setBounds((WIDTH / 2) - 50, 250, 150, 25);
        MessageLabel.setFont(new Font(null, Font.ITALIC, 20));
        MessageLabel.setText("");

        UserIdLabel.setBounds(70, 100, 75, 25);
        UserIdLabel.setForeground(TextColor);

        UserPasswordLabel.setBounds(50, 150, 75, 25);
        UserPasswordLabel.setForeground(TextColor);

        UserPasswordBisLabel.setBounds(10, 200, 125, 25);
        UserPasswordBisLabel.setForeground(TextColor);

        UserIdTextField.setBounds(135, 100, 200, 25);
        UserIdTextField.setBackground(BackGroundFrameColor);
        UserIdTextField.setCaretColor(TextColor);
        UserIdTextField.setForeground(TextColor);

        UserPasswordField.setBounds(135, 150, 200, 25);
        UserPasswordField.setBackground(BackGroundFrameColor);
        UserPasswordField.setCaretColor(TextColor);
        UserPasswordField.setForeground(TextColor);

        UserPasswordBisField.setBounds(135, 200, 200, 25);
        UserPasswordBisField.setBackground(BackGroundFrameColor);
        UserPasswordBisField.setCaretColor(TextColor);
        UserPasswordBisField.setForeground(TextColor);

        SignUpButton.setBounds((WIDTH / 2) - 50, 300, 100, 25);
        SignUpButton.setFocusable(false);
        SignUpButton.setText("Sign Up");
        SignUpButton.addActionListener(this);
        SignUpButton.setBackground(BackGroundButtonColor);
        SignUpButton.setForeground(TextColor);

        // Add
        Frame.add(MessageLabel);
        Frame.add(UserIdLabel);
        Frame.add(UserPasswordLabel);
        Frame.add(UserPasswordBisLabel);
        Frame.add(UserIdTextField);
        Frame.add(UserPasswordField);
        Frame.add(UserPasswordBisField);
        Frame.add(SignUpButton);

        Frame.setVisible(true);
    }

    public void Pause()
    {
        try
        {
            Thread.sleep(500);
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        if (e.getSource() == SignUpButton)
        {
            if (String.valueOf(UserIdTextField.getText()).equals("") || String.valueOf(UserPasswordField.getPassword()).equals("") || String.valueOf(UserPasswordBisField.getPassword()).equals(""))
            {
                MessageLabel.setText("Please fill all text fields");
            }
            else
            {
                String UserPassword = "";

                if (!(String.valueOf(UserPasswordField.getPassword()).equals(String.valueOf(UserPasswordBisField.getPassword()))))
                {
                    MessageLabel.setText("Password are not equal");
                }
                else
                {
                    MessageLabel.setText("Sign up successfully");
                    Pause();
                    UserPassword = String.valueOf(UserPasswordField.getPassword());
                    String UserId = String.valueOf(UserIdTextField.getText());

                    PasswordHash PasswordHash = new PasswordHash(UserPassword);
                    UserPassword = PasswordHash.GetHash();

                    SQL Sql = new SQL();
                    Sql.Insert(UserId, UserPassword);
                    Frame.dispose();
                    new LoginPage();
                }
            }
        }
    }
}
