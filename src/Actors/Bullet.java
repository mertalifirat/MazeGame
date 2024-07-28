package Actors;

import Components.ICollisionListener;
import Components.IRealTimeComponent;
import Core.GameEngine;
import Util.Position2D;

import java.awt.*;
import java.util.ArrayList;
import java.util.Date;

public class Bullet extends AbstractActor
{
    // TODO:
    public static enum  bulletDirection{
        UP,
        DOWN,
        LEFT,
        RIGHT
    }
    protected Date bulletLife;
    protected bulletDirection bd;
    public Bullet(Position2D<Float> pos, float sizeX, float sizeY, GameEngine gm, bulletDirection bd){
        super(pos, sizeX, sizeY, "./data/img/bullet.png",gm,100);
        this.bulletLife=new Date();
        this.bd=bd;
    }
    @Override
    public void update(float deltaT, Graphics2D g)
    {
        if (bd==bulletDirection.UP){
            this.getPos().y-=this.getSpeed()*deltaT;
        }
        if (bd==bulletDirection.DOWN){
            this.getPos().y+=this.getSpeed()*deltaT;
        }
        if (bd==bulletDirection.LEFT){
            this.getPos().x-=this.getSpeed()*deltaT;
        }
        if (bd==bulletDirection.RIGHT){
            this.getPos().x+=this.getSpeed()*deltaT;
        }
        Date now=new Date();
        if (now.getTime()-this.bulletLife.getTime()>=700){
            this.setDeathness(true);
        }
        super.update(deltaT,g);
    }

    @Override
    public boolean isDead()
    {
        return this.death;
    }
    public void aCollisionIsHappened(int res, AbstractActor mainActor){

    }


    public void CollisionHandler(AbstractActor hittedActor){
        hittedActor.health-=40;
        if (hittedActor.health<=0){
            hittedActor.setDeathness(true);
        }
        this.setDeathness(true);
    }
}
