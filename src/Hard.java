public class Hard implements Player {

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
