package Model;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
public class IceSkater extends Competitor {
    private int[] scores;
    private Level level;
    private   String category ;

    public IceSkater(Name name, String email, int age, String gender, String country, Level level) {
        super(name, email, age, gender, country);
        this.level = level;
        this.category = "ICE SKATING";
        this.scores = new int[4];
    }

    public Level getLevel() {
        return level;
    }


    public void setLevel(Level level) {
        this.level = level;
    }

    public  String getCategory() {
        return this.category;
    }

    // Override methods or add additional methods specific to IceSkatingCompetitor

    @Override
    public String getFullDetails() {
        String details = super.getFullDetails()+"\nand received these scores :"+this.getScores()+"\nThis gives him an overall score of "+this.getOverallScore(3);
        return details;
    }
    @Override
    public String getShortDetails() {
        String shortDetails = super.getShortDetails() + " in category " + category+" Scores:"+this.getScores();
        return shortDetails;
    }

    @Override
    public double getOverallScore() {
        int [] scores=this.scores;
        double sum=0;
        for(int i=0;i<scores.length;i++)
        {
            sum+=scores[i];
        }
        double average = sum / scores.length;
        return new BigDecimal(average).setScale(3, RoundingMode.HALF_UP).doubleValue();

    }
    public int[] getScoresArray() {
        return this.scores;
    }

    public void setScores(int[] scores) {
        this.scores=scores;
    }
    public int[] getScoreArray()
    {
        return this.scores;
    }


    // Overloaded method specific to SubclassB
    public double getOverallScore(int top) {
        int [] scores=this.scores;
        Arrays.sort(scores);
        double sum=0;
        for(int i=0;i<top;i++)
        {
            sum+=scores[i];
        }
        double average = sum / scores.length;
        return new BigDecimal(average).setScale(3, RoundingMode.HALF_UP).doubleValue();

    }


}
