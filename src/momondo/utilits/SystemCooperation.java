package momondo.utilits;

import com.sun.management.OperatingSystemMXBean;

import java.io.File;
import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.text.NumberFormat;

/**
 * Created by Cats on 04.10.2015.
 */
public class SystemCooperation {

    public void killTask(String[] nameTask){
        for (String x:nameTask) {
            try {
                Runtime.getRuntime().exec("TaskKill /F /IM " + nameTask);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public void memo() {
        NumberFormat format = NumberFormat.getInstance();
        long maxMemory = Runtime.getRuntime().maxMemory();
        long allocatedMemory = Runtime.getRuntime().totalMemory();
        long freeMemory = Runtime.getRuntime().freeMemory();
        long memorySize = ((com.sun.management.OperatingSystemMXBean) ManagementFactory
                .getOperatingSystemMXBean()).getTotalPhysicalMemorySize();
        long memorySizeFree = ((com.sun.management.OperatingSystemMXBean) ManagementFactory
                .getOperatingSystemMXBean()).getFreePhysicalMemorySize();

        System.out.println("Free Memory: " + format.format(freeMemory / 1024));
        System.out.println("Allocated memory: " + format.format(allocatedMemory / 1024));
        System.out.println("Max Memory: " + format.format(maxMemory / 1024));
        System.out.println("Total free Memory: " + format.format((freeMemory + (maxMemory - allocatedMemory)) / 1024));
        System.out.println("Total!!! Memory: " + format.format(memorySize/1024));
        System.out.println("Total!!! free Memory: " + format.format(memorySizeFree/1024));

    }
    public void diskInfo() {
        NumberFormat format = NumberFormat.getInstance();
        /* Get a list of all filesystem roots on this system */
        File[] roots = File.listRoots();
        /* For each filesystem root, print some info */
        for (File root : roots) {
            System.out.println("File root path: " + root.getAbsolutePath());
            System.out.println("Total space (kilobytes): " + format.format(root.getTotalSpace()/1024));
            System.out.println("Free space (kilobytes): " + format.format(root.getFreeSpace()/1024));
            System.out.println("Usable space (kilobytes): " + format.format(root.getUsableSpace() / 1024));
        }
    }

    public void shutDownSystem() {
        diskInfo();
        memo();
        getAllSystemProperties();
        String property = System.getProperty("os.name");
        System.out.println(property);

        if (property.toLowerCase().contains("windows")) {
            String[] commands = { "shutdown", "-s" };
            String[] commands2 = { "notepad" };
            try {
            //Runtime.getRuntime().exec(commands);

                Runtime.getRuntime().exec(commands2);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        if (property.toLowerCase().contains("linux")) {
            String[] commands = { "shutdown", "-p", "now" };
            try {
                // Õ¿ƒŒ ¬¬≈—“» œ¿–ŒÀ‹ ﬁ«≈–¿
                Runtime.getRuntime().exec(commands);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        System.out.println("System terminated");
    }
    public void proccesor(){
        NumberFormat format = NumberFormat.getInstance();

        long processCpuTime = ((com.sun.management.OperatingSystemMXBean) ManagementFactory
                .getOperatingSystemMXBean()).getProcessCpuTime();
        double processCpuLoad = ((OperatingSystemMXBean) ManagementFactory
                .getOperatingSystemMXBean()).getProcessCpuLoad();
        double systemCpuLoad = ((OperatingSystemMXBean) ManagementFactory
                .getOperatingSystemMXBean()).getSystemCpuLoad();
        double systemLoadAverage = ((OperatingSystemMXBean) ManagementFactory
                .getOperatingSystemMXBean()).getSystemLoadAverage();
        System.out.println("processCpuTime--"+format.format(processCpuTime));
        System.out.println("processCpuLoad--"+processCpuLoad);
        System.out.println("systemCpuLoad--"+systemCpuLoad);
        System.out.println("systemLoadAverage--"+systemLoadAverage);
    }
    public void getAllSystemProperties(){
        System.out.println("file.separator--"+System.getProperty("file.separator"));
        System.out.println("java.class.path--"+System.getProperty("java.class.path"));
        System.out.println("java.home--"+System.getProperty("java.home"));
        System.out.println("java.vendor--"+System.getProperty("java.vendor"));
        System.out.println("java.vendor.url--"+System.getProperty("java.vendor.url"));
        System.out.println("java.version--"+System.getProperty("java.version"));
        System.out.print("line.separator--"); for(byte x:System.getProperty("line.separator").getBytes()){System.out.print(x+" ");};
        System.out.println("");
        System.out.println("os.arch--"+System.getProperty("os.arch"));
        System.out.println("os.name--"+System.getProperty("os.name"));
        System.out.println("os.version--"+System.getProperty("os.version"));
        System.out.print("path.separator--"); for(byte x:System.getProperty("path.separator").getBytes()){System.out.print(x+" ");};
        System.out.println("");
        System.out.println("user.dir--" + System.getProperty("user.dir"));
        System.out.println("user.home--" + System.getProperty("user.home"));
        System.out.println("user.name--" + System.getProperty("user.name"));
        System.out.println("Available processors: " + Runtime.getRuntime().availableProcessors());
    }
}
