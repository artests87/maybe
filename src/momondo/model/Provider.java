package momondo.model;



import momondo.vo.Flight;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by artests on 13.08.2015.
 */
public class Provider
{
    private Strategy strategy;

    public Provider(Strategy strategy)
    {
        this.strategy = strategy;
    }

    public void setStrategy(Strategy strategy)
    {
        this.strategy = strategy;
    }
    public List<Flight> getFlights(String toStart, String dateStart, String fromEnd, String dateEnd){
        if(strategy==null) {
            return new ArrayList<>();
        }
        return strategy.getFlights(toStart, dateStart, fromEnd, dateEnd);
    };
}
