import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class RandomWord {
    public static void main(String[] args) {
        String champion = StdIn.readString();
        int count = 2;
        while (!StdIn.isEmpty()) {
            String candidate = StdIn.readString();
            if (StdRandom.bernoulli(1.0 / (double) count)) {
                champion = candidate;
            }
            count++;
        }
        StdOut.println(champion);
    }
}
