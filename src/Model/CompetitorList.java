package Model;

import java.util.ArrayList;

public class CompetitorList {
    private static ArrayList<Competitor> allCompetitors = new ArrayList<>();

    public void addCompetitor(Competitor newCompetitor) {
        for (Competitor existingCompetitor : allCompetitors) {
            // Check if the competitor already exists with the same email and category
            if (existingCompetitor.getEmail().equalsIgnoreCase(newCompetitor.getEmail()) &&
                    existingCompetitor.getCategory().equalsIgnoreCase(newCompetitor.getCategory())) {
                //System.out.println("Competitor with the same email already exists in the same category.");
                return; // Do not add the competitor
            }
        }
        // Add the competitor if no duplicate is found in the same category
        allCompetitors.add(newCompetitor);
    }

    // Method to remove a competitor
    public void removeCompetitor(Competitor competitor) {
        allCompetitors.remove(competitor);
    }

    // Method to get all competitors
    public ArrayList<Competitor> getAllCompetitors() {
        return new ArrayList<>(allCompetitors);
    }

    // Method to get competitors by category
    public ArrayList<Competitor> getCompetitorsByCategory(String category) {
        ArrayList<Competitor> competitorsInCategory = new ArrayList<>();
        for (Competitor competitor : this.getAllCompetitors()) {
            if (competitor.getCategory().equalsIgnoreCase(category)) {
                competitorsInCategory.add(competitor);
            }
        }
        return competitorsInCategory;
    }

    // Method to search competitors by level
    public ArrayList<Competitor> searchCompetitorsByLevel(String category, Level level) {
        ArrayList<Competitor> competitorsInCategory = this.getCompetitorsByCategory(category);
        ArrayList<Competitor> competitorsInLevel = new ArrayList<>();
        for (Competitor competitor : competitorsInCategory) {
            if (competitor.getLevel() == level) {
                competitorsInLevel.add(competitor);
            }
        }
        return competitorsInLevel;
    }

    // Method to get a competitor by ID
    public Competitor getCompetitor(int competitorId) {
        for (Competitor competitor : allCompetitors) {
            if (competitor.getCompetitorNumber() == competitorId) {
                return competitor;
            }
        }
        return null;
    }
}
