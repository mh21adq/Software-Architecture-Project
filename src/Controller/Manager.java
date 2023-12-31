package Controller;
import Model.*;
import View.*;
import java.io.*;
import java.util.*;
public class Manager {
    private final CompetitorList competitorList;
    private static final String[] CATEGORIES = {"ICE SKATING", "GAMING"};
    private static final Level[] LEVELS = Level.values();

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
        Competitor competitor = CATEGORIES[0].equalsIgnoreCase(category) ?
                new IceSkater(name, email, age, gender, country, level) :
                new Gamer(name, email, age, gender, country, level);
        competitor.setScores(scores);
        return competitor;
    }

    public void generateFinalReport(String filename) {
        StringBuilder report = new StringBuilder();

        // Iterate through each category
        for (String category : CATEGORIES) {
            // Append the competitors table for the category
            report.append("==== Competitors in Category: ").append(category).append(" ====\n");
            report.append(competitorList.getCompetitorsTable(category)).append("\n");

            // Iterate through each level
            for (Level level : LEVELS) {
                // Append top scorers by category and level
                report.append("***** Top Scorers by Category and Level *****\n");
                report.append("-- Category: ").append(category).append(" --\n");
                report.append("-- Level: ").append(level).append(" --\n");
                report.append(competitorList.getTopScorerDetails(category, level)).append("\n");
            }

            // Append summary statistics and score frequency report
            report.append("==== Summary Statistics ====\n");
            report.append(competitorList.getSummaryStatistics()).append("\n");
            report.append("==== Score Frequency Report ====\n");
            report.append(competitorList.getScoreFrequencyReport()).append("\n");
        }

        // Output to System.out
        System.out.println(report);

        // Write to file
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            writer.write(report.toString());
        } catch (IOException e) {
            System.err.println("Error writing to file '" + filename + "': " + e.getMessage());
        }
    }


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

        return competitorList.getCompetitorsTable(category);
    }
    public Competitor highestScoringCompetitor(String category, Level level)
    {
       return competitorList.highestScoringCompetitor(category,level);
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
        int[] scores = competitor.getScoreArray(); // Assuming Competitor class has this method
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
