package momondo;


import momondo.model.MMStrategy;
import momondo.model.Model;
import momondo.model.Provider;
import momondo.view.HtmlView;

import java.util.Calendar;
import java.util.Locale;

/**
 * Created by artests on 12.08.2015.
 */
public class Aggregator
{
    public static void main(String[] args){

        Provider provider=new Provider(new MMStrategy());
        Provider[] providers={provider};
        HtmlView view=new HtmlView();
        Model model=new Model(view,providers);
        Controller controller=new Controller(model);
        Calendar presentDate=Calendar.getInstance();

        System.out.println(presentDate.get(presentDate.YEAR));
        Calendar innerCalendar= (Calendar) presentDate.clone();
        presentDate.add(Calendar.DATE, 2);
        System.out.println(presentDate.getTime());
        System.out.println(innerCalendar.getTime());

        view.setController(controller);
        //view.userCitySelectEmulationMethod();

        //System.out.println("kalsdasdasdasdasdsadas");

    }
}
