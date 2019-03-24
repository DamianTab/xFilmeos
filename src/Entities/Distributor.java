package Entities;

import Main_package.SimulationExceptionProduct;
import Main_package.Threads_Manager;
import Main_package.Time_Manager;
import Products.Product;
import Products.Products_Manager;

import java.io.Serializable;
import java.util.Random;

public class Distributor extends Contractor implements Runnable, Serializable {

    private String CompanyName;
    private double BankAccountNumber;
    private float AmountOfTheContract;
    private boolean isMonthlyPaid;
    private boolean shutdown = false;
    private String StrMonthlyPain;

    public Distributor(String name, String surname, String email, String companyName, double bankAccountNumber, float amountOfTheContract, boolean typeOfContract) {
        super(name, surname, email);
        CompanyName = companyName;
        BankAccountNumber = bankAccountNumber;
        AmountOfTheContract = amountOfTheContract;
        isMonthlyPaid = typeOfContract;
        if (isMonthlyPaid) StrMonthlyPain = "True";
        else StrMonthlyPain = "False";
    }

    public String getStrMonthlyPain() {
        return StrMonthlyPain;
    }

    public void setStrMonthlyPain(String strMonthlyPain) {
        StrMonthlyPain = strMonthlyPain;
    }

    public String getCompanyName() {
        return CompanyName;
    }

    public boolean getMonthlyPaid() {
        return isMonthlyPaid;
    }

    public void setMonthlyPaid(boolean monthlyPaid) {
        isMonthlyPaid = monthlyPaid;
    }

    public void setCompanyName(String companyName) {
        CompanyName = companyName;
    }

    public double getBankAccountNumber() {
        return BankAccountNumber;
    }

    public void setBankAccountNumber(double bankAccountNumber) {
        BankAccountNumber = bankAccountNumber;
    }

    public float getAmountOfTheContract() {
        return AmountOfTheContract;
    }

    public void setAmountOfTheContract(float amountOfTheContract) {
        AmountOfTheContract = amountOfTheContract;
    }

    public boolean getisMonthlyPaid() {
        return isMonthlyPaid;
    }

    public void setisMonthlyPaid(boolean typeOfContract) {
        isMonthlyPaid = typeOfContract;
    }

    public void show() {
        super.show();
        System.out.println(CompanyName + "  " + BankAccountNumber + "  " + AmountOfTheContract + "  " + isMonthlyPaid);
    }

    public void giveNewProduct(){
        Random generate = new Random();
        Products_Manager Ruler = Products_Manager.getInstance();
        Product createdProduct = null;
        try {
            switch (generate.nextInt(3)) {
                case 0:
                    createdProduct = Ruler.addMovie(this);

                    break;
                case 1:
                    createdProduct = Ruler.addLive(this);
                    break;
                case 2:
                    createdProduct = Ruler.addSerial(this);
                    break;
            }
        } catch (SimulationExceptionProduct simulationExceptionProduct) {
            simulationExceptionProduct.printStackTrace();
        }

        if (isMonthlyPaid){
            AmountOfTheContract+=5*createdProduct.getPrice();
        }
    }

    public void reciveMoneyForShowingMyProduct(float price){
        Time_Manager.getInstance().AdditionToFinancialConditionOfService(-price);
    }
    public void  reciveMontlyPaymentFromVODService(){
        Time_Manager.getInstance().AdditionToFinancialConditionOfService(-AmountOfTheContract);
    }

    public void shutdownThread(){
        shutdown=true;
        Threads_Manager.getInstance().deleteDistributorThread(Thread.currentThread());
    }
    public Thread resumeThread(){
        shutdown=false;
        Thread tmp = new Thread(this);
        tmp.start();
        return tmp;
    }



    @Override
    public void run() {
        System.out.println("+++++++NOWY PROCES Dystrybutora");
        Random generate = new Random();
        Time_Manager time_manager = Time_Manager.getInstance();

        while (!shutdown){
            if (generate.nextInt(10)==0)giveNewProduct();

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
