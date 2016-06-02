import javafx.scene.shape.Circle;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.ImageObserver;
import java.util.ArrayList;

/**
 * Created by Jere on 29.4.2016.
 */
public class Character_Base extends JPanel
{
    public ArrayList<Bullet> bullets;
    protected boolean player;
    protected Image character; // This contains the player image
    protected boolean pressingUp; // Used for detecting when the up button is pressed

    private Rectangle playerRect = new Rectangle();
    private Rectangle objectRect = new Rectangle();
    private Rectangle rect = new Rectangle();
    private Rectangle.Double TopHitBox;
    private Rectangle.Double RightHitBox;
    private Rectangle.Double LeftHitBox;
    private Rectangle.Double BottomHitBox;

    // Here are the y and x coordinate speeds
    protected int yspeed = 5;
    protected int xspeedBase;
    protected int xspeed = 7;

    public int PlayerDirection = 2;
    private boolean HittingBottom = false;
    protected Boolean jumping = false;
    public double GravitySpeed = 0;
    double JumpSpeed = 0;
    int JumpTickCounter;
    public Boolean hittingGround = false;
    public Boolean hittingLeft = false;
    public Boolean hittingRight = false;

    public Boolean hittingGroundCurrentBlock = false;
    public boolean CollisionTop = false;
    public boolean DropDown = false;
    protected int animationCounter;
    public int x;
    public int y;
    protected boolean up;
    protected boolean right;
    protected boolean down;
    protected boolean left;
    ImageIcon sprite;
    ImageObserver observer;


    public Character_Base(int StartX, int StartY)
    {
        x = StartX;
        y = StartY;
        bullets = new ArrayList<Bullet>();
        player = true;
    }

    public void LoadImage(int animation)
    {
        if(animation == 1)
        {
            sprite = new ImageIcon("Resources\\SHIBA2.png");
            sprite.setImageObserver(observer);
            character = sprite.getImage();
        }
        if(animation == 2)
        {
            sprite = new ImageIcon("Resources\\SHIBA1.png");
            sprite.setImageObserver(observer);
            character = sprite.getImage();
        }
        if(animation == 3)
        {
            sprite = new ImageIcon("Resources\\SHIBA3.png");
            sprite.setImageObserver(observer);
            character = sprite.getImage();
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
        return character.getHeight(this);
    }

    public int GetWidth()
    {
        return character.getWidth(this);
    }

    public Image GetImage()
    {
        return character;
    }

    public Rectangle getFullBounds()
    {
        Rectangle rect;

        return rect = new Rectangle(GetX(), GetY(), GetWidth(), GetHeight());
    }

    public Rectangle getBounds(int nmb)
    {
        switch(nmb)
        {
            //left
            case 1:
                rect.setBounds(GetX() - 14, GetY() + 14, 20, GetHeight() - 28);
                return rect;
            case 2:
                rect.setBounds(GetX() - 14, GetY() + 14, 20, GetHeight() - 28);
                return rect;
            //top
            case 3:
                rect.setBounds(GetX() + 30, GetY(), 20, 20);
                return rect;
            case 4:
                rect.setBounds(GetX() + GetWidth() - 30, GetY(), 20, 20);
                return rect;
            // right
            case 5:
                rect.setBounds(GetX() + GetWidth() - 7, GetY() + 14, 20, GetHeight() - 28);
                return rect;
            case 6:
                rect.setBounds(GetX() + GetWidth() - 7, GetY() + 14, 20, GetHeight() - 28);
                return rect;
            // bottom
            case 7:
                rect.setBounds(GetX() + 30, GetY() + GetHeight() - 20, 20, 20);
                return rect;
            case 8:
                rect.setBounds(GetX() + GetWidth() - 30, GetY() + GetHeight() - 20, 20, 20);
                return rect;

        }
        if(PlayerDirection == 2)
        {
            rect.setBounds(GetX(), GetY(), GetWidth() - 10, GetHeight());
            return rect;
        }
        else
        {
            rect.setBounds(GetX(), GetY(), GetWidth() - 10, GetHeight());
            return rect;
        }
    }

    public void UpdateSprite()
    {
        int animation;

        if(up)
        {
            if(!hittingGround)
            {
                Jump();
            }
            animationCounter++;
        }
        if(down)
        {
            DropDown = true;
        }
        if(left)
        {
            if(!hittingLeft)
            {
                PlayerDirection = 1;
                xspeed = xspeedBase;
                x -= xspeed;
                animationCounter++;
            }
        }
        if(right)
        {
            if(!hittingRight)
            {
                PlayerDirection = 2;
                xspeed = xspeedBase;
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
        }

        if(key == KeyEvent.VK_S)
        {
            down = false;
            DropDown = false;
        }
        if(key == KeyEvent.VK_D)
        {
            right = false;
        }
    }

    public void HittingGround(int objecty)
    {
        y = objecty - character.getHeight(observer);
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
            if(JumpTickCounter >= 3)
            {
                JumpTickCounter = 0;
                JumpSpeed -= 1;
            }
        }
    }

    public void Gravity()
    {
        if(!hittingGround)
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

    public void CollisionCheck(ArrayList<Object> objects)
    {
        hittingGround = false;
        hittingRight = false;
        hittingLeft = false;


        for (Object object : objects) {
            for(int i = 0; i < 8 ; i++) {
                playerRect = getBounds(i + 1);
                objectRect = object.getBounds();

                if (playerRect.intersects(objectRect)) {

                    if((i == 0 || i == 1) && object.CanHitSides)
                    {
                        StopLeft();
                    }
                    if((i == 2 || i == 3) && object.CanHitTop)
                    {
                        HittingTop();
                    }
                    if((i == 4 || i == 5) && object.CanHitSides)
                    {
                        StopRight();
                    }
                    if((i == 6 || i == 7) && ((playerRect.getY()) < (objectRect.getY() + objectRect.getHeight() / 2 - 20)) && (!DropDown && object.CanDropThrough || !object.CanDropThrough))
                    {
                        HittingGround(object.y);
                        hittingGroundCurrentBlock = true;

                    }
                    else
                        hittingGroundCurrentBlock = false;
                }
            }
            for (Bullet bullet: bullets)
            {
                playerRect = getFullBounds();
                Circle bulletCircle = bullet.GetBounds();
                HittingBottom = false;
                boolean hittingLeft = false;
                boolean hittingRight = false;
                boolean hittingTop = false;

                TopHitBox = new Rectangle.Double(objectRect.getX(), objectRect.getY() - 30, objectRect.getWidth(), (objectRect.getHeight()));

                LeftHitBox = new Rectangle.Double(objectRect.getX() - 30, objectRect.getY(), objectRect.getWidth() / 2, objectRect.getHeight());

                BottomHitBox = new Rectangle.Double(objectRect.getX(), objectRect.getY() + objectRect.getHeight() / 2, objectRect.getWidth(), objectRect.getHeight() / 2 + 30);

                RightHitBox = new Rectangle.Double(objectRect.getX() + objectRect.getWidth() / 2, objectRect.getY(), objectRect.getWidth()/2 + 30, objectRect.getHeight());


                double circleDistanceX;
                double circleDistanceY;
                    circleDistanceX = (bulletCircle.getCenterX() - (objectRect.getCenterX()));
                    circleDistanceY = (bulletCircle.getCenterY() - (objectRect.getCenterY()));
                    if(circleDistanceX < 0)
                        circleDistanceX *= -1;
                    if(circleDistanceY <0)
                        circleDistanceY *= -1;

                    boolean intersects;
                    if (circleDistanceX > (objectRect.getWidth()/2 + bulletCircle.getRadius()))
                    {
                        intersects = false;
                    }

                    if (circleDistanceY > (objectRect.getHeight()/2 + bulletCircle.getRadius()))
                    {
                        intersects = false;
                    }
                    if ((bulletCircle.getCenterX() >= TopHitBox.getX()) && (bulletCircle.getCenterX() <= (TopHitBox.getX() + TopHitBox.getWidth())) && (bulletCircle.getCenterY() - bulletCircle.getRadius() >= TopHitBox.getY()) && (bulletCircle.getCenterY() - bulletCircle.getRadius() <= (TopHitBox.getY() + TopHitBox.getHeight())) && object.CanHitSides)
                    {
                        HittingBottom = true;
                        bullet.Collision(1, objectRect.getCenterY(), objectRect.getCenterX(), objectRect.getWidth(), objectRect.getHeight());
                    }
                    if ((bulletCircle.getCenterX() >= BottomHitBox.getX()) && (bulletCircle.getCenterX() <= (BottomHitBox.getX() + BottomHitBox.getWidth())) && (bulletCircle.getCenterY() + bulletCircle.getRadius() >= BottomHitBox.getY()) && (bulletCircle.getCenterY() + bulletCircle.getRadius() <= (BottomHitBox.getY() + BottomHitBox.getHeight())) && object.CanHitSides) {
                        bullet.Collision(2, objectRect.getCenterY(), objectRect.getCenterX(), objectRect.getWidth(), objectRect.getHeight());
                        hittingTop = true;
                    }

                    if((bulletCircle.getCenterX() - bulletCircle.getRadius() >= LeftHitBox.getX()) && (bulletCircle.getCenterX() - bulletCircle.getRadius() <= (LeftHitBox.getX() + LeftHitBox.getWidth())) && (bulletCircle.getCenterY() >= LeftHitBox.getY()) && (bulletCircle.getCenterY() <= (LeftHitBox.getY() + LeftHitBox.getHeight())) && object.CanHitSides) {
                        bullet.Collision(3, objectRect.getCenterY(), objectRect.getCenterX(), objectRect.getWidth(), objectRect.getHeight());
                        hittingLeft = true;
                    }

                    if((bulletCircle.getCenterX() + bulletCircle.getRadius() >= RightHitBox.getX()) && (bulletCircle.getCenterX() + bulletCircle.getRadius() <= (RightHitBox.getX() + RightHitBox.getWidth())) && (bulletCircle.getCenterY() >= RightHitBox.getY()) && (bulletCircle.getCenterY() <= (RightHitBox.getY() + RightHitBox.getHeight())) && object.CanHitSides) {
                        bullet.Collision(4, objectRect.getCenterY(), objectRect.getCenterX(), objectRect.getWidth(), objectRect.getHeight());
                        hittingRight = true;
                    }

                TopHitBox = null;
                BottomHitBox = null;
                LeftHitBox = null;
                RightHitBox = null;
            }
        }
    }
}
