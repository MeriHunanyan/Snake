import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.awt.event.*;
import java.awt.Graphics;

public class checkers2
{
    //Window
    int width = 600;
    int length = 600;
    JFrame window = new JFrame("Checkers");
    JPanel panel = new JPanel();
    JPanel chessboard = new JPanel();
    JButton[][] board = new JButton[8][8];
    checkers2()
    {
        window.setVisible(true);
        window.setSize(width, length);
        window.setLocationRelativeTo(null);
        window.setResizable(false);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        chessboard.setSize(400, 400);
        chessboard.setLayout(new GridLayout(8, 8));
        panel.setLayout(new BorderLayout());
        panel.setBackground(new Color(160, 160, 160));
        window.add(panel);
        panel.add(chessboard, BorderLayout.CENTER);
        build_board();
        chessboard.setVisible(true);

    }

    public void build_board()
    {
        for(int i = 0; i<8; ++i)
        {
            for(int j = 0; j<8; ++j)
            {
                int idk = (i%2==0) ? 1:0;
                if((j+(i*8)+idk)%2 == 0)
                {

                    board[i][j] = new JButton()
                    {
                        @Override
                        protected void paintComponent(Graphics g)
                        {
                            super.paintComponent(g);
                            System.out.println("inside");
                            Image square;
                            try
                            {
                                System.out.println("black");
                                square = new ImageIcon(getClass().getResource("/black.png")).getImage().getScaledInstance(75, 75, Image.SCALE_SMOOTH);
                                g.drawImage(square, 5, 5, 75, 75, null);
                            } catch (Exception e)
                            {
                                e.printStackTrace();
                            }
                        }
                    };
                    board[i][j].setSize(new Dimension(75, 75));
                    board[i][j].setVisible(true);
                    chessboard.add(board[i][j]);
                    continue;
                }
                board[i][j] = new JButton()
                {
                    @Override
                    protected void paintComponent(Graphics g)
                    {
                        super.paintComponent(g);
                        System.out.println("inside");
                        Image square;
                        try
                        {
                            System.out.println("white");
                            square = new ImageIcon(getClass().getResource("/Untitled.png")).getImage().getScaledInstance(75, 75, Image.SCALE_SMOOTH);
                            g.drawImage(square, 5, 5, 75, 75, null);
                        } catch (Exception e)
                        {
                            e.printStackTrace();
                        }
                    }
                };
                //board[i][j].setBackground(Color.WHITE); //White
                board[i][j].setSize(new Dimension(75, 75));
                board[i][j].setVisible(true);
                chessboard.add(board[i][j]);
            }

            System.out.println();
        }
    }
}
