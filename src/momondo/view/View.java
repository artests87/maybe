package momondo.view;


import momondo.Controller;
import momondo.vo.Flight;

import java.util.List;

/**
 * Created by artests on 17.08.2015.
 */
public interface View
{
    void update(List<Flight> vacancies);
    void setController(Controller controller);
}
