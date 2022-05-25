import java.util.Random;

public class Easy implements Player {

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
