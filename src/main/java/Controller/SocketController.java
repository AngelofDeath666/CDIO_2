package Controller;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
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

    public void sendLine(String line) throws IOException {
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

    public int getUserSelectionWithIntReturn(String text) throws IOException {
        sendLine("RM20 8 \""+text+"\" \"\" \"&3\"");
        System.out.println(reader.readLine());
        String response = reader.readLine();
        System.out.println(response);
        if (response.length()==9) return 0;
        System.out.println(response);
        int userID = Integer.parseInt(response.split("\"")[1].split("\"")[0]);
        System.out.println(userID);
        waitTimer(1);
        return userID;
    }
    public String getUserSelectionWithStringReturn(String text) throws IOException {
        sendLine("RM20 8 \""+text+"\" \"\" \"&3\"");
        System.out.println(reader.readLine());
        String response = reader.readLine();
        if (response.length()==9) return "Nej";
        System.out.println(response);
        String selection = response.split("\"")[1].split("\"")[0];
        return selection;
    }
    public void getUserSelectionWithoutReturn(String text) throws IOException {
        sendLine("RM20 8 \""+text+"\" \"\" \"&3\"");
        System.out.println(reader.readLine());
        System.out.println(reader.readLine());
    }

    public double getUserSelectionDouble(String text) throws IOException {
        sendLine("RM20 8 \""+text+"\" \"\" \"&3\"");
        System.out.println(reader.readLine());
        System.out.println(reader.readLine());
        waitTimer(1);
        sendLine("S");
        char[] responseArray = reader.readLine().toCharArray();
        ArrayList list = new ArrayList();
        for (int i = 0; i < responseArray.length; i++) {
            if (isThisAnInt(responseArray[i]) || responseArray[i] == '.') {
                list.add(responseArray[i]);
            }
        }
        String responseString = "";
        for (int j = 0; j < list.size(); j++) {
            responseString = responseString + list.get(j);
        }
        System.out.println(responseString);
        return Double.parseDouble(responseString);

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

    public void displayBigOnWeight(String text) throws IOException {
        sendLine("D " + text);
        System.out.println(reader.readLine());
        waitTimer(2);
        sendLine("DW");
    }

    public boolean isThisAnInt(char s) {
        String intChecker = Character.toString(s);
        try
        {
            Integer.parseInt(intChecker);
            return true;
        } catch (NumberFormatException ex)
        {
            return false;
        }
    }

}
