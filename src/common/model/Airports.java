package common.model;

import java.io.*;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Created by Cats on 20.09.2015.
 */
public class Airports {
    Set<String> airports=new LinkedHashSet<>();
    Set<String> countries=new LinkedHashSet<>();
    Set<String> towns=new LinkedHashSet<>();
    Set<String> airportsCode=new LinkedHashSet<>();
    Set<String> airportsAndCode=new LinkedHashSet<>();

    public void readFileAirports(String fileName){
        BufferedReader bufferedReader= null;
        String txtPath=fileName+".txt";
        try {
            bufferedReader = new BufferedReader( new InputStreamReader( new FileInputStream( txtPath), "UTF-8" ) );
            //bufferedReader = new BufferedReader(new FileReader(txtPath));
            while (bufferedReader.ready()) {
                String[] strings=bufferedReader.readLine().split("---");
                if (strings.length>1) {
                    countries.add(strings[0]);
                    airports.add(strings[1]);
                    towns.add(strings[2]);
                    airportsCode.add(strings[3]);
                    airportsAndCode.add(strings[3]+"-"+strings[2]+"-"+strings[1]);
                }
                else {
                    airports.add(strings[0]);
                }
            }
            bufferedReader.close();
        } catch (Exception e) {

        }
    }

    public Set<String> getAirports(String fileName) {
        if (airports.size()==0) {
            readFileAirports(fileName);
        }
        return airports;
    }
    public Set<String> getAirportsCode(String fileName) {
        if (airportsCode.size()==0) {
            readFileAirports(fileName);
        }
        return airportsCode;
    }
    public Set<String> getCountries(String fileName) {
        if (countries.size()==0) {
            readFileAirports(fileName);
        }
        return countries;
    }
    public Set<String> getAirportsAndCode(String fileName) {
        if (airportsAndCode.size()==0) {
            readFileAirports(fileName);
        }
        return airportsAndCode;
    }
    public Set<String> getTowns(String fileName) {
        if (towns.size()==0) {
            readFileAirports(fileName);
        }
        return towns;
    }
}
