package momondo.model;

import momondo.view.Airports;
import momondo.view.Dates;
import momondo.view.HtmlView;

import java.util.*;
import java.util.concurrent.*;
import java.util.logging.Logger;

/**
 * Created by artests on 17.08.2015.
 */
public class ExecutorThread
{
    //CONSTANTS FOR METHOD SEARCH
    public static final int TOANDFROM=0;
    public static final int TO=1;

    //Boolean for sleep
    private int amountDates=0;
    private int amountRoutes=0;
    private int amountFinishedRoutes=0;
    private int amountFinishedDates=0;
    private int amountQuery=0;
    private int threadsCount;
    private String fromStart;
    private static Logger log = Logger.getLogger(ExecutorThread.class.getName());
    private Map<Calendar,LinkedHashSet<Calendar>> mapCalendar;
    private List<String> airports;
    private int methodSearch;


    public ExecutorThread(String fromStart,int methodSearch,int threadsCount) {
        this.fromStart=fromStart;
        this.methodSearch=methodSearch;
        this.threadsCount=threadsCount;
        mapCalendar= Dates.getMapCalendarStatic();
        airports= Airports.getAirports();
        if (airports.size()>0 && mapCalendar.size()>0) {
            userDateSelectEmulationMethod(threadsCount);
        }
        else{
            if (airports.size()==0){
                System.out.println("Size airports is 0");
            }

            if (mapCalendar.size()==0){
                System.out.println("Size Dates is 0");
            }
        }
    }

    public void userDateSelectEmulationMethod(int threads){
        ExecutorService service = Executors.newFixedThreadPool(threads);
        for (Map.Entry<Calendar,LinkedHashSet<Calendar>>  pair:mapCalendar.entrySet()){
            amountDates+=pair.getValue().size();
        }
        amountRoutes=airports.size();

        amountQuery = amountRoutes * amountDates;

        System.out.println("Total dates-" + amountDates);
        System.out.println("Total routes-" + amountRoutes);
        System.out.println("Total search-" + amountRoutes * amountDates);
        log.info("Total dates-" + amountDates);
        log.info("Total routes-" + amountRoutes);
        log.info("Total search-" + amountQuery);

        Collection<Future<?>> futures = new LinkedList<Future<?>>();
        for (String x : airports) {
            futures.add(service.submit(new HtmlView(mapCalendar, x, fromStart,methodSearch)));
        }
        for (Future<?> x : futures) {
            try {
                synchronized (Thread.currentThread()){
                    while (SingltonAliveAndSleep.getInstance().isSleep()) {
                        Thread.currentThread().wait(1000);
                    }
                }
                x.get();
                amountFinishedRoutes++;
                amountFinishedDates += amountDates;
                System.out.println("Left routes - " + (amountRoutes - amountFinishedRoutes));
                System.out.println("Left query - " + (amountQuery - amountFinishedDates));
                if (!SingltonAliveAndSleep.getInstance().isAlive()){
                    service.shutdownNow();
                    System.out.println("Exit begin...");
                    service.awaitTermination(10,TimeUnit.SECONDS);
                    break;
                }
            } catch (Exception e) {
                log.warning("Something wrong with the Main Thread---"+e.getLocalizedMessage());
            }
        }
        System.out.println("Exit end!");
    }
/*
    public void userDateSelectEmulationMethodTo(int threads){
        ExecutorService service = Executors.newFixedThreadPool(threads);
        Map<Calendar,LinkedHashSet<Calendar>> mapCalendar= Dates.getMapCalendarStatic();
        List<String> airports= Airports.getAirports();
        for (Map.Entry<Calendar,LinkedHashSet<Calendar>>  pair:mapCalendar.entrySet()){
            amountDates+=pair.getValue().size();
        }
        amountRoutes=airports.size();
        amountQuery=amountRoutes*amountDates;

        System.out.println("SINGLE Total dates-"+amountDates);
        System.out.println("SINGLE Total routes-" + amountRoutes);
        System.out.println("SINGLE Total search-" + amountRoutes * amountDates);
        log.info("SINGLE Total dates-" + amountDates);
        log.info("SINGLE Total routes-" + amountRoutes);
        log.info("SINGLE Total search-"+amountQuery);

        Collection<Future<?>> futures=new LinkedList<Future<?>>();
        for (String x:airports) {
            futures.add(service.submit(new HtmlViewSingle(mapCalendar,x,fromStart)));
        }
        for (Future<?> x:futures){
            try {
                synchronized (Thread.currentThread()){
                    while (SingltonAliveAndSleep.getInstance().isSleep()) {
                        Thread.currentThread().wait(1000);
                    }
                }
                x.get();
                amountFinishedRoutes++;
                amountFinishedDates+=amountDates;

                System.out.println("SINGLE Left routes - "+(amountRoutes-amountFinishedRoutes));
                System.out.println("SINGLE Left query - "+(amountQuery-amountFinishedDates));
                if (!SingltonAliveAndSleep.getInstance().isAlive()){
                    //service.shutdown();
                    service.shutdownNow();
                    System.out.println("Exit begin...");
                    service.awaitTermination(10,TimeUnit.SECONDS);
                    break;
                }
            }
            catch (NullPointerException | InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }
        service.shutdown();
    }*/
}
