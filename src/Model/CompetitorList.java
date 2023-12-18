package Model;

import java.util.ArrayList;

public class CompetitorList {
    private static ArrayList<Competitor> competitors = new ArrayList<>();

    // Method to add a competitor
    public void addCompetitor(Competitor competitor) {
        CompetitorList.competitors.add(competitor);
    }

    // Method to remove a competitor
    public void removeCompetitor(Competitor competitor) {
        CompetitorList.competitors.remove(competitor);
    }
    public  ArrayList<Competitor> getAllCompetitors() {

        return new ArrayList<>(CompetitorList.competitors);
    }
    public ArrayList<Competitor> getCompetitorsByCategory(String category)
    {
        ArrayList<Competitor> comepetitor=new ArrayList<>();
        for(Competitor competitor:this.getAllCompetitors())
        {
            if(competitor.getCategory().toUpperCase()==category.toUpperCase())
            {
                comepetitor.add(competitor);
            }

        }
        return comepetitor;
    }
    public ArrayList<Competitor> searchCompetitorsByLevel(String category, Level level)
    {

        ArrayList<Competitor> inCategory= this.getCompetitorsByCategory(category);
        ArrayList<Competitor> inLevel=new ArrayList<>();
        for(Competitor competitor:inCategory)
        {
            if(competitor.getLevel()==level)
            {
                inLevel.add(competitor);
            }

        }
        return inLevel;
    }
    public Competitor getCompetitor(int id)
    {
        Competitor competitorIS=null;
        for(Competitor competitor:competitors)
        {
            if(competitor.getCompetitorNumber()==id)
            {
                competitorIS=competitor;
            }
        }
        return competitorIS;
    }
}
