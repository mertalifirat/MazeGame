package Components;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.Graphics2D;

import Actors.AbstractActor;
import Actors.Bullet;
import Actors.Player;
import Core.GameWindow;
import Util.Position2D;

public class PlayerInputComponent extends Decorators implements KeyListener
{
    // Internal States

    private boolean leftPressed;
    private Bullet.bulletDirection lastPressed;
    private boolean rightPressed;
    private boolean upPressed;
    private boolean downPressed;
    private boolean firePressed;
    // TODO: Add additional properties if required

    // TODO: Implement a constructor
    public PlayerInputComponent()
    {
        super();
        GameWindow.GetInstance().attachKeyListener(this);

    }

    @Override
    public void update(float deltaT)
    {
        // TODO:
    }

    /**
     * Player will gain the movement.
     * If the firePressed bullet is initialized and added to bullets in the gameEngine
     * @param actor
     * @param deltaT
     */
    @Override
    public void update(AbstractActor actor, float deltaT){
        if (this.rightPressed){
            this.lastPressed=Bullet.bulletDirection.RIGHT;
            actor.adjustPosX(actor.getSpeed()*deltaT);
        }
        else if (this.leftPressed){
            this.lastPressed=Bullet.bulletDirection.LEFT;
            actor.adjustPosX(actor.getSpeed()*deltaT*-1);
        }

        else if (this.downPressed){
            this.lastPressed=Bullet.bulletDirection.DOWN;
            actor.adjustPosY(actor.getSpeed()*deltaT);
        }

        else if (this.upPressed){
            this.lastPressed=Bullet.bulletDirection.UP;
            actor.adjustPosY(actor.getSpeed()*deltaT*-1);
        }
        if  (firePressed){
            Position2D pos=new Position2D<Float>(actor.getPos().x, actor.getPos().y);
            Bullet bullet=new Bullet(pos,10,10,actor.getGameEngine(),this.lastPressed);
            bullet.addComponents(actor.getGameEngine().getBulletComponents());
            actor.getGameEngine().getBullets().add(bullet);
            this.firePressed=false;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) { /* Do nothing */ }

    @Override
    public void keyPressed(KeyEvent e)
    {
        if(e.getKeyCode() == KeyEvent.VK_LEFT) leftPressed = true;
        if(e.getKeyCode() == KeyEvent.VK_RIGHT) rightPressed = true;
        if(e.getKeyCode() == KeyEvent.VK_UP) upPressed = true;
        if(e.getKeyCode() == KeyEvent.VK_DOWN) downPressed = true;
        // TODO: You can also change this code if you want to handle inputs differently
        // this is given as a guideline to read key events
    }

    @Override
    public void keyReleased(KeyEvent e)
    {
        if(e.getKeyCode() == KeyEvent.VK_LEFT) leftPressed = false;
        if(e.getKeyCode() == KeyEvent.VK_RIGHT) rightPressed = false;
        if(e.getKeyCode() == KeyEvent.VK_UP) upPressed = false;
        if(e.getKeyCode() == KeyEvent.VK_DOWN) downPressed = false;
        // Enforce release operation on fire
        if(e.getKeyCode() == KeyEvent.VK_SPACE) firePressed = true;
        // TODO: You can also change this code if you want to handle inputs differently
        // this is given as a guideline to read key events
    }


}
