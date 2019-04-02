package Entities;

import java.io.Serializable;

public enum Subscription implements Serializable {
    Basic("Basic",49.99f,1,"720p"),
    Family("Family",69.99f,3,"1080p"),
    Premium("Premium",99.99f,5,"2K");

    private String Title;
    private float Price;
    private int NumberOfDevices;
    private String MaxResolution;

    Subscription(String title, float price, int numberOfDevices, String maxResolution) {
        Title = title;
        Price = price;
        NumberOfDevices = numberOfDevices;
        MaxResolution = maxResolution;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public float getPrice() {
        return Price;
    }

    public void setPrice(float price) {
        Price = price;
    }

    public int getNumberOfDevices() {
        return NumberOfDevices;
    }

    public void setNumberOfDevices(int numberOfDevices) {
        NumberOfDevices = numberOfDevices;
    }

    public String getMaxResolution() {
        return MaxResolution;
    }

    public void setMaxResolution(String maxResolution) {
        MaxResolution = maxResolution;
    }


}
