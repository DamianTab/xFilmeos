package Products;
import Entities.Distributor;

import java.io.Serializable;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;

public class Serial extends Product implements Serializable {
    private HashSet<String> ActorsList;
    private ArrayList<Season> ListOfSeasons;
    private String Genre;

    public Serial(String title, String photoURL, String description, LocalDate productionDate, int length, Distributor productDistributor, String productionCountries, int rating, float price, HashSet<String> actorsList, ArrayList<Season> listOfSeasons, String genre) {
        super(title, photoURL, description, productionDate, length, productDistributor, productionCountries, rating, price);
        ActorsList = actorsList;
        ListOfSeasons = listOfSeasons;
        Genre = genre;
    }

    public HashSet<String> getActorsList() {
        return ActorsList;
    }

    public void setActorsList(HashSet<String> actorsList) {
        ActorsList = actorsList;
    }

    public ArrayList<Season> getListOfSeasons() {
        return ListOfSeasons;
    }

    public void setListOfSeasons(ArrayList<Season> listOfSeasons) {
        ListOfSeasons = listOfSeasons;
    }

    public String getGenre() {
        return Genre;
    }

    public void setGenre(String genre) {
        Genre = genre;
    }

    public void show(){
        super.show();
        ActorsList.forEach(System.out::println);
        System.out.println(ListOfSeasons.size());
        System.out.println(Genre);
    }


}
