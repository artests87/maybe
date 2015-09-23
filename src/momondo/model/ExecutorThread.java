package momondo.model;

import momondo.view.Airports;
import momondo.view.Dates;
import momondo.view.HtmlView;
import momondo.view.HtmlViewSingle;

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
    public static final int TOANDFROMTOGETHER=2;

    private int amountDates=0;
    private int amountRoutes=0;
    private int amountFinishedRoutes=0;
    private int amountFinishedDates=0;
    private int amountQuery=0;
    private final static int threads=4;
    private String fromStart;
    private static Logger log = Logger.getLogger(ExecutorThread.class.getName());

    public ExecutorThread(int method, String fromStart) {
        this.fromStart=fromStart;
        switch (method){
            case TOANDFROM:
                userDateSelectEmulationMethodToAndFrom(threads);
                break;
            case TO:
                userDateSelectEmulationMethodTo(threads);
                break;
            case TOANDFROMTOGETHER:
                userDateSelectEmulationMethodTo(threads);
                userDateSelectEmulationMethodToAndFrom(threads);
                break;
            default:
                userDateSelectEmulationMethodToAndFrom(threads);
        }
    }

    public void userDateSelectEmulationMethodToAndFrom(int threads){
        ExecutorService service = Executors.newFixedThreadPool(threads);
        Map<Calendar,LinkedHashSet<Calendar>> mapCalendar= Dates.getMapCalendarStatic();
        List<String> airports= Airports.getAirports();
        for (Map.Entry<Calendar,LinkedHashSet<Calendar>>  pair:mapCalendar.entrySet()){
            amountDates+=pair.getValue().size();
        }
        amountRoutes=airports.size();
        amountQuery=amountRoutes*amountDates;

        System.out.println("Total dates-"+amountDates);
        System.out.println("Total routes-" + amountRoutes);
        System.out.println("Total search-" + amountRoutes * amountDates);
        log.info("Total dates-" + amountDates);
        log.info("Total routes-" + amountRoutes);
        log.info("Total search-"+amountQuery);

        Collection<Future<?>> futures=new LinkedList<Future<?>>();
        for (String x:airports) {
            futures.add(service.submit(new HtmlView(mapCalendar,x,fromStart)));
        }
        for (Future<?> x:futures){
            try {
                x.get();
                amountFinishedRoutes++;
                amountFinishedDates+=amountDates;

                System.out.println("Left routes - "+(amountRoutes-amountFinishedRoutes));
                System.out.println("Left query - "+(amountQuery-amountFinishedDates));
            }
            catch (NullPointerException | InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }
        service.shutdown();
    }

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
                x.get();
                amountFinishedRoutes++;
                amountFinishedDates+=amountDates;

                System.out.println("SINGLE Left routes - "+(amountRoutes-amountFinishedRoutes));
                System.out.println("SINGLE Left query - "+(amountQuery-amountFinishedDates));
            }
            catch (NullPointerException | InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }
        service.shutdown();
    }
}
