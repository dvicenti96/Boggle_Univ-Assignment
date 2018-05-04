/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core;

/**
 *
 * @author dvice
 */
public interface IDie 
{
    public final int NUMBER_OF_SIDES = 6;
    
    String rollDie();
    void addLetter(String letter);
    void displayLetters();
}
