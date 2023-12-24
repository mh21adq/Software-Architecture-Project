package Model;

import java.util.ArrayList;
import  java.util.*;

public class CompetitorList {
    private static ArrayList<Competitor> allCompetitors = new ArrayList<>();

    public void addCompetitor(Competitor newCompetitor) {
        allCompetitors.add(newCompetitor);

    }

    // Method to remove a competitor
    public boolean removeCompetitor(int competitorId) {
        for (Competitor competitor : allCompetitors) {
            if (competitor.getCompetitorNumber() == competitorId) {
                allCompetitors.remove(competitor);
                return true;
            }
        }
        return false; // Competitor not found
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
        competitorsInLevel.sort(Comparator.comparingDouble(Competitor::getOverallScore).reversed());

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
