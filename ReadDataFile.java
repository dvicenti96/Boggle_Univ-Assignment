/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package inputOutput;

import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.net.*;
import java.io.BufferedReader;
import java.io.FileReader;

/**
 *
 * @author dvice
 */
public class ReadDataFile implements IReadDataFile {
    
    //To read the file
    private Scanner readFile = null;
    
    //To store the file name
    private String fileName;
    
    //To store the file data
    private ArrayList<String> fileData;
   
    public ReadDataFile(String nameOfFile)
    {
        fileName = nameOfFile;
        fileData = new ArrayList();
    }

    /**
     * @return the fileData
     */
    public ArrayList<String> getFileData() {
        return fileData;
    }
    
    @Override
    public void populateData()
    {
        try
        {
            URL fileURL = getClass().getResource(fileName);
            File fileFile = new File(fileURL.toURI());
            readFile = new Scanner(new BufferedReader(new FileReader(fileFile)));
            
            while(readFile.hasNext())
            {
                fileData.add(readFile.next());
            }
        }
        catch(URISyntaxException|FileNotFoundException e)
        {
            e.printStackTrace();
        }
        finally
        {
            readFile.close();
        }
    }
}
