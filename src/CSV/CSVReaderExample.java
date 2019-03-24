package CSV;

import com.opencsv.CSVReader;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class CSVReaderExample {

    public static String[][] read(int howManyFields, String fileName) {

        String[][] buffer = new String [howManyFields][1000];
        String csvFile = new File("").getAbsolutePath();
        csvFile = csvFile.replaceAll("\\\\","\\\\\\\\");
        csvFile += "\\\\src\\\\CSV\\\\"+fileName;
        CSVReader reader = null;
        try {
            reader = new CSVReader(new FileReader(csvFile));
            String[] line;
            line = reader.readNext();
            int lineNumber=0;
            while ((line = reader.readNext()) != null) {
                for (int i = 0; i <line.length; i++) {
                    buffer[i][lineNumber]=line[i];
//                    System.out.println(buffer[i][lineNumber]);
                }
//                System.out.println("-----");
                lineNumber++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    return buffer;
    }

}
