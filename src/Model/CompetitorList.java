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
        ArrayList<Competitor> comepetitors=new ArrayList<>();
        for(Competitor competitor:this.getAllCompetitors())
        {
            if(competitor.getCategory()==category.toUpperCase())
            {
                comepetitors.add(competitor);
            }

        }
        return comepetitors;
    }
}
