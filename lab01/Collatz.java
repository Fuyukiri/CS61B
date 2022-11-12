/** Class that prints the Collatz sequence starting from a given number.
 *  @author YOUR NAME HERE
 */
public class Collatz {

<<<<<<< HEAD
    /** Returns the nextNumber in a Collatz sequence. */
    public static int nextNumber(int n) {
        // TODO: Fill in this method.
        return 1;
=======
    /** Buggy implementation of nextNumber! */
    public static int nextNumber(int n) {
        if (n  == 128) {
            return 1;
        } else if (n == 5) {
            return 3 * n + 1;
        } else {
            return n * 2;
        }
>>>>>>> ca91837418f0c4f7449ec7916d28103aed61f5e3
    }

    public static void main(String[] args) {
        int n = 5;
        System.out.print(n + " ");

        // Some starter code to test
        while (n != 1) {          
            n = nextNumber(n);          
            System.out.print(n + " ");
        }
        System.out.println();

    }
}

