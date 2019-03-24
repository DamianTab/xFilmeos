package Entities;

import java.io.Serializable;

public abstract class Contractor implements Serializable {
    private String Name;
    private String Surname;
    private String Email;


    public Contractor(String name, String surname, String email) {
        Name = name;
        Surname = surname;
        Email = email; // Identyfikator każdego kontrachenta
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getSurname() {
        return Surname;
    }

    public void setSurname(String surname) {
        Surname = surname;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public void show(){
        System.out.println(Name+"  "+ Surname+"  "+Email);
    }

}
