package momondo.model;

import momondo.view.Airports;
import momondo.view.Dates;
import momondo.view.HtmlView;

import java.util.*;
import java.util.concurrent.*;

/**
 * Created by artests on 17.08.2015.
 */
public class ExecutorThread
{

    public void userDateSelectEmulationMethod(){
        //controller.onCitySelect("IST", "15-11-2015", "IST", "25-11-2015");
        ExecutorService service = Executors.newFixedThreadPool(5);
        LinkedHashMap<Calendar,LinkedHashSet<Calendar>> mapCalendar= Dates.getMapCalendarStatic();
        List<String> airports= Airports.getAirports();
        Collection<Future<?>> futures=new LinkedList<Future<?>>();
        for (String x:airports) {
            futures.add(service.submit(new HtmlView(mapCalendar,x)));
        }
        for (Future<?> x:futures){
            try {
                x.get();
            }
            catch (NullPointerException | InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }
        service.shutdown();
    }

}
