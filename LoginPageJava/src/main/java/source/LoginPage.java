package source;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JPasswordField;


public class LoginPage implements ActionListener
{
    private JFrame Frame = new JFrame();
    private JButton LoginButton = new JButton("Login");
    private JButton ResetButton = new JButton("Reset");
    private JButton SignUpButton = new JButton("Sign Up");
    private JTextField UserIdField = new JTextField();
    private JPasswordField UserPasswordFiel = new JPasswordField();
    private JLabel UserIdLabel = new JLabel("UserID:");
    private JLabel UserPasswordLabel = new JLabel("Password:");
    private JLabel MessageLabel = new JLabel(""); // Message erreur ou de connection réussite

    // Recupère les dimension de l'écran
    private final Dimension MonitorSize = Toolkit.getDefaultToolkit().getScreenSize();
    private int MONITORWIDTH = MonitorSize.width;
    private int MONITORHEIGHT = MonitorSize.height;

    final static private int WIDTH = 420;
    final static private int HEIGHT = 420;

    final static private Color TextColor = new Color(255, 255, 255);
    final static private Color BackGroundFrameColor = new Color(27, 38, 49);
    final static private Color BackGroundButtonColor = new Color(66, 73, 73);

    public LoginPage()
    {
        Frame.setLayout(null);  // null permet de faire fonctionner la fonction setBounds() et de placer ou l'on veut les composants
        Frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Frame.setSize(WIDTH, HEIGHT);
        Frame.setResizable(false);
        Frame.setTitle("Login");
        Frame.setLocation((MONITORWIDTH / 2) - (WIDTH / 2), (MONITORHEIGHT / 2) - (HEIGHT / 2));
        Frame.getContentPane().setBackground(BackGroundFrameColor);    // Couleur du fond

        UserIdLabel.setBounds(70, 100, 75, 25);     // Indique ou le label est placé
        UserIdLabel.setForeground(TextColor);   // Couleur de la police d'écriture
        UserPasswordLabel.setBounds(50, 150, 75, 25);
        UserPasswordLabel.setForeground(TextColor);

        MessageLabel.setBounds(125, 250, 250, 35);
        MessageLabel.setFont(new Font(null, Font.ITALIC, 20));

        UserIdField.setBounds(125, 100, 200, 25);
        UserIdField.setBackground(BackGroundFrameColor);
        UserIdField.setForeground(TextColor);
        UserIdField.setCaretColor(TextColor);

        UserPasswordFiel.setBounds(125, 150, 200, 25);
        UserPasswordFiel.setBackground(BackGroundFrameColor);
        UserPasswordFiel.setForeground(TextColor);
        UserPasswordFiel.setCaretColor(TextColor);

        LoginButton.setBounds(125, 200, 100, 25);
        LoginButton.setFocusable(false);    // Enleve le carré autour du texte si false
        LoginButton.addActionListener(this);
        LoginButton.setBackground(BackGroundButtonColor);
        LoginButton.setForeground(TextColor);

        ResetButton.setBounds(225, 200, 100, 25);
        ResetButton.setFocusable(false);
        ResetButton.addActionListener(this);
        ResetButton.setBackground(BackGroundButtonColor);
        ResetButton.setForeground(TextColor);

        SignUpButton.setBounds((WIDTH / 2) - 50, 350, 100, 25);
        SignUpButton.setFocusable(false);
        SignUpButton.addActionListener(this);
        SignUpButton.setBackground(BackGroundButtonColor);
        SignUpButton.setForeground(TextColor);

        // Add
        Frame.add(UserIdLabel);
        Frame.add(UserPasswordLabel);
        Frame.add(MessageLabel);
        Frame.add(UserIdField);
        Frame.add(UserPasswordFiel);
        Frame.add(ResetButton);
        Frame.add(LoginButton);
        Frame.add(SignUpButton);


        Frame.setVisible(true);
    }

    // A modifier avec SQL pour login etc
    @Override
    public void actionPerformed(ActionEvent e)
    {
        // Bouton Sign Up
        if (e.getSource() == SignUpButton)
        {
            Frame.dispose();
            SignUp SignUp = new SignUp();
        }

        // Bouton Reset
        if (e.getSource() == ResetButton)
        {
            UserIdField.setText("");
            UserPasswordFiel.setText("");
        }

        // Bouton Login
        if (e.getSource() == LoginButton)
        {
            String UserId = String.valueOf(UserIdField.getText());
            String UserPassword = String.valueOf(UserPasswordFiel.getPassword());

            PasswordHash PasswordHash = new PasswordHash(UserPassword);
            UserPassword = PasswordHash.GetHash();

            SQL Sql = new SQL();

            if (Sql.Show(UserId, UserPassword) == 1)
            {
                MessageLabel.setForeground(Color.GREEN);
                MessageLabel.setText("Connection successfully");
                Frame.dispose();
                new Client(UserId);

            }
            else if (Sql.Show(UserId, UserPassword) == 2)
            {
                MessageLabel.setForeground(Color.RED);
                MessageLabel.setText("Wrong Password");
            }
            else if (Sql.Show(UserId, UserPassword) == 0)
            {
                MessageLabel.setForeground(Color.RED);
                MessageLabel.setText("Not Registered");
            }
            else if (Sql.Show(UserId, UserPassword) == 3)
            {
                MessageLabel.setForeground(Color.RED);
                MessageLabel.setText("Error Server");
            }
        }
    }

    public void Pause(int millis)
    {
        try
        {
            Thread.sleep(millis);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
