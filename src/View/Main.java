package View;
import Controller.Manager;
import Model.*;
public class Main {
public static void main(String[] args)
{
    Manager manager=new Manager();
    manager.readFromFile("src/RunCompetitor.csv");

}
}
