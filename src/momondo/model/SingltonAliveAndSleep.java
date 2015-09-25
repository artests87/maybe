package momondo.model;

/**
 * Created by Cats on 24.09.2015.
 */
public class SingltonAliveAndSleep {
    private static SingltonAliveAndSleep ourInstance = new SingltonAliveAndSleep();
    private static Boolean isSleepAllThread=false;
    private static Boolean isAlive=true;
    public static SingltonAliveAndSleep getInstance() {
        return ourInstance;
    }

    private SingltonAliveAndSleep() {
    }
    public void offAlive(){
        isAlive=false;
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
