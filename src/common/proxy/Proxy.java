package common.proxy;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.*;

/**
 * Created by Cats on 27.02.2016.
 */
public class Proxy {
    private static Map<String,Integer> proxiesWithPorts=new HashMap<>() ;
    private static List<String> proxiesWithPortsList=new ArrayList<>() ;
    private static Random random = new Random();
    volatile private static int repeat=0;
    volatile private static int count=0;
    volatile private static int maxRows=0;
    public static void readFileProxy(){
        BufferedReader bufferedReader= null;
        String txtPath=System.getProperty("user.dir")+"\\src\\common\\proxy\\proxy.txt";
        try {
            bufferedReader = new BufferedReader( new InputStreamReader( new FileInputStream( txtPath), "UTF-8" ) );
            //bufferedReader = new BufferedReader(new FileReader(txtPath));
            while (bufferedReader.ready()) {
                String theWholeString=bufferedReader.readLine();
                proxiesWithPorts.put(theWholeString,0);
                proxiesWithPortsList.add(theWholeString);
            }
            maxRows=proxiesWithPorts.size()-1;
            bufferedReader.close();
        } catch (Exception e) {
            System.out.println("theWholeString exception"+e.getLocalizedMessage());
        }
    }
    public static synchronized String getRandomProxyAndPort(){
        if (maxRows==0){
            readFileProxy();
        }
        String tempProxy="";
        int tempValue=0;
        while (true) {
            tempProxy = proxiesWithPortsList.get(random.nextInt(maxRows));
            tempValue=proxiesWithPorts.get(tempProxy);
            if (tempValue==0){
                proxiesWithPorts.put(tempProxy,1);
                repeat=1;
                count++;
                break;
            }
            if (tempValue==repeat && count==maxRows){
                repeat++;
                count=0;
                proxiesWithPorts.put(tempProxy,repeat);
                break;
            }
            if (tempValue<repeat && count<maxRows){
                break;
            }
        }
        return tempProxy;
    }
}
