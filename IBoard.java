/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core;

import java.util.ArrayList;

/**
 *
 * @author dvice
 */
public interface IBoard {
    public final int NUMBER_OF_DICE = 16;
    public final int GRID = 4;
    
    public void populateDice();
//    public ArrayList shakeDice();
}
