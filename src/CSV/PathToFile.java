package CSV;

import java.io.File;

public interface PathToFile {

    public static String getFilePath(String subfolder, String fileName){

        String myPath = new File("").getAbsolutePath();

        if (System.getProperty("os.name").equals("Linux")){
            System.out.println(myPath);
            myPath += subfolder+fileName;
            System.out.println(myPath);

        }else{

            myPath = myPath.replaceAll("\\\\","\\\\\\\\");
            subfolder = subfolder.replaceAll("/","\\\\");
            myPath += subfolder+fileName;
        }
        return myPath;
    }
}
