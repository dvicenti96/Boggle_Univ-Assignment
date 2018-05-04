/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package boggle;

import inputOutput.ReadDataFile;
import core.Board;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import userInterface.BoggleUI;

/**
 *
 * @author dvice
 * Daniel Vicenti
 */
public class Boggle {
    
    static ArrayList<String> aBoggleData = new ArrayList();
    
    String boggleData;
    
    static ArrayList<String> aDictionary = new ArrayList();
    
    String dictionary;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        
        //Greet the Player
        System.out.println("Hello, Welcome to Boggle!");
        
        //Pop-up with a message asking the play
        JOptionPane.showMessageDialog(null, "Let's play Boggle!");
        
        ReadDataFile readBoggleData = new ReadDataFile("../data/BoggleData.txt");
        readBoggleData.populateData();
        
        ReadDataFile readDictionary = new ReadDataFile("../data/Dictionary.txt");
        readDictionary.populateData();
        aDictionary = readDictionary.getFileData();
        
        Board boggleBoard = new Board(readBoggleData.getFileData(), readDictionary.getFileData());
        boggleBoard.populateDice();
        aBoggleData = boggleBoard.shakeDice();
        
        System.out.println("There are " + aDictionary.size() + " entries in the Dictionary.");
        System.out.println();
        boggleBoard.displayRolledDice();
        
        BoggleUI boggleUserInterface = new BoggleUI(boggleBoard);
    }
    
}
