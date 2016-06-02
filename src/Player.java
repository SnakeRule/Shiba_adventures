import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.ImageObserver;
import java.util.ArrayList;

/**
 * Created by Jere on 29.4.2016.
 */
public class Player extends Character_Base
{
    public boolean Quake;

    public Player(int StartX, int StartY)
    {

        super(StartX, StartY);

        xspeedBase = 7;
    }


    public void keyPressed(KeyEvent e)
    {
        int key = e.getKeyCode();

        if(key == KeyEvent.VK_W)
        {
            if(!jumping && !pressingUp) {
                JumpSpeed = 14;
                pressingUp = true;
                up = true;
                Jump();
            }
        }

        if(key == KeyEvent.VK_A)
        {
            left = true;
            PlayerDirection = 1;
        }

        if(key == KeyEvent.VK_S)
        {
            down = true;
        }

        if(key == KeyEvent.VK_D)
        {
            right = true;
            PlayerDirection = 2;
        }

        if(key == KeyEvent.VK_Q)
        {
            if(Quake)
            {
                Quake = false;
            }
            else
                Quake = true;
        }
    }

    public void keyReleased(KeyEvent e)
    {
        int key = e.getKeyCode();

        if(key == KeyEvent.VK_W)
        {
            pressingUp = false;
            up = false;
        }

        if(key == KeyEvent.VK_A)
        {
            left = false;
            xspeed = 0;
        }

        if(key == KeyEvent.VK_S)
        {
            down = false;
            DropDown = false;
        }
        if(key == KeyEvent.VK_D)
        {
            right = false;
            xspeed = 0;
        }
    }

    public void Shoot(Point MousePosition)
    {
        if((PlayerDirection == 1 && MousePosition.getX() < x)) {
            Bullet bullet = new Bullet(MousePosition, x, y, PlayerDirection);
            bullets.add(bullet);
        }
        else if((PlayerDirection == 2 && MousePosition.getX() > x + GetWidth() - 30))
        {
            MousePosition.x -= GetWidth();
            Bullet bullet = new Bullet(MousePosition, x, y, PlayerDirection);
            bullets.add(bullet);
        }
    }
}
