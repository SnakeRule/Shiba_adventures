import com.sun.deploy.uitoolkit.*;
import com.sun.deploy.uitoolkit.Window;
import javafx.embed.swing.JFXPanel;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.shape.Circle;
import sun.audio.AudioData;
import sun.audio.AudioPlayer;
import sun.audio.AudioStream;
import sun.audio.ContinuousAudioDataStream;

import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;

/**
 * Created by Jere on 29.4.2016.
 */
public class Board extends JPanel implements ActionListener
{
    private Player player;
    public static double ScaleX;
    public static double ScaleY;
    private static MediaPlayer mediaPlayer;
    public Level level;
    private boolean hit;
    private Point mousePosition;
    private Object ground;
    private Enemy enemy;
    private Rectangle playerRect;
    private Rectangle objectRect;
    private int quakeCounter = 0;
    private Image backGround;
    ArrayList<Object> objects;
    ArrayList<Enemy> enemies;

    private Button button = new Button();
    public static boolean HittingBottom;

    public Board() {
        button.setLabel("SHIBE");
        level = new Level();
        level.LoadLevel(1);
        level.BuildLevel();
        initBoard();
    }

    private void initBoard()
    {
        JFXPanel jfxPanel = new JFXPanel();
        File f = new File("Resources\\BgMusic.mp3");
        URI u = f.toURI();
        Media BgMusic = new Media(u.toString());
        mediaPlayer = new MediaPlayer(BgMusic);
        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        mediaPlayer.play();

        player = level.player;
        objects = level.objects;
        backGround = level.backGround;
        enemies = level.enemies;
        ImageIcon bg = level.bg;
        int animation = 1;
        player.LoadImage(animation);
        for (Enemy enemy:enemies)
        {
            enemy.LoadImage(animation);
        }
        addKeyListener(new TAdapter());
        addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mousePressed(MouseEvent e)
            {
                mousePosition = getMousePosition();
                mousePosition.x /= ScaleX / 1920;
                mousePosition.y /= ScaleY / 1080;
                if(player.PlayerDirection == 1)
                {
                    player.Shoot(mousePosition);
                }
                else {
                    mousePosition.x -= player.getWidth() *2;
                    mousePosition.y -= player.getHeight()/2;
                    player.Shoot(mousePosition);
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });
        setFocusable(true);

        int w = player.GetWidth();
        int h = player.GetHeight();
        setPreferredSize(new Dimension(w, h));

        int DELAY = 16;
        Timer timer = new Timer(DELAY, this);
        timer.start();
    }

    @Override
    public void paintComponent(Graphics g)
    {
        Graphics2D g2d = (Graphics2D) g.create();
        ScaleY = getHeight();
        ScaleX = getWidth();
        g2d.scale(ScaleX / 1920, ScaleY / 1080);
        g2d.drawImage(backGround, 0, 0, null);

        if(!player.bullets.isEmpty()) {
            for (Bullet bullet : player.bullets) {
                if(bullet.x > -150 && bullet.x < 1920 + 150) {
                    g2d.setColor(Color.red);
                    g2d.fillOval(bullet.x - 10, bullet.y - 10, 20, 20);
                }
            }
        }

        for (Object ground: objects)
        {
            if(ground.x > -150 && ground.x < 1920 + 150)
            g2d.drawImage(ground.image, ground.x, ground.y, null);
        }

        if(player.PlayerDirection == 1)
        {
            g2d.drawImage(player.GetImage(), player.GetX() + player.GetHeight(), player.GetY(), -player.GetWidth(), player.GetHeight(), null);
        }
        else
            g2d.drawImage(player.GetImage(), player.GetX(), player.GetY(), this);

        for (Enemy enemy: enemies)
        {
            if(enemy.x > -150 && enemy.x < 1920 + 150)
            {
                if (enemy.PlayerDirection == 1) {
                    g2d.drawImage(enemy.GetImage(), enemy.GetX() + enemy.GetHeight(), enemy.GetY(), -enemy.GetWidth(), enemy.GetHeight(), null);
                } else
                    g2d.drawImage(enemy.GetImage(), enemy.GetX(), enemy.GetY(), this);
            }
            for (Bullet bullet:enemy.bullets)
            {
                if(bullet.x > -150 && bullet.x < 1920 + 150) {
                    g2d.setColor(Color.red);
                    g2d.fillOval(bullet.x - 10, bullet.y - 10, 20, 20);
                }
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        MoveScreen();
        player.Gravity();
        player.UpdateSprite();
        for (Enemy enemy:enemies)
        {
            enemy.AI(player.getFullBounds());
            enemy.Gravity();
            enemy.UpdateSprite();
            for (Bullet bullet:enemy.bullets)
            {
                bullet.UpdateBullet();
            }
        }
        CollisionCheck();
        for (Bullet bullet:player.bullets)
        {
            bullet.UpdateBullet();
        }
        repaint();
    }



    private void MoveScreen()
    {
        if((player.GetX() + player.getWidth() / 2) > 700)
        {
            player.x -= 7;

            for (Object object:objects)
            {
                object.x -= 7;
            }
            for (Bullet bullet:player.bullets)
            {
                bullet.x -= 7;
            }
            for (Enemy enemy:enemies)
            {
                enemy.x -= 7;
                for (Bullet bullet:enemy.bullets)
                    bullet.x -=7;
            }
        }
        if((player.GetX() + player.getWidth() / 2) < 500)
        {
            player.x += 7;

            for (Object object:objects)
            {
                object.x += 7;
            }
            for (Bullet bullet:player.bullets)
            {
                bullet.x += 7;
            }
            for (Enemy enemy:enemies)
            {
                enemy.x += 7;
                    for (Bullet bullet:enemy.bullets)
                        bullet.x +=7;
            }
        }
        if(player.Quake)
        {
            for (Object object:objects)
            {
                if(quakeCounter >= 0 && quakeCounter <= 5)
                {
                    object.y -= 3;
                }
                else if(quakeCounter >= 6 && quakeCounter <= 11)
                {
                    object.y += 3;
                }
                if(quakeCounter >= 12)
                {
                    quakeCounter = 0;
                }
            }
            quakeCounter++;
        }
    }

    private void CollisionCheck()
    {

        player.CollisionCheck(objects);
        for (Enemy enemy:enemies)
        {
            enemy.CollisionCheck(objects);
            for (Bullet bullet:player.bullets)
            {
                hit = enemy.BulletCollisionCheck(bullet.GetBounds());
                if(hit)
                {
                    player.bullets.remove(bullet);
                    break;
                }
            }
            if(hit)
            {
                //enemies.remove(enemy);
                break;
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
