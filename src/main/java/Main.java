import Controller.SocketController;

import java.io.IOException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws IOException {
        new Main().run();
    }

    public void run() throws IOException {
        String userName = "Anders_And";
        SocketController sc = new SocketController();
        sc.connectToServer("127.0.0.1",8000);
        Scanner s = new Scanner(System.in);
        while(true) {
            int userID = sc.getUserSelectionWithReturn("Indtast_operatør_ID:");
            if (userID == 12) {
                sc.getUserSelection(userName + "?");
                operatorConfirmed(sc);
            }
            else {
                sc.displayOnWeight("Ukendt_operatør");
                sc.displayOnWeight("Prøv_igen");
            }
        }
    }

    public void operatorConfirmed(SocketController sc) throws IOException {
        int batchNumber = sc.getUserSelectionWithReturn("Indtast_batch_nummer:");
        sc.getUserSelection("Ubelast_vægt");
        sc.resetWeight();
        sc.getUserSelection("Placer_tara");
        int taraWeight = sc.getWeight();
        sc.resetWeight();
        sc.getUserSelection("Placer_netto");
        int nettoWeight = sc.getWeight();
        sc.resetWeight();
        sc.getUserSelection("Fjern_brutto");
        int bruttoWeight = sc.getWeight();
        if (-bruttoWeight - taraWeight == nettoWeight) {
            sc.displayBigOnWeight("OK");
        }
        else sc.displayBigOnWeight("KASSER");
    }
}