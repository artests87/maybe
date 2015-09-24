package momondo.model;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.concurrent.Callable;

/**
 * Created by Cats on 24.09.2015.
 */
public class SleepThread implements Runnable {
    private Boolean isAlive=true;
    BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(System.in));


    public void onIsSleepAllThread(){
        SingltonAliveAndSleep.getInstance().onIsSleepAllThread();
    }
    public void offIsSleepAllThread(){
        SingltonAliveAndSleep.getInstance().offIsSleepAllThread();
    }
    public void shutdownThread(){
        isAlive=false;
    }

    @Override
    public void run() {
        try {
            while (isAlive) {
                String readLine=bufferedReader.readLine().toLowerCase();
                if (SingltonAliveAndSleep.getInstance().isSleep()) {
                    if (readLine.equals("go")) {
                        offIsSleepAllThread();
                        System.out.println("Resume");
                    }
                }
                if (readLine.equals("p")) {
                    onIsSleepAllThread();
                    System.out.println("Pause");
                }
                if (readLine.equals("exit")){
                    shutdownThread();
                    System.out.println("Exit");
                }
            }
        }
        catch (Exception e){

        }
    }
}
