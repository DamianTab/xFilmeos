package Main_package;

import Entities.Client;
import Entities.Distributor;
import Entities.Entities_Manager;
import Products.Discount;
import Products.Discount_Manager;
import Products.Product;
import Products.Products_Manager;

import java.io.Serializable;
import java.util.HashSet;
import java.util.LinkedHashSet;

public class Threads_Manager implements Runnable, Serializable {


//    public static Threads_Manager getInstance(){
//        return Threads_Manager.SingletonHolder.INSTANCE;
//    }
//    private static class SingletonHolder {
//        private static final Threads_Manager INSTANCE = new Threads_Manager();
//    }

    private static Threads_Manager INSTANCE;
    public static synchronized Threads_Manager getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new Threads_Manager();
        }
        return INSTANCE;
    }

    public static void setINSTANCE(Threads_Manager threads_Manager){
        INSTANCE = threads_Manager;
    }
    private Threads_Manager() {
    }


    private HashSet<Thread> ContainerOfDistributorsThreads = new HashSet<>();
    private HashSet<Thread> ContainerOfClientsThreads = new HashSet<>();
    private LinkedHashSet<Thread> ContainerOfDiscountsThreads = new LinkedHashSet<>();
    private boolean shutdown=false;


    public void shutdownThread(){
        shutdown=true;
    }
    public void resumeThread(){
        shutdown=false;
    }


    public void addNewDistributorThread(){
        Thread tmpThread = null;
        try {
            tmpThread = new Thread(Entities_Manager.getInstance().addDistributor());
            ContainerOfDistributorsThreads.add(tmpThread);
            tmpThread.start();
        } catch (SimulationExceptionContractor simulationExceptionContractor) {
            simulationExceptionContractor.printStackTrace();
        }

    }
    public void addNewClientThread(){
        Thread tmpThread = null;
        try {
            tmpThread = new Thread(Entities_Manager.getInstance().addClient());
            ContainerOfClientsThreads.add(tmpThread);
            tmpThread.start();
        } catch (SimulationExceptionContractor e) {
            e.printStackTrace();
        }

    }
    public void addNewDiscountThread(Product product){
        Thread tmpThread = new Thread(Discount_Manager.getInstance().addDiscount(product));
        ContainerOfDiscountsThreads.add(tmpThread);
        tmpThread.start();
    }


    public void addOldDistributorThread(Distributor distributor){
        distributor.resumeThread();
        Thread tmpThread = new Thread(distributor);
        ContainerOfDistributorsThreads.add(tmpThread);
        tmpThread.start();
    }
    public void addOldClientThread(Client client){
        client.resumeThread();
        Thread tmpThread = new Thread(client);
        ContainerOfClientsThreads.add(tmpThread);
        tmpThread.start();
    }
    public void addOldDiscountThread(Discount discount){
        discount.resumeThread();
        Thread tmpThread = new Thread(discount);
        ContainerOfDiscountsThreads.add(tmpThread);
        tmpThread.start();
    }

    public void deleteClientThread(Thread thread){
        ContainerOfClientsThreads.remove(thread);
    }
    public void deleteDistributorThread(Thread thread){
        ContainerOfDistributorsThreads.remove(thread);
    }
    public void deleteDiscountThread(Thread thread){
        ContainerOfDiscountsThreads.remove(thread);
    }

    public void interruptAllClientsThreads(){
        if (ContainerOfClientsThreads.size()!=0)
        ContainerOfClientsThreads.forEach((object)->object.interrupt());
    }
    public void interruptAllDistributorsThreads(){
        if (ContainerOfDistributorsThreads.size()!=0)
        ContainerOfDistributorsThreads.forEach((object)->object.interrupt());
    }
    public void interruptDiscountsThreads(){
        if (ContainerOfDiscountsThreads.size()!=0)
        ContainerOfDiscountsThreads.forEach((object)->object.interrupt());
    }
    public void interruptAllThreads(){
        interruptAllClientsThreads();
        interruptAllDistributorsThreads();
        interruptDiscountsThreads();
    }

    public void shutdownAllClientsThreads(){
        Entities_Manager entities_manager = Entities_Manager.getInstance();
        Object[] values = entities_manager.getContainerOfClients().values().toArray();
        for (int i = 0; i < values.length; i++) {
            Client client = (Client) values[i];
            client.shutdownThread();
        }
    }
    public void shutdownAllDistributorsThreads(){
        Entities_Manager entities_manager = Entities_Manager.getInstance();
        Object[]values = entities_manager.getContainerOfDistributors().values().toArray();
        for (int i = 0; i <values.length ; i++) {
            Distributor distributor = (Distributor) values[i];
            distributor.shutdownThread();
        }
    }
    public void shutdownAllDiscountsThreads(){
        Object[] values = Discount_Manager.getInstance().getContainerOfDiscounts().toArray();
        for (int i = 0; i <values.length; i++) {
            Discount discount = (Discount) values[i];
            discount.shutdownThread();
        }
    }
    public void shutdownAllThreads(){
        shutdownThread();
        Discount_Manager.getInstance().shutdownThread();
        shutdownAllDiscountsThreads();
        shutdownAllClientsThreads();
        shutdownAllDistributorsThreads();
        Time_Manager.getInstance().shutdownThread();
    }

    public void resumeAllClientsThreads(){
        for (Client v : Entities_Manager.getInstance().getContainerOfClients().values()){
            ContainerOfClientsThreads.add(v.resumeThread());
        }
    }
    public void resumeAllDistributorsThreads(){
        for (Distributor v : Entities_Manager.getInstance().getContainerOfDistributors().values()){
            ContainerOfDistributorsThreads.add(v.resumeThread());
        }
    }
    public void resumeAllDiscountsThreads(){
        for (Discount v : Discount_Manager.getInstance().getContainerOfDiscounts()){
            ContainerOfDiscountsThreads.add(v.resumeThread());
        }
    }
    public void resumeAllThreads(){


        Time_Manager time_manager = Time_Manager.getInstance();
        time_manager.resumeThread();
        new Thread(time_manager).start();

        resumeAllClientsThreads();
        resumeAllDistributorsThreads();
        resumeAllDiscountsThreads();

        Discount_Manager discount_manager = Discount_Manager.getInstance();
        discount_manager.resumeThread();
        new Thread(discount_manager).start();

        resumeThread();
        new Thread(this).start();

    }


    @Override
    public void run() {
        Time_Manager time_manager = Time_Manager.getInstance();
        Products_Manager products_manager = Products_Manager.getInstance();
        Entities_Manager entities_manager = Entities_Manager.getInstance();
        while (!shutdown) {
            while (!time_manager.isIfStopSimulation()) {
                if (products_manager.getNumberOfProducts() != 0) {
                    if (entities_manager.getNumberOfClients() * 2 < products_manager.getNumberOfProducts()) {
                        addNewClientThread();
                    }
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

}

