import java.util.InputMismatchException;
import java.util.Scanner;

public class User implements Player {

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
