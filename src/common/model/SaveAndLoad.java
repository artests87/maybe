package common.model;

import common.Aggregator;

import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Set;
import java.util.logging.Logger;

/**
 * Created by Cats on 02.10.2015.
 */
public class SaveAndLoad {
    private static Set<String> airportsLoad;
    private static Set<String> airportsSave;
    private static final String separatorFromTo="---";
    private static Logger log = Logger.getLogger(Aggregator.class.getName());

    public static void load(String fileLoad){
        airportsLoad =new Airports().readFileAirports(fileLoad).getAirportsCode();
    }

    public static String getSeparatorFromTo() {
        return separatorFromTo;
    }

    public static Set<String> getAirportsLoad() {
        return airportsLoad;
    }

    /*public static Set<String> getAirportsToLoad() {
        return airportsToLoad;
    }*/

    public synchronized static void saveRout(String from, String to, String fileSave){
            PrintWriter pw = null;
            try {
                boolean isRoutExist = false;
                Set<String> airportsTempLoad = new Airports().readFileAirports(fileSave).getAirportsCode();
                StringBuffer stringBuffer = new StringBuffer();
                for (String x : airportsTempLoad) {
                    String[] tempLoad=x.split(separatorFromTo);
                    stringBuffer.append(x).append(System.lineSeparator());
                    if (tempLoad.length>1) {
                        if (tempLoad[0].equals(from) && tempLoad[1].equals(to)) {
                            isRoutExist = true;
                        }
                    }
                }
                if (!isRoutExist) {
                    pw = new PrintWriter(new OutputStreamWriter(new FileOutputStream(fileSave + ".txt"), "UTF-8"));
                    stringBuffer.append(from).append(separatorFromTo).append(to);
                    pw.write(stringBuffer.toString());
                    pw.close();
                }
            } catch (Exception e) {
                log.warning("Some problem in saveRout -----" + e.getLocalizedMessage());
                System.out.println("Some problem in saveRout -----" + e.getLocalizedMessage());
            }
    }
}
