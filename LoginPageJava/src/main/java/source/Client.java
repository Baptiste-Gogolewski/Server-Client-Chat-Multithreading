package source;


import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

public class Client implements ActionListener
{
    private JFrame Frame = new JFrame();
    private JButton SendButton = new JButton("Send");   // Bouton pour envoyer le message
    private JTextField MessageToSendField = new JTextField(40);
    private JTextArea TextArea = new JTextArea(10, 40);   // Va afficher les messages envoyés
    private JScrollPane ScrollPane;

    final static private int WIDTH = 420;
    final static private int HEIGHT = 420;
    final static private String NEWLINE = "\n";
    private String Name;

    final static private Color TextColor = new Color(255, 255, 255);
    final static private Color BackGroundFrameColor = new Color(27, 38, 49);
    final static private Color BackGroundButtonColor = new Color(66, 73, 73);

    // Recupère les dimension de l'écran
    private final Dimension MonitorSize = Toolkit.getDefaultToolkit().getScreenSize();
    private int MONITORWIDTH = MonitorSize.width;
    private int MONITORHEIGHT = MonitorSize.height;

    // Socket du client
    Socket ClientSocket;

    // Pour recevoir les messages
    private InputStream In;

    // Pour envoyer les messages
    private OutputStream Out;

    // Le numéro du port
    private static final int PORT_NUMBER = 1234;

    public Client(String name)
    {
        this.Name = name;
        Frame.setLayout(null);
        Frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Frame.setSize(WIDTH, HEIGHT);
        Frame.setResizable(false);
        Frame.setTitle(this.Name);
        Frame.getContentPane().setBackground(BackGroundFrameColor);    // Couleur du fond
        Frame.setLocation((MONITORWIDTH / 2) - (WIDTH / 2), (MONITORHEIGHT / 2) - (HEIGHT / 2));

        MessageToSendField.setBounds(2, 356, 300, 25);
        MessageToSendField.addActionListener(this);     // Intéraction avec le clavier
        MessageToSendField.setBackground(BackGroundFrameColor);
        MessageToSendField.setForeground(TextColor);
        MessageToSendField.setCaretColor(TextColor);

        SendButton.setBounds(302, 356, 100, 25);
        SendButton.setFocusable(false);
        SendButton.setBackground(BackGroundButtonColor);
        SendButton.setForeground(TextColor);
        SendButton.addActionListener(new ActionListener()   // Si l'utilisateur appuie sur le bouton
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                SendMessage();
            }
        });

        TextArea.setEditable(false);
        TextArea.setFocusable(false);
        TextArea.setBackground(BackGroundFrameColor);
        TextArea.setForeground(TextColor);

        ScrollPane = new JScrollPane(TextArea);
        ScrollPane.setBounds(0, 0, WIDTH - 15, HEIGHT - 70);
        ScrollPane.setVerticalScrollBarPolicy(ScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        // Add
        Frame.add(MessageToSendField);
        Frame.add(SendButton);
        Frame.add(ScrollPane);


        Frame.setVisible(true);

        TextArea.append("Bienvenue " + Name + NEWLINE);

        try
        {
            // Si "192.168.1.16" n'est pas bon vous devez remplacer cette ip par l'ip du pc sur lequel le serveur est entrain de tourner
            //InetAddress ServeurAddress = InetAddress.getByName("192.168.1.16");
            ClientSocket = new Socket("127.0.0.1", PORT_NUMBER);     // Si le serveur est sur la meme machine alors remplacé par 127.0.0.1 et mettre en commentaire la ligne 87

            if (ClientSocket != null)
                System.out.println("Connecté au serveur local sur le port " + PORT_NUMBER);

            In = ClientSocket.getInputStream();
            Out = ClientSocket.getOutputStream();

            Thread ReceiveThread = new Thread(this::Receive);
            ReceiveThread.start();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    private void Receive()
    {
        try
        {
            byte[] Buffer = new byte[1024];
            int BytesRead;

            while ((BytesRead = In.read(Buffer)) != -1) {
                String message = new String(Buffer, 0, BytesRead);
                AppendMessage(message);
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        finally {
            Close();
        }
    }

    private void SendMessage()
    {
        String Message = String.valueOf(MessageToSendField.getText());
        if (!(Message.isEmpty()))
        {
            String Messatmp = Message;
            Message = this.Name + " : " + Message;
            try
            {
                Out.write(Message.getBytes());
                Out.flush();
                AppendMessage("moi : " + Messatmp);
                MessageToSendField.selectAll();
            }
            catch (IOException e)
            {
                e.printStackTrace();
                Close();
            }
        }
    }

    private void AppendMessage(String message)
    {
        SwingUtilities.invokeLater(new Runnable()
        {
            public void run()
            {

                TextArea.append(message + NEWLINE);
            }
        });
    }

    private void Close()
    {
        try {
            if (In != null) {
                In.close();
            }
            if (Out != null) {
                Out.close();
            }
            if (ClientSocket != null) {
                ClientSocket.close();
            }
            System.out.println("Connexion fermé");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        if (e.getSource() == SendButton)
        {
            String Text = String.valueOf(MessageToSendField.getText());

            // Efface le texte
            if (Text.equals("/clearText"))
            {
                TextArea.setText("");
                MessageToSendField.selectAll();
            }
            else    // Ajoute le texte au TextArea
            {
                //TextArea.append("Moi : " + Text + NEWLINE);   // Ajoute le texte au TextArea
                MessageToSendField.selectAll();
            }
        }
    }
}
