import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.awt.event.*;
import java.awt.Graphics;
import java.util.Arrays;
import java.util.concurrent.CountDownLatch;
import java.net.HttpURLConnection;
import java.net.URL;
import java.io.OutputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;

import com.google.genai.Client;
import com.google.genai.types.GenerateContentResponse;
public class checkers2
{
    //Window
    int width = 600;
    int length = 600;
    JFrame window = new JFrame("Checkers");
    JPanel panel = new JPanel();
    JPanel chessboard = new JPanel();
    square[][] board = new square[8][8];
    //user turn
    String turn = "black";
    CountDownLatch latch;
    private int[] nowcur;
    public class square
    {
        int bloffpos;
        int[] loc;
        int i;
        int j;
        private String imgpath;
        JButton button;
        int[] pos;
        square(int i, int j, String imgpath)
        {
            this.imgpath = imgpath;
            int[] pos = {j, i};
            this.loc = pos;
            button = new JButton()
            {
                @Override
                protected void paintComponent(Graphics g)
                {
                    super.paintComponent(g);
                    Image square;
                    try
                    {
                        square = new ImageIcon(getClass().getResource(square.this.imgpath)).getImage();
                        g.drawImage(square, 0, 0, 49, 50, null);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            };
            button.addActionListener(e -> {
                nowcur = loc;
                latch.countDown();
            });
            button.setSize(new Dimension(75, 60));
            button.setVisible(true);
            chessboard.add(button);
            chessboard.setLayout(new GridLayout(8, 8));
        }
        square(int i, int j, String imgpath, int val)
        {
            this.bloffpos = val;
            this.imgpath = imgpath;
            this.pos = new int[] {j,i};
            this.loc = pos;
            button = new JButton()
            {
                @Override
                protected void paintComponent(Graphics g)
                {
                    super.paintComponent(g);
                    Image square;
                    try
                    {
                        square = new ImageIcon(getClass().getResource(square.this.imgpath)).getImage();
                        g.drawImage(square, 0, 0, 49, 50, null);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            };
            button.addActionListener(e -> {
                nowcur = loc;
                latch.countDown();
            });
            button.setSize(new Dimension(75, 60));
            button.setVisible(true);
            chessboard.add(button);
            chessboard.setLayout(new GridLayout(8, 8));
        }
        public int[] getloc()
        {
            return loc.clone();
        }
        public JButton getButton()
        {
            return button;
        }
        public String getImgpath()
        {
            return imgpath;
        }
        public int getBloffpos()
        {
            return bloffpos;
        }
        public void changeloc(int[] newloc)
        {
            pos[0] = newloc[0];
            pos[1] = newloc[1];
            square.this.loc = newloc.clone();
        }
        public void changeimg(String newimg)
        {
            this.imgpath = newimg;
            button.repaint();
        }
    }

    checkers2()
    {
        window.setVisible(true);
        window.setSize(width, length);
        window.setLocationRelativeTo(null);
        window.setResizable(false);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        chessboard.setPreferredSize(new Dimension(400, 400));
        chessboard.setLayout(new GridLayout(8, 8));
        panel.setLayout(new BorderLayout());
        panel.setBackground(new Color(160, 160, 160));
        window.add(panel);
        panel.add(chessboard, BorderLayout.CENTER);
        window.pack();
        build_board();
        window.pack();
        chessboard.setVisible(true);
        startgame();

    }
    public void startgame()
    {
        while(true) {
            turn = "black";
            blackturn();
            turn = "white";
            whiteturn();
        }
    }
    public void whiteturn()
    {
        String whitelocs = "";
        String blacklocs = "";
        for (int i = 0; i<8; ++i)
        {
            for(int j=0; j<8; ++j)
            {
                if(board[i][j].getImgpath().equals("/whitepiece.png"))
                {
                    whitelocs += (board[i][j].getBloffpos() + ", ");
                } else if(board[i][j].getImgpath().equals("/blackpiece.png"))
                {
                    blacklocs += (board[i][j].getBloffpos() + ", ");
                }
            }
        }
        System.out.println(whitelocs);
        System.out.println("GOOGLE_API_KEY = " + System.getenv("GOOGLE_API_KEY"));
        try (Client client = new Client()){
            System.out.println("flower");
            GenerateContentResponse response =
                    client.models.generateContent(
                            "gemini-2.0-flash",
                            "I am playing checkers and I am playing the white pieces. What would be a good move if the white pieces are in positions" + whitelocs + " and black pieces are in positions " + blacklocs + ". please use standard notation and respond in this exact format: position of the piece to be moved:the position of the piece after we moved it. Please don't include anything else in your response. Have the rules of checkers in mind.",
                            null);
            System.out.println(response.text());
            String sresponse = response.text();
            String[] parts = sresponse.split(":");
            int[] curpos = new int[2];
            int[] potpos = new int[2];
            System.out.println("befoire white");
            for (int i = 0; i<8; ++i)
            {
                for(int j=0; j<8; ++j)
                {
                    if(board[i][j].getImgpath().equals("/whitepiece.png") && String.valueOf(board[i][j].getBloffpos()).equals(parts[0]))
                    {
                        curpos = board[i][j].getloc();
                        break;
                    }
                }
            }
            System.out.println("before black");
            for (int i = 0; i<8; ++i)
            {
                for(int j=0; j<8; ++j)
                {

                    if((board[i][j].getImgpath().equals("/black.png"))  && board[i][j].getBloffpos() == Integer.parseInt(parts[1].trim()))
                    {
                        System.out.println("inside");
                        potpos = board[i][j].getloc();
                        break;
                    }
                }
            }
            System.out.println(Arrays.toString(curpos));
            System.out.println(Arrays.toString(potpos));
            //checkeat(potpos, curpos);
            switchpiece(potpos, curpos);
        } catch (Exception e)
        {
            System.out.println("fu");
            System.out.println(e);
        }
    }
    public void blackturn()
    {
        latch = new CountDownLatch(1);
        try {
            latch.await();
        } catch (Exception e)
        {
            System.out.println(e);
        }
        int[] curpos = nowcur.clone();
        latch = new CountDownLatch(1);
        try {
            latch.await();
        } catch (Exception e)
        {
            System.out.println(e);
        }
        int[] potpos = nowcur.clone();
        int[] apotpos = potpos.clone();
        int[] acurpos = curpos.clone();
        System.out.println("running checkeat");
        checkeat(potpos, curpos);
        System.out.println("running switchpiece");
        switchpiece(apotpos, acurpos);
    }
    public void checkeat(int[] potpos,int[] curpos)
    {
        int x0 = curpos[0];
        int y0 = curpos[1];
        int x1 = potpos[0];
        int y1 = potpos[1];
        System.out.println("Pos " + x0 + " " + y0 + " " + x1 + " " + y1);

        if(curpos[1]+2 == potpos[1] && curpos[0]-2 == potpos[0])
        {
            board[curpos[1]-1][curpos[0]+1].changeimg("/black.png");
            board[curpos[1]-1][curpos[0]+1].getButton().repaint();
            System.out.println("inside1");
            chessboard.repaint();
        } else if(curpos[1]+2 == potpos[1] && curpos[0] +2 == potpos[0])
        {
            board[curpos[1]+1][curpos[0]+1].changeimg("/black.png");
            board[curpos[1]+1][curpos[0]+1].getButton().repaint();
            System.out.println("inside2");
            chessboard.repaint();
        }else if(curpos[1]-2 == potpos[1] && curpos[0]+2 == potpos[0])
        {
            board[curpos[1]+1][curpos[0]-1].changeimg("/black.png");
            board[curpos[1]+1][curpos[0]-1].getButton().repaint();
            System.out.println("inside3");
            chessboard.repaint();
        }else if(curpos[1]-2 == potpos[1] && curpos[0]-2 == potpos[0])
        {
            board[curpos[1]-1][curpos[0]-1].changeimg("/black.png");
            board[curpos[1]-1][curpos[0]-1].getButton().repaint();
            System.out.println("inside4");
            chessboard.repaint();
        }
    }
    public void switchpiece(int[] potpos, int[] curpos)
    {

        square toswitch;
        square toswitchw;
        toswitch = board[curpos[1]][curpos[0]];
        toswitchw = board[potpos[1]][potpos[0]];
        //switch img
        String tempImg = toswitch.getImgpath();
        String tempImg2 = toswitchw.getImgpath();
        toswitch.changeimg(tempImg2);
        toswitchw.changeimg(tempImg);
        //switch locs
        int[] toswitchwloc = toswitchw.getloc().clone();
        toswitchw.changeloc(toswitch.getloc().clone());
        toswitch.changeloc(toswitchwloc);
        board[potpos[1]][potpos[0]].getButton().repaint();
        board[curpos[1]][curpos[0]].getButton().repaint();
        chessboard.repaint(); // whole board

    }

    public void build_board()
    {
        int val = 32;
        for(int i = 0; i<8; ++i)
        {
            for(int j = 0; j<8; ++j)
            {
                int idk = (i%2==0) ? 1:0;
                if((j+(i*8)+idk)%2 == 0)
                { // yes, the square is black
                    if( i<3) //
                    { //yes there is a black game piece
                        System.out.println("hello");
                        board[i][j] = new square(i, j, "/whitepiece.png", val);
                        --val;
                        continue;
                    } else if(4<i)
                    {
                        board[i][j] = new square(i, j, "/blackpiece.png", val);
                        --val;
                        continue;

                    }
                    board[i][j] = new square(i, j, "/black.png", val);
                    --val;
                    continue;


                }
                board[i][j] = new square(i, j, "/Untitled.png");
            }
        }
    }
}
