/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core;

import static core.IDie.NUMBER_OF_SIDES;
import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author dvice
 */
public class Board implements IBoard {
    
    private ArrayList<String> diceData;
    private ArrayList<String> dictionaryData;
    private ArrayList<String> gameData;
    private ArrayList<Die> gameDice;
    
    public Board(ArrayList<String> boggleDataNew, ArrayList<String> dictionaryDataNew)
    {
        diceData = boggleDataNew;
        dictionaryData = dictionaryDataNew;
        gameDice = new ArrayList<Die>();
    }

    @Override
    public void populateDice()
    {
        Die dice;
        int counter = 0;
        for (int i = 0; i < NUMBER_OF_DICE; i++)
        {
            dice = new Die();
            for (int j = 0; j < NUMBER_OF_SIDES; j++)
            {
                dice.addLetter(diceData.get(counter));
                counter++;
            }
            gameDice.add(dice);
            System.out.print("Die " + i + ": ");
            dice.displayLetters();
            System.out.println();
        }
    }

    public ArrayList shakeDice() {
        Random randomDice = new Random();
        //For debugging:
//        int diceSeed = 1;
//        randomDice.setSeed(diceSeed);
        gameData = new ArrayList<String>();
        ArrayList tracker = new ArrayList();
        for (int x = 0; x < NUMBER_OF_DICE; x++)
        {
            int diceNumber = randomDice.nextInt(NUMBER_OF_DICE);
            if (tracker.isEmpty())
            {
                tracker.add(diceNumber);
            }
            else
            {
                while (tracker.contains(diceNumber))
                {
                    diceNumber = randomDice.nextInt(16);
                }
            }
            gameData.add(gameDice.get(diceNumber).rollDie());
            tracker.add(diceNumber);
        }
        return gameData;
    }
    
    public void displayRolledDice()
    {
        System.out.println("Boggle Board");
        int rowCounter = 0;
        for (Object displayData: gameData)
        {
            if (rowCounter == GRID)
            {
                System.out.println();
                rowCounter = 0;
            }
            System.out.print(displayData + " ");
            rowCounter++;
        }
        System.out.println();
    }
    
    /**
     * @return the gameData
     */
    public ArrayList<String> getGameData() {
        return gameData;
    }

    /**
     * @return the dictionaryData
     */
    public ArrayList<String> getDictionaryData() {
        return dictionaryData;
    }
}
