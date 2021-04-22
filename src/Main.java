import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.Random;

class Board {
    private final String[][] board;

    public Board() {
        board = new String[3][3];
        emptyBoard();
    }

    public void emptyBoard() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                board[i][j] = " ";
            }
        }
    }

    public void printBoard() {
        System.out.println("---------");
        for (int i = 0; i < 3; i++) {
            System.out.print("|");
            for (int j = 0; j < 3; j++) {
                System.out.print(" " + board[i][j]);
            }
            System.out.println(" |");
        }
        System.out.println("---------");
    }

    public boolean isAvailable(int i, int j) {
        return " ".equals(board[i][j]);
    }

    public void updateBoard(int i, int j, String mark) {
        board[i][j] = mark;
    }

    public boolean win(String mark) {
        for (int i = 0; i < 3; i++) {
            if (board[i][0].equals(mark) && board[i][1].equals(mark) && board[i][2].equals(mark)) {
                return true;
            }
            if (board[0][i].equals(mark) && board[1][i].equals(mark) && board[2][i].equals(mark)) {
                return true;
            }
        }
        if (board[0][0].equals(mark) && board[1][1].equals(mark) && board[2][2].equals(mark)) {
            return true;
        }
        if (board[0][2].equals(mark) && board[1][1].equals(mark) && board[2][0].equals(mark)) {
            return true;
        }

        return false;
    }

    public boolean endGame() {
        boolean res = true;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (" ".equals(board[i][j])) {
                    res = false;
                    break;
                }
            }
        }
        return res;
    }

}

interface Player {

    void makeMove(Board board, String mark);
}

class User implements Player {

    final private Scanner scanner = new Scanner(System.in);

    @Override
    public void makeMove(Board board, String mark) {
        int i;
        int j;
        while (true) {
            System.out.print("Enter the coordinates: ");

            try {
                i = scanner.nextInt() - 1;
                j = scanner.nextInt() - 1;
                scanner.nextLine(); // clean \r\n

                //checking if i or j is invalid
                if (!board.isAvailable(i, j)) {
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
        board.updateBoard(i, j, mark);
    }
}

class Easy implements Player {

    final private Random random = new Random();

    @Override
    public void makeMove(Board board, String mark) {
        int i;
        int j;
        do {
            i = random.nextInt(3);
            j = random.nextInt(3);
        } while (!board.isAvailable(i, j));
        System.out.println("Making move level \"easy\"");
        board.updateBoard(i, j, mark);
    }
}

class Medium implements Player {

    final static Random random = new Random();

    @Override
    public void makeMove(Board board, String mark) {

        int iMove;
        int jMove;
        do {
            iMove = random.nextInt(3);
            jMove = random.nextInt(3);
        } while (!board.isAvailable(iMove, jMove));

        String oppositeMark = ("X".equals(mark)) ? "O" : "X";

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board.isAvailable(i, j)) {
                    board.updateBoard(i, j, oppositeMark);
                    if (board.win(oppositeMark)) {
                        iMove = i;
                        jMove = j;
                    }
                    board.updateBoard(i, j, " ");
                }
            }
        }

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board.isAvailable(i, j)) {
                    board.updateBoard(i, j, mark);
                    if (board.win(mark)) {
                        iMove = i;
                        jMove = j;
                    }
                    board.updateBoard(i, j, " ");
                }
            }
        }

        System.out.println("Making move level \"medium\"");
        board.updateBoard(iMove, jMove, mark);
    }
}

class Hard implements Player {

    private int minimax(Board board, String AIMark, boolean AITurn) {
        if (board.win(AIMark)) {
            return 1;
        }
        if (board.win("X".equals(AIMark) ? "O" : "X")) {
            return -1;
        }
        if (board.endGame()) {
            return 0;
        }

        if (AITurn) {
            int best = Integer.MIN_VALUE;

            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (board.isAvailable(i, j)) {
                        board.updateBoard(i, j, AIMark);
                        best = Math.max(best, minimax(board, AIMark, false));
                        board.updateBoard(i, j, " ");
                    }
                }
            }
            return best;
        } else {
            int best = Integer.MAX_VALUE;

            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (board.isAvailable(i, j)) {
                        board.updateBoard(i, j, "X".equals(AIMark) ? "O" : "X");
                        best = Math.min(best, minimax(board, AIMark, true));
                        board.updateBoard(i, j, " ");
                    }
                }
            }
            return best;
        }
    }

    @Override
    public void makeMove(Board board, String mark) {
        int bestValue = Integer.MIN_VALUE;
        int iMove = -1;
        int jMove = -1;

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board.isAvailable(i, j)) {
                    board.updateBoard(i, j, mark);
                    int moveValue = minimax(board, mark, false);
                    board.updateBoard(i, j, " ");

                    if (moveValue > bestValue) {
                        bestValue = moveValue;
                        iMove = i;
                        jMove = j;
                    }
                }

            }
        }
        System.out.println("Making move level \"hard\"");
        board.updateBoard(iMove, jMove, mark);
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


public class Main {
    final static Board BOARD = new Board();

    public static void main(String[] args) {
        while (true) {
            String[] command = Menu.getCommand();
            if ("exit".equals(command[0])) {
                break;
            }

            Player[] players = Menu.getPlayers(command);
            for (int i = 0; i < 9; i++) {
                BOARD.printBoard();
                players[i % 2].makeMove(BOARD, (i % 2 == 0) ? "X" : "O");

                if (BOARD.win("X")) {
                    BOARD.printBoard();
                    System.out.println("X wins");
                    break;
                } else if (BOARD.win("O")) {
                    BOARD.printBoard();
                    System.out.println("O wins");
                    break;
                } else if (BOARD.endGame()) {
                    BOARD.printBoard();
                    System.out.println("Draw");
                }
            }
            BOARD.emptyBoard();
        }
    }
}