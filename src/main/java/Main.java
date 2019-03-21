import Controller.SocketController;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        SocketController sc = new SocketController();
        sc.connectToServer("127.0.0.1",8000);
        new Main().run(sc);
    }

    public void run(SocketController sc) throws IOException {
        String userName = "Anders And";
        int userID;
        while(true) {
            userID = sc.getUserSelectionWithIntReturn("Indtast operatør ID:");
            if (userID == 12) {
                String nameConfirm = sc.getUserSelectionWithStringReturn("Er dit navn " + userName + "?");
                if (nameConfirm.contains("JA") || nameConfirm.contains("Ja") || nameConfirm.contains("ja") || nameConfirm.contains("jA")) operatorConfirmed(sc);
            }
            else {
                String tryAgain = sc.getUserSelectionWithStringReturn("Ukendt operatør ID. Vil du prøve igen?");
                if (tryAgain.contains("JA") || tryAgain.contains("Ja") || tryAgain.contains("ja") || tryAgain.contains("jA")) continue;
                else {
                    System.exit(0);
                }
            }
        }
    }

    public void operatorConfirmed(SocketController sc) throws IOException {
        int expectedBatchNumber = 1234;
        String batchType = "Salt";
        while (true) {
            int batchNumber = sc.getUserSelectionWithIntReturn("Indtast batch nummer:");
            if (batchNumber == expectedBatchNumber) {
                String correctBatch = sc.getUserSelectionWithStringReturn(expectedBatchNumber + " er " + batchType + ". Er det hvad du vil veje?");
                if (correctBatch.contains("JA") || correctBatch.contains("Ja") || correctBatch.contains("ja") || correctBatch.contains("jA")) break;
                else continue;
            }
            else {
                String tryAgain = sc.getUserSelectionWithStringReturn("Ukendt batch nummer. Vil du prøve igen?");
                if (tryAgain.contains("JA") || tryAgain.contains("Ja") || tryAgain.contains("ja") || tryAgain.contains("jA")) continue;
                else {
                    System.exit(0);
                }
            }
        }
        sc.getUserSelectionWithoutReturn("Fjern alt fra vægt");
        sc.resetWeight();
        double taraWeight = sc.getUserSelectionDouble("Placer tara");
        sc.resetWeight();
        double nettoWeight = sc.getUserSelectionDouble("Placer netto");
        sc.resetWeight();
        double bruttoWeight = sc.getUserSelectionDouble("Fjern brutto");
        if (bruttoWeight - taraWeight <= nettoWeight+0.001 && bruttoWeight -taraWeight >= nettoWeight-0.001) {
            sc.displayBigOnWeight("OK");
        }
        else sc.displayBigOnWeight("KASSER");
        sc.sendLine("Q");
        System.exit(0);
    }
}