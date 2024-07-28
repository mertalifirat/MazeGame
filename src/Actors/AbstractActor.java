package Actors;

import Components.SpriteComponent;
import Core.GameEngine;
import Util.AABB;
import Util.Position2D;
import java.awt.*;
import Components.*;

import java.util.ArrayList;

// Meta Actor Class
// Everything in the game is an actor


public abstract class AbstractActor extends AABB  implements IRealTimeComponent,ICollisionListener
{
    protected SpriteComponent spriteComponent;
    protected float speed;
    public static GameEngine gameEngine;
    protected String spritePath;
    protected int health;
    protected boolean death;
    public boolean toggle;
    protected ArrayList<IRealTimeComponent> iRealTimeComponents;

    public AbstractActor(Position2D<Float> pos, float szX, float szY, String spritePath, GameEngine gameEngine, float speed){
        super(pos, szX, szY);
        AbstractActor.gameEngine =gameEngine;
        this.speed=speed;
        this.spritePath=spritePath;
        this.spriteComponent=new SpriteComponent(this.spritePath);
        this.iRealTimeComponents=new ArrayList<IRealTimeComponent>();
        this.health=120;
        this.toggle=false;
    }
    public void adjustPosX(float delta){
        this.getPos().x+=delta;
    }
    public void adjustPosY(float delta){
        this.getPos().y+=delta;
    }
    public GameEngine getGameEngine(){
        return AbstractActor.gameEngine;
    }

    /**
     * Updates of the Decorators that has been added up to AbstractActor will be called in this update function
     * by iteration.
     * @param deltaT
     * @param g
     */
    public void update(float deltaT, Graphics2D g)
    {
        // TODO:
        for (int i=0; i<this.iRealTimeComponents.size(); i++) {
            this.iRealTimeComponents.get(i).update(this, deltaT);
        }
        spriteComponent.draw(g, this);

    }
    public abstract boolean isDead();
    public void update(float deltaT) {

    }
    public void update(AbstractActor actor, float deltaT){

    }

    /**
     * Adding components for Decorator Pattern
     * @param iRealTimeComponents
     */
    public void addComponents(ArrayList<IRealTimeComponent> iRealTimeComponents){
        this.iRealTimeComponents=iRealTimeComponents;
    }


    public float getSpeed(){
        return this.speed;
    }
    /**
     * Collision handler function implemented for the same hittedActor with different actions.
     * One can emphasize the bullet-enemy and wall-enemy hit occasion.
     * It is a helper for these cases.
     * @param hittedActor
     */
    public abstract void CollisionHandler(AbstractActor hittedActor);
    public void setDeathness(boolean flag){
        this.death=flag;
    }

    /**
     *  It will be called if a hit occasion happens from our publishers (the main notify function)
     * @param res
     * @param mainActor
     */
    public abstract void aCollisionIsHappened(int res, AbstractActor mainActor);
}

