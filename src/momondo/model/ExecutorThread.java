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
    private String folder;
    private int amountDates=0;
    private int amountRoutes=0;
    private int amountRoutesFrom=0;
    private int amountFinishedRoutes=0;
    private int amountFinishedDates=0;
    private int amountQuery=0;
    private int threadsCount;
    private static Logger log = Logger.getLogger(ExecutorThread.class.getName());
    private Map<Calendar,LinkedHashSet<Calendar>> mapCalendar;
    private Set<String> airports;
    private Set<String> airportsFrom;
    private int methodSearch;


    public ExecutorThread(int methodSearch,int threadsCount, String fileName, String fileNameFrom, String folder,
                          int amountMin, int amountMax,int dateMin,int theEndDate,int missingDays) {
        this.methodSearch=methodSearch;
        this.threadsCount=threadsCount;
        this.folder=folder;
        mapCalendar=new Dates(amountMin,amountMax,dateMin,theEndDate,missingDays).getMapCalendarStatic();
        airports= Airports.getAirports(fileName);
        airportsFrom= Airports.getAirports(fileNameFrom);
        if (airports.size()>0 && mapCalendar.size()>0 && airportsFrom.size()>0) {
            userDateSelectEmulationMethod();
        }
        else{
            if (airports.size()==0 || airportsFrom.size()==0){
                System.out.println("Size airports is 0");
            }

            if (mapCalendar.size()==0){
                System.out.println("Size Dates is 0");
            }
        }
    }
    public void userDateSelectEmulationMethod(){
        ExecutorService service = Executors.newFixedThreadPool(threadsCount);
        for (Map.Entry<Calendar,LinkedHashSet<Calendar>>  pair:mapCalendar.entrySet()){
            amountDates+=pair.getValue().size();
        }
        amountRoutes=airports.size();
        amountRoutesFrom=airportsFrom.size();

        amountQuery = amountRoutes * amountDates;

        System.out.println("Total dates-" + amountDates);
        System.out.println("Total routes-" + amountRoutes*amountRoutesFrom);
        System.out.println("Total search-" + amountRoutes * amountDates*amountRoutesFrom);
        log.info("Total dates-" + amountDates);
        log.info("Total routes-" + amountRoutes*amountRoutesFrom);
        log.info("Total search-" + amountQuery);

        Collection<Future<?>> futures = new LinkedList<Future<?>>();
        for (String y:airportsFrom) {
            for (String x : airports) {
                if (y.equals(x)){
                    amountFinishedRoutes++;
                    amountFinishedDates += amountDates;
                    continue;
                }
                futures.add(service.submit(new HtmlView(mapCalendar, x, y, methodSearch,folder)));
            }
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

}
