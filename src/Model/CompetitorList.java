package Model;

import java.util.ArrayList;
import java.util.Comparator;

import java.util.stream.Collectors;


public class CompetitorList {
    private static CompetitorList instance;
    private final ArrayList<Competitor> allCompetitors;

    private CompetitorList() {
        allCompetitors = new ArrayList<>();
    }

    public static CompetitorList getInstance() {
        if (instance == null) {
            instance = new CompetitorList();
        }
        return instance;
    }

    public boolean addCompetitor(Competitor competitor) {
        for (Competitor existingCompetitor : getAllCompetitors()) {
            if (existingCompetitor.getEmail().equalsIgnoreCase(competitor.getEmail()) &&
                    existingCompetitor.getCategory().equalsIgnoreCase(competitor.getCategory())) {
                return false;
            }
        }
        allCompetitors.add(competitor);
        return true;
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
                .collect(Collectors.toCollection(ArrayList::new));
    }
    public ArrayList<Competitor> sortCompetitorsByScore(String category, Level level) {
        ArrayList<Competitor> competitors = searchCompetitorsByLevel(category, level);
        competitors.sort(Comparator.comparingDouble(Competitor::getOverallScore).reversed());
        return competitors;
    }
    public ArrayList<Competitor> sortByAge(String category, Level level) {
        ArrayList<Competitor> competitors = searchCompetitorsByLevel(category, level);
        competitors.sort(Comparator.comparingInt(Competitor::getAge));
        return competitors;
    }
    public ArrayList<Competitor> sortByFirstName(String category, Level level) {
        ArrayList<Competitor> competitors = searchCompetitorsByLevel(category, level);
        competitors.sort(Comparator.comparing(competitor -> competitor.getName().getFirstName()));
        return competitors;
    }



    public Competitor highestScoringCompetitor(String category, Level level) {
        ArrayList<Competitor> competitorsInLevel = sortCompetitorsByScore(category, level);
        if (competitorsInLevel.isEmpty()) {
            return null;
        }
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
