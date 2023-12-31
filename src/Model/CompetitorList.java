package Model;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.*;
import java.util.stream.Collectors;
import java.util.function.Function;

public class CompetitorList {
    private static CompetitorList instance;
    private ArrayList<Competitor> allCompetitors;
    private static final String[] CATEGORIES = {"ICE SKATING", "GAMING"};
    private static final List<Level> GAMING_LEVELS = List.of(Level.NOVICE, Level.EXPERT);
    private static final List<Level> ICE_SKATING_LEVELS = List.of(Level.BEGINNER, Level.INTERMEDIATE, Level.ADVANCED);

    private CompetitorList() {
        allCompetitors = new ArrayList<>();
    }

    public static CompetitorList getInstance() {
        if (instance == null) {
            instance = new CompetitorList();
        }
        return instance;
    }

    public void addCompetitor(Competitor newCompetitor) {
        if (newCompetitor != null) {
            allCompetitors.add(newCompetitor);
        }
    }

    public boolean removeCompetitor(int competitorId) {
        return allCompetitors.removeIf(competitor -> competitor.getCompetitorNumber() == competitorId);
    }

    public ArrayList<Competitor> getAllCompetitors() {
        return new ArrayList<>(allCompetitors);
    }

    public ArrayList<Competitor> getCompetitorsByCategory(String category) {
        return allCompetitors.stream()
                .filter(competitor -> competitor.getCategory().equalsIgnoreCase(category))
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public ArrayList<Competitor> searchCompetitorsByLevel(String category, Level level) {
        return getCompetitorsByCategory(category).stream()
                .filter(competitor -> competitor.getLevel() == level)
                .sorted(Comparator.comparingDouble(Competitor::getOverallScore).reversed())
                .collect(Collectors.toCollection(ArrayList::new));
    }
    public Competitor highestScoringCompetitor(String category, Level level)
    {
        ArrayList<Competitor> competitorsInLevel=searchCompetitorsByLevel(category,level);
        return competitorsInLevel.get(0);
    }
    public Competitor getCompetitor(int competitorId) {
        for (Competitor competitor : allCompetitors) {
            if (competitor.getCompetitorNumber() == competitorId) {
                return competitor;
            }
        }
        return null;
    }

    public String getCompetitorsTable(String category) {
        StringBuilder detailsBuilder = new StringBuilder();
        // Choose the relevant list of levels based on the category
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

                ArrayList<Competitor> competitorsInLevel = searchCompetitorsByLevel(category, level);

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
        ArrayList<Competitor> competitorsInLevel = searchCompetitorsByLevel(category, level);

        if (!competitorsInLevel.isEmpty()) {
            Competitor highestScorer = competitorsInLevel.get(0);
            return "\nHighest Scorer:\n" + highestScorer.getFullDetails() + "\n";
        } else {
            return "No competitors found in the specified category and level.";
        }
    }
    public String getSummaryStatistics() {
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

    public String getScoreFrequencyReport() {
        Map<Integer, Long> frequencyMap = allCompetitors.stream()
                .flatMapToInt(c -> Arrays.stream(c.getScoreArray()))
                .boxed()
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

        return frequencyMap.entrySet().stream()
                .map(entry -> "Score " + entry.getKey() + ": " + entry.getValue() + " times")
                .collect(Collectors.joining("\n"));
    }

}
