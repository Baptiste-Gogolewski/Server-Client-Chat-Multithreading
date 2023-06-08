package source;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Serveur
{
    // Liste des clients connectés
    private List<ClientHandler> Clients;

    public Serveur()
    {
        Clients = new ArrayList<ClientHandler>();
    }

    public void Start(int Port)
    {
        try
        {
            ServerSocket ServerSocket = new ServerSocket(Port);
            System.out.println("Le serveur s'est lancé sur le port " + Port);

            while (true)
            {
                Socket ClientSocket = ServerSocket.accept();

                if (ClientSocket != null)
                    System.out.println("Nouvelle connexion " + ClientSocket);

                // Cr
                ClientHandler ClientHandler = new ClientHandler(ClientSocket);
                Clients.add(ClientHandler);
                ClientHandler.start();
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public void BroadCastMessage(String message, ClientHandler sender)
    {
        for (ClientHandler client : Clients)
        {
            if (client != sender)
                client.SendMessage(message);
        }
    }

    private class ClientHandler extends Thread
    {
        private Socket ClientSocket;
        private InputStream In;
        private OutputStream Out;

        public ClientHandler(Socket socket)
        {
            this.ClientSocket = socket;
        }

        @Override
        public void run()
        {
            try
            {
                In = ClientSocket.getInputStream();
                Out = ClientSocket.getOutputStream();

                byte[] Buffer = new byte[1024];
                int BytesRead;

                while ((BytesRead = In.read(Buffer)) != -1)
                {
                    String Message = new String(Buffer, 0, BytesRead);
                    BroadCastMessage(Message, this);
                }

            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
            finally
            {
                try
                {
                    ClientSocket.close();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
        }

        public void SendMessage(String message)
        {
            try
            {
                Out.write(message.getBytes());
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) throws IOException
    {
        Serveur Serveur = new Serveur();
        Serveur.Start(1234);
    }
}
