package Main_package;

import Entities.Entities_Manager;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Time_Manager implements Runnable, Serializable {


    private static Time_Manager INSTANCE;
    public static synchronized Time_Manager getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new Time_Manager();
        }
        return INSTANCE;
    }

    public static void setINSTANCE(Time_Manager time_Manager){
        INSTANCE = time_Manager;
    }
//    public static Time_Manager getInstance(){
//        return Time_Manager.SingletonHolder.INSTANCE;
//    }
//    private static class SingletonHolder {
//        private static final Time_Manager INSTANCE = new Time_Manager();
//    }
    private Time_Manager() {
    }


    private volatile LocalDate TIME_OF_SIMULATION = LocalDate.now();
    private volatile boolean IfStopSimulation=true;
    private int SimulationSpeed =1200;
    private boolean shutdown = false;
    private volatile float FinancialConditionOfService=4000f;
    private int HowManyMonthsInRowToBeInTheRed = 0;
    private boolean THE_END = false;

    public boolean isShutdown() {
        return shutdown;
    }
    public boolean isTHE_END() {
        return THE_END;
    }
    public void setTHE_END(boolean THE_END) {
        this.THE_END = THE_END;
    }
    public float getFinancialConditionOfService() {
        return FinancialConditionOfService;
    }
    public void setFinancialConditionOfService(float financialConditionOfService) {
        FinancialConditionOfService = financialConditionOfService;
    }
    public synchronized void AdditionToFinancialConditionOfService(float amount){
        FinancialConditionOfService+=amount;
    }
    public void showFinancialStatus(){
        System.out.println("===================");
        System.out.println("Stan konta wynosi: " + FinancialConditionOfService);
        System.out.println("===================");
    }
    public int getHowManyMonthsInRowToBeInTheRed() {
        return HowManyMonthsInRowToBeInTheRed;
    }
    public void setHowManyMonthsInRowToBeInTheRed(int howManyMonthsInRowToBeInTheRed) {
        HowManyMonthsInRowToBeInTheRed = howManyMonthsInRowToBeInTheRed;
    }


    public void shutdownThread(){
        shutdown=true;
        IfStopSimulation=true;
    }
    public void resumeThread(){
        shutdown=false;
        IfStopSimulation=false;
    }
    public LocalDate getTimeOfSimulation() {
        return TIME_OF_SIMULATION;
    }
    public int getSimulationSpeed() {
        return SimulationSpeed;
    }
    public void slowDownSimulation(){
        if (SimulationSpeed >= 4000)return;
        else {
            SimulationSpeed+=500;
        }
    }
    public void speedUpSimulation(){ // UWAGA nie mozna robic speedup a ptem sleep bo bedzie Error
        if (SimulationSpeed <= 500)return;
        else {
            SimulationSpeed-=500;
//            interruptSleepSimulationTime();
//            Threads_Manager.getInstance().interruptAllThreads();
        }
    }
    public void startOrStopRunTime(){
        if (!IfStopSimulation) IfStopSimulation=true;
        else IfStopSimulation=false;
    }
    public void setIfStopSimulation(boolean ifStopSimulation) {
        IfStopSimulation = ifStopSimulation;
    }
    public boolean isIfStopSimulation() {
        return IfStopSimulation;
    }

    private void interruptSleepSimulationTime(){
        Thread.currentThread().interrupt();
    }


    @Override
    public void run() {
        while (!shutdown){
                TIME_OF_SIMULATION=TIME_OF_SIMULATION.plus(1, ChronoUnit.DAYS);
                System.out.println(TIME_OF_SIMULATION);
                if (TIME_OF_SIMULATION.getDayOfMonth()==1) (new Thread(new MontlyPayoutForDistributorsAndClients())).start();
                do {
                    try {
                        Thread.sleep(SimulationSpeed);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }while (isIfStopSimulation());
        }
    }

    public class MontlyPayoutForDistributorsAndClients implements Runnable{
        public MontlyPayoutForDistributorsAndClients() { }

        @Override
        public void run() {
            showFinancialStatus();
            Entities_Manager.getInstance().payForSubscriptionByAllClients();
            showFinancialStatus();
            Entities_Manager.getInstance().payMonthlyPaymentToAllDistributors();
            showFinancialStatus();

            if (getFinancialConditionOfService()<0)
                setHowManyMonthsInRowToBeInTheRed(getHowManyMonthsInRowToBeInTheRed()+1);
            else setHowManyMonthsInRowToBeInTheRed(0);

            if (getHowManyMonthsInRowToBeInTheRed()==3){
                Time_Manager.getInstance().setTHE_END(true);
                Threads_Manager.getInstance().shutdownAllThreads();
            }
        }
    }

}
