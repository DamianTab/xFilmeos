package Main_package;

import Entities.Entities_Manager;
import Products.Discount_Manager;
import Products.Products_Manager;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


public class Main extends Application {

    private static boolean isSplashLoaded = false;
    public static boolean isIsSplashLoaded() {
        return isSplashLoaded;
    }
    public static void setIsSplashLoaded(boolean isSplashLoaded) {
        Main.isSplashLoaded = isSplashLoaded;
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/GUI/Window_App_UI.fxml"));
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.show();
    }



    public static void main(String[] args) throws InterruptedException {
        launch(args);
    }
}
