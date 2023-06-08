# Server-Client-Chat-Multithreading
The goal of this project is to implement an server in localhost and a client to chat with people who are connected to the server

The Serveur class is on the port 1234 by default. If you want to change it you can but check before if the port is free. You can start the server directly because the class owns a main method.

In the Client class, if you want to connect in local replace the string by 127.0.0.1 or localhost at the line 88 in the socket. If you want to connect remotely replace 127.0.0.1 or localhost by the variable at the line 87 in the socket at the line 88. At the line 87 replace the string by the ip address of the machine which host the server.
