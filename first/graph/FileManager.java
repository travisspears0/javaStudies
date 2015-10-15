package first.graph;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

public class FileManager {
    
    private final String name;
    
    public FileManager(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
    
    public String readData() {
        String result="";
        try {
            FileInputStream fileInputStream = new FileInputStream(this.name);
            BufferedReader reader = new BufferedReader(new InputStreamReader(fileInputStream));
            String line;
            while((line = reader.readLine()) != null) {
                result += line+Main.NEWLINE;
            }
        } catch(IOException e) {
            e.printStackTrace();
        }
        return result;
    }
    
    public void saveData(String data) {
        try {
            PrintWriter writer = new PrintWriter(this.name,"UTF-8");
            String[] dataToWrite = data.split(Main.NEWLINE);
            for( int i=0 ; i<dataToWrite.length ; ++i ) {
                writer.println(dataToWrite[i]);
            }
            writer.close();
        } catch(FileNotFoundException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
    
}
