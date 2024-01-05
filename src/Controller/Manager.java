package Controller;
import Model.*;
import View.*;
import java.io.*;
import java.util.*;
import java.util.stream.Collectors;
import java.util.function.Function;

/**
 * The Manager class is responsible for managing competitors and generating reports for a competition.
 * It interacts with the CompetitorList, Competitor, and other related classes.
 */
public class Manager {
    private final CompetitorList competitorList;
    private static final Level[] LEVELS = Level.values();
    private static final List<Level> GAMING_LEVELS = List.of(Level.NOVICE, Level.EXPERT);
    private static final List<Level> ICE_SKATING_LEVELS = List.of(Level.BEGINNER, Level.INTERMEDIATE, Level.ADVANCED);

    /**
     * Constructor for the Manager class. Initializes the CompetitorList.(Singleton)
     */
    public Manager() {

        competitorList = CompetitorList.getInstance();
    }

    /**
     * Reads competitor data from a file and processes it.
     *
     * @param fileName The name of the file to read competitor data from.
     */
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

    /**
     * Processes a single line of input representing a competitor's data.
     * This method extracts data from the line, creates a Competitor object, and adds it to the competition.
     * If the data format is incorrect and causes an exception, an error message is printed.
     *
     * @param line A string representing a line of input with competitor data.
     */
    private void processLine(String line) {
        try {
            String[] data = line.split(",");
            int competitorNumber = Integer.parseInt(data[0].trim());
            Name name = parseName(data[1]);
            String email = data[2].trim();
            int age = Integer.parseInt(data[3].trim());
            String gender = data[4].trim();
            Level level = Level.valueOf(data[5].trim().toUpperCase());
            Category category = Category.valueOf(data[6].trim().toUpperCase());
            String country = data[7].trim();
            int[] scores = parseScores(data);

            Competitor competitor =
                    createCompetitor(competitorNumber, name, email, age, gender, country, level, category, scores);
            addCompetitor(competitor);
        } catch (Exception e) {
            System.err.println("Error processing line: " + line + "; Error: " + e.getMessage());
        }
    }

    /**
     * Parses a string representing a name into a Name object.
     * The string can contain first, middle, and last names separated by spaces.
     * Depending on the number of space-separated values, different Name constructors are called.
     *
     * @param nameData The string containing the name data.
     * @return A Name object representing the parsed name.
     */
    private Name parseName(String nameData) {
        String[] nameParts = nameData.trim().split("\\s+");
        return switch (nameParts.length) {
            case 1 -> new Name(nameParts[0]);
            case 2 -> new Name(nameParts[0], nameParts[1]);
            default -> new Name(nameParts[0], nameParts[1], nameParts[2]);
        };
    }

    /**
     * Parses an array of strings representing score data into an array of integers.
     * If a score cannot be parsed due to format issues, it is set to 0 by default.
     *
     * @param data The array of strings containing score data.
     * @return An array of integers representing the parsed scores.
     */
    private int[] parseScores(String[] data) {
        int[] scores = new int[data.length - 8];
        for (int i = 8; i < data.length; i++) {
            try {
                scores[i - 8] = Integer.parseInt(data[i].trim());
            } catch (NumberFormatException e) {
                scores[i - 8] = 0;
            }
        }
        return scores;
    }


    /**
     * Creates a Competitor object based on the provided data.
     *
     * @param name     The name of the competitor.
     * @param email    The email address of the competitor.
     * @param age      The age of the competitor.
     * @param gender   The gender of the competitor.
     * @param country  The country of the competitor.
     * @param level    The level of the competitor.
     * @param category The category of the competitor (ICE SKATING or GAMING).
     * @param scores   The scores of the competitor.
     * @return A Competitor object representing the competitor.
     */
    private Competitor createCompetitor(int competitorNumber, Name name,
                                        String email, int age, String gender,
                                        String country, Level level, Category category, int[] scores) {
        Competitor competitor;

        if (Category.ICE_SKATING.equals(category)) {
            competitor = new IceSkater(name, email, age, gender, country, level);
        } else {
            competitor = new Gamer(name, email, age, gender, country, level);
        }

        competitor.setScores(scores);
        competitor.setCompetitorNumber(competitorNumber);
        return competitor;
    }


    /**
     * Generates a final competition report and writes it to a file.
     *
     * @param filename The name of the file to write the report to.
     */
    public void generateFinalReport(String filename) {
        StringBuilder report = new StringBuilder();
        for (Category category : Category.values()) {
            report.append("==== Competitors in Category: ").append(category).append(" ====\n");
            report.append(getCompetitorsTable(category)).append("\n");

            // Iterate through each level within the category
            for (Level level : LEVELS) {
                // Check if there are competitors and  highest scorer in this level for the category
                if (!competitorList.searchCompetitorsByLevel(category, level).isEmpty() &&
                        competitorList.highestScoringCompetitor(category, level) != null) {
                    report.append("-- Level: ").append(level).append(" --\n");
                    report.append(getTopScorerDetails(category, level)).append("\n");
                }
            }

            // Append summary statistics and score frequency report for the entire category
            report.append("==== Summary Statistics ====\n").append(getSummaryStatistics(category)).append("\n");
            report.append("==== Score Frequency Report ====\n").append(getScoreFrequencyReport(category)).append("\n");
        }

        // Output to System.out and Write to file
        System.out.println(report);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            writer.write(report.toString());
        } catch (IOException e) {
            System.err.println("Error writing to file '" + filename + "': " + e.getMessage());
        }
    }

    /**
     * Gets a formatted table of competitors for a specific category.
     *
     * @param category The category for which to get the competitors' table.
     * @return A string containing the formatted table of competitors.
     */
    public String getCompetitorsTable(Category category) {
        StringBuilder detailsBuilder = new StringBuilder();
        List<Level> relevantLevels;
        if (Category.ICE_SKATING.equals(category)) {
            relevantLevels = ICE_SKATING_LEVELS;
        } else if (Category.GAMING.equals(category)) {
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

    /**
     * Gets details of the highest scorer in a specific category and level.
     *
     * @param category The category for which to find the highest scorer.
     * @param level    The level for which to find the highest scorer.
     * @return A string containing the details of the highest scorer.
     */
    public String getTopScorerDetails(Category category, Level level) {
        Competitor highestScorer = competitorList.highestScoringCompetitor(category, level);
        if (highestScorer == null) {
            return "No highest scorer found for " + category + " in level " + level + "\n";
        }
        return "\nHighest Scorer:\n" + highestScorer.getFullDetails() + "\n";
    }

    /**
     * Calculates and returns summary statistics for a specific category.
     *
     * @param category The category for which to calculate summary statistics.
     * @return A formatted string containing summary statistics.
     */
    public String getSummaryStatistics(Category category) {
        ArrayList<Competitor> competitorsInCategory = competitorList.getCompetitorsByCategory(category);

        double totalScore = competitorsInCategory.stream()
                .mapToDouble(Competitor::getOverallScore)
                .sum();
        double averageScore = competitorsInCategory.stream()
                .mapToDouble(Competitor::getOverallScore)
                .average()
                .orElse(0);
        double maxScore = competitorsInCategory.stream()
                .mapToDouble(Competitor::getOverallScore)
                .max()
                .orElse(0);
        double minScore = competitorsInCategory.stream()
                .mapToDouble(Competitor::getOverallScore)
                .min()
                .orElse(0);

        return String.format("Total Score: %.2f\nAverage Score: %.2f\nMax Score: %.2f\nMin Score: %.2f",
                totalScore, averageScore, maxScore, minScore);
    }

    /**
     * Generates a score frequency report for a specific category.
     *
     * @param category The category for which to generate the score frequency report.
     * @return A string containing the score frequency report.
     */
    public String getScoreFrequencyReport(Category category) {
        ArrayList<Competitor> competitorsInCategory = competitorList.getCompetitorsByCategory(category);

        Map<Integer, Long> frequencyMap = competitorsInCategory.stream()
                .flatMapToInt(c -> Arrays.stream(c.getScoreArray()))
                .boxed()
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

        return frequencyMap.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .map(entry -> "Score " + entry.getKey() + ": " + entry.getValue() + " times")
                .collect(Collectors.joining("\n"));
    }

    /**
     * Adds a competitor to the competitor list.
     *
     * @param competitor The competitor to add.
     * @return True if the competitor was successfully added, false otherwise.
     */
    public boolean addCompetitor(Competitor competitor) {

        return competitorList.addCompetitor(competitor);
    }

    /**
     * Removes a competitor from the competitor list by their competitor number.
     *
     * @param competitorNumber The competitor number of the competitor to remove.
     * @return True if the competitor was successfully removed, false otherwise.
     */
    public boolean removeCompetitor(int competitorNumber) {
        return  competitorList.removeCompetitor(competitorNumber);
    }

    /**
     * Retrieves a competitor by their competitor ID.
     *
     * @param competitorId The ID of the competitor to retrieve.
     * @return The Competitor object associated with the given ID, or null if not found.
     */
    public Competitor getCompetitor(int competitorId) {
        return competitorList.getCompetitor(competitorId);
    }

    /**
     * Sorts competitors by their scores for a specific category and level.
     *
     * @param category The category for which to sort competitors.
     * @param level    The level within the category for sorting.
     * @return A sorted list of competitors.
     */
    public ArrayList<Competitor> sortCompetitorsByScore(Category category, Level level) {
        return competitorList.sortCompetitorsByScore(category,level);
    }

    /**
     * Sorts competitors by their age for a specific category and level.
     *
     * @param category The category for which to sort competitors.
     * @param level    The level within the category for sorting.
     * @return A sorted list of competitors.
     */
    public ArrayList<Competitor> sortByAge(Category category, Level level) {
        return competitorList.sortByAge(category,level);
    }

    /**
     * Sorts competitors by their first names for a specific category and level.
     *
     * @param category The category for which to sort competitors.
     * @param level    The level within the category for sorting.
     * @return A sorted list of competitors.
     */
    public ArrayList<Competitor> sortByFirstName(Category category, Level level) {
        return competitorList.sortByFirstName(category,level);
    }

    /**
     * Finds and returns the highest-scoring competitor in a specific category and level.
     *
     * @param category The category for which to find the highest scorer.
     * @param level    The level within the category for which to find the highest scorer.
     * @return The highest-scoring Competitor object, or null if no such competitor exists.
     */
    public Competitor highestScoringCompetitor(Category category, Level level)
    {
       return competitorList.highestScoringCompetitor(category,level);
    }

    /**
     * Checks if the competition is completed for a specific category and level.
     *
     * @param category The category for which to check completion.
     * @param level    The level within the category for which to check completion.
     * @return True if the competition for the given category and level is completed, false otherwise.
     */
    public boolean isCompetitionCompleted(Category category, Level level) {
        ArrayList<Competitor> competitorsInLevel = competitorList.searchCompetitorsByLevel(category, level);
        if (competitorsInLevel.isEmpty()) {
            return false;
        }

        for (Competitor competitor : competitorsInLevel) {
            int[] scores = competitor.getScoreArray();
            if (scores == null || scores.length == 0) {
                return false;
            }

        }

        return true;
    }

    /**
     * Opens the Staff GUI for managing competition operations.
     *
     * @param manager The Manager instance associated with the GUI.
     */
    public void openStaffGUI(Manager manager) {
      new StaffGUI(manager);
    }

    /**
     * Opens the Competitor GUI for competitor-related operations.
     *
     * @param manager The Manager instance associated with the GUI.
     */
    public void openCompetitorGUI(Manager manager) {

        new CompetitorGUI(manager);
    }
    /**
     * Opens the Audience GUI for viewing competition-related information.
     *
     * @param manager The Manager instance associated with the GUI.
     */
    public void openAudienceGUI(Manager manager) {

       new AudienceGUI(manager);
    }



}
