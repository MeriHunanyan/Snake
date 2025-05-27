import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.*;

public class Snake extends JFrame implements KeyListener
{
    int cur_score = 0;
    int best_score = 0;
    //window
    int width = 600;
    int length=600;
    JFrame frame = new JFrame("Snake");
    JPanel bigpanel = new JPanel();
    JPanel mediumpanel = new JPanel();
    JPanel gamearea = new JPanel();
    Snake()
    {
        startGame();
        frame.setVisible(true);
        frame.setSize(width, length);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        bigpanel.setLayout(new BorderLayout());
        mediumpanel.setLayout(new BorderLayout());
        bigpanel.setBackground(new Color(79, 115, 57));
        mediumpanel.setBackground(new Color(93, 136,67));
        frame.add(bigpanel);
        frame.add(mediumpanel, BorderLayout.SOUTH);

    }

    @Override
    public void keyTyped(KeyEvent e) {
        switch (e.getKeyCode()){
        case 38:
        case 87:
            System.out.println("go up");
        break;
        case
        if (e.getKeyCode() == 38 || e.getKeyCode() == 87)
        {
            System.out.println("go up");
        } else if(e.getKeyCode() == )

    }

    public void startGame()
    {

    }
}
