package Entities;

import Main_package.Threads_Manager;
import Main_package.Time_Manager;
import Products.Live;
import Products.Product;
import Products.Products_Manager;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Random;

public class Client extends Contractor implements Runnable, Serializable {
    private double CardNumber;
    private LocalDate DateOfBirth;
    private String Nick;
    private Subscription TypeOfSubscryption;
    private boolean shutdown = false;

    public Client(String name, String surname, String email,double cardNumber, LocalDate dateOfBirth, String nick, Subscription subscription) {
        super(name, surname, email);
        CardNumber = cardNumber;
        DateOfBirth = dateOfBirth;
        Nick = nick;
        TypeOfSubscryption = subscription;
    }
    public double getCardNumber() {
        return CardNumber;
    }

    public void setCardNumber(double cardNumber) {
        CardNumber = cardNumber;
    }

    public LocalDate getDateOfBirth() {
        return DateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        DateOfBirth = dateOfBirth;
    }

    public String getNick() {
        return Nick;
    }

    public void setNick(String nick) {
        Nick = nick;
    }

    public Subscription getTypeOfSubscryption() {
        return TypeOfSubscryption;
    }

    public void setTypeOfSubscryption(Subscription typeofsubscryption) {
        TypeOfSubscryption = typeofsubscryption;
    }

    public void show(){
        super.show();
        System.out.println(CardNumber +"  "+ DateOfBirth.toString() +"  "+Nick+"  "+ TypeOfSubscryption );
    }

    public void payMoneyToService(float amount){
        Time_Manager.getInstance().AdditionToFinancialConditionOfService(amount);
    }

    public void showAndMaybeBuyProduct(){
        Random generate = new Random();
        Products_Manager manager = Products_Manager.getInstance();
        Object[] values = null;
        do {
            switch (generate.nextInt(3)){
                case 0:
                    values = manager.getValuesOfMovies();
                    break;
                case 1:
                    values = manager.getValuesOfLives();
                    break;
                case 2:
                    values = manager.getValuesOfSerials();
                    break;
            }
        }while (values.length==0);

        Product randomProduct = (Product) values[generate.nextInt(values.length)];
        if (TypeOfSubscryption == null || randomProduct instanceof Live){ // Jeśli klient nie ma subskrypcji lub jest to Live to płaci jednorazowo
            if (randomProduct instanceof Live){
                Live tmpLive = (Live) randomProduct;
                if (tmpLive.getDateOfTheShow().isAfter(Time_Manager.getInstance().getTimeOfSimulation())){
                    return; // zapobiega oglądaniu i kupowaniu już odbytych Livestreamów
                }
            }
            payMoneyToService(randomProduct.getPrice());
        }
        randomProduct.addValueToShownCounter();
//        randomProduct.show();
        if (!randomProduct.getProductDistributor().getisMonthlyPaid()) // Jeśli dystrybutor nie ma ryczałtu miesięcznego to serwis od razu płaci za obejrzenie
            randomProduct.getProductDistributor().reciveMoneyForShowingMyProduct(randomProduct.getPrice()/10);
    }

    public void payForSubscribtion(){
        Time_Manager.getInstance().AdditionToFinancialConditionOfService(TypeOfSubscryption.getPrice());
    }

    public void shutdownThread(){
        shutdown=true;
        Threads_Manager threads_manager = Threads_Manager.getInstance();
        threads_manager.deleteClientThread(Thread.currentThread());
    }
    public Thread resumeThread(){
        shutdown=false;
        Thread tmp = new Thread(this);
        tmp.start();
        return tmp;
    }


    @Override
    public void run() {
        System.out.println("+++++++NOWY PROCES KLIENTA");
        Random generate = new Random();
        Products_Manager products_manager = Products_Manager.getInstance();
        Time_Manager time_manager = Time_Manager.getInstance();

        while (!shutdown){
            if ( generate.nextInt(8)==0 && products_manager.getNumberOfProducts()!=0){
                showAndMaybeBuyProduct();
//                System.out.println("-----OBEJRZANO PRODUKT");
            }
            do {
                try {
                    Thread.sleep(time_manager.getSimulationSpeed());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }while (time_manager.isIfStopSimulation());
        }
    }

}
