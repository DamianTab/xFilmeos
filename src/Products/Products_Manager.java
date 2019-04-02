package Products;

import CSV.MyCSVReader;
import CSV.PathToFile;
import Entities.Distributor;
import GUI.Window_App_UI_Controller;
import Main_package.SimulationExceptionProduct;
import Main_package.Time_Manager;

import java.io.Serializable;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;

public class Products_Manager implements Serializable, PathToFile {

//    public static Products_Manager getInstance(){
//        return Products_Manager.SingletonHolder.INSTANCE;
//    }
//    private static class SingletonHolder {
//        private static final Products_Manager INSTANCE = new Products_Manager();
//    }

    private static Products_Manager INSTANCE;
    public static synchronized Products_Manager getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new Products_Manager();
        }
        return INSTANCE;
    }

    public static void setINSTANCE(Products_Manager products_Manager){
        INSTANCE = products_Manager;
    }
    private Products_Manager() {
    }


    private String [] RandTitle = new String[1000];
    private String [] RandURLPhoto = new String[1000];
    private String [] RandDescription = new String[1000];
    private String [] RandDate = new String[1000];
    private String [] RandCountries = new String[1000];
    private int NumberOfProducts = 0;

    private String [] RandActors = new String[1000];
    private String [] RandURLTrailer = new String[1000];
    private String [] RandGenre = new String[1000];
    private int NumberOfMovies = 0;
    private HashMap<Integer,Movie> ContainerOfMovies = new HashMap<>();


    //data livow bedzie brany obecny czas + cos
    private int NumberofLives;
    private HashMap<Integer,Live> ContainerOfLives = new HashMap<>();

    //lista aktorow jest wczesniej
    //lista gatunkow jest wczesniej
    private int NumberofSerials = 0;
    private HashMap<Integer,Serial> ContainerOfSerials = new HashMap<>();

    //dla sezonow nic nie potrzebuje

    //dla odcinkow data premiery bedzie brana z produktu + cos
    private String [] RandTitlesForEpisode = new String[1000];


    public int getNumberOfProducts() {
        return NumberOfProducts;
    }
    public void setNumberOfProducts(int numberOfProducts) {
        NumberOfProducts = numberOfProducts;
    }
    public int getNumberOfMovies() {
        return NumberOfMovies;
    }
    public void setNumberOfMovies(int numberOfMovies) {
        NumberOfMovies = numberOfMovies;
    }
    public HashMap<Integer, Movie> getContainerOfMovies() {
        return ContainerOfMovies;
    }
    public void setContainerOfMovies(HashMap<Integer, Movie> containerOfMovies) {
        ContainerOfMovies = containerOfMovies;
    }
    public int getNumberofLives() {
        return NumberofLives;
    }
    public void setNumberofLives(int numberofLives) {
        NumberofLives = numberofLives;
    }
    public HashMap<Integer, Live> getContainerOfLives() {
        return ContainerOfLives;
    }
    public void setContainerOfLives(HashMap<Integer, Live> containerOfLives) {
        ContainerOfLives = containerOfLives;
    }
    public int getNumberofSerials() {
        return NumberofSerials;
    }
    public void setNumberofSerials(int numberofSerials) {
        NumberofSerials = numberofSerials;
    }
    public HashMap<Integer, Serial> getContainerOfSerials() {
        return ContainerOfSerials;
    }
    public void setContainerOfSerials(HashMap<Integer, Serial> containerOfSerials) {
        ContainerOfSerials = containerOfSerials;
    }

    public Product findProduct(String title){
        Product product = null;
        if ((product = ContainerOfMovies.get(title.hashCode()))!=null);
            else if ((product = ContainerOfSerials.get(title.hashCode()))!=null);
            else product = ContainerOfLives.get(title.hashCode());
//        System.out.println(product);
            return product;
    }
    public void deleteProduct(Product product){
        synchronized (ContainerOfMovies){
            if (ContainerOfMovies.remove(product.getTitle().hashCode())!=null) {
                return;
            }
        }
        synchronized (ContainerOfSerials){
            if (ContainerOfSerials.remove(product.getTitle().hashCode())!=null) return;
        }
        synchronized (ContainerOfLives){
            if (ContainerOfLives.remove(product.getTitle().hashCode())!=null) return;
        }
    }


    public Object[] getValuesOfMovies(){
        Object[] values = null;
        synchronized (ContainerOfMovies){
            values = ContainerOfMovies.values().toArray();
        }
        return values;
    }
    public Object[] getValuesOfSerials(){
        Object[] values = null;
        synchronized (ContainerOfSerials){
            values = ContainerOfSerials.values().toArray();
        }
        return values;
    }
    public Object[] getValuesOfLives(){
        Object[] values = null;
        synchronized (ContainerOfLives){
            values = ContainerOfLives.values().toArray();
        }
        return values;
    }


    public Movie addMovie(Distributor distributor) throws SimulationExceptionProduct{
        if (NumberOfProducts>=1000) throw new SimulationExceptionProduct();
        Random generate = new Random();

        String title = RandTitle[NumberOfProducts];

        while (ContainerOfMovies.containsKey(title.hashCode())) {
            System.out.println("UWAGA!!!! TAKI Film JUZ ISTNIEJE\n");
            NumberOfProducts++;
            title = RandTitle[NumberOfProducts];
        }

        LocalDate date = LocalDate.parse(RandDate[NumberOfProducts]);
        HashSet<String> ActorsList = new HashSet<>();
        for (int i = 0; i < 5; i++) {
            ActorsList.add(RandActors[generate.nextInt(1000)]);
        }

        String photoFile = generate.nextInt(300)+".jpg";
        System.out.println(photoFile);
        photoFile = PathToFile.getFilePath("/src/Images/",photoFile);


//        Druga wersja
//        String photoFile = generate.nextInt(300)+".jpg";
//        System.out.println(photoFile);
//        photoFile = "src/Images/" + photoFile;

        Movie tmpMovie = new Movie(title,photoFile,RandDescription[NumberOfProducts],
                date,generate.nextInt(100)+90,distributor,RandCountries[NumberOfProducts],generate.nextInt(9)+1,
                generate.nextInt(10)+10,ActorsList,RandURLTrailer[NumberOfMovies],RandGenre[NumberOfMovies],30);

//        tmpMovie.show();
        System.out.println("Dodano film");
        synchronized (ContainerOfMovies){
            ContainerOfMovies.put(tmpMovie.getTitle().hashCode(),tmpMovie);
        }
        NumberOfProducts++;
        NumberOfMovies++;
        Window_App_UI_Controller.addToMovieObservableList(tmpMovie);
        return tmpMovie;
    }
    public Live addLive(Distributor distributor) throws SimulationExceptionProduct{
        if (NumberOfProducts>=1000) throw new SimulationExceptionProduct();

        Random generate = new Random();
        String title = RandTitle[NumberOfProducts];

        while (ContainerOfLives.containsKey(title.hashCode())) {
            System.out.println("UWAGA!!!! TAKI Live JUZ ISTNIEJE\n");
            NumberOfProducts++;
            title = RandTitle[NumberOfProducts];
        }
        LocalDate date = LocalDate.parse(RandDate[NumberOfProducts]);

        String photoFile = generate.nextInt(300)+".jpg";
        System.out.println(photoFile);
        photoFile = PathToFile.getFilePath("/src/Images/",photoFile);


        Live tmpLive = new Live(title,photoFile,RandDescription[NumberOfProducts],
                date,generate.nextInt(100)+90,distributor,RandCountries[NumberOfProducts],generate.nextInt(9)+1,
                generate.nextInt(10)+10, Time_Manager.getInstance().getTimeOfSimulation().plus(generate.nextInt(20)+1, ChronoUnit.DAYS));

//        tmpLive.show();
        System.out.println("Dodano Live");

        synchronized (ContainerOfLives){
            ContainerOfLives.put(tmpLive.getTitle().hashCode(),tmpLive);
        }
        NumberOfProducts++;
        NumberofLives++;
        Window_App_UI_Controller.addToLiveObservableList(tmpLive);
        return tmpLive;
    }
    public Serial addSerial(Distributor distributor)throws SimulationExceptionProduct{
        if (NumberOfProducts>=1000) throw new SimulationExceptionProduct();

        Random generate = new Random();
        String title = RandTitle[NumberOfProducts];

        while (ContainerOfSerials.containsKey(title.hashCode())) {
            System.out.println("UWAGA!!!! TAKI Serial JUZ ISTNIEJE\n");
            NumberOfProducts++;
            title = RandTitle[NumberOfProducts];

        }

        LocalDate date = LocalDate.parse(RandDate[NumberOfProducts]);
        LocalDate episodeDate = date;
        ArrayList<Season> ListOfSeasons = new ArrayList<>();

        int amountOfSeasons = generate.nextInt(3)+1;
        for (int i = 0; i <amountOfSeasons; i++) {
            Season tmpSeason = new Season();
            int amountOfEpisodes = generate.nextInt(4)+8;
            for (int j = 0; j < amountOfEpisodes; j++) {
                episodeDate = episodeDate.plus(generate.nextInt(30)+10, ChronoUnit.DAYS);
                Episode tmpEpisode= new Episode(RandTitlesForEpisode[generate.nextInt(1000)],episodeDate,45);
                tmpSeason.addEpisode(tmpEpisode);
            }
            ListOfSeasons.add(tmpSeason);
        }

        HashSet<String> ActorsList = new HashSet<>();
        for (int i = 0; i < 5; i++) {
            ActorsList.add(RandActors[generate.nextInt(1000)]);
        }

        String photoFile = generate.nextInt(300)+".jpg";
        System.out.println(photoFile);
        photoFile = PathToFile.getFilePath("/src/Images/",photoFile);

        Serial tmpSerial = new Serial(title,photoFile,RandDescription[NumberOfProducts],
                date,generate.nextInt(100)+90,distributor,RandCountries[NumberOfProducts],generate.nextInt(9)+1,
                generate.nextInt(10)+10,ActorsList,ListOfSeasons,RandGenre[NumberOfMovies]);

//        tmpSerial.show();
        System.out.println("Dodano Serial");

        synchronized (ContainerOfSerials){
            ContainerOfSerials.put(tmpSerial.getTitle().hashCode(),tmpSerial);
        }
        NumberOfProducts++;
        NumberofSerials++;
        Window_App_UI_Controller.addToSerialObservableList(tmpSerial);
        return tmpSerial;
    }

    public void loadAllDataFromCSV(){
        loadProducts();
        loadMovies();
        loadEpisodes();
    }
    public void loadProducts(){
        String[][] temp = MyCSVReader.read(5,"products.csv");
        RandTitle = temp[0];
        RandURLPhoto = temp [1];
        RandDescription = temp [2];
        RandDate = temp [3];
        RandCountries = temp [4];
    }
    public void loadMovies(){
        String[][] temp = MyCSVReader.read(3,"movies.csv");
        RandActors = temp[0];
        RandURLTrailer = temp [1];
        RandGenre = temp [2];
    }
    public void loadEpisodes(){
        String[][] temp = MyCSVReader.read(1,"episodes.csv");
        RandTitlesForEpisode = temp[0];
    }

    public void showContainersSize(){
        System.out.println("-------------------");
        System.out.println("FIlmy maja: "+ContainerOfMovies.size());
        System.out.println("Livy maja: "+ContainerOfLives.size());
        System.out.println("Seriale maja: "+ContainerOfSerials.size());
        System.out.println("-------------------");
    }


}
