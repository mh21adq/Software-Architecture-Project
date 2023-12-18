package View;
import Model.Name;
import Model.Level;
import Model.IceSkater;
import Model.Gamer;
public class Main {
    public static void main(String[] args) {
        // Create a Name object for the Gamer
        Name gamerName = new Name("John", "Doe");

        // Create a Gamer object
        Gamer gamer = new Gamer(gamerName, "john@example.com", 25, "Male", "USA", Level.INTERMEDIATE);

        // Set scores for the Gamer
        int[] scores = {90, 85, 78, 92, 88};
        gamer.setScores(scores);

        // Display full details of the Gamer
        System.out.println("Full Details:");
        System.out.println(gamer.getFullDetails());

        // Display short details of the Gamer
        System.out.println("\nShort Details:");
        System.out.println(gamer.getShortDetails());
    }
}
