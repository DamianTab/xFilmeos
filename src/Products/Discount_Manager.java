package Products;

import GUI.Window_App_UI_Controller;
import Main_package.Threads_Manager;
import Main_package.Time_Manager;

import java.io.Serializable;
import java.util.LinkedHashSet;
import java.util.Random;

public class Discount_Manager implements Runnable, Serializable {

//    public static Discount_Manager getInstance(){
//        return Discount_Manager.SingletonHolder.INSTANCE;
//    }
//    private static class SingletonHolder {
//        private static final Discount_Manager INSTANCE = new Discount_Manager();
//    }
private static Discount_Manager INSTANCE;
    public static synchronized Discount_Manager getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new Discount_Manager();
        }
        return INSTANCE;
    }

    public static void setINSTANCE(Discount_Manager discount_Manager){
        INSTANCE = discount_Manager;
    }

    private Discount_Manager() {
    }

    private LinkedHashSet<Discount> ContainerOfDiscounts = new LinkedHashSet<>();
    private boolean shutdown = false;


    public void shutdownThread(){
        shutdown=true;
    }
    public void resumeThread(){
        shutdown=false;
    }
    public LinkedHashSet<Discount> getContainerOfDiscounts() {
        return ContainerOfDiscounts;
    }
    public void setContainerOfDiscounts(LinkedHashSet<Discount> containerOfDiscounts) {
        ContainerOfDiscounts = containerOfDiscounts;
    }

    public Discount addDiscount(Product product){
        Discount tmpDiscount = new Discount(product);
        ContainerOfDiscounts.add(tmpDiscount);
        tmpDiscount.makeDiscount();
        Window_App_UI_Controller.addToDiscountObservableList(tmpDiscount);
        return tmpDiscount;
    }
    public void deleteDiscount(Discount discount){
        ContainerOfDiscounts.remove(discount);
    }

    @Override
    public void run() {
        Random generate = new Random();
        Products_Manager manager = Products_Manager.getInstance();
        Time_Manager time_manager = Time_Manager.getInstance();

        while (!shutdown){
            if (generate.nextInt(3)==0){ // Jakies prawdopodobienstwo na zrobienie promocji dla dowolnego filmu lub live'a
                Object[] values = null;
                do {
                    switch (generate.nextInt(2)){
                        case 0:
                            synchronized (manager.getContainerOfMovies()){
                                values = manager.getContainerOfMovies().values().toArray();
                            }
                            break;
                        case 1:
                            synchronized (manager.getContainerOfLives()){
                                values = manager.getContainerOfLives().values().toArray();
                            }
                            break;
                    }
                }while (values.length==0);
                Product randomProduct = (Product) values[generate.nextInt(values.length)];
                if (randomProduct.getLimitedTimeDiscount()==null) Threads_Manager.getInstance().addNewDiscountThread(randomProduct);
                //Jezeli produkt ma juz promocje no to trudno jedziemy dalej
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
