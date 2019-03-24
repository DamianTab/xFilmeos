package GUI;

import Entities.Client;
import Entities.Distributor;
import Entities.Entities_Manager;
import Entities.Subscription;
import Main_package.Main;
import Main_package.Threads_Manager;
import Main_package.Time_Manager;
import Products.*;
import animatefx.animation.*;
import com.jfoenix.controls.*;
import com.jfoenix.transitions.hamburger.HamburgerBackArrowBasicTransition;
import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import java.io.*;
import java.net.URL;
import java.util.ResourceBundle;


public class Window_App_UI_Controller implements Initializable, Serializable {


    private static double xOffset = 0;
    private static double yOffset = 0;

    @FXML
    public JFXButton dragButton;

    @FXML
    private AnchorPane rootPane;

    @FXML
    private JFXHamburger hamburger;

    @FXML
    private JFXDrawer drawer;

    @FXML
    private JFXToggleNode closeButton;

    @FXML
    private JFXButton slowDownButton;

    @FXML
    private JFXButton stopStartButton;

    @FXML
    private JFXButton speedUpButton;

    @FXML
    private JFXButton addUserButton;

    @FXML
    private JFXButton addDistributorButton;

    @FXML
    private ScrollPane movieScrollPane;

    @FXML
    private JFXMasonryPane movieMasonry;

    @FXML
    private ScrollPane serialScrollPane;

    @FXML
    private JFXMasonryPane serialMasonry;

    @FXML
    private ScrollPane liveScrollPane;

    @FXML
    private JFXMasonryPane liveMasonry;


    @FXML
    private TableView<Client> clientTableView;

    @FXML
    private TableColumn<?, ?> C1;

    @FXML
    private TableColumn<?, ?> C2;

    @FXML
    private TableColumn<?, ?> C3;

    @FXML
    private TableColumn<?, ?> C4;

    @FXML
    private TableColumn<?, ?> C5;

    @FXML
    private TableColumn<?, ?> C6;

    @FXML
    private TableColumn<?, ?> C7;

    @FXML
    private TableView<Distributor> distributorTableView;

    @FXML
    private TableColumn<?, ?> D1;

    @FXML
    private TableColumn<?, ?> D2;

    @FXML
    private TableColumn<?, ?> D3;

    @FXML
    private TableColumn<?, ?> D4;

    @FXML
    private TableColumn<?, ?> D5;

    @FXML
    private TableColumn<?, ?> D6;

    @FXML
    private TableColumn<?, ?> D7;

    @FXML
    private TableView<Discount> discountTableView;

    @FXML
    private TableColumn<?, ?> DC1;

    @FXML
    private TableColumn<?, ?> DC2;

    @FXML
    private TableColumn<?, ?> DC3;

    @FXML
    private TableColumn<?, ?> DC4;

    @FXML
    private TableView<Subscription> subscriptionTableView;

    @FXML
    private TableColumn<?, ?> S1;

    @FXML
    private TableColumn<?, ?> S2;

    @FXML
    private TableColumn<?, ?> S3;

    @FXML
    private TableColumn<?, ?> S4;

    @FXML
    private JFXButton refreshButton;

    @FXML
    private JFXTabPane adminBoxPane;

    @FXML
    private DatePicker datePicker;

    @FXML
    private Label financialStatus;

    @FXML
    private JFXButton loadButton;

    @FXML
    private JFXButton saveButton;

    @FXML
    private JFXButton pureSimulationButton;

    @FXML
    void saveSimulation(ActionEvent event) {
        String FileName = "save.s";
        try {
            ObjectOutputStream out = new ObjectOutputStream(
                    new BufferedOutputStream(
                            new FileOutputStream(FileName)));

            out.writeObject(Entities_Manager.getInstance());
            out.writeObject(Discount_Manager.getInstance());
            out.writeObject(Products_Manager.getInstance());
            out.writeObject(Time_Manager.getInstance());
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
    @FXML
    void loadSimulation(ActionEvent event) {

        String FileName = "save.s";
        try {
            ObjectInputStream in = new ObjectInputStream(
                    new BufferedInputStream(
                            new FileInputStream(FileName)));
            Entities_Manager.setINSTANCE( (Entities_Manager) in.readObject());
            Discount_Manager.setINSTANCE( (Discount_Manager) in.readObject());
            Products_Manager.setINSTANCE( (Products_Manager) in.readObject());
            Time_Manager.setINSTANCE((Time_Manager) in.readObject());
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        loadDataToTableView();
        changeDateAndFinancial();

        Products_Manager products_manager = Products_Manager.getInstance();
        Threads_Manager threads_manager = Threads_Manager.getInstance();

        products_manager.getContainerOfMovies().forEach((k,v)->{
            movieList.add(v);
        });
        products_manager.getContainerOfSerials().forEach((k,v)->{
            serialList.add(v);
        });
        products_manager.getContainerOfLives().forEach((k,v)->{
            liveList.add(v);
        });

        for (Discount v : Discount_Manager.getInstance().getContainerOfDiscounts()){
            threads_manager.addOldDiscountThread(v);
        }
        for (Client v : Entities_Manager.getInstance().getContainerOfClients().values()){
            threads_manager.addOldClientThread(v);
        }
        for (Distributor v : Entities_Manager.getInstance().getContainerOfDistributors().values()){
            threads_manager.addOldDistributorThread(v);
        }

        Discount_Manager discount_manager = Discount_Manager.getInstance();
        discount_manager.resumeThread();
        new Thread(discount_manager).start();

        Time_Manager time_manager = Time_Manager.getInstance();
        time_manager.resumeThread();
        new Thread(time_manager).start();

        threads_manager.resumeThread();
        new Thread(threads_manager).start();

        showDisabledButtonsAfterLoadingData();
    }

    @FXML
    void startPureSimulation(ActionEvent event) {
        showDisabledButtonsAfterLoadingData();

        Entities_Manager entitiesManager = Entities_Manager.getInstance();
        entitiesManager.loadAllDataFromCSV();
        Products_Manager productsManager = Products_Manager.getInstance();
        productsManager.loadAllDataFromCSV();
        Threads_Manager threads_manager = Threads_Manager.getInstance();
        Time_Manager time_manager = Time_Manager.getInstance();

        new Thread(time_manager).start();
        time_manager.startOrStopRunTime();

        Thread clientAdder = new Thread(threads_manager);
        clientAdder.start();

        new Thread(Discount_Manager.getInstance()).start();
    }
    @FXML
    void addDistributor(ActionEvent event) {
        Threads_Manager.getInstance().addNewDistributorThread();
    }
    @FXML
    void addUser(ActionEvent event) {
        Threads_Manager.getInstance().addNewClientThread();
    }
    @FXML
    void backwardButton(ActionEvent event) {
        Time_Manager.getInstance().setIfStopSimulation(false);
        Time_Manager.getInstance().slowDownSimulation();
    }
    @FXML
    void forwardButton(ActionEvent event) {
        Time_Manager.getInstance().setIfStopSimulation(false);
        Time_Manager.getInstance().speedUpSimulation();
    }
    @FXML
    void pauseButton(ActionEvent event) {
        Time_Manager.getInstance().startOrStopRunTime();
    }
    @FXML
    void refreshTables(ActionEvent event) {
        refreshClientList();
        refreshDistributorList();
        refreshDiscountList();
        refreshSubscriptionList();
    }
    @FXML
    void closeSimulation(ActionEvent event) {
        Threads_Manager.getInstance().shutdownAllThreads();
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
        System.exit(0);
    }

    public void loadSplashScreen(){
        try {
            Main.setIsSplashLoaded(true);


            StackPane splashScreen = FXMLLoader.load(getClass().getResource("SplashScreen.fxml"));
            rootPane.getChildren().setAll(splashScreen);


            FadeTransition fadeIn = new FadeTransition(Duration.seconds(2),splashScreen);

            fadeIn.setFromValue(0);
            fadeIn.setToValue(1);
            fadeIn.setCycleCount(1);


            FadeTransition fadeOut = new FadeTransition(Duration.seconds(2),splashScreen);
            fadeOut.setFromValue(1);
            fadeOut.setToValue(0);
            fadeOut.setCycleCount(1);

            fadeIn.play();
            fadeIn.setOnFinished((e)->{
                fadeOut.play();
            });

            fadeOut.setOnFinished((e)->{
                try {
                    Stage primaryStage = (Stage) rootPane.getScene().getWindow();
                    Parent newrootPane = FXMLLoader.load(getClass().getResource("../GUI/Window_App_UI.fxml"));
                    Scene scene = new Scene(newrootPane);
                    primaryStage.setScene(scene);

                    // nie dziala resizable
//                AnchorPane parentContent = FXMLLoader.load(getClass().getResource("../GUI/Window_App_UI.fxml"));
//                rootPane.getChildren().setAll(parentContent);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            });

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void showDisabledButtonsAfterLoadingData(){
        loadButton.setDisable(true);
        pureSimulationButton.setDisable(true);
        saveButton.setDisable(false);
        slowDownButton.setDisable(false);
        speedUpButton.setDisable(false);
        stopStartButton.setDisable(false);
        addUserButton.setDisable(false);
        addDistributorButton.setDisable(false);
    }

    private static ObservableList<Movie> movieList = FXCollections.observableArrayList();
    private static ObservableList<Serial> serialList = FXCollections.observableArrayList();
    private static ObservableList<Live> liveList = FXCollections.observableArrayList();
    private static ObservableList<Client> clientList = FXCollections.observableArrayList();
    private static ObservableList<Distributor> distributorList = FXCollections.observableArrayList();
    private static ObservableList<Discount> discountList = FXCollections.observableArrayList();
    private static ObservableList<Subscription> subscriptionList = FXCollections.observableArrayList();

    public static void addToMovieObservableList(Movie movie){
            movieList.add(movie);
    }
    public static void addToSerialObservableList(Serial serial){
        serialList.add(serial);
    }
    public static void addToLiveObservableList(Live live){
        liveList.add(live);
    }
    public static void addToClientObservableList(Client client){
        clientList.add(client);
    }
    public static void addToDistributorObservableList(Distributor distributor){
        distributorList.add(distributor);
    }
    public static void addToDiscountObservableList(Discount discount){
        discountList.add(discount);
    }

    private boolean fadeInRefreshButton = false;
    private boolean fadeOutRefreshButton = true;
    private int whichPaneNow = 1;

    public void changePane(String switcher){
        switch (switcher){
            case "M":
                if (whichPaneNow!=1){
                    hideRefreshButton();

                    goOut(whichPaneNow);
                    whichPaneNow = 1;
                    goIn(whichPaneNow);
                }
                break;
            case "S":
                if (whichPaneNow!=2){
                    hideRefreshButton();

                    goOut(whichPaneNow);
                    whichPaneNow=2;
                    goIn(whichPaneNow);

                }
                break;
            case "L":
                if (whichPaneNow!=3){
                    hideRefreshButton();
                    goOut(whichPaneNow);
                    whichPaneNow=3;
                    goIn(whichPaneNow);
                }
                break;
            case "A":
                if (whichPaneNow!=4){
                    showRefreshButton();

                    goOut(whichPaneNow);
                    whichPaneNow = 4;
                    goIn(whichPaneNow);

                }
                break;
        }

    }
    public void showRefreshButton(){

        if (!fadeInRefreshButton){
            if (!refreshButton.isVisible()){
                refreshButton.setVisible(true);
            }
            refreshButton.setDisable(false);
            new FadeIn(refreshButton).play();
            fadeInRefreshButton = true;
            fadeOutRefreshButton = false;
        }


    }
    public void hideRefreshButton(){
        if (!fadeOutRefreshButton){
            new FadeOut(refreshButton).play();
        }
        refreshButton.setDisable(true);
        fadeInRefreshButton = false;
        fadeOutRefreshButton = true;
    }
    public void goOut(int number){
        switch (number){
            case 1:
                movieScrollPane.setVisible(false);
                movieScrollPane.setDisable(true);
                break;
            case 2:
                serialScrollPane.setVisible(false);
                serialScrollPane.setDisable(true);
                break;
            case 3:
                liveScrollPane.setVisible(false);
                liveScrollPane.setDisable(true);
                break;
            case 4:
                adminBoxPane.setVisible(false);
                adminBoxPane.setDisable(true);
                break;
        }
    }
    public void goIn(int number){
        switch (number){
            case 1:
                movieScrollPane.setVisible(true);
                movieScrollPane.setDisable(false);
                new BounceIn(movieScrollPane).play();

                break;
            case 2:
                serialScrollPane.setVisible(true);
                serialScrollPane.setDisable(false);
                new JackInTheBox(serialScrollPane).play();
                break;
            case 3:
                liveScrollPane.setVisible(true);
                liveScrollPane.setDisable(false);
                new FlipInY(liveScrollPane).play();

                break;
            case 4:
//                if (!adminBoxPane.isVisible()) adminBoxPane.setVisible(true);
                adminBoxPane.setVisible(true);
                adminBoxPane.setDisable(false);
                new ZoomIn(adminBoxPane).play();
                break;
        }
    }



    public void initAllColumns(){
        C1.setCellValueFactory(new PropertyValueFactory<>("Name"));
        C2.setCellValueFactory(new PropertyValueFactory<>("Surname"));
        C3.setCellValueFactory(new PropertyValueFactory<>("Email"));
        C4.setCellValueFactory(new PropertyValueFactory<>("CardNumber"));
        C5.setCellValueFactory(new PropertyValueFactory<>("DateOfBirth"));
        C6.setCellValueFactory(new PropertyValueFactory<>("Nick"));
        C7.setCellValueFactory(new PropertyValueFactory<>("TypeOfSubscryption"));

        D1.setCellValueFactory(new PropertyValueFactory<>("Name"));
        D2.setCellValueFactory(new PropertyValueFactory<>("Surname"));
        D3.setCellValueFactory(new PropertyValueFactory<>("Email"));
        D4.setCellValueFactory(new PropertyValueFactory<>("CompanyName"));
        D5.setCellValueFactory(new PropertyValueFactory<>("BankAccountNumber"));
        D6.setCellValueFactory(new PropertyValueFactory<>("AmountOfTheContract"));
        D7.setCellValueFactory(new PropertyValueFactory<>("StrMonthlyPain"));

        DC1.setCellValueFactory(new PropertyValueFactory<>("StartDiscountDate"));
        DC2.setCellValueFactory(new PropertyValueFactory<>("EndDiscountDate"));
        DC3.setCellValueFactory(new PropertyValueFactory<>("PercentAmountOfDiscount"));
        DC4.setCellValueFactory(new PropertyValueFactory<>("DiscountedProduct"));

        S1.setCellValueFactory(new PropertyValueFactory<>("Title"));
        S2.setCellValueFactory(new PropertyValueFactory<>("Price"));
        S3.setCellValueFactory(new PropertyValueFactory<>("NumberOfDevices"));
        S4.setCellValueFactory(new PropertyValueFactory<>("MaxResolution"));
    }
    public void refreshClientList(){
        clientList.clear();
        Object[] values = Entities_Manager.getInstance().getContainerOfClients().values().toArray();
        for (int i = 0; i < values.length; i++) {
            Client client = (Client) values[i];
            clientList.add(client);
        }
    }
    public void refreshDistributorList(){
        distributorList.clear();
        Object[]values = Entities_Manager.getInstance().getContainerOfDistributors().values().toArray();
        for (int i = 0; i <values.length ; i++) {
            Distributor distributor = (Distributor) values[i];
            distributorList.add(distributor);
        }
    }
    public void refreshDiscountList(){
        discountList.clear();
        Object[] values = Discount_Manager.getInstance().getContainerOfDiscounts().toArray();
        for (int i = 0; i <values.length; i++) {
            Discount discount = (Discount) values[i];
            discountList.add(discount);
        }

    }
    public void refreshSubscriptionList(){
        subscriptionList.clear();
        subscriptionList.addAll(Subscription.Basic,Subscription.Family,Subscription.Premium);
    }
    public void loadDataToTableView(){
        refreshClientList();
        refreshDistributorList();
        refreshDiscountList();
        refreshSubscriptionList();
        clientTableView.setItems(clientList);
        distributorTableView.setItems(distributorList);
        discountTableView.setItems(discountList);
        subscriptionTableView.setItems(subscriptionList);
    }
    public void changeDateAndFinancial(){
        financialStatus.setText(String.valueOf(Time_Manager.getInstance().getFinancialConditionOfService())+" $");
        datePicker.setValue(Time_Manager.getInstance().getTimeOfSimulation());
    }


    public MyLabel newPicture(Product product){

        MyLabel lbl = new MyLabel(product.getTitle(),product);
        lbl.setPrefSize(210,380);
        lbl.setContentDisplay(ContentDisplay.TOP);
        lbl.setAlignment(Pos.BASELINE_CENTER);
        lbl.setFont(new Font("Segoe Print",23));
        lbl.setWrapText(true);
//        lbl.setStyle("-fx-background-color:rgb("+100+","+250+","+0+");");
//        Image img = new Image("https://picsum.photos/260/330/?random");
        Image img = new Image(product.getPhotoURL());
        ImageView imageView = new ImageView(img);
        imageView.setFitHeight(320);
        imageView.fitWidthProperty().bind(lbl.widthProperty());
        lbl.setGraphic(imageView);

//        imageView.setFitWidth(200);
//        imageView.fitHeightProperty().bind(lbl.heightProperty());

        lbl.addEventHandler(MouseEvent.MOUSE_PRESSED,(event -> {
            MouseButton button = event.getButton();

            if(button==MouseButton.PRIMARY){
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("InformationPane.fxml"));
                try {
                    Parent newroot = fxmlLoader.load();
                    InformationPane_Controller controller = fxmlLoader.<InformationPane_Controller>getController();
                    controller.setAll(product);
                    Scene scene = new Scene(newroot);
                    Stage stage = new Stage();
                    stage.setScene(scene);
//                    stage.initStyle(StageStyle.UNDECORATED);
                    stage.show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else if(button==MouseButton.SECONDARY) {
                Products_Manager.getInstance().deleteProduct(product);
                if (product instanceof Movie){
                    movieList.remove((Movie)product);
                    movieMasonry.getChildren().remove(lbl);
                }
                else if (product instanceof Serial){
                    serialList.remove((Serial)product);
                    serialMasonry.getChildren().remove(lbl);
                }
                else {
                    liveList.remove((Live)product);
                    liveMasonry.getChildren().remove(lbl);
                }
            }



        }));
        return lbl;
    }
    public void addNewMovieToBeVisible(Movie movie){
        movieMasonry.getChildren().add(newPicture(movie));
    }
    public void addNewSerialToBeVisible(Serial serial){
        serialMasonry.getChildren().add(newPicture(serial));
    }
    public void addNewLiveToBeVisible(Live live){
        liveMasonry.getChildren().add(newPicture(live));
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        //Aktualizacja Daty i Pieniedzy
        new Thread(()->{
                Time_Manager time_manager = Time_Manager.getInstance();
                while (!Time_Manager.getInstance().isShutdown()){
                    Platform.runLater(this::changeDateAndFinancial);
                    try {
                        Thread.sleep(time_manager.getSimulationSpeed());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }).start();

        //SplashScreen
        if (!Main.isIsSplashLoaded()) loadSplashScreen();

        //PRZECIAGANIE APLIKACJI
        rootPane.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                xOffset = event.getSceneX();
                yOffset = event.getSceneY();
            }
        });
        rootPane.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Stage primaryStage = (Stage) rootPane.getScene().getWindow();
                primaryStage.setX(event.getScreenX() - xOffset);
                primaryStage.setY(event.getScreenY() - yOffset);
            }
        });

        //ROZSZERZANIE APLIKACJI
        dragButton.setOnMouseDragged(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent e) {
                Stage primaryStage = (Stage) rootPane.getScene().getWindow();
//                System.out.println("TO JEST X: "+ String.valueOf(e.getScreenX() - primaryStage.getX()));
//                System.out.println("TO JEST Y: "+ String.valueOf(e.getScreenY() - primaryStage.getY()));
                if (e.getScreenX() - primaryStage.getX()>=1280){
                    primaryStage.setWidth(e.getScreenX() - primaryStage.getX());
                }
                if (e.getScreenY() - primaryStage.getY()>=600){
                    primaryStage.setHeight(e.getScreenY() - primaryStage.getY());
                }
            }
        });

        //sidebar
        VBox box = null;
        try {
            box = FXMLLoader.load(getClass().getResource("../GUI/SideBar.fxml"));
            drawer.setSidePane(box);

            for (Node node : box.getChildren()){
                if (node.getAccessibleText() !=null){
                    node.addEventHandler(MouseEvent.MOUSE_CLICKED,(e)->{
                    changePane(node.getAccessibleText());
                    });
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Animacja Hamburgera
        HamburgerBackArrowBasicTransition transition = new HamburgerBackArrowBasicTransition(hamburger);
        transition.setRate(-1);
        hamburger.addEventHandler(MouseEvent.MOUSE_PRESSED, (e) -> {
            transition.setRate(transition.getRate() * -1);
            transition.play();

            if (drawer.isOpened()) {
                drawer.close();
            } else {
                drawer.open();
            }
        });

        //movieListiner
        movieList.addListener(new ListChangeListener<Movie>() {
            @Override
            public void onChanged(Change<? extends Movie> change) {
                while (change.next()) {
                    if (change.wasPermutated()) {
                        System.out.println("A permutation is detected.");
                    } else if (change.wasUpdated()) {
                        System.out.println("An update is detected.");
                    } else if (change.wasReplaced()) {
                        System.out.println("A replacement is detected.");
                    } else {
                        if (change.wasRemoved()) {
                            System.out.println("A removal is detected.");
                        } else if (change.wasAdded()) {
//                            System.out.println("An addiiton is detected.  " + change);
                            int start = change.getFrom();
                            int end = change.getTo();
                            for (int i = start; i < end; i++) {
//                                System.out.println("Element at position " + i + " was updated to: " + change.getList().get(i).getTitle());
                                final int lol = i;
                                Platform.runLater(()->{
                                    addNewMovieToBeVisible(change.getList().get(lol));
                                });
                            }
                        }
                    }
                }
            }
        });


        //serialListiner
        serialList.addListener(new ListChangeListener<Serial>() {
            @Override
            public void onChanged(Change<? extends Serial> change) {
                while (change.next()) {
                    if (change.wasPermutated()) {
                        System.out.println("A permutation is detected.");
                    } else if (change.wasUpdated()) {
                        System.out.println("An update is detected.");
                    } else if (change.wasReplaced()) {
                        System.out.println("A replacement is detected.");
                    } else {
                        if (change.wasRemoved()) {
                            System.out.println("A removal is detected.");
                        } else if (change.wasAdded()) {
//                            System.out.println("An addiiton is detected.  " + change);
                            int start = change.getFrom();
                            int end = change.getTo();
                            for (int i = start; i < end; i++) {
//                                System.out.println("Element at position " + i + " was updated to: " + change.getList().get(i).getTitle());
                                final int lol = i;
                                Platform.runLater(()->{
                                    addNewSerialToBeVisible(change.getList().get(lol));
                                });
                            }
                        }
                    }
                }
            }
        });


        //liveListiner
        liveList.addListener(new ListChangeListener<Live>() {
            @Override
            public void onChanged(Change<? extends Live> change) {
                while (change.next()) {
                    if (change.wasPermutated()) {
                        System.out.println("A permutation is detected.");
                    } else if (change.wasUpdated()) {
                        System.out.println("An update is detected.");
                    } else if (change.wasReplaced()) {
                        System.out.println("A replacement is detected.");
                    } else {
                        if (change.wasRemoved()) {
                            System.out.println("A removal is detected.");
                        } else if (change.wasAdded()) {
//                            System.out.println("An addiiton is detected.  " + change);
                            int start = change.getFrom();
                            int end = change.getTo();
                            for (int i = start; i < end; i++) {
//                                System.out.println("Element at position " + i + " was updated to: " + change.getList().get(i).getTitle());
                                final int lol = i;
                                Platform.runLater(()->{
                                    addNewLiveToBeVisible(change.getList().get(lol));
                                });
                            }
                        }
                    }
                }
            }
        });

        //Inicjalizacja Admin Boxa
        initAllColumns();
        loadDataToTableView();


//        clientList.addListener(new InvalidationListener() {
//            @Override
//            public void invalidated(Observable observable) {
//            }
//        });
//        distributorList.addListener(new InvalidationListener() {
//            @Override
//            public void invalidated(Observable observable) {
//                Platform.runLater(()->{
//                    changeDateAndFinancial();
//                });
//            }
//        });
//        discountList.addListener(new InvalidationListener() {
//            @Override
//            public void invalidated(Observable observable) {
//                Platform.runLater(()->{
//                    changeDateAndFinancial();
//                });            }
//        });
//        subscriptionList.addListener(new InvalidationListener() {
//            @Override
//            public void invalidated(Observable observable) {
//                Platform.runLater(()->{
//                    changeDateAndFinancial();
//                });            }
//        });

    }
}
