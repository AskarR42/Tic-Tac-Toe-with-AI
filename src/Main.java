import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.Random;

class Field {
    private final String[][] desk;

    Field() {
        desk = new String[3][3];
        emptyField();
    }

    public void printField() {
        System.out.println("---------");
        for (int i = 0; i < 3; i++) {
            System.out.print("|");
            for (int j = 0; j < 3; j++) {
                System.out.print(" " + desk[i][j]);
            }
            System.out.println(" |");
        }
        System.out.println("---------");
    }

    public void emptyField() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                desk[i][j] = " ";
            }
        }
    }

    public boolean isNotAvailable(int i, int j) {
        return !" ".equals(desk[i][j]);
    }

    public void updateField(int i, int j, String s) {
        desk[i][j] = s;
    }

    public boolean wins(String s) {
        for (int i = 0; i < 3; i++) {
            if (desk[i][0].equals(s) && desk[i][1].equals(s) && desk[i][2].equals(s)) {
                return true;
            }
            if (desk[0][i].equals(s) && desk[1][i].equals(s) && desk[2][i].equals(s)) {
                return true;
            }
        }
        if (desk[0][0].equals(s) && desk[1][1].equals(s) && desk[2][2].equals(s)) {
            return true;
        }
        if (desk[0][2].equals(s) && desk[1][1].equals(s) && desk[2][0].equals(s)) {
            return true;
        }

        return false;
    }

    public boolean canWin(int i, int j, String s) {
        updateField(i, j, s);
        boolean res = wins(s);
        updateField(i, j, " ");
        return res;
    }
}

interface Player {

    void makeMove(Field field, String s);
}

class User implements Player {

    final private Scanner scanner = new Scanner(System.in);

    @Override
    public void makeMove(Field field, String s) {
        int i;
        int j;
        while (true) {
            System.out.print("Enter the coordinates: ");

            try {
                i = scanner.nextInt() - 1;
                j = scanner.nextInt() - 1;
                scanner.nextLine(); // clean \r\n

                //checking if i and j is invalid
                if (field.isNotAvailable(i, j)) {
                    System.out.println("This cell is occupied! Choose another one!");
                    continue;
                }
                if (i < 0 || i > 2 || j < 0 || j > 2) {
                    System.out.println("Coordinates should be from 1 to 3!");
                    continue;
                }

                break;
            } catch (InputMismatchException e) {
                System.out.println("You should enter numbers!");
                scanner.next();
            }
        }
        field.updateField(i, j, s);

    }
}

class Easy implements Player {

    final private Random random = new Random();

    @Override
    public void makeMove(Field field, String s) {
        int i;
        int j;
        do {
            i = random.nextInt(3);
            j = random.nextInt(3);
        } while (field.isNotAvailable(i, j));
        System.out.println("Making move level \"easy\"");
        field.updateField(i, j, s);
    }
}

class Medium implements Player {

    final static Random random = new Random();

    @Override
    public void makeMove(Field field, String s) {

        int i;
        int j;
        do {
            i = random.nextInt(3);
            j = random.nextInt(3);
        } while (field.isNotAvailable(i, j));

        String opposite = ("X".equals(s)) ? "O" : "X";

        for (int k = 0; k < 3; k++) {
            for (int l = 0; l < 3; l++) {
                if (!field.isNotAvailable(k, l)) {
                    if (field.canWin(k, l, opposite)) {
                        i = k;
                        j = l;
                    }
                }
            }
        }

        for (int k = 0; k < 3; k++) {
            for (int l = 0; l < 3; l++) {
                if (!field.isNotAvailable(k, l)) {
                    if (field.canWin(k, l, s)) {
                        i = k;
                        j = l;
                    }
                }
            }
        }

        System.out.println("Making move level \"medium\"");
        field.updateField(i, j, s);
    }
}

class Menu {
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
            if (!("user".equals(command[1]) || "easy".equals(command[1]) || "medium".equals(command[1]))) {
                System.out.println("Bad parameters!");
                continue;
            }
            if (!("user".equals(command[2]) || "easy".equals(command[2]) || "medium".equals(command[2]))) {
                System.out.println("Bad parameters!");
                continue;
            }

            break;
        }
        return command;
    }

    public static Player[] getPlayers(String[] command) {
        Player[] players = new Player[2];
        if (command == null) {
            return null;
        }

        for (int i = 0; i < 2; i++) {
            if ("easy".equals(command[i + 1])) {
                players[i] = new Easy();
            }
            if ("medium".equals(command[i + 1])) {
                players[i] = new Medium();
            }
            if ("user".equals(command[i + 1])) {
                players[i] = new User();
            }
        }
        return players;
    }
}


public class Main {
    final static Field field = new Field();

    public static void main(String[] args) {
        while (true) {
            String[] command = Menu.getCommand();
            if ("exit".equals(command[0])) {
                break;
            }

            Player[] players = Menu.getPlayers(command);
            for (int i = 0; i < 9; i++) {
                field.printField();
                players[i % 2].makeMove(field, (i % 2 == 0) ? "X" : "O");

                if (field.wins("X")) {
                    field.printField();
                    System.out.println("X wins");
                    break;
                } else if (field.wins("O")) {
                    field.printField();
                    System.out.println("O wins");
                    break;
                } else if (i == 8) {
                    field.printField();
                    System.out.println("Draw");
                }
            }
        }
    }
}
