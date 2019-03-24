package Products;

import Entities.Distributor;

import java.io.Serializable;
import java.time.LocalDate;

public abstract class Product implements Serializable {
    private String Title;
    private String PhotoURL;
    private String Description;
    private LocalDate ProductionDate;
    private int Length;
    private Distributor ProductDistributor;
    private String ProductionCountries;
    private int Rating;
    private float Price;
    private int HowManyTimeWasShown;
    private Discount LimitedTimeDiscount;


    public Product(String title, String photoURL, String description, LocalDate productionDate, int length, Distributor productDistributor, String productionCountries, int rating, float price) {
        Title = title;
        PhotoURL = photoURL;
        Description = description;
        ProductionDate = productionDate;
        Length = length;
        ProductDistributor = productDistributor;
        ProductionCountries = productionCountries;
        Rating = rating;
        Price = price;
        HowManyTimeWasShown = 0;
        LimitedTimeDiscount = null;
    }
    public String getTitle() {
        return Title;
    }

    public int getHowManyTimeWasShown() {
        return HowManyTimeWasShown;
    }

    public void setHowManyTimeWasShown(int howManyTimeWasShown) {
        HowManyTimeWasShown = howManyTimeWasShown;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getPhotoURL() {
        return PhotoURL;
    }

    public void setPhotoURL(String photoURL) {
        PhotoURL = photoURL;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public LocalDate getProductionDate() {
        return ProductionDate;
    }

    public void setProductionDate(LocalDate productionDate) {
        ProductionDate = productionDate;
    }

    public int getLength() {
        return Length;
    }

    public void setLength(int length) {
        Length = length;
    }

    public Distributor getProductDistributor() {
        return ProductDistributor;
    }

    public void setProductDistributor(Distributor productDistributor) {
        ProductDistributor = productDistributor;
    }

    public String getProductionCountries() {
        return ProductionCountries;
    }

    public void setProductionCountries(String productionCountries) {
        ProductionCountries = productionCountries;
    }

    public int getRating() {
        return Rating;
    }

    public void setRating(int rating) {
        Rating = rating;
    }

    public float getPrice() {
        return Price;
    }

    public void setPrice(float price) {
        Price = price;
    }

    public void show(){
        System.out.println(Title+"  "+PhotoURL+"  "+Description+"  "+ProductionDate+"  "+Length+"  "+ProductDistributor+"  "+
                ProductionCountries+"  "+ Rating+"  "+Price);
        System.out.println("ZOstał wyswietlony tyle razy: "+HowManyTimeWasShown);
    }

    public synchronized void addValueToShownCounter(){
        HowManyTimeWasShown+=1;
    }

    public Discount getLimitedTimeDiscount() {
        return LimitedTimeDiscount;
    }

    public void setLimitedTimeDiscount(Discount limitedTimeDiscount) {
        LimitedTimeDiscount = limitedTimeDiscount;
    }
}




