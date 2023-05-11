import java.util.*;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.io.*;

public class ConfigInit {

    static Properties config = new Properties();

    private static void config_reader(String path) throws IOException {
        if (path != null) {
            FileInputStream fis = new FileInputStream(path);
            config.load(fis);
            fis.close();
        }
    }

    private static double[] generateCoordinates(int n) {
        double[] coordinates = new double[n];
        Set<Double> xyCoordinates = new HashSet<Double>();
        Random rand = new Random();

        do {
            double point = rand.nextDouble()*10000;
            xyCoordinates.add(point);
        }
        while (xyCoordinates.size() < n);
        List<Double> pointList = new ArrayList<Double>();
        pointList.addAll(xyCoordinates);

        for (int i = 0; i < n; i++) {
            coordinates[i] = pointList.get(i);
        }
        return coordinates;
    }

    public static void main(String[] args) throws IOException {

        try {
            config_reader(".config");
            String[] nbrSensors = config.getProperty("n").split(" ");
            String Tp =  config.getProperty("Tp");
            String ts = config.getProperty("ts");
            String r = config.getProperty("r");

            for (String n : nbrSensors) {
                double[] xCoordinates = generateCoordinates(Integer.parseInt(n));
                double[] yCoordinates = generateCoordinates(Integer.parseInt(n));

                PrintWriter writer = new PrintWriter("config_" + n);
                writer.println("n = " + n);
                writer.println("Tp = " + Tp);
                writer.println("ts = " + ts);
                writer.println("r = " + r);
                for (int i = 0; i < Integer.parseInt(n); i++) {
                    writer.println("point_" + (i+1) + " = [" + xCoordinates[i] + ", " + yCoordinates[i] + "]");
                }
                writer.close();
            }
        } catch (IOException ex) {
            // I/O error
        }

    }


}
