package Model;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.stream.Collectors;

/**
 * Manages a list of competitors and provides operations for adding, removing, and querying competitors.
 */
public class CompetitorList {
    private static CompetitorList instance;
    private final ArrayList<Competitor> allCompetitors;

    /**
     * Private constructor to create a new instance of CompetitorList.
     */
    private CompetitorList() {
        allCompetitors = new ArrayList<>();
    }

    /**
     * Get the singleton instance of CompetitorList.
     *
     * @return The CompetitorList instance.
     */
    public static CompetitorList getInstance() {
        if (instance == null) {
            instance = new CompetitorList();
        }
        return instance;
    }

    /**
     * Add a competitor to the list, preventing duplicates based on email and category.
     *
     * @param competitor The competitor to add.
     * @return true if the competitor is added, false if a competitor with the same email and category already exists.
     */
    public boolean addCompetitor(Competitor competitor) {
        for (Competitor existingCompetitor : getAllCompetitors()) {
            if (existingCompetitor.getEmail().equalsIgnoreCase(competitor.getEmail()) &&
                    existingCompetitor.getCategory().equals(competitor.getCategory())) {
                return false;
            }
        }
        allCompetitors.add(competitor);
        return true;
    }
    /**
     * Remove a competitor from the list by their competitor number.
     *
     * @param competitorId The competitor number of the competitor to remove.
     * @return true if the competitor is removed, false if no competitor with the given competitor number is found.
     */
    public boolean removeCompetitor(int competitorId) {
        return allCompetitors.removeIf(competitor -> competitor.getCompetitorNumber() == competitorId);
    }

    /**
     * Get a list of all competitors.
     *
     * @return An ArrayList containing all competitors.
     */
    public ArrayList<Competitor> getAllCompetitors() {
        return new ArrayList<>(allCompetitors);
    }

    /**
     * Get a competitor by their competitor number.
     *
     * @param competitorId The competitor number of the competitor to retrieve.
     * @return The competitor with the specified competitor number, or null if not found.
     */
    public Competitor getCompetitor(int competitorId) {
        for (Competitor competitor : allCompetitors) {
            if (competitor.getCompetitorNumber() == competitorId) {
                return competitor;
            }
        }
        return null;
    }

    /**
     * Get a list of competitors in a specific category.
     *
     * @param category The category to filter by.
     * @return An ArrayList containing competitors in the specified category.
     */
    public ArrayList<Competitor> getCompetitorsByCategory(Category category) {
        return allCompetitors.stream()
                .filter(competitor -> competitor.getCategory().equals(category))
                .collect(Collectors.toCollection(ArrayList::new));
    }

    /**
     * Search for competitors in a specific category and level.
     *
     * @param category The category to filter by.
     * @param level    The level to filter by.
     * @return An ArrayList containing competitors in the specified category and level.
     */
    public ArrayList<Competitor> searchCompetitorsByLevel(Category category, Level level) {
        return getCompetitorsByCategory(category).stream()
                .filter(competitor -> competitor.getLevel() == level)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    /**
     * Sort competitors in a specific category and level by their overall score in descending order.
     *
     * @param category The category to filter by.
     * @param level    The level to filter by.
     * @return An ArrayList containing competitors sorted by overall score in descending order.
     */
    public ArrayList<Competitor> sortCompetitorsByScore(Category category, Level level) {
        ArrayList<Competitor> competitors = searchCompetitorsByLevel(category, level);
        competitors.sort(Comparator.comparingDouble(Competitor::getOverallScore).reversed());
        return competitors;
    }

    /**
     * Sort competitors in a specific category and level by their age in ascending order.
     *
     * @param category The category to filter by.
     * @param level    The level to filter by.
     * @return An ArrayList containing competitors sorted by age in ascending order.
     */
    public ArrayList<Competitor> sortByAge(Category category, Level level) {
        ArrayList<Competitor> competitors = searchCompetitorsByLevel(category, level);
        competitors.sort(Comparator.comparingInt(Competitor::getAge));
        return competitors;
    }

    /**
     * Sort competitors in a specific category and level by their first name in ascending order.
     *
     * @param category The category to filter by.
     * @param level    The level to filter by.
     * @return An ArrayList containing competitors sorted by first name in ascending order.
     */
    public ArrayList<Competitor> sortByFirstName(Category category, Level level) {
        ArrayList<Competitor> competitors = searchCompetitorsByLevel(category, level);
        competitors.sort(Comparator.comparing(competitor -> competitor.getName().getFirstName()));
        return competitors;
    }
    /**
     * Get the highest scoring competitor in a specific category and level.
     *
     * @param category The category to filter by.
     * @param level    The level to filter by.
     * @return The highest scoring competitor in the specified category and level, or null if no competitors are found.
     */
    public Competitor highestScoringCompetitor(Category category, Level level) {
        ArrayList<Competitor> competitorsInLevel = sortCompetitorsByScore(category, level);
        if (competitorsInLevel.isEmpty()) {
            return null;
        }
        return competitorsInLevel.get(0);
    }



}
