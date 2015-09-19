package momondo.model;


import momondo.vo.Flight;

import java.util.List;

/**
 * Created by artests on 13.08.2015.
 */
public interface Strategy
{
    List<Flight> getFlights(String toStart, String dateStart, String fromEnd, String dateEnd);
}
