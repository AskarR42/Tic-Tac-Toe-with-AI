public class Board {

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
