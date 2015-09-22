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
    private int amountDates=0;
    private int amountRoutes=0;
    private int amountFinishedRoutes=0;
    private int amountFinishedDates=0;
    private int amountQuery=0;
    private static Logger log = Logger.getLogger(ExecutorThread.class.getName());

    public void userDateSelectEmulationMethod(){
        //controller.onCitySelect("IST", "15-11-2015", "IST", "25-11-2015");
        ExecutorService service = Executors.newFixedThreadPool(5);
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
            futures.add(service.submit(new HtmlView(mapCalendar,x)));
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

}
