import javafx.scene.shape.Circle;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.ImageObserver;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Jere on 31.5.2016.
 */
public class Enemy extends Character_Base
{
    private Random rand = new Random();
    private  int direction;
    private int counter;

    public Enemy(int StartX, int StartY)
    {
        super(StartX, StartY);
        xspeedBase = 4;
        player = false;
    }

    public void AI(Rectangle playerRect)
    {
        Rectangle viewRect = new Rectangle(GetX() - 300, GetY() - 40, GetWidth() + 600, GetHeight() + 80);

        int x = (int) playerRect.getCenterX();
        int y = (int) playerRect.getCenterY() - 30;
        Point playerPosition = new Point(x, y);

        if(playerRect.intersects(viewRect)) {

            int shoot = rand.nextInt(30) +1;

            if (playerRect.getCenterX() < viewRect.getCenterX()) {
                right = false;
                left = true;
            } else if (playerRect.getCenterX() > viewRect.getCenterX()) {
                left = false;
                right = true;
            }
            if(shoot == 10)
            {
                Shoot(playerPosition);
            }
        }
            else {

                counter++;

                if(counter >= 100)
                {
                    direction = rand.nextInt(2) + 1;
                    if(direction == 1) {
                        right = false;
                        left = true;
                    }
                    else if(direction == 2)
                    {
                        left = false;
                        right = true;
                    }

                    counter = 0;
                }
            }
    }

    public boolean BulletCollisionCheck(Circle bulletCircle)
    {
        Rectangle playerRect = getFullBounds();
        if((bulletCircle.getCenterX() + bulletCircle.getRadius() >= playerRect.getX()) && (bulletCircle.getCenterX() + bulletCircle.getRadius() <= (playerRect.getX() + playerRect.getWidth())) && (bulletCircle.getCenterY() >= playerRect.getY()) && (bulletCircle.getCenterY() <= (playerRect.getY() + playerRect.getHeight())))
        {
            return true;
        }
        else
            return false;
    }



    public void Shoot(Point PlayerPosition)
    {
        {
            Bullet bullet = new Bullet(PlayerPosition, x, y, PlayerDirection);
            bullets.add(bullet);
        }
    }
}
