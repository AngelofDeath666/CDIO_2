import Controller.SocketController;

import java.io.IOException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws IOException {
        new Main().run();
    }

    public void run() throws IOException {
        String userName = "Anders And";
        SocketController sc = new SocketController();
        sc.connectToServer("127.0.0.1",8000);
        Scanner s = new Scanner(System.in);
        while(true) {
            int userID = sc.getOperatorID();
            if (userID == 12) {

            }

        }
    }

    public void userConfirmed() {

    }
}