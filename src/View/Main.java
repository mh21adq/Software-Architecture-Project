package View;
import Model.*;
import java.util.ArrayList;
import Controller.*;
import Model.Participant.*;
import Model.Staff.*;

public class Main {

    public static void main(String[] args) {
        testNameClass();
        testLevelEnum();
        testCompetitorClass();
        testGamerClass();
        testIceSkaterClass();
        testCompetitorList();
        testStaffClass();
        testStaffList();
        managerClassTest();
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
        iceSkater.setScores(new int[]{1, 2, 4, 3});
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
        competitorList.getCompetitorsByCategory(Category.GAMING).forEach(c -> System.out.println(c.getName().getFullName()));

        // Sort by age
        System.out.println("Competitors sorted by age in GAMING:");
        competitorList.sortByAge(Category.GAMING, Level.EXPERT).forEach(c -> System.out.println(c.getName().getFullName() + ", Age: " + c.getAge()));

        // Sort by first name
        System.out.println("Competitors sorted by first name in ICE SKATING:");
        competitorList.sortByFirstName(Category.ICE_SKATING, Level.ADVANCED).forEach(c -> System.out.println(c.getName().getFullName()));

        // Find @highest scoring competitor
        Competitor topScorer = competitorList.highestScoringCompetitor(Category.GAMING, Level.EXPERT);
        System.out.println("Top Scorer in GAMING: " + (topScorer != null ? topScorer.getName().getFullName() : "Not Available"));
    }
    private static void testStaffClass() {
        Name staffName = new Name("John", "Doe");
        Role staffRole = Role.DATA_ENTRY;
        Staff staff = new Staff(staffName, 123, staffRole);

        // Test getName method
        System.out.println("Testing getName:");
        System.out.println("Expected: John Doe, Actual: " + staff.getName().getFullName());

        // Test setName method
        System.out.println("\nTesting setName:");
        Name newName = new Name("Jane", "Doe");
        staff.setName(newName);
        System.out.println("Expected: Jane Doe, Actual: " + staff.getName().getFullName());

        // Test getStaffId method
        System.out.println("\nTesting getStaffId:");
        System.out.println("Expected: 123, Actual: " + staff.getStaffId());

        // Test getStaffRole method
        System.out.println("\nTesting getStaffRole:");
        System.out.println("Expected: " + staffRole + ", Actual: " + staff.getStaffRole());
    }
    private static void testStaffList() {
        String testFilePath = "src/RunStaff.csv";
        StaffList staffList = new StaffList();
        staffList.readFile(testFilePath);
        int testStaffId = 1111;
        Staff foundStaff = staffList.findStaffById(testStaffId);
        if (foundStaff != null) {
            System.out.println("Found Staff: " + foundStaff.getName().getFullName() + ", ID: " + foundStaff.getStaffId() + ", Role: " + foundStaff.getStaffRole());
        } else {
            System.out.println("Staff member with ID " + testStaffId + " not found.");
        }
    }

    private static void managerClassTest() {
        // Initialize Manager
        Manager manager = new Manager();

        // Test: Reading competitors from a file
        String testFilePath = "src/RunCompetitor.csv";
        manager.readFromFile(testFilePath);

        // Test: Adding a new competitor
        Name newName = new Name("Test", "User");
        Competitor newCompetitor = new Gamer(newName, "test@example.com", 20, "Male", "TestCountry", Level.EXPERT);
        boolean addResult = manager.addCompetitor(newCompetitor);
        System.out.println("Add Competitor Result: " + addResult);

        // Test: Removing a competitor (assuming a competitor with ID exists)
        boolean removeResult = manager.removeCompetitor(100);
        System.out.println("Remove Competitor Result: " + removeResult);

        // Test: Retrieving a competitor
        Competitor retrievedCompetitor = manager.getCompetitor(102);
        if (retrievedCompetitor != null) {
            System.out.println("Retrieved Competitor: " + retrievedCompetitor.getFullDetails());
        } else {
            System.out.println("Competitor not found.");
        }

        // Test: Sorting competitors by score
        Level level = Level.EXPERT;
        System.out.println("Sorted Competitors by Score:");
        manager.sortCompetitorsByScore(Category.GAMING, level).forEach(c -> System.out.println(c.getName().getFullName()));

        // Test: Finding @highest scoring competitor
        Competitor highestScorer = manager.highestScoringCompetitor(Category.GAMING, level);
        if (highestScorer != null) {
            System.out.println("Highest Scorer: " + highestScorer.getName().getFullName());
        } else {
            System.out.println("No highest scorer found.");
        }

        // Test: Generating final report
        String reportFileName = "Test_report_writing_in_Main.txt";
        manager.generateFinalReport(reportFileName);
        System.out.println("Generated report: " + reportFileName);

    }


}
