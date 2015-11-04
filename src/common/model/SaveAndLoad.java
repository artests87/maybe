package common.model;

import common.Aggregator;

import java.io.*;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.logging.Logger;

/**
 * Created by Cats on 02.10.2015.
 */
public class SaveAndLoad {
    private static Set<String> airportsLoad;
    private static Set<String> airportsSave;
    private static final String SEPARATOR_FROM_TO ="---";
    private static Logger log = Logger.getLogger(Aggregator.class.getName());

    public static void load(String fileLoad){
        airportsLoad =readFileSave(fileLoad);
    }

    public static String getSeparatorFromTo() {
        return SEPARATOR_FROM_TO;
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
                Set<String> airportsTempLoad = readFileSave(fileSave);
                StringBuffer stringBuffer = new StringBuffer();
                for (String x : airportsTempLoad) {
                    String[] tempLoad=x.split(SEPARATOR_FROM_TO);
                    stringBuffer.append(x).append(System.lineSeparator());
                    if (tempLoad.length>1) {
                        if (tempLoad[0].equals(from) && tempLoad[1].equals(to)) {
                            isRoutExist = true;
                            break;
                        }
                    }
                }
                if (!isRoutExist) {
                    pw = new PrintWriter(new OutputStreamWriter(new FileOutputStream(fileSave + ".txt"), "UTF-8"));
                    stringBuffer.append(from).append(SEPARATOR_FROM_TO).append(to);
                    pw.write(stringBuffer.toString());
                    pw.close();
                }
            } catch (Exception e) {
                log.warning("Some problem in saveRout -----" + e.getLocalizedMessage());
                System.out.println("Some problem in saveRout -----" + e.getLocalizedMessage());
            }
    }
    public static Set<String> readFileSave(String fileName){
        BufferedReader bufferedReader= null;
        String txtPath=fileName+".txt";
        Set<String> stringSet=new LinkedHashSet<>();
        try {
            bufferedReader = new BufferedReader( new InputStreamReader( new FileInputStream( txtPath), "UTF-8" ) );
            //bufferedReader = new BufferedReader(new FileReader(txtPath));
            while (bufferedReader.ready()) {
                String theWholeString=bufferedReader.readLine();
                stringSet.add(theWholeString);
            }
            bufferedReader.close();
        } catch (Exception e) {

        }
        return stringSet;
    }
}
