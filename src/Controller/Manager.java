package Controller;
import Model.CompetitorList;
import Model.Name;
import Model.Level;
import Model.Competitor;
import Model.IceSkater;
import Model.Gamer;
import java.io.*;
import java.util.*;
public class Manager {
    private CompetitorList competitorList = new CompetitorList();
    public void readFromFile(String fname) {
        try {
            File file = new File(fname);
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line;

            while ((line = bufferedReader.readLine()) != null) {
                String[] data = line.split(",");
                int competitorNumber = Integer.parseInt(data[0].trim());
                String fullName = data[1].trim();
                //converting to Name typ from String
                Name name=this.createNameFromFullName(fullName);
                String email = data[2].trim();
                int age = Integer.parseInt(data[3].trim());
                String gender = data[4].trim();
                String levelString = data[5].trim().toUpperCase(); // Convert to uppercase to match enum constants
                Level levelEnum;

                try {
                    levelEnum = Level.valueOf(levelString);
                } catch (IllegalArgumentException e) {
                    System.out.println("Invalid level: " + levelString);
                    // Handle the invalid level case, maybe set a default value or throw an exception
                    levelEnum = Level.BEGINNER; // Example of setting a default value
                }

                String category = data[6].trim();
                String country = data[7].trim();
                // Scores start from the 8th index, parse until the end of the array
                int[] scores = new int[data.length - 8];
                for (int i = 8; i < data.length; i++) {
                    try {
                        scores[i - 8] = Integer.parseInt(data[i].trim());
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid score format: " + data[i]);
                        // Handle the error or assign a default value
                    }
                }

                if (category.toUpperCase().equals("ICE SKATING"))
                {

                    IceSkater iceSkater=new IceSkater(name,email,age,gender,country,levelEnum);
                    iceSkater.setCompetitorNumber(competitorNumber);
                    iceSkater.setScores(scores);
                    competitorList.addCompetitor(iceSkater);


                }
                else if(category.toUpperCase().equals("GAMING")){
                    Gamer gamer=new Gamer(name,email,age,gender,country,levelEnum);
                    gamer.setCompetitorNumber(competitorNumber);
                    gamer.setScores(scores);
                    competitorList.addCompetitor(gamer);


                }



            }
            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Name createNameFromFullName(String fullName) {
        String[] nameParts = fullName.trim().split("\\s+");
        Name name;

        switch (nameParts.length) {
            case 1:
                // Only one name part
                name = new Name(nameParts[0]);
                break;
            case 2:
                // Two name parts
                name = new Name(nameParts[0], nameParts[1]);
                break;
            case 3:
                // Three name parts
                name = new Name(nameParts[0], nameParts[1], nameParts[2]);
                break;
            default:
                // Handle the case where there are more than three name parts
                // For example, use only the first three, or handle it as an error
                name = new Name(nameParts[0], nameParts[1], nameParts[2]);
                break;
        }

        return name;
    }

    public void Print()
    {
        for(Competitor competitor:competitorList.getAllCompetitors())
        {
            System.out.println(competitor.getFullDetails());

        }
    }
    public void printCompetitorsTable() {
        CompetitorList newCompetitorListInstance = new CompetitorList();
        ArrayList<Competitor> iceSkaters=newCompetitorListInstance.getCompetitorsByCategory("ICE SKATING");
        for (Competitor competitor : iceSkaters) {
            System.out.println("#########################Ice Skaters#########################\n"+
                    competitor.getFullDetails());
        }

        ArrayList<Competitor> gamers=newCompetitorListInstance.getCompetitorsByCategory("Gamers");
        for (Competitor competitor : gamers) {
            System.out.println("#########################Electronic Gamer#########################\n"+
                    competitor.getFullDetails());
        }
    }

    public void highestScoringCompetitor(String category, Level level)
    {
        CompetitorList newCompetitorListInstance = new CompetitorList();
        ArrayList<Competitor> competitorsInLevel=newCompetitorListInstance.searchCompetitorsByLevel("ICE SKATING",level);
        competitorsInLevel.sort(Comparator.comparingDouble(Competitor::getOverallScore).reversed());
        System.out.println(competitorsInLevel.get(0).getFullDetails());
    }


}
