import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.awt.event.*;
import java.awt.Graphics;
import java.util.Arrays;
import java.util.Objects;
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
    int width = 250;
    int length = 150;
    JFrame window = new JFrame("Checkers");
    JPanel panel = new JPanel();
    JPanel starttpanel = new JPanel();
    JPanel chessboard = new JPanel();
    square[][] board = new square[8][8];
    //user turn
    String userchoice;
    String turn = "black";
    CountDownLatch latch;
    CountDownLatch latch1 = new CountDownLatch(1);
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
                checkers2.this.nowcur = square.this.loc;
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
                System.out.println();
                checkers2.this.nowcur = square.this.loc;
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
            System.out.println("before change loc: " + Arrays.toString(square.this.loc) + "pos: " + Arrays.toString(pos));
            pos[0] = newloc[0];
            pos[1] = newloc[1];
            square.this.loc = newloc.clone();
            System.out.println("after  change loc: " + Arrays.toString(square.this.loc) + "pos: " + Arrays.toString(pos));
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
        startwindow();
        try{
            latch1.await();
        } catch(Exception e){
            System.out.println(e);
        }
        if (userchoice.equals("multiplayer"))
        {
            startirlgame();
        }
        startwaigame();
        /*
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
        */
    }
    public void startirlgame()
    {
        while(true)
        {
            blackturn();
        }

    }
    public void startwaigame()
    {

        while(true) {
            turn = "black";
            blackturn();
            System.out.println();
            turn = "white";
            whiteturn();
        }
    }
    public void startwindow()
    {
        JPanel question = new JPanel(){
            @Override
            protected void paintComponent(Graphics g)
            {
                super.paintComponent(g);
                g.setColor(Color.WHITE);

                // Set font (optional)
                g.setFont(new Font("Arial", Font.BOLD, 20));

                // Draw the string
                g.drawString("Choose game mode", 25, 25); // (x, y) = pixel position
            }

        };
        question.setLayout(null);
        JButton multiplayer = new JButton("with another person?");
        multiplayer.addActionListener(e -> {
            userchoice = "multiplayer";
            // Completely remove everything from the window
            window.getContentPane().removeAll();
            // Add your new layout/panel
            panel.setLayout(new BorderLayout());
            panel.setBackground(new Color(160, 160, 160));
            chessboard.setPreferredSize(new Dimension(400, 400));
            chessboard.setLayout(new GridLayout(8, 8));
            panel.add(chessboard, BorderLayout.CENTER);
            window.getContentPane().add(panel);

            window.revalidate();
            window.repaint();

            window.setSize(400, 400);
            window.setLocationRelativeTo(null);
            window.setResizable(false);
            window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            build_board();
            window.pack();
            latch1.countDown();
        });
        JButton wai = new JButton("with bot?");
        wai.addActionListener(e -> {
            userchoice = "bot";
            // Completely remove everything from the window
            window.getContentPane().removeAll();
            // Add your new layout/panel
            panel.setLayout(new BorderLayout());
            panel.setBackground(new Color(160, 160, 160));
            chessboard.setPreferredSize(new Dimension(400, 400));
            chessboard.setLayout(new GridLayout(8, 8));
            panel.add(chessboard, BorderLayout.CENTER);
            window.getContentPane().add(panel);

            window.revalidate();
            window.repaint();

            window.setSize(400, 400);
            window.setLocationRelativeTo(null);
            window.setResizable(false);
            window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            build_board();
            window.pack();
            latch1.countDown();
        });
        multiplayer.setBounds(20, 35, 200, 30);
        wai.setBounds(20, 75, 200, 30);

        question.add(multiplayer);
        question.add(wai);
        question.setSize(100, 100);
        question.setBackground(new Color(160, 0, 0));
        question.setVisible(true);
        window.add(question);
        window.setVisible(true);
    }
    public void whiteturn()
    {
        System.out.println("white turn");
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
        System.out.println(blacklocs);
        System.out.println("GOOGLE_API_KEY = " + System.getenv("GOOGLE_API_KEY")); //GOOGLE_API_KEY=AIzaSyDaoNSJ7jnb0uj_UqriTswq1Wf9aRMWykk
        try (Client client = new Client()){
            System.out.println("flower");
            int[] curpos = new int[2];
            int[] potpos = new int[2];
            while(true) {
                GenerateContentResponse response =
                        client.models.generateContent(
                                "gemini-1.5-flash",
                                "I am playing a game of standard American checkers. I am controlling the white pieces, and I want you to generate the best legal move for me. " + "The white pieces are currently in positions: " + whitelocs + ". " + "The black pieces are in positions: " + blacklocs + ". " + "Respond in this exact format: 'the location of the piece to be moved':'location after moving' using numeric notation. Follow these strict rules: " + " Capturing is allowed if you are capturing an opponent piece, " + "White can move only 1 diagonal forward unless capturing, " + "A capture must jump over an adjacent black piece into an empty square, " +"Do not move into already occupied positions, " + "Only reply with the move, no explanation",
                                null);
                System.out.println(response.text());
                String sresponse = response.text();
                String[] parts = sresponse.split(":");
                for (int i = 0; i < 8; ++i) {
                    for (int j = 0; j < 8; ++j) {
                        if (board[i][j].getImgpath().equals("/whitepiece.png") && String.valueOf(board[i][j].getBloffpos()).equals(parts[0])) {
                            curpos = board[i][j].getloc();
                            break;
                        }
                    }
                }
                for (int i = 0; i < 8; ++i) {
                    for (int j = 0; j < 8; ++j) {

                        if ((board[i][j].getImgpath().equals("/black.png")) && board[i][j].getBloffpos() == Integer.parseInt(parts[1].trim())) {
                            potpos = board[i][j].getloc();
                            break;
                        }
                    }
                }
                System.out.println("potpos");
                System.out.println(Arrays.toString(potpos));
                if (Arrays.equals(potpos, new int[]{0, 0})) {
                    continue;
                }
                break;
            }
            checkeat(potpos, curpos);
            switchpiece(potpos, curpos);
            System.out.println();
        } catch (Exception e)
        {
            System.out.println("fu");
            System.out.println(e);
        }
    }
    public void blackturn()
    {
        checkers2.this.nowcur = new int[]{0, 0};
        System.out.println("black");
        latch = new CountDownLatch(1);
        System.out.println("after latch");
        try {
            latch.await();
            System.out.println("after awaot");
        } catch (Exception e)
        {
            System.out.println(e);
        }
        int[] curpos = nowcur.clone();
        System.out.println("curpos");
        System.out.println(Arrays.toString(curpos));
        latch = new CountDownLatch(1);
        try {
            latch.await();
        } catch (Exception e)
        {
            System.out.println(e);
        }
        System.out.println(Arrays.toString(nowcur));
        int[] potpos = nowcur.clone();
        System.out.println("potpos");
        System.out.println(Arrays.toString(potpos));
        int[] apotpos = potpos.clone();
        int[] acurpos = curpos.clone();
        checkeat(potpos, curpos);
        switchpiece(apotpos, acurpos);
    }
    public void checkeat(int[] potpos,int[] curpos)
    {
        int x0 = curpos[0];
        int y0 = curpos[1];
        int x1 = potpos[0];
        int y1 = potpos[1];
        System.out.println("check eat. current pos and potential pos");
        System.out.println("Pos " + x0 + " " + y0 + " " + x1 + " " + y1);

        if(curpos[1]-2 == potpos[1] && curpos[0]+2 == potpos[0])
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
        }else if(curpos[1]+2 == potpos[1] && curpos[0]-2 == potpos[0])
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
        //int[] toswitchwloc = toswitchw.getloc().clone();
        //toswitchw.changeloc(toswitch.getloc().clone());
        //toswitch.changeloc(toswitchwloc);
        //switch pos in the array
        //square temp = board[curpos[1]][curpos[0]];
        //board[curpos[1]][curpos[0]] = board[potpos[1]][potpos[0]];
        //board[potpos[1]][potpos[0]] = temp;
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
