package Actors;

import Components.ICollisionListener;
import Components.IRealTimeComponent;
import Core.GameEngine;
import Util.Position2D;

import java.awt.*;
import java.util.ArrayList;

public class PowerUp extends AbstractActor implements ICollisionListener
{
    // TODO:

    public PowerUp(Position2D<Float> pos, float sizeX, float sizeY, GameEngine gm){
        super(pos, sizeX, sizeY, "./data/img/scroll.png",gm,0);
    }
    @Override
    public void update(float deltaT, Graphics2D g)
    {
        // TODO or delete
        super.update(deltaT,g);
    }

    @Override
    public boolean isDead()
    {
        return this.death;
    }

    @Override
    public void aCollisionIsHappened(int res, AbstractActor mainActor){
        this.setDeathness(true);
    }
    public void CollisionHandler(AbstractActor hittedActor){

    }
}
