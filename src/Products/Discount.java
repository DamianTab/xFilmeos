package Products;

import GUI.Window_App_UI_Controller;
import Main_package.Threads_Manager;
import Main_package.Time_Manager;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Random;

public class Discount implements Runnable, Serializable {

    private LocalDate StartDiscountDate;
    private LocalDate EndDiscountDate;
    private int PercentAmountOfDiscount;
    private Product DiscountedProduct;
    private boolean shutdown;

    public LocalDate getStartDiscountDate() {
        return StartDiscountDate;
    }

    public void setStartDiscountDate(LocalDate startDiscountDate) {
        StartDiscountDate = startDiscountDate;
    }

    public LocalDate getEndDiscountDate() {
        return EndDiscountDate;
    }

    public void setEndDiscountDate(LocalDate endDiscountDate) {
        EndDiscountDate = endDiscountDate;
    }

    public int getPercentAmountOfDiscount() {
        return PercentAmountOfDiscount;
    }

    public void setPercentAmountOfDiscount(int percentAmountOfDiscount) {
        PercentAmountOfDiscount = percentAmountOfDiscount;
    }

    public Product getDiscountedProduct() {
        return DiscountedProduct;
    }

    public void setDiscountedProduct(Product discountedProduct) {
        DiscountedProduct = discountedProduct;
    }

    public boolean isShutdown() {
        return shutdown;
    }

    public void setShutdown(boolean shutdown) {
        this.shutdown = shutdown;
    }

    public Discount(Product product) {
        StartDiscountDate= Time_Manager.getInstance().getTimeOfSimulation();
        EndDiscountDate = StartDiscountDate.plus(30, ChronoUnit.DAYS);
        PercentAmountOfDiscount = new Random().nextInt(50)+5;
        DiscountedProduct = product;
        shutdown = false;
        product.setLimitedTimeDiscount(this);
    }
    public void show(){
        System.out.println("ooooooooooooooooooooooooo");
        System.out.println("TO JEST PROMOCJA");
        System.out.println(StartDiscountDate.toString() +"   "+ EndDiscountDate.toString()+"   "+PercentAmountOfDiscount+"%");
        System.out.println("ooooooooooooooooooooooooo");
    }
    public void makeDiscount(){
        float newprice = (100-PercentAmountOfDiscount)*DiscountedProduct.getPrice()/100;
        DiscountedProduct.setPrice(newprice);
    }
    public void cancelDiscount(){
        float oldprice = DiscountedProduct.getPrice()*100/(100-PercentAmountOfDiscount);
        DiscountedProduct.setPrice(oldprice);
        DiscountedProduct.setLimitedTimeDiscount(null);
        Discount_Manager.getInstance().deleteDiscount(this);

    }
    public void shutdownThread(){
        shutdown=true;
        Threads_Manager.getInstance().deleteDiscountThread(Thread.currentThread());
    }
    public Thread resumeThread(){
        shutdown=false;
        Thread tmp = new Thread(this);
        tmp.start();
        return tmp;    }



    @Override
    public void run() {
        show();
//        DiscountedProduct.show();
        Time_Manager time_manager = Time_Manager.getInstance();

        while (!shutdown && EndDiscountDate.isAfter(Time_Manager.getInstance().getTimeOfSimulation()) || time_manager.isIfStopSimulation()){
            try {
                Thread.sleep(time_manager.getSimulationSpeed());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
        if (!shutdown){
            cancelDiscount();
//            DiscountedProduct.show();
            shutdownThread();
        }

    }
}
