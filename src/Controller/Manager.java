package Controller;
import Model.*;
import View.*;
import java.io.*;
import java.util.*;
import java.util.stream.Collectors;
import java.util.function.Function;

public class Manager {
    private final CompetitorList competitorList;
    private static final String[] CATEGORIES = {"ICE SKATING", "GAMING"};
    private static final Level[] LEVELS = Level.values();
    private static final List<Level> GAMING_LEVELS = List.of(Level.NOVICE, Level.EXPERT);
    private static final List<Level> ICE_SKATING_LEVELS = List.of(Level.BEGINNER, Level.INTERMEDIATE, Level.ADVANCED);


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
            report.append(getCompetitorsTable(category)).append("\n");

            // Iterate through each level
            for (Level level : LEVELS) {
                // Append top scorers by category and level
                report.append("***** Top Scorers by Category and Level *****\n");
                report.append("-- Category: ").append(category).append(" --\n");
                report.append("-- Level: ").append(level).append(" --\n");
                report.append(getTopScorerDetails(category, level)).append("\n");
            }

            // Append summary statistics and score frequency report
            report.append("==== Summary Statistics ====\n");
            report.append(getSummaryStatistics()).append("\n");
            report.append("==== Score Frequency Report ====\n");
            report.append(getScoreFrequencyReport()).append("\n");
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
    public String getCompetitorsTable(String category) {
        StringBuilder detailsBuilder = new StringBuilder();
        List<Level> relevantLevels;
        if (CATEGORIES[0].equals(category)) {
            relevantLevels = ICE_SKATING_LEVELS;
        } else if (CATEGORIES[1].equals(category)) {
            relevantLevels = GAMING_LEVELS;
        } else {
            relevantLevels = Arrays.asList(Level.values());
        }

        if (!relevantLevels.isEmpty()) {
            String categoryLine = "============= " + category + " ==============";
            detailsBuilder.append(categoryLine).append("\n");

            for (Level level : relevantLevels) {
                String levelLine = "-------------- Level: " + level + " -------------";
                detailsBuilder.append(levelLine).append("\n");

                ArrayList<Competitor> competitorsInLevel = competitorList.searchCompetitorsByLevel(category, level);

                for (Competitor competitor : competitorsInLevel) {
                    detailsBuilder.append(competitor.getFullDetails()).append("\n");
                }
            }
        } else {
            detailsBuilder.append("No competitors available.");
        }

        return detailsBuilder.toString();
    }

    public String getTopScorerDetails(String category, Level level) {
        Competitor competitor =competitorList.highestScoringCompetitor(category,level);
            return "\nHighest Scorer:\n" + competitor.getFullDetails() + "\n";
    }

    // Method to get summary statistics
    public String getSummaryStatistics() {
        ArrayList<Competitor> allCompetitors = competitorList.getAllCompetitors();

        double totalScore = allCompetitors.stream()
                .mapToDouble(Competitor::getOverallScore)
                .sum();
        double averageScore = allCompetitors.stream()
                .mapToDouble(Competitor::getOverallScore)
                .average()
                .orElse(0);
        double maxScore = allCompetitors.stream()
                .mapToDouble(Competitor::getOverallScore)
                .max()
                .orElse(0);
        double minScore = allCompetitors.stream()
                .mapToDouble(Competitor::getOverallScore)
                .min()
                .orElse(0);

        return String.format("Total Score: %.2f\nAverage Score: %.2f\nMax Score: %.2f\nMin Score: %.2f",
                totalScore, averageScore, maxScore, minScore);
    }

    // Method to get score frequency report
    public String getScoreFrequencyReport() {
        ArrayList<Competitor> allCompetitors = competitorList.getAllCompetitors();

        Map<Integer, Long> frequencyMap = allCompetitors.stream()
                .flatMapToInt(c -> Arrays.stream(c.getScoreArray()))
                .boxed()
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

        return frequencyMap.entrySet().stream()
                .map(entry -> "Score " + entry.getKey() + ": " + entry.getValue() + " times")
                .collect(Collectors.joining("\n"));
    }

    public boolean addCompetitor(Competitor competitor) {
        for (Competitor existingCompetitor : competitorList.getAllCompetitors()) {
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

    public ArrayList<Competitor> searchCompetitorsByLevel(String category, Level level) {
        return competitorList.searchCompetitorsByLevel(category, level);
    }

    public Competitor getCompetitor(int competitorId) {
        return competitorList.getCompetitor(competitorId);
    }

    public ArrayList<Competitor> getAllCompetitors() {
        return competitorList.getAllCompetitors();
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
