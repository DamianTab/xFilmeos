package Products;

import Entities.Distributor;

import java.io.Serializable;
import java.util.HashSet;
import java.time.LocalDate;

public class Movie extends Product implements Serializable {
    private HashSet<String> ActorsList;
    private String TrailerURL;
    private String Genre;
    private int DaysToEndAcces;

    public Movie(String title, String photoURL, String description, LocalDate productionDate, int length, Distributor productDistributor, String productionCountries, int rating, float price, HashSet<String> actorsList, String trailerURL, String genre, int DaysToEnd) {
        super(title, photoURL, description, productionDate, length, productDistributor, productionCountries, rating, price);
        ActorsList = actorsList;
        TrailerURL = trailerURL;
        Genre = genre;
        DaysToEndAcces = DaysToEnd;
    }

    public HashSet<String> getActorsList() {
        return ActorsList;
    }

    public void setActorsList(HashSet<String> actorsList) {
        ActorsList = actorsList;
    }

    public String getTrailerURL() {
        return TrailerURL;
    }

    public void setTrailerURL(String trailerURL) {
        TrailerURL = trailerURL;
    }

    public String getGenre() {
        return Genre;
    }

    public void setGenre(String genre) {
        Genre = genre;
    }

    public int getDaysToEndAcces() {
        return DaysToEndAcces;
    }

    public void setDaysToEndAcces(int DaysToEnd) {
        DaysToEndAcces = DaysToEnd;
    }

    public void show() {
        super.show();
        ActorsList.forEach(System.out::println);
        System.out.println(TrailerURL + "  " + Genre + "  " + DaysToEndAcces);
    }
}
