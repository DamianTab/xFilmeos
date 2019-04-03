package GUI;

import Products.*;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.File;
import java.io.Serializable;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicReference;

public class InformationPane_Controller implements Initializable, Serializable {

    @FXML
    private LineChart<?, ?> lineChart;

    @FXML
    private Label productLabel;

    @FXML
    private Text changableTitle1;

    @FXML
    private Text changableTitle2;

    @FXML
    private JFXTextArea title;

    @FXML
    private JFXTextArea description;

    @FXML
    private JFXTextArea productionCountries;

    @FXML
    private JFXTextArea changable1;

    @FXML
    private JFXTextArea changable2;

    @FXML
    private JFXTextArea length;

    @FXML
    private JFXTextArea rating;

    @FXML
    private JFXTextArea numberOfShows;

    @FXML
    private ImageView photo;

    @FXML
    private JFXButton deleteButton;

    @FXML
    void deleteFile(ActionEvent event) {
        Stage stage = (Stage) deleteButton.getScene().getWindow();
        stage.close();
    }

    public void setAll(Product product){

        title.setText(product.getTitle());
        description.setText(product.getDescription());
        File file = new File(product.getPhotoURL());
        photo.setImage(new Image(file.toURI().toString()));
        productionCountries.setText(product.getProductionCountries());
        length.setText(String.valueOf(product.getLength()));
        rating.setText(String.valueOf(product.getRating()));
        numberOfShows.setText(String.valueOf(product.getHowManyTimeWasShown()));


        if (product instanceof Movie){
            productLabel.setText("Movie");
            changableTitle1.setText("Actors");
            changableTitle2.setText("Genre");
            changable2.setText(((Movie) product).getGenre());
            AtomicReference<String> Actors= new AtomicReference<>("");
            ((Movie) product).getActorsList().forEach((v) -> {
                if (Actors.get()=="") Actors.set(v);
                else Actors.set(Actors +", "+v);
                changable1.setText(Actors.get());
            });

        }
        else if (product instanceof Serial){
            productLabel.setText("Serial");
            changableTitle1.setText("Actors");
            changableTitle2.setText("Number of Seasons");
            AtomicReference<String> Actors= new AtomicReference<>("");
            ((Serial) product).getActorsList().forEach((v) -> {
                if (Actors.get()=="") Actors.set(v);
                else Actors.set(Actors +", "+v);
                changable1.setText(Actors.get());
            });
            changable2.setText(String.valueOf(((Serial)product).getListOfSeasons().size()));


        }
        else {
            productLabel.setText("Live");
            changableTitle1.setText("Date of Show");
            changable1.setText(((Live)product).getDateOfTheShow().toString());

            changable2.setVisible(false);
            changable2.setDisable(true);
            changableTitle2.setVisible(false);
        }

    }



    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
