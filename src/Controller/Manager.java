package Controller;
import Model.*;
import View.*;
import java.io.*;
import java.util.*;
public class Manager {
    private CompetitorList competitorList = new CompetitorList();

    public void readFromFile(String fileName) {
        try {
            File file = new File(fileName);
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line;

            while ((line = bufferedReader.readLine()) != null) {
                String[] data = line.split(",");
                Name name = createNameFromFullName(data[0].trim());
                String email = data[1].trim();
                int age = Integer.parseInt(data[2].trim());
                String gender = data[3].trim();
                Level level = Level.valueOf(data[4].trim().toUpperCase());
                String category = data[5].trim();
                String country = data[6].trim();

                int[] scores = parseScores(data, 7);
                Competitor competitor = createCompetitor(category, name, email, age, gender, country, level, scores);
                competitorList.addCompetitor(competitor);
            }
            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid data format: " + e.getMessage());
        }
    }
    private int[] parseScores(String[] data, int startIndex) {
        int[] scores = new int[data.length - startIndex];
        for (int i = startIndex; i < data.length; i++) {
            try {
                scores[i - startIndex] = Integer.parseInt(data[i].trim());
            } catch (NumberFormatException e) {
                System.out.println("Invalid score format at index " + i + ": " + data[i]);
                // Set a default score value, e.g., 0, or handle as needed
                scores[i - startIndex] = 0;
            }
        }
        return scores;
    }


    private Competitor createCompetitor(String category, Name name, String email, int age, String gender, String country, Level level, int[] scores) {
        Competitor competitor;
        if ("ICE SKATING".equalsIgnoreCase(category)) {
            competitor = new IceSkater(name, email, age, gender, country, level);
        } else {
            competitor = new Gamer(name, email, age, gender, country, level);
        }
        competitor.setScores(scores);
        return competitor;
    }

    public void writeToFile(String filename) {
        try (FileWriter fileWriter = new FileWriter(filename, true);
             BufferedWriter bufferedWriter = new BufferedWriter(fileWriter)) {

            for (Competitor competitor : competitorList.getAllCompetitors()) {
                String competitorData = formatCompetitorData(competitor);
                bufferedWriter.write(competitorData);
                bufferedWriter.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String formatCompetitorData(Competitor competitor) {
        System.out.println(competitor.getScores());
        return competitor.getCompetitorNumber() + "," +
                competitor.getName() + "," +
                competitor.getEmail() + "," +
                competitor.getAge() + "," +
                competitor.getGender() + "," +
                competitor.getCountry() + "," +
                competitor.getCategory() + "," +
                competitor.getLevel() + "," +competitor.getScores();

    }

    public Name createNameFromFullName(String fullName) {
        String[] nameParts = fullName.trim().split("\\s+");
        switch (nameParts.length) {
            case 1: return new Name(nameParts[0]);
            case 2: return new Name(nameParts[0], nameParts[1]);
            case 3: return new Name(nameParts[0], nameParts[1], nameParts[2]);
            default: return new Name(nameParts[0], nameParts[1], nameParts[2]); // Handle more than three parts
        }
    }

    // Delegated methods to CompetitorList
    public void addCompetitor(Competitor competitor) {
        competitorList.addCompetitor(competitor);
    }

    public void removeCompetitor(Competitor competitor) {
        competitorList.removeCompetitor(competitor);
    }

    public ArrayList<Competitor> getCompetitorsByCategory(String category) {
        return competitorList.getCompetitorsByCategory(category);
    }

    public ArrayList<Competitor> searchCompetitorsByLevel(String category, Level level) {
        return competitorList.searchCompetitorsByLevel(category, level);
    }

    public Competitor getCompetitor(int competitorId) {
        return competitorList.getCompetitor(competitorId);
    }

    public ArrayList<Competitor> getAllCompetitors() {
        return competitorList.getAllCompetitors();
    }

    public void Print()
    {
        for(Competitor competitor:competitorList.getAllCompetitors())
        {
            System.out.println(competitor.getFullDetails());

        }
    }
    public String printCompetitorsTable() {
        CompetitorList newCompetitorListInstance = new CompetitorList();
        ArrayList<Competitor> iceSkaters=newCompetitorListInstance.getCompetitorsByCategory("ICE SKATING");
        String allCompetitors="";
        allCompetitors+="\n################Ice Skaters###############\n";
        for (Competitor competitor : iceSkaters) {
            allCompetitors+=competitor.getFullDetails();
        }

        ArrayList<Competitor> gamers=newCompetitorListInstance.getCompetitorsByCategory("GAMING");
        allCompetitors+="\n########Electronic Gamers################\n";
        for (Competitor competitor : gamers) {
            allCompetitors+=competitor.getFullDetails();
        }
        return allCompetitors;
    }

    public void highestScoringCompetitor(String category, Level level)
    {
        CompetitorList newCompetitorListInstance = new CompetitorList();
        ArrayList<Competitor> competitorsInLevel=newCompetitorListInstance.searchCompetitorsByLevel("ICE SKATING",level);
        competitorsInLevel.sort(Comparator.comparingDouble(Competitor::getOverallScore).reversed());
        System.out.println(competitorsInLevel.get(0).getFullDetails());
    }
    public Competitor searchCompetitor(int id)
    {
        CompetitorList competitorList = new CompetitorList();
        Competitor competitorIs=competitorList.getCompetitor(id);
        if(competitorIs==null)
        {
            System.out.println("\nInvalid Competitor Number");
        }

        return competitorIs;
    }
    public boolean isCompetitionCompleted(String category, Level level) {
        ArrayList<Competitor> competitorsInLevel = competitorList.searchCompetitorsByLevel(category, level);
        if (competitorsInLevel.isEmpty()) {
            return false; // No competitors in the given category and level
        }

        for (Competitor competitor : competitorsInLevel) {
            if (!isCompetitorScoreComplete(competitor)) {
                return false; // Found a competitor with incomplete scores
            }
        }

        return true; // All competitors in the specified category and level have complete scores
    }

    private boolean isCompetitorScoreComplete(Competitor competitor) {
        int[] scores = competitor.getScoresArray(); // Assuming Competitor class has this method
        if (scores == null || scores.length == 0) {
            return false;
        }

        for (int score : scores) {
            if (score == 0) {
                return false; // Score is incomplete
            }
        }

        return true; // All scores are non-zero
    }



    public void openStaffGUI(Manager manager) {
      new StaffGUI(manager);
    }

    public void openCompetitorGUI() {
        // Directly create and display the StaffGUI
        new CompetitorGUI();
    }
    public void openAudienceGUI(Manager manager) {
        // Directly create and display the StaffGUI

       new AudienceGUI(manager);
    }


}
