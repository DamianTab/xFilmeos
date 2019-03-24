package Products;

import java.io.Serializable;
import java.util.ArrayList;

public class Season implements Serializable {
    private ArrayList <Episode> ListOfEpisodes;

    public Season() {
        ListOfEpisodes = new ArrayList<>();
    }

    public ArrayList<Episode> getListOfEpisodes() {
        return ListOfEpisodes;
    }

    public void setListOfEpisodes(ArrayList<Episode> listOfEpisodes) {
        ListOfEpisodes = listOfEpisodes;
    }
    public void addEpisode(Episode episode){
        ListOfEpisodes.add(episode);
    }
    public void deleteEpisode(Episode episode){
        ListOfEpisodes.remove(episode);
    }
}
