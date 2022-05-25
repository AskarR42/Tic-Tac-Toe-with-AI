import java.util.Scanner;

public class Menu {

    final static Scanner scanner = new Scanner(System.in);

    public static String[] getCommand() {
        String[] command;
        while (true) {
            System.out.print("Input command: ");
            command = scanner.nextLine().split(" ");

            if ("exit".equals(command[0])) {
                return command;
            }

            //checking if command is invalid
            if (command.length < 3) {
                System.out.println("Bad parameters!");
                continue;
            }
            if (!"start".equals(command[0])) {
                System.out.println("Bad parameters!");
                continue;
            }
            if (!("user".equals(command[1]) || "easy".equals(command[1]) || "medium".equals(command[1]) || "hard".equals(command[1]))) {
                System.out.println("Bad parameters!");
                continue;
            }
            if (!("user".equals(command[2]) || "easy".equals(command[2]) || "medium".equals(command[2]) || "hard".equals(command[2]))) {
                System.out.println("Bad parameters!");
                continue;
            }

            break;
        }
        return command;
    }

    public static Player[] getPlayers(String[] command) {
        Player[] players = new Player[2];

        for (int i = 0; i < 2; i++) {
            if ("easy".equals(command[i + 1])) {
                players[i] = new Easy();
            }
            if ("medium".equals(command[i + 1])) {
                players[i] = new Medium();
            }
            if ("hard".equals(command[i + 1])) {
                players[i] = new Hard();
            }
            if ("user".equals(command[i + 1])) {
                players[i] = new User();
            }
        }
        return players;
    }
}
