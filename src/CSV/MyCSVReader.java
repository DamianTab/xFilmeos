package CSV;

import com.opencsv.CSVReader;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class CSVReaderExample {

    public static String[][] read(int howManyFields, String fileName) {

        String[][] buffer = new String [howManyFields][1000];
        String csvFile = new File("").getAbsolutePath();


        if (System.getProperty("os.name").equals("Linux")){
            System.out.println(csvFile);
            csvFile += "/src/CSV/"+fileName;
            System.out.println(csvFile);

        }else{
            csvFile = csvFile.replaceAll("\\\\","\\\\\\\\");
            csvFile += "\\\\src\\\\CSV\\\\"+fileName;
        }


        CSVReader reader = null;
        try {
            reader = new CSVReader(new FileReader(csvFile));
            String[] line;
            line = reader.readNext(); //blank line
            int lineNumber=0;
            while ((line = reader.readNext()) != null) {
                for (int i = 0; i <line.length; i++) {
                    buffer[i][lineNumber]=line[i];
                }
                lineNumber++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    return buffer;
    }

}
