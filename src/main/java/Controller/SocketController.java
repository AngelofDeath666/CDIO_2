package Controller;

import java.io.*;
import java.net.Socket;

public class SocketController {
    private Socket clientSocket = null;
    private BufferedReader reader = null;
    private BufferedWriter writer = null;

    public synchronized void connectToServer(String host, int port) throws IOException {
        if (clientSocket != null) {
            throw new IOException("You are already connected to the server, discconnect before continuing!");
        }
        clientSocket = new Socket(host, port);
        reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        writer = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
    }

    private void sendLine(String line) throws IOException {
        if (clientSocket == null) {
            throw new IOException("No socket connection found, please connect first+");
        }
        try {
            writer.write(line + "\r\n");
            writer.flush();
        } catch (IOException e) {
            clientSocket = null;
            throw e;
        }
    }

    public int getOperatorID() throws IOException {
        sendLine("RM20 8 Indtast operator ID ”” ”&3”");
        System.out.println(reader.readLine());
        String userID = reader.readLine().split("\"")[ 1 ].split("\"")[ 0 ];
        return Integer.parseInt(userID);
    }

}
