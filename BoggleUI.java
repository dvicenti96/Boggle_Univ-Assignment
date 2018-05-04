/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package userInterface;

import core.Board;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import javax.swing.text.BadLocationException;
import javax.swing.text.MutableAttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;

/**
 *
 * @author dvice
 */
public class BoggleUI {
    //The Frame
    private JFrame boggleFrame;
    
    //The Menu
    private JMenuBar boggleMenuBar;
    private JMenu boggleMenu;
    private JMenuItem boggleMenuNewGame;
    private JMenuItem boggleMenuExit;
    
    //The Current Word Panel
    private JPanel boggleCurrentWord;
    private JLabel boggleCurrentWordCreate;
    private JButton boggleSubmitCurrentWord;
    private JLabel bogglePlayerScore;
    
    //The Boggle Board
    private JPanel boggleBoard;
    private JButton[][] boggleDiceButton;
    static final int DICE_GRID_SIDE = 4;
    
    //Enter The Words Found
    private JPanel boggleEnterWordsFound;
    private JTextPane boggleWordsFound;
    private JScrollPane scrollPaneForWordsFound;
    private JLabel boggleDisplayTimeLeft;
    private JButton boggleShakeDice;
    
    private Timer boggleTimer;
    private static int minutes = 1;
    private static int seconds = 0;
    
    private static final int SCORE_RESET = 0;
    private static final int IF_COMPARE_IS_ZERO = 0;
    
    ArrayList<String> PlayerWords = new ArrayList<String>();
    
    Board currentBoggleBoard;
    
    public BoggleUI(Board boardParameter)
    {
        currentBoggleBoard = boardParameter;
        initComponents();
        ResetGameBoard Reset = new ResetGameBoard();
    }
    
    public void initComponents()
    {
        //Set up the Border Layout layout variable
        BorderLayout boggleBorderLayout = new BorderLayout();
        FlowLayout boggleFlowLayout = new FlowLayout();
        
        //Set up the frame
        boggleFrame = new JFrame("Boggle");
        boggleFrame.setSize(new Dimension(620,550));
        boggleFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        boggleFrame.setLayout(boggleBorderLayout);
        
        //Set up the menu bar, the menu, and the menu items
        boggleMenuBar = new JMenuBar();
        boggleMenu = new JMenu("Options");
        boggleMenu.setMnemonic('O');
        boggleMenuNewGame = new JMenuItem("New Game");
        boggleMenuNewGame.addActionListener(new ResetGameBoard());
        boggleMenuExit = new JMenuItem("Exit");
        boggleMenuExit.addActionListener(new ExitGameListener());
        
        //Add the Menu Items to the Menu and the Menu to the Menu Bar
        boggleMenu.add(boggleMenuNewGame);
        boggleMenu.add(boggleMenuExit);
        boggleMenuBar.add(boggleMenu);
        
        //Now to set up the Current Word area
        //Set up JPanel for Current Word area
        boggleCurrentWord = new JPanel();
        boggleCurrentWord.setBorder(BorderFactory.createTitledBorder("Current Word"));
        boggleCurrentWord.setLayout(boggleFlowLayout);
        
        //Set up JLabel for Current Word being submitted.
        boggleCurrentWordCreate = new JLabel();
        boggleCurrentWordCreate.setBorder(BorderFactory.createTitledBorder("The Current Created Word:"));
        boggleCurrentWordCreate.setMinimumSize(new Dimension(300,50));
        boggleCurrentWordCreate.setPreferredSize(new Dimension(300,50));
        boggleCurrentWord.add(boggleCurrentWordCreate);
        
        //Set up JButton for submitting the word created.
        boggleSubmitCurrentWord = new JButton("Submit Word");
        boggleSubmitCurrentWord.setMinimumSize(new Dimension(150,40));
        boggleSubmitCurrentWord.setPreferredSize(new Dimension(150,40));
        boggleSubmitCurrentWord.addActionListener(new SubmitWordListener());
        boggleCurrentWord.add(boggleSubmitCurrentWord);
        
        //Set up JLabel for the player's current score.
        bogglePlayerScore = new JLabel();
        bogglePlayerScore.setBorder(BorderFactory.createTitledBorder("Score:"));
        bogglePlayerScore.setMinimumSize(new Dimension(100,50));
        bogglePlayerScore.setPreferredSize(new Dimension(100,50));
        bogglePlayerScore.setText(String.valueOf(SCORE_RESET));
        boggleCurrentWord.add(bogglePlayerScore);
        
        //Now to set up the Board.
        //Set up the Board JPanel
        boggleBoard = new JPanel();
        boggleBoard.setBorder(BorderFactory.createTitledBorder("Board"));
        boggleBoard.setPreferredSize(new Dimension(400,400));
        boggleBoard.setMinimumSize(new Dimension(400,400));
        boggleBoard.setLayout(new GridLayout(DICE_GRID_SIDE,DICE_GRID_SIDE));
        
        //Set up the JButton grid
        boggleDiceButton = new JButton[4][4];
        int tracker = 0;
        for(int x = 0; x < DICE_GRID_SIDE; x++)
        {
            for (int y = 0; y < DICE_GRID_SIDE; y++)
            {
                boggleDiceButton[x][y] = new JButton();
                boggleDiceButton[x][y].setText(currentBoggleBoard.getGameData().get(tracker));
                boggleDiceButton[x][y].addActionListener(new DiceListener());
                tracker++;
                boggleBoard.add(boggleDiceButton[x][y]);
            }
        }
        
        //Set up the words found panel
        boggleEnterWordsFound = new JPanel();
        boggleEnterWordsFound.setBorder(BorderFactory.createTitledBorder("Words Found"));
        boggleEnterWordsFound.setLayout(new BoxLayout(boggleEnterWordsFound, BoxLayout.Y_AXIS));
        
          //Set up a JLabel to display time.
        boggleDisplayTimeLeft = new JLabel();
        boggleDisplayTimeLeft.setBorder(BorderFactory.createTitledBorder("Time Left:"));
        boggleDisplayTimeLeft.setMaximumSize(new Dimension(400,100));
        boggleDisplayTimeLeft.setText("3:00");
        boggleDisplayTimeLeft.setFont(new Font(Font.SERIF, Font.PLAIN,40));
        boggleDisplayTimeLeft.setHorizontalAlignment(SwingConstants.CENTER);
        boggleEnterWordsFound.add(boggleDisplayTimeLeft);
        final int ONE_SEC_MILISEC = 1000;
        boggleTimer = new Timer(ONE_SEC_MILISEC, new BoggleTimerListener());
        boggleTimer.start();
        
        //Set up the shake dice button
        boggleShakeDice = new JButton("Shake the Dice");
        boggleShakeDice.setMaximumSize(new Dimension(400,50));
        boggleShakeDice.addActionListener(new ResetGameBoard());
        boggleEnterWordsFound.add(boggleShakeDice);
        
        //Set up the JTextPane and make sure it has a scroll pane.
        boggleWordsFound = new JTextPane();
        boggleWordsFound.setToolTipText("Put words here.");
        scrollPaneForWordsFound = new JScrollPane(boggleWordsFound);
        scrollPaneForWordsFound.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPaneForWordsFound.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPaneForWordsFound.setMaximumSize(new Dimension(400,300));
        boggleEnterWordsFound.add(scrollPaneForWordsFound);
        
        //Now we set up the panels in the frame, and set the frame to visible.
        boggleFrame.setJMenuBar(boggleMenuBar);
        boggleFrame.add(boggleCurrentWord, BorderLayout.SOUTH);
        boggleFrame.add(boggleBoard, BorderLayout.EAST);
        boggleFrame.add(boggleEnterWordsFound, BorderLayout.CENTER);
        boggleFrame.setVisible(true);
    }
    
    public class ResetGameBoard implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent ae) {
            //Get new dice and set the buttons
            currentBoggleBoard.shakeDice();
            int tracker = 0;
            for(int x = 0; x < DICE_GRID_SIDE; x++)
            {
                for (int y = 0; y < DICE_GRID_SIDE; y++)
                {
                    boggleDiceButton[x][y].setText(currentBoggleBoard.getGameData().get(tracker));
                    boggleDiceButton[x][y].setEnabled(true);
                    tracker++;
                }
            }
            
            //Reset the JTextPane
            //boggleWordsFound.setText(null);
            try
            {
                boggleWordsFound.getDocument().remove(0, boggleWordsFound.getDocument().getLength());
            }
            catch(BadLocationException ble)
            {
                
            }
            
            //Reset Score
            bogglePlayerScore.setText(String.valueOf(SCORE_RESET));
            
            //Reset Current Word label
            boggleCurrentWordCreate.setText("");
            
            //Reset Time
            boggleDisplayTimeLeft.setText("3:00");
            
            //Revalidate and repaint the frame
            boggleFrame.revalidate();
            boggleFrame.repaint();
            
            //reset timer
            boggleTimer.stop();
            minutes = 3;
            seconds = 0;
            boggleTimer.start();
        }
        
    }
    
    public class BoggleTimerListener implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent ae) {
            //stop the timer if time is up
            if (minutes == 0 && seconds == 0)
            {
                boggleTimer.stop();
                
                //Randomly determine how many words computer found as well.
//                if(boggleWordsFound.getText().isEmpty())
//                {
//                    bogglePlayerScore.setText(String.valueOf(SCORE_RESET));
//                }
                if(boggleWordsFound.getDocument().getLength() == 0)
                {
                    bogglePlayerScore.setText(String.valueOf(SCORE_RESET));
                }
                else
                {
                    Random randomWords = new Random();
                    ArrayList<String> ComputerWords = new ArrayList<String>();
                    
                    int howManyWords = randomWords.nextInt(PlayerWords.size());
                    int whatWord;
                    for(int x = 0; x < howManyWords; x++)
                    {
                        whatWord = randomWords.nextInt(PlayerWords.size());
                        ComputerWords.add(PlayerWords.get(whatWord));
                    }
                    //Now we strikethrough the words that the computer also got.
                    try
                    {
                        StrikethroughWords(ComputerWords);
                    }
                    catch(BadLocationException ble)
                    {
                        JOptionPane.showMessageDialog(null, "exception thrown, resetting");
                        ResetGameBoard reset = new ResetGameBoard();
                        reset.actionPerformed(ae);
                    }
                    //Update Final game score
                    int scoreFinal = PlayerWords.size() - ComputerWords.size();
                    bogglePlayerScore.setText(String.valueOf(scoreFinal));
                }
                //Now show score
                JOptionPane.showMessageDialog(null, "Final Game Score: " + bogglePlayerScore.getText());
                //Now Reset the Game Board
                ResetGameBoard reset = new ResetGameBoard();
                reset.actionPerformed(ae);
            }
            else
            {
                if(seconds == 0)
                {
                    seconds = 59;
                    minutes--;
                } else
                {
                    seconds--;
                }
            }
            
            if(seconds < 10)
            {
                String secondsString = "0" + String.valueOf(seconds);
                boggleDisplayTimeLeft.setText(String.valueOf(minutes) + ":" + secondsString);
            } else
            {
                boggleDisplayTimeLeft.setText(String.valueOf(minutes) + ":" + String.valueOf(seconds));
            }
            
            
        }
        
        public void StrikethroughWords(ArrayList<String> compWords) throws BadLocationException
        {
            //Clear the text for the boggleWordsFound
            boggleWordsFound.getDocument().remove(0, boggleWordsFound.getDocument().getLength());
            MutableAttributeSet strike = new SimpleAttributeSet();
            StyleConstants.setStrikeThrough(strike, true);
            for(String playerCompare : PlayerWords)
            {
                boolean didStrike = false;
                for(String compCompare : compWords)
                {
                    if(compCompare.compareToIgnoreCase(playerCompare) == IF_COMPARE_IS_ZERO)
                    {
                        //newWord.addAttribute(TextAttribute.STRIKETHROUGH,TextAttribute.STRIKETHROUGH_ON);
                        boggleWordsFound.getDocument().insertString(0, playerCompare, strike);
                        didStrike = true;
                    }
                }
                if(didStrike == false)
                {
                    boggleWordsFound.getDocument().insertString(0, playerCompare, null);
                }
                boggleWordsFound.getDocument().insertString(0, "\n", null);
            }
        }
    }
    
    public class ExitGameListener implements ActionListener
    {

        @Override
        public void actionPerformed(ActionEvent ae) {
            //To confirm if the user really wants to exit.
            int exitResponse = JOptionPane.showConfirmDialog(null, "Exiting?", "Do you wish to Exit?", JOptionPane.YES_NO_OPTION);
            
            //To exit if the user confirms desire to exit.
            if (exitResponse == JOptionPane.YES_OPTION)
            {
                System.exit(0);
            }
        }
        
    }
    
    //boggleCurrentWordCreate
    public class SubmitWordListener implements ActionListener
    {
        public void submitWord(String newWord) throws BadLocationException
        {
            boggleWordsFound.getDocument().insertString(0, newWord, null);
            boggleWordsFound.getDocument().insertString(0, "\n", null);
        }
        
        @Override
        public void actionPerformed(ActionEvent ae)
        {
            
            boolean wordFound = false;
            for(String checkWord : currentBoggleBoard.getDictionaryData())
            {
               if(boggleCurrentWordCreate.getText().compareToIgnoreCase(checkWord) == IF_COMPARE_IS_ZERO)
               {
                   
                   try
                   {
                       submitWord(boggleCurrentWordCreate.getText());
                   }
                   catch(BadLocationException ble)
                   {
                   
                   }
//                   if(boggleWordsFound.getText().isEmpty())
//                   {
//                       boggleWordsFound.setText(boggleCurrentWordCreate.getText());
//                   }
//                   else
//                   {
//                       boggleWordsFound.setText(boggleWordsFound.getText() + "\n" + boggleCurrentWordCreate.getText());
//                   }
                   PlayerWords.add(boggleCurrentWordCreate.getText());
                   wordFound = true;
                   break;
               }
            }
            if (wordFound == false)
            {
                JOptionPane.showMessageDialog(null, "Submitted Word Not Valid!");
            }
            boggleCurrentWordCreate.setText("");
            //reset buttons
            for (int x = 0; x < DICE_GRID_SIDE; x++)
            {
                for (int y = 0; y < DICE_GRID_SIDE; y++)
                {
                    boggleDiceButton[x][y].setEnabled(true);
                }
            }
        }
    }
    
    public class DiceListener implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent ae)
        {
            for(int x = 0; x < DICE_GRID_SIDE; x++)
            {
                for(int y = 0; y < DICE_GRID_SIDE; y++)
                {
                    if(boggleDiceButton[x][y].equals(ae.getSource()))
                    {
                        boggleCurrentWordCreate.setText(boggleCurrentWordCreate.getText() + boggleDiceButton[x][y].getText());
                        boggleDiceButton[x][y].setEnabled(false);
                        for(int row = 0; row < DICE_GRID_SIDE; row++)
                        {
                            for(int col = 0; col < DICE_GRID_SIDE; col++)
                            {
                                if(row == (x - 1) && col == y)
                                {
                                    boggleDiceButton[row][col].setEnabled(true);
                                }
                                else if(row == (x - 1) && col == (y - 1))
                                {
                                    boggleDiceButton[row][col].setEnabled(true);
                                }
                                else if(row == (x - 1) && col == (y + 1))
                                {
                                    boggleDiceButton[row][col].setEnabled(true);
                                }
                                else if(row == (x + 1) && col == y)
                                {
                                    boggleDiceButton[row][col].setEnabled(true);
                                }
                                else if(row == (x + 1) && col == (y - 1))
                                {
                                    boggleDiceButton[row][col].setEnabled(true);
                                }
                                else if(row == (x + 1) && col == (y + 1))
                                {
                                    boggleDiceButton[row][col].setEnabled(true);
                                }
                                else if(row == (x) && col == (y - 1))
                                {
                                    boggleDiceButton[row][col].setEnabled(true);
                                }
                                else if(row == (x) && col == (y + 1))
                                {
                                    boggleDiceButton[row][col].setEnabled(true);
                                }
                                else
                                {
                                    boggleDiceButton[row][col].setEnabled(false);
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
