package Products;

import Entities.Distributor;

import java.io.Serializable;
import java.net.URL;
import java.time.LocalDate;

public class Live extends Product implements Serializable {
    private LocalDate DateOfTheShow;

    public Live(String title, String photoURL, String description, LocalDate productionDate, int length, Distributor productDistributor, String productionCountries, int rating, float price, LocalDate dateOfTheShow) {
        super(title, photoURL, description, productionDate, length, productDistributor, productionCountries, rating, price);
        DateOfTheShow = dateOfTheShow;
    }

    public LocalDate getDateOfTheShow() {
        return DateOfTheShow;
    }

    public void setDateOfTheShow(LocalDate dateOfTheShow) {
        DateOfTheShow = dateOfTheShow;
    }

    public void show(){
        super.show();
        System.out.println(DateOfTheShow);
    }
}
