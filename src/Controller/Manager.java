package Controller;
import Model.*;
import View.*;
import java.io.*;
import java.util.*;
public class Manager {
    private final CompetitorList competitorList;
    private static final String ICE_SKATING = "ICE SKATING";

    public Manager() {
        competitorList = CompetitorList.getInstance();
    }

    public void readFromFile(String fileName) {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                processLine(line);
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
    }

    private void processLine(String line) {
        try {
            String[] data = line.split(",");

            Name name = parseName(data[0]);
            String email = data[1].trim();
            int age = Integer.parseInt(data[2].trim());
            String gender = data[3].trim();
            Level level = Level.valueOf(data[4].trim().toUpperCase());
            String category = data[5].trim();
            String country = data[6].trim();
            int[] scores = parseScores(data);

            Competitor competitor = createCompetitor(name, email, age, gender, country, level, category, scores);
            addCompetitor(competitor);
        } catch (IllegalArgumentException e) {
            System.err.println("Error processing line: " + line + "; Error: " + e.getMessage());
        }
    }
    private Name parseName(String nameData) {
        String[] nameParts = nameData.trim().split("\\s+");
        return switch (nameParts.length) {
            case 1 -> new Name(nameParts[0]);
            case 2 -> new Name(nameParts[0], nameParts[1]);
            default -> new Name(nameParts[0], nameParts[1], nameParts[2]);
        };
    }


    private int[] parseScores(String[] data) {
        int[] scores = new int[data.length - 7];
        for (int i = 7; i < data.length; i++) {
            try {
                scores[i - 7] = Integer.parseInt(data[i].trim());
            } catch (NumberFormatException e) {
                scores[i - 7] = 0; // Default score in case of format error
            }
        }
        return scores;
    }

    private Competitor createCompetitor(Name name, String email, int age, String gender, String country, Level level, String category, int[] scores) {
        Competitor competitor = ICE_SKATING.equalsIgnoreCase(category) ?
                new IceSkater(name, email, age, gender, country, level) :
                new Gamer(name, email, age, gender, country, level);
        competitor.setScores(scores);
        return competitor;
    }

    public void writeToFile(String filename) {
        try (FileWriter fileWriter = new FileWriter(filename, false);
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
        return competitor.getCompetitorNumber() + "," +
                competitor.getName().getFullName() + "," +
                competitor.getEmail() + "," +
                competitor.getAge() + "," +
                competitor.getGender() + "," +
                competitor.getCountry() + "," +
                competitor.getCategory() + "," +
                competitor.getLevel() + "," +competitor.getScores();

    }



    // Delegated methods to CompetitorList
    public boolean addCompetitor(Competitor competitor) {
        for (Competitor existingCompetitor : getAllCompetitors()) {
            if (existingCompetitor.getEmail().equalsIgnoreCase(competitor.getEmail()) &&
                    existingCompetitor.getCategory().equalsIgnoreCase(competitor.getCategory())) {
                return false;
            }
        }
        competitorList.addCompetitor(competitor);
        return true;
    }


    public boolean removeCompetitor(int competitorNumber) {
        return  competitorList.removeCompetitor(competitorNumber);
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


    public String competitorsTable(String category) {
        ArrayList<Competitor> competitors = getCompetitorsByCategory(category);
        StringBuilder allCompetitors = new StringBuilder();

        for (Competitor competitor : competitors) {
            allCompetitors.append(competitor.getFullDetails());
        }

        return allCompetitors.toString();
    }
    public Competitor highestScoringCompetitor(String category, Level level)
    {

        ArrayList<Competitor> competitorsInLevel=competitorList.searchCompetitorsByLevel(category,level);
        competitorsInLevel.sort(Comparator.comparingDouble(Competitor::getOverallScore).reversed());

       return competitorsInLevel.get(0);
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

    public void openCompetitorGUI(Manager manager) {
        // Directly create and display the StaffGUI
        new CompetitorGUI(manager);
    }
    public void openAudienceGUI(Manager manager) {
        // Directly create and display the StaffGUI

       new AudienceGUI(manager);
    }


}
