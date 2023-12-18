package View;
import Controller.Manager;
import Model.*;

import java.util.ArrayList;

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

         /* System.out.println("Name class test\n");
        Name firstname = new Name("Numan");
        System.out.println(firstname.getFirstName());
        System.out.println(firstname.getMiddleName());
        System.out.println(firstname.getFullName());
        System.out.println(firstname.getInitials());

        Name firstAndMiddle = new Name("Numan", "Hussain");
        System.out.println(firstAndMiddle.getFirstName());
        System.out.println(firstAndMiddle.getMiddleName());
        System.out.println(firstAndMiddle.getFullName());
        System.out.println(firstAndMiddle.getInitials());

        Name fullName = new Name("Numan", "Hussain", "Mahim");
        System.out.println(fullName.getFirstName());
        System.out.println(fullName.getMiddleName());
        System.out.println(fullName.getFullName());
        System.out.println(fullName.getInitials());


        Name name = new Name("John Doe");

        // Create a Gamer
        Gamer gamer = new Gamer(name, "johndoe@example.com", 25, "Male", "USA", LevelInteger.LEVEL_TWO);
        CompetitorList competitorList=new CompetitorList();
        competitorList.addCompetitor(gamer);
        gamer.setScores(new int[]{80, 90, 95, 87, 88});
        System.out.println("Gamer Full Details: " + gamer.getFullDetails());
        System.out.println("Gamer Short Details: " + gamer.getShortDetails());
        System.out.println("Gamer Category: " + Gamer.getCategory());
        System.out.println("Gamer Level: " + gamer.getLevel().getLevelValue());

        // Create an IceSkater
        IceSkater iceSkater = new IceSkater(name, "janedoe@example.com", 22, "Female", "Canada", LevelString.ADVANCED);
        competitorList.addCompetitor(iceSkater);
        System.out.println("IceSkater Full Details: " + iceSkater.getFullDetails());
        System.out.println("IceSkater Short Details: " + iceSkater.getShortDetails());
        System.out.println("IceSkater Category: " + IceSkater.getCategory());
        System.out.println("IceSkater Level: " + iceSkater.getLevel().name());

        */
        Manager manager=new Manager();
        manager.readFromFile("/Users/mdnumanhussain/Documents/Software Architecture/AssignmentPart-2/src/RunCompetitor.csv");
        Name name = new Name("John Doe");
        IceSkater iceSkater = new IceSkater(name, "janedoe@example.com", 22, "Female", "Canada", Level.ADVANCED);
        //System.out.println(iceSkater.getCategory());
        int[] scores1 = new int[]{1, 2, 3, 4};
        iceSkater.setScores(scores1);
        //System.out.println(iceSkater.getOverallScore(3));
        // System.out.println(iceSkater.getOverallScore());
        CompetitorList newCompetitorListInstance = new CompetitorList();
        //newCompetitorListInstance.addCompetitor(iceSkater);

        ArrayList<Competitor> iceSkaters=newCompetitorListInstance.searchCompetitorsByLevel("GAMING", Level.BEGINNER);
        //manager.printCompetitorsTable();


/*
       for (Competitor competitor : iceSkaters) {
          System.out.println(competitor.getFullDetails());
      }

        //manager.printCompetitorsTable();

*/

        // manager.highestScoringCompetitor("GAMING", Level.BEGINNER);

        //manager.searchCompetitor(100);
    }
}
