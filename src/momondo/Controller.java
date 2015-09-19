package momondo;


import momondo.model.Model;

/**
 * Created by artests on 13.08.2015.
 */
public class Controller
{

    private Model model;

    public Controller(Model model)
    {
        if (model ==null){
            throw new IllegalArgumentException();
        }
        this.model = model;
    }

    public  void onCitySelect(String toStart, String dateStart, String fromEnd, String dateEnd){
        model.selectCityToAndDateToFrom(toStart, dateStart, fromEnd, dateEnd);

    }
}
