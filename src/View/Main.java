package View;
import Model.*;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
        testNameClass();
        testLevelEnum();
        testCompetitorClass();
        testGamerClass();
        testIceSkaterClass();
        testCompetitorList();
    }

    private static void testNameClass() {
        System.out.println("Testing Name Class");
        Name name = new Name("John", "Edward", "Doe");
        System.out.println("Full Name: " + name.getFullName());
        System.out.println("Initials: " + name.getInitials());
        System.out.println();
    }

    private static void testLevelEnum() {
        System.out.println("Testing Level Enum");
        for (Level level : Level.values()) {
            System.out.println("Level: " + level);
        }
        System.out.println();
    }

    private static void testCompetitorClass() {
        System.out.println("Testing Competitor Class");
        // Since Competitor is abstract, test it through a subclass
        Gamer gamer = new Gamer(new Name("Alice"), "alice@example.com", 30, "Female", "USA", Level.EXPERT);
        System.out.println("Competitor Number: " + gamer.getCompetitorNumber());
        System.out.println("Competitor Email: " + gamer.getEmail());
        System.out.println();
    }

    private static void testGamerClass() {
        System.out.println("Testing Gamer Class");
        Gamer gamer = new Gamer(new Name("Bob"), "bob@example.com", 25, "Male", "UK", Level.NOVICE);
        gamer.setScores(new int[]{5, 4, 3, 2, 1});
        System.out.println("Overall Score: " + gamer.getOverallScore());
        System.out.println("Gamer Category: " + gamer.getCategory());
        System.out.println();
    }

    private static void testIceSkaterClass() {
        System.out.println("Testing IceSkater Class");
        IceSkater iceSkater = new IceSkater(new Name("Charlie"), "charlie@example.com", 22, "Non-Binary", "Canada", Level.BEGINNER);
        iceSkater.setScores(new int[]{6, 5, 4, 3});
        System.out.println("Overall Score: " + iceSkater.getOverallScore());
        System.out.println("Ice Skater Category: " + iceSkater.getCategory());
        System.out.println();
    }
    private static void testCompetitorList() {
        CompetitorList competitorList = CompetitorList.getInstance();

        // Add some competitors
        Competitor gamer = new Gamer(new Name("Alice"), "alice@example.com", 30, "Female", "USA", Level.EXPERT);
        Competitor iceSkater = new IceSkater(new Name("Bob"), "bob@example.com", 25, "Male", "Canada", Level.ADVANCED);

        System.out.println("Adding competitors:");
        System.out.println("Added Alice: " + competitorList.addCompetitor(gamer));
        System.out.println("Added Bob: " + competitorList.addCompetitor(iceSkater));

        // Attempt to add a duplicate
        System.out.println("Adding duplicate (Alice): " + competitorList.addCompetitor(gamer));

        // Remove a competitor
        System.out.println("Removing Bob: " + competitorList.removeCompetitor(iceSkater.getCompetitorNumber()));

        // Retrieve a competitor
        Competitor retrievedCompetitor = competitorList.getCompetitor(gamer.getCompetitorNumber());
        System.out.println("Retrieved Competitor: " + (retrievedCompetitor != null ? retrievedCompetitor.getName().getFullName() : "Not Found"));

        // List all competitors
        System.out.println("All Competitors: ");
        ArrayList<Competitor> allCompetitors = competitorList.getAllCompetitors();
        allCompetitors.forEach(c -> System.out.println(c.getName().getFullName()));

        // Get competitors by category
        System.out.println("Competitors in GAMING category: ");
        competitorList.getCompetitorsByCategory("GAMING").forEach(c -> System.out.println(c.getName().getFullName()));

        // Sort by age
        System.out.println("Competitors sorted by age in GAMING:");
        competitorList.sortByAge("GAMING", Level.EXPERT).forEach(c -> System.out.println(c.getName().getFullName() + ", Age: " + c.getAge()));

        // Sort by first name
        System.out.println("Competitors sorted by first name in ICE SKATING:");
        competitorList.sortByFirstName("ICE SKATING", Level.ADVANCED).forEach(c -> System.out.println(c.getName().getFullName()));

        // Find @highest scoring competitor
        Competitor topScorer = competitorList.highestScoringCompetitor("GAMING", Level.EXPERT);
        System.out.println("Top Scorer in GAMING: " + (topScorer != null ? topScorer.getName().getFullName() : "Not Available"));
    }
}
