import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.ImageObserver;

/**
 * Created by Jere on 29.4.2016.
 */
public class Player extends JPanel
{
    private Image shiba; // This contains the player image
    private boolean pressingUp; // Used for detecting when the up button is pressed

    // Here are the y and x coordinate speeds
    private int yspeed = 5;
    private int xspeed = 8;

    public int PlayerDirection = 2;
    private Boolean jumping = false;
    public double GravitySpeed = 0;
    double JumpSpeed = 0;
    int JumpTickCounter;
    public Boolean hittingGround = false;
    public Boolean hittingLeft = false;
    public Boolean hittingRight = false;

    public Boolean hittingGroundCurrentBlock = false;
    public boolean CollisionTop = false;
    private int animationCounter;
    private int x;
    public int y;
    private boolean up;
    private boolean right;
    private boolean down;
    private boolean left;
    ImageIcon sprite;
    private Rectangle rect;
    ImageObserver observer;


    public void LoadImage(int animation)
    {
        if(animation == 1)
        {
                sprite = new ImageIcon("SHIBA2.png");
                sprite.setImageObserver(observer);
                shiba = sprite.getImage();
        }
        if(animation == 2)
        {
            sprite = new ImageIcon("SHIBA1.png");
            sprite.setImageObserver(observer);
            shiba = sprite.getImage();
        }
        if(animation == 3)
        {
            sprite = new ImageIcon("SHIBA3.png");
            sprite.setImageObserver(observer);
            shiba = sprite.getImage();
        }
    }

    public int GetX()
    {
        return x;
    }

    public int GetY()
    {
        return y;
    }

    public int GetHeight()
    {
        return shiba.getHeight(this);
    }

    public int GetWidth()
    {
        return shiba.getWidth(this);
    }

    public Image GetImage()
    {
        return shiba;
    }
    public Rectangle getBounds()
    {
        if(PlayerDirection == 2)
        {
            return rect = new Rectangle(GetX(), GetY(), GetWidth() - 10, GetHeight());
        }
        else
        {
            return rect = new Rectangle(GetX(), GetY(), GetWidth() - 10, GetHeight());
        }
    }

    public void UpdateSprite()
    {
        int animation;

        if(up == true)
        {
            Jump();
            animationCounter++;
        }
        if(down == true)
        {
            y += yspeed;
            animationCounter++;
        }
        if(left == true)
        {
            if(!hittingLeft)
            {
                PlayerDirection = 1;
                xspeed = 7;
                x -= xspeed;
                animationCounter++;
            }
        }
        if(right == true)
        {
            if(!hittingRight)
            {
                PlayerDirection = 2;
                xspeed = 7;
                x += xspeed;
                animationCounter++;
            }
        }
        if(animationCounter >= 0 && animationCounter <= 10)
        {
            LoadImage(animation = 1);
        }
        if(animationCounter >= 11 && animationCounter <= 20)
        {
            LoadImage(animation = 3);
        }
        if(animationCounter >= 21 && animationCounter <= 30)
        {
            LoadImage(animation = 2);
        }
        if(animationCounter >= 30)
        {
            animationCounter = 0;
        }
    }

    public void keyPressed(KeyEvent e)
    {
        int key = e.getKeyCode();

        if(key == KeyEvent.VK_UP)
        {
            if(jumping == false && pressingUp == false) {
                JumpSpeed = 15;
                pressingUp = true;
                up = true;
                System.out.print("JUMP");
                Jump();
            }
        }

        if(key == KeyEvent.VK_LEFT)
        {
            left = true;
        }

        if(key == KeyEvent.VK_DOWN)
        {
            //down = true;
        }

        if(key == KeyEvent.VK_RIGHT)
        {
            System.out.print("PRESSING RIGHT");
            right = true;
        }
    }

    public void keyReleased(KeyEvent e)
    {
        int key = e.getKeyCode();

        if(key == KeyEvent.VK_UP)
        {
            System.out.print("PERSE");
            pressingUp = false;
            up = false;
        }

        if(key == KeyEvent.VK_LEFT)
        {
            left = false;
        }

        if(key == KeyEvent.VK_DOWN)
        {
            //down = false;
        }

        if(key == KeyEvent.VK_RIGHT)
        {
            right = false;
        }
    }

    public void HittingGround(int objecty)
    {
            y = objecty - shiba.getHeight(observer);
            hittingGround = true;
            jumping = false;
            GravitySpeed = 0;
            down = false;
    }

    public void HittingTop()
    {
        JumpSpeed = 0;
        up = false;
    }

    public void StopRight()
    {
        xspeed = 0;
        hittingRight = true;
    }

    public void StopLeft()
    {
        xspeed = 0;
        hittingLeft = true;
    }

    public void Jump()
    {
        if(JumpSpeed <= 0)
        {
            up = false;
        }
        if (pressingUp) {
            jumping = true;
            JumpTickCounter++;
            y -= JumpSpeed;
            if(JumpTickCounter >= 2)
            {
                JumpTickCounter = 0;
                JumpSpeed -= 1;
            }
        }
    }

    public void Gravity()
    {
        if(hittingGround == false)
        {
            if (GravitySpeed <= 20) {
                GravitySpeed += 0.6;
            }
            y += GravitySpeed;
        }
        if(!pressingUp && JumpSpeed > 1)
        {
            JumpTickCounter++;
            if (JumpTickCounter >= 2)
            {
                JumpTickCounter = 0;
                JumpSpeed -= 3;
            }
            y -= JumpSpeed;
        }
    }
}
