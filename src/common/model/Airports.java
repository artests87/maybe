package common.model;

import java.io.*;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.TreeSet;

/**
 * Created by Cats on 20.09.2015.
 */
public class Airports {
    Set<String> airports=new LinkedHashSet<>();
    Set<String> countries=new LinkedHashSet<>();
    Set<String> towns=new LinkedHashSet<>();
    Set<String> airportsCode=new LinkedHashSet<>();
    Set<String> airportsAndCode=new TreeSet<>();
    Set<String> airportsAndCodeAndCountries=new TreeSet<>();
    static final String SEPARATOR ="---";


    public Airports readFileAirports(String fileName){
        BufferedReader bufferedReader= null;
        String txtPath=fileName+".txt";
        try {
            bufferedReader = new BufferedReader( new InputStreamReader( new FileInputStream( txtPath), "UTF-8" ) );
            //bufferedReader = new BufferedReader(new FileReader(txtPath));
            while (bufferedReader.ready()) {
                String theWholeString=bufferedReader.readLine();
                String[] strings=theWholeString.split(SEPARATOR);
                if (strings.length>1) {
                    countries.add(strings[0]);
                    airports.add(strings[1]);
                    towns.add(strings[2]);
                    airportsCode.add(strings[3]);
                    airportsAndCode.add(strings[2]+"-"+strings[3]+"-"+strings[1]);
                    airportsAndCodeAndCountries.add(theWholeString);
                }
                else {
                    airports.add(strings[0]);
                }
            }
            bufferedReader.close();
        } catch (Exception e) {

        }
        return this;
    }
    public Set<String> getAirportsArraysLimit(String[] strings){
        Set<String> stringSet=new TreeSet<>();
        for (String x:strings){
            for (String y:airportsAndCodeAndCountries) {
                String[] theWholeStringSplit=y.split(SEPARATOR);
                if (x.equals(theWholeStringSplit[0])){
                    stringSet.add(theWholeStringSplit[2]+"-"+theWholeStringSplit[3]+"-"+theWholeStringSplit[1]);
                }
            }
        }
        return stringSet;
    }

    public Set<String> getAirports() {
        return airports;
    }
    public Set<String> getAirportsCode() {
        return airportsCode;
    }
    public Set<String> getCountries() {
        return countries;
    }
    public Set<String> getAirportsAndCode() {
        return airportsAndCode;
    }
    public Set<String> getTowns() {
        return towns;
    }
}
