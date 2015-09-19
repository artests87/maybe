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
        presentDate.add(Calendar.DATE,30);
        System.out.println(presentDate.get(Calendar.MONTH));

        view.setController(controller);
        view.userDateSelectEmulationMethod();

        //System.out.println("kalsdasdasdasdasdsadas");

    }
}
