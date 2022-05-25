import java.util.Random;

public class Medium implements Player {

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
