import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

/**
 * Created by Jere on 29.4.2016.
 */
public class Board extends JPanel implements ActionListener
{
    private Player player;
    private Object ground;
    private Rectangle playerRect;
    private Rectangle objectRect;
    private int w;
    private int h;
    private int DELAY = 16;
    private ImageIcon bg;
    private Image backGround;
    ArrayList<Object> objects;

    private Timer timer;
    private Button button = new Button();

    public Board()
    {
        objects = new ArrayList<Object>();
        player = new Player();
        ground = new Object("1", 0, 655);
        objects.add(ground);
        ground = new Object("1", 400, 300);
        objects.add(ground);
        ground = new Object("2", 300, 550);
        objects.add(ground);
        ground = new Object("2", 900, 400);
        objects.add(ground);
        bg = new ImageIcon("BackGround.jpg");
        backGround = bg.getImage();
        initBoard();
    }

    private void initBoard()
    {
        int animation = 1;
        add(button);
        player.LoadImage(animation);
        button.setLabel("SHIBE");
        button.setFocusable(false);

        addKeyListener(new TAdapter());
        setFocusable(true);

        w = player.GetWidth();
        h = player.GetHeight();
        setPreferredSize(new Dimension(w, h));

        timer = new Timer(DELAY, this);
        timer.start();
    }

    @Override
    public void paintComponent(Graphics g)
    {
        Graphics2D g2d = (Graphics2D) g.create();


        g2d.drawImage(backGround, 0, 0, null);
        for (Object ground: objects)
        {
            g2d.drawImage(ground.image, ground.x, ground.y, null);
        }
        if(player.PlayerDirection == 1)
        {
            g2d.drawImage(player.GetImage(), player.GetX() + player.GetHeight(), player.GetY(), -player.GetWidth(), player.GetHeight(), null);
        }
        else
            g2d.drawImage(player.GetImage(), player.GetX(), player.GetY(), this);
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        player.Gravity();
        CollisionCheck();
        player.UpdateSprite();
        repaint();
    }

    private void CollisionCheck()
    {
        playerRect = player.getBounds();
        player.hittingGround = false;
        player.hittingRight = false;
        player.hittingLeft = false;

        for(Object ground: objects) {
            objectRect = ground.getBounds();

            if (playerRect.intersects(objectRect)) {
                if (((player.GetY() + playerRect.getHeight() - 20) < ground.y))
                {
                    player.HittingGround(ground.y);
                    player.hittingGroundCurrentBlock = true;
                }
                else
                {
                    player.hittingGroundCurrentBlock = false;
                }
                if ((player.GetY() < (ground.y + objectRect.getHeight())) && (player.GetY() > (ground.y + objectRect.getHeight() - 30)))
                {
                    player.HittingTop();
                }
                if((playerRect.getX() + playerRect.getWidth() - 20) < (objectRect.getX()) && !player.hittingGroundCurrentBlock)
                {
                    player.StopRight();
                }
                if((playerRect.getX() + playerRect.getWidth() > (objectRect.getX() + objectRect.getWidth() + 100) && !player.hittingGroundCurrentBlock))
                {
                    player.StopLeft();
                }
            }
        }
    }

    private class TAdapter extends KeyAdapter
    {
        @Override
        public void keyReleased(KeyEvent e)
        {
            player.keyReleased(e);
        }

        @Override
        public void keyPressed(KeyEvent e)
        {
            player.keyPressed(e);
        }
    }
}
