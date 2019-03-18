package Controller;

import java.io.*;
import java.net.Socket;
import java.util.concurrent.TimeUnit;

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

    public int getUserSelectionWithReturn(String text) throws IOException {
        sendLine("RM20 8 "+text+" ”” &3");
        System.out.println(reader.readLine());
        String response = reader.readLine();
        System.out.println(response);
        int userID = Integer.parseInt(response.split("\"")[1].split("\"")[0]);
        System.out.println(userID);
        waitTimer(1);
        return userID;
    }
    public void getUserSelection(String text) throws IOException {
        sendLine("RM20 8 "+text+" ”” &3");
        System.out.println(reader.readLine());
        String response = reader.readLine();
        System.out.println(response);
    }

    public void displayOnWeight(String text) throws IOException {
        sendLine("P111 " + text);
        System.out.println(reader.readLine());
        waitTimer(2);
        sendLine("DW");
        System.out.println(reader.readLine());
    }

    public void waitTimer(int seconds) {
        try {
            TimeUnit.SECONDS.sleep(seconds);
        }
        catch (InterruptedException e) {
            System.out.println(e);
        }
    }

    public void resetWeight() throws IOException {
        sendLine("T");
        System.out.println(reader.readLine());
    }

    public int getWeight() throws IOException {
        sendLine("S");
        System.out.println(reader.readLine());
        return 1;
    }

    public void displayBigOnWeight(String text) throws IOException {
        sendLine("D " + text);
        System.out.println(reader.readLine());
        waitTimer(2);
        sendLine("DW");
    }

}
