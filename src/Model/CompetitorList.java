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


}
