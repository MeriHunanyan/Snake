import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.awt.event.*;


public class Checkers
{
    private class square
    {
        boolean color;
        gamepiece piece;
        square(boolean color)
        {
            this.color = color;
            JPanel small = new JPanel()
            {
                @Override
                public void paintComponent(Graphics g)
                {
                    Image square;
                    try
                    {
                        if(color == true)
                        {
                            square = new ImageIcon(getClass().getResource("/black.png")).getImage();
                            g.drawImage(square, 20, 20, 100, 100, null);
                        } else
                        {
                            square = new ImageIcon(getClass().getResource("/untitled.png")).getImage();
                            g.drawImage(square, 20, 20, 100, 100, null);
                        }

                    } catch (Exception e)
                    {
                        e.printStackTrace();
                    }

                }
            };
            chessboard.add(small);
        }
        square(boolean color, gamepiece piece)
        {
            this.color = color;
            this.piece = piece;
            System.out.println("constructor");
            JPanel small = new JPanel() {
                @Override
                public void paintComponent(Graphics g) {
                    System.out.println("inside");
                    try {
                        Image square;
                        if (color) {
                            System.out.println("hello");
                            square = new ImageIcon(getClass().getResource("/blackpiece.png")).getImage();
                            g.drawImage(square, 20, 20, 100, 100, null);
                        } else {
                            //System.out.println("hello");
                            square = new ImageIcon(getClass().getResource("/whitepiece.png")).getImage();
                            g.drawImage(square, 20, 20, 100, 100, null);
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            };
        }
    }
    private class gamepiece
    {
        boolean value;
        boolean king;
        //String locationh;
        //int locationv; // I don't really need these but I commented them out in case I do need them

        gamepiece(boolean value, boolean king)
        {
            this.value = value;
            this.king = king;
            //this.locationh = locationh;
            //this.locationv = locationv;
        }
    }
    //board
    Object[][] board = new Object[8][8];
    //visual board
    JPanel chessboard = new JPanel();

    //Game play
    boolean userTurn = true;
    //how many game pieces taken
    int t_user = 0;
    int t_opp = 0;
    //window
    int width = 600;
    int length = 600;
    JFrame frame = new JFrame("Checkers");
    JPanel panel = new JPanel();
    Checkers()                                                      //constructor
    {
        chessboard.setSize(400, 400);
        chessboard.setLayout(new GridLayout(8, 8));
        chessboard.setVisible(true);
        startGame();
        chessboard.repaint();
        frame.setVisible(true);
        frame.setSize(width, length);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        panel.setLayout(new BorderLayout());
        panel.setBackground(new Color(160, 160, 160));
        frame.add(panel);
        panel.add(chessboard, BorderLayout.CENTER);
        chessboard.setVisible(true);
    }

    public void startGame()
    {
        buildBoard();

    }

    public void buildBoard()
    {
        //board
        for(int i = 0; i < 8; ++i)
        {
            for(int j=0; j<8; ++j)
            {
                if((i+(8*j))%2 == 0)
                {
                    if(0<j && j<3)
                    {
                        //System.out.println("hello");
                        board[i][j] = new square(true, new gamepiece(true, false)); //black pieces
                        chessboard.setVisible(true);
                    } else if(4<j)
                    {
                        board[i][j] = new square(true, new gamepiece(false, false)); //white pieces
                        chessboard.setVisible(true);
                    }
                    board[i][j] = new square(true);
                    chessboard.setVisible(true);
                }
                board[i][j] =new square(false);
                chessboard.setVisible(true);
            }
        }
        chessboard.setVisible(true);
    }
    public void userturn()
    {

    }
    public void oppturn()
    {

    }
    public void take()
    {

    }

}