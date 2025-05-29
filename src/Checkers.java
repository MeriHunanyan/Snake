import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;


public class Checkers
{
    private class square
    {
        boolean color;
        gamepiece piece;
        square(boolean color)
        {
            this.color = color;
            if(color == true)
            {
                JPanel small = new JPanel()
                {
                    @Override
                    public void paintComponent(Graphics g) {
                        try
                        {
                            Image square = new ImageIcon(getClass().getResource("./"));
                            g.drawImage(square);
                        } catch (Exception e)
                        {
                            e.printStackTrace();
                        }
                    }
                };
            }
        }
        square(boolean color, gamepiece piece)
        {
            this.color = color;
            this.piece = piece;
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
        startGame();

        frame.setVisible(true);
        frame.setSize(width, length);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        panel.setLayout(new BorderLayout());
        panel.setBackground(new Color(160, 160, 160));
        frame.add(panel);
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
                        board[i][j] = new square(true, new gamepiece(true, false)); //black pieces
                    } else if(4<j)
                    {
                        board[i][j] = new square(true, new gamepiece(false, false)); //white pieces
                    }
                    board[i][j] = new square(true);
                }
                board[i][j] =new square(false);
            }
        }
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