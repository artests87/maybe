package momondo;


import momondo.model.MMStrategy;
import momondo.model.Model;
import momondo.model.Provider;
import momondo.view.HtmlView;

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

        view.setController(controller);
        view.userCitySelectEmulationMethod();
    }
}
