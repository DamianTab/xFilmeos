package CSV;

import com.opencsv.CSVReader;
import java.io.FileReader;
import java.io.IOException;

public class MyCSVReader implements PathToFile{

    public static String[][] read(int howManyFields, String fileName) {

        String[][] buffer = new String [howManyFields][1000];
//        Windows
//        csvFile += "\\\\src\\\\CSV\\\\"+fileName;
        String csvFile = PathToFile.getFilePath("/src/CSV/",fileName);


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
