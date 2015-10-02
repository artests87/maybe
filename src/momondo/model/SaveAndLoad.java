package momondo.model;

import momondo.Aggregator;
import momondo.view.Airports;

import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Set;
import java.util.logging.Logger;

/**
 * Created by Cats on 02.10.2015.
 */
public class SaveAndLoad {
    private static Set<String> airportsFromLoad;
    private static Set<String> airportsToLoad;
    private static Set<String> airportsFromSave;
    private static Set<String> airportsToSave;
    private static Logger log = Logger.getLogger(Aggregator.class.getName());

    public static void load(String fileLoadFrom,String fileLoadTo){
        airportsToLoad=new Airports().getAirports(fileLoadTo);
        airportsFromLoad=new Airports().getAirports(fileLoadFrom);
    }

    public static Set<String> getAirportsFromLoad() {
        return airportsFromLoad;
    }

    public static Set<String> getAirportsToLoad() {
        return airportsToLoad;
    }

    public synchronized static void saveRout(String string, String fileSave){
            PrintWriter pw = null;
            try {
                boolean isRoutExist = false;
                Set<String> airportsTempLoad = new Airports().getAirports(fileSave);
                StringBuffer stringBuffer = new StringBuffer();
                for (String x : airportsTempLoad) {
                    if (x.equals(string)) {
                        isRoutExist = true;
                        break;
                    }
                    stringBuffer.append(x + System.lineSeparator());
                }
                if (!isRoutExist) {
                    pw = new PrintWriter(new OutputStreamWriter(new FileOutputStream(fileSave + ".txt"), "UTF-8"));
                    pw.write(stringBuffer + string);
                    pw.close();
                }
            } catch (Exception e) {
                log.warning("Some problem in saveRout -----" + e.getLocalizedMessage());
            }
    }
}
