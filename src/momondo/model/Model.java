package momondo.model;


import momondo.view.View;
import momondo.vo.Flight;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by artests on 17.08.2015.
 */
public class Model
{
    private View view;
    private Provider[] providers;

    public Model(View view, Provider... providers)
    {
        if (view==null || providers==null || providers.length==0)
        {
            throw new IllegalArgumentException();
        }

        this.view = view;
        this.providers = providers;

    }

    public void selectCityToAndDateToFrom(String toStart, String dateStart, String fromEnd, String dateEnd){
        List<Flight> flights = new ArrayList<>();

        for (Provider provider : providers)
            flights.addAll(provider.getFlights(toStart, dateStart, fromEnd, dateEnd));

        view.update(flights);
    }
}
