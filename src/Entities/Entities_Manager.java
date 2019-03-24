package Entities;

import CSV.CSVReaderExample;
import GUI.Window_App_UI_Controller;
import Main_package.Main;
import Main_package.SimulationException;
import Main_package.SimulationExceptionContractor;
import Products.Products_Manager;
import com.sun.org.apache.xpath.internal.operations.Number;
import sun.misc.Cleaner;

import javax.management.RuntimeErrorException;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.*;

public class Entities_Manager implements Serializable {

    //    public static Entities_Manager getInstance(){
//    return SingletonHolder.INSTANCE;
//    }

    //    private static class SingletonHolder {
//        private static final Entities_Manager INSTANCE = new Entities_Manager();
//    }

    private static Entities_Manager INSTANCE;
    public static synchronized Entities_Manager getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new Entities_Manager();
        }
        return INSTANCE;
    }

    public static void setINSTANCE(Entities_Manager entities_manager){
            INSTANCE = entities_manager;
    }

    private Entities_Manager() {
    }


    private String [] RandName = new String[1000];
    private String [] RandSurname = new String[1000];
    private String [] RandEmail = new String[1000];
    private int NumberOfContractors = 0;
    private String[] RandCardNumberClient = new String[1000];
    private String[] RandDateOfBirth = new String[1000];
    private String[] RandNick = new String[1000];
    private int NumberOfClients = 0;
    private HashMap<Integer, Client> ContainerOfClients = new HashMap<>();
    private String[] RandCompanyName = new String[1000];
    private String[] RandCardNumberDistributor = new String[1000];
    private int NumberOfDistributors =0;
    private HashMap<Integer, Distributor> ContainerOfDistributors = new HashMap<>();

    public String[] getRandEmail() {
        return RandEmail;
    }


    public HashMap<Integer, Client> getContainerOfClients() {
        return ContainerOfClients;
    }
    public HashMap<Integer, Distributor> getContainerOfDistributors() {
        return ContainerOfDistributors;
    }


    public int getNumberOfDistributors() {
        return NumberOfDistributors;
    }

    public void setNumberOfDistributors(int numberOfDistributors) {
        NumberOfDistributors = numberOfDistributors;
    }

    public void setContainerOfDistributors(HashMap<Integer, Distributor> ContainerOfDistributors) {
        ContainerOfDistributors = ContainerOfDistributors;
    }

    public int getNumberOfContractors() {
        return NumberOfContractors;
    }

    public void setNumberOfContractors(int numberOfContractors) {
        NumberOfContractors = numberOfContractors;
    }

    public int getNumberOfClients() {
        return NumberOfClients;
    }

    public void setNumberOfClients(int numberOfClients) {
        NumberOfClients = numberOfClients;
    }

    public void setContainerOfClients(HashMap<Integer, Client> ContainerOfClients) {
        ContainerOfClients = ContainerOfClients;
    }


    public void loadAllDataFromCSV(){
        loadContractors();
        loadClients();
        loadDistributors();
    }
    public void loadContractors(){
       String[][] temp = CSVReaderExample.read(3,"contractor.csv");
       RandName = temp[0];
       RandSurname = temp[1];
       RandEmail = temp[2];
    }
    public void loadClients(){
        String[][] temp = CSVReaderExample.read(3,"client.csv");
        RandCardNumberClient = temp[0];
        RandDateOfBirth = temp[1];
        RandNick = temp[2];
    }
    public void loadDistributors(){
        String[][] temp = CSVReaderExample.read(2,"distributor.csv");
        RandCompanyName = temp[0];
        RandCardNumberDistributor = temp[1];
    }

    public Client addClient() throws SimulationExceptionContractor {
        if (NumberOfContractors>=1000) throw new SimulationExceptionContractor();

        Random generate = new Random();
        String email = RandEmail[NumberOfContractors];

        while (ContainerOfClients.containsKey(email.hashCode())){
            System.out.println("UWAGA!!!! TAKI KLIENT JUZ ISTNIEJE\n");
            NumberOfContractors++;
            email = RandEmail[NumberOfContractors];
        }

        LocalDate date = LocalDate.parse(RandDateOfBirth[NumberOfClients]);
        Subscription subscription = null;
        switch (generate.nextInt(5)){
            case 0:
                subscription=Subscription.Basic;
                break;
            case 1:
                subscription=Subscription.Family;
                break;
            case 2:
                subscription=Subscription.Premium;
                break;
        }

        Client tmpClient = new Client(RandName[NumberOfContractors],RandSurname[NumberOfContractors],email,
                Double.parseDouble(RandCardNumberClient[NumberOfClients]),date,RandNick[NumberOfClients],subscription);

//        tmpClient.show();
        ContainerOfClients.put(tmpClient.getEmail().hashCode(),tmpClient);
        NumberOfContractors++;
        NumberOfClients++;
        Window_App_UI_Controller.addToClientObservableList(tmpClient);
        return  tmpClient;
    }
    public Distributor addDistributor() throws SimulationExceptionContractor{
        if (NumberOfContractors>=1000) throw new SimulationExceptionContractor();
        String email = RandEmail[NumberOfContractors];

        while (ContainerOfDistributors.containsKey(email.hashCode())){
            System.out.println("UWAGA!!!! TAKI Dystrybutor JUZ ISTNIEJE\n");
            NumberOfContractors++;
            email = RandEmail[NumberOfContractors];
        }

        Distributor tmpDistributor = new Distributor(RandName[NumberOfContractors],RandSurname[NumberOfContractors],RandEmail[NumberOfContractors],
                RandCompanyName[NumberOfDistributors],Double.parseDouble(RandCardNumberDistributor[NumberOfDistributors]),
                0f,new Random().nextBoolean());
//        tmpDistributor.show();
        System.out.println("Dodano Dystrybutora");

        ContainerOfDistributors.put(tmpDistributor.getEmail().hashCode(),tmpDistributor);
        NumberOfContractors++;
        NumberOfDistributors++;
        Window_App_UI_Controller.addToDistributorObservableList(tmpDistributor);
        return tmpDistributor;
    }

    public void deleteClient(Client client){
        ContainerOfClients.remove(client.getEmail().hashCode());
    }
    public void deleteDistributor(Distributor distributor){
        ContainerOfDistributors.remove(distributor.getEmail().hashCode());
    }


    public void payMontlyPaymentToAllDistributors(){
        for (Distributor distributor:ContainerOfDistributors.values()) {
            if (distributor.getisMonthlyPaid() && distributor.getAmountOfTheContract()!=0)
                distributor.reciveMontlyPaymentFromVODService();
        }
    }
    public void payForSubscribtionByAllClients(){
        for (Client client:ContainerOfClients.values()) {
            if (client.getTypeOfSubscryption()!=null)
                client.payForSubscribtion();
        }
    }


    public void showContainerOfClients(){
        System.out.println("----------------");
        ContainerOfClients.forEach((k,v)->System.out.println("Key: " + k + " Value: " + v));
        System.out.println("----------------");
    }
    public void showContainerOfDistributors(){
        System.out.println("----------------");
        ContainerOfDistributors.forEach((k,v)->System.out.println("Key: " + k + " Value: " + v));
        System.out.println("----------------");
    }

}
