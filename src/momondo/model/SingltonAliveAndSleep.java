package momondo.model;

/**
 * Created by Cats on 24.09.2015.
 */
public class SingltonAliveAndSleep {
    private static SingltonAliveAndSleep ourInstance = new SingltonAliveAndSleep();
    private static Boolean isSleepAllThread=false;
    public static SingltonAliveAndSleep getInstance() {
        return ourInstance;
    }

    private SingltonAliveAndSleep() {
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

}
