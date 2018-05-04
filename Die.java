/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core;

import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author dvice
 */
public class Die implements IDie
{
    private ArrayList<String> diceSides = new ArrayList<String>();
    
    @Override
    public void addLetter(String letter) {
        diceSides.add(letter);
    }

    @Override
    public void displayLetters() {
        for(Object side : diceSides)
        {
            System.out.print(side + " ");
        }
    }
    
    @Override
    public String rollDie() {
        Random randomDiceSide = new Random();
        int randomDiceResult = randomDiceSide.nextInt(NUMBER_OF_SIDES);
        return diceSides.get(randomDiceResult);
    }
}
