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
            sc.writeOnWeight("Indtast_ID");
            int userid = s.nextInt();
            if (userid == 12) {
                System.out.println("Er du " + userName + "?");
                System.out.println("Indast 1 for ja og 2 for nej");
                int userChoice = s.nextInt();
                switch (userChoice) {
                    case 1:
                        userConfirmed();
                        break;
                    case 2:
                        System.out.println("Prøv venligst igen så");
                        break;
                }
            }
            else System.out.println("Detter brugerid findes ikke");
        }
    }

    public void userConfirmed() {

    }
}