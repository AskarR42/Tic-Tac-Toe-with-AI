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