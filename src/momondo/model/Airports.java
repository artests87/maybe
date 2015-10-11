package momondo.model;

import java.io.*;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Cats on 20.09.2015.
 */
public class Airports {

    public Set<String> readFileAirports(String fileName){
        BufferedReader bufferedReader= null;
        Set<String> airports=new LinkedHashSet<>();
        String txtPath=fileName+".txt";
        try {
            bufferedReader = new BufferedReader(new FileReader(txtPath));
            while (bufferedReader.ready()) {
                airports.add(bufferedReader.readLine());
            }
            bufferedReader.close();
        } catch (Exception e) {

        }
        return airports;
    }

    public Set<String> getAirports(String fileName) {
        return readFileAirports(fileName);
    }
}
