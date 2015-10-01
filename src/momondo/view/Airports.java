package momondo.view;

import java.io.*;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Cats on 20.09.2015.
 */
public class Airports {
    private Set<String> airports=new LinkedHashSet<>();

    public void readFileAirports(String fileName){
        BufferedReader bufferedReader= null;
        String txtPath=fileName+".txt";
        try {
            bufferedReader = new BufferedReader(new FileReader(txtPath));
            while (bufferedReader.ready()) {
                airports.add(bufferedReader.readLine());
            }
            bufferedReader.close();
        } catch (Exception e) {

        }
    }

    public Set<String> getAirports(String fileName) {
        if (airports.size()==0){
            readFileAirports(fileName);
        }
        return airports;
    }
}
