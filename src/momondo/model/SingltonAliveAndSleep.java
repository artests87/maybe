package momondo.model;

/**
 * Created by Cats on 24.09.2015.
 */
public class SingltonAliveAndSleep {
    private static SingltonAliveAndSleep ourInstance = new SingltonAliveAndSleep();
    private static Boolean isSleepAllThread=false;
    private static Boolean isAlive=true;
    private static int amountQuery=0;
    private static int countThread=0;
    private static long summSecondWork=0;
    private static int summCountQueries=0;
    public static SingltonAliveAndSleep getInstance() {
        return ourInstance;
    }

    private SingltonAliveAndSleep() {
    }
    public void offAlive(){
        isAlive=false;
    }

    public int getAmountQuery() {
        return amountQuery;
    }

    public int getCountThread() {
        return countThread;
    }

    public void setCountThread(int countThread) {
        this.countThread = countThread;
    }

    public long getSummSecondWork() {
        return summSecondWork;
    }

    public void setSummSecondWork(long summSecondWork) {
        SingltonAliveAndSleep.summSecondWork = summSecondWork;
    }

    public void setSummCountQueries(int summCountQueries) {
        SingltonAliveAndSleep.summCountQueries = summCountQueries;
    }

    public int getSummCountQueries() {
        return summCountQueries;
    }

    public void setAmountQuery(int amountQuery) {
        SingltonAliveAndSleep.amountQuery = amountQuery;
    }
    public void decrementAmountQuery(){
        amountQuery--;
    }

    public void offIsSleepAllThread(){
        isSleepAllThread=false;
    }
    public void onIsSleepAllThread(){
        isSleepAllThread=true;
    }
    public boolean isSleep(){
        return isSleepAllThread;
    }
    public boolean isAlive(){
        return isAlive;
    }

}
