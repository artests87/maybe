package momondo.model;


import momondo.vo.Flight;

import java.util.List;
import java.util.Set;

/**
 * Created by artests on 13.08.2015.
 */
public interface Strategy
{
    Set<Flight> getFlights(String toStart, String dateStart, String fromEnd, String dateEnd);
}
