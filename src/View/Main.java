package View;
import Model.*;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        //nameClassTest();
        //gamerClassTest();
        competitorListClassTest();

    }
    public static void nameClassTest()
    {
        System.out.println("\n*******************Name Class Test******************");
        Name firstName=new Name("Numan");
        System.out.println("Full Name:"+firstName.getFullName());
        System.out.println("Initials:"+firstName.getInitials());
        System.out.println("First Name:"+firstName.getFirstName());
        firstName.setFirstName("Muhammad");
        System.out.println("New First Name:"+firstName.getFirstName());

        System.out.println("Middle Name:"+firstName.getMiddleName());
        firstName.setMiddleName("Numan");
        System.out.println("New Middle Name:"+firstName.getMiddleName());

        System.out.println("Surname Name:"+firstName.getSurname());
        firstName.setSurname("Hussain");
        System.out.println("New Surname Name:"+firstName.getSurname());


        Name firstAndLastName=new Name("Numan","Hussain");
        System.out.println("Full Name:"+firstAndLastName.getFullName());
        System.out.println("Initials:"+firstAndLastName.getInitials());
        Name fullName =new Name("Md","Numan","Hussain");
        System.out.println("Full Name:"+fullName.getFullName());
        System.out.println("Initials:"+fullName.getInitials());
        System.out.println("\n************************************************\n");
    }
    public static void gamerClassTest()
    {
        Name fullName =new Name("Md","Numan","Hussain");
        Gamer gamer =new Gamer(fullName,"mh21adq@herts.ac.uk",23,"Male","BD",Level.ADVANCED);
        System.out.println("Number:"+gamer.getCompetitorNumber());
        System.out.println(gamer.getFullDetails());
    }
    public static void competitorListClassTest() {
        // Create Competitor List
        CompetitorList competitorList = new CompetitorList();

        // Create Competitors
        Name fullName = new Name("Md", "Numan", "Hussain");
        Gamer gamer = new Gamer(fullName, "mh21adq@herts.ac.uk", 23, "Male", "BD", Level.ADVANCED);
        Name fullName1 = new Name("Jane", "Doe");
        Gamer gamer1 = new Gamer(fullName1, "jane.doe@example.com", 25, "Female", "US", Level.BEGINNER);

        // Add competitors
        competitorList.addCompetitor(gamer);
        competitorList.addCompetitor(gamer1);

        // Check if competitors are added
        System.out.println("Competitors Added: " + competitorList.getAllCompetitors().size());

        // Attempt to add a duplicate
        competitorList.addCompetitor(gamer);  // Assuming your system allows duplicates
        System.out.println("Competitors after adding duplicate: " + competitorList.getAllCompetitors().size());

        // Remove a competitor
        competitorList.removeCompetitor(gamer1);
        System.out.println("Competitors after removal: " + competitorList.getAllCompetitors().size());

        // Retrieve by Category
        System.out.println("Competitors in ADVANCED: " + competitorList.getCompetitorsByCategory("ADVANCED").size());

        // Retrieve by Level
        System.out.println("Competitors in Level BEGINNER: " + competitorList.searchCompetitorsByLevel("GAMING", Level.ADVANCED).size());

        // Retrieve by ID
        Competitor foundCompetitor = competitorList.getCompetitor(gamer.getCompetitorNumber());
        System.out.println("Found Competitor: " + (foundCompetitor != null ? foundCompetitor.getName() : "Not Found"));
    }

}
