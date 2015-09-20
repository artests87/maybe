package momondo.view;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Cats on 20.09.2015.
 */
public class Airports {
    static private List<String> airports=new ArrayList<>();

    public static void readFileAirports(String txt){
        BufferedReader bufferedReader= null;
        String txtPath=System.getProperty("user.dir")+"\\res\\airports.txt";
        if (txt!=null){
            txtPath=txt;
        }
        try {
            bufferedReader = new BufferedReader(new FileReader(txtPath));
            while (bufferedReader.ready()) {
                airports.add(bufferedReader.readLine());
            }
            bufferedReader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<String> getAirports() {
        if (airports.size()==0){
            readFileAirports(null);
        }
        return airports;
    }
}
