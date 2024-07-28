package Actors;

import Components.*;
import Core.GameEngine;
import Util.AABB;
import Util.Position2D;

import java.awt.*;
import java.util.ArrayList;

public class Player extends AbstractActor implements ICollisionListener
{

    // TODO:
    public Player(Position2D<Float> pos, float sizeX, float sizeY, GameEngine gameEngine){
        super(pos,sizeX,sizeY,"./data/img/player.png", gameEngine,100);
        this.death=false;
    }


    @Override
    public void update(float deltaT, Graphics2D g)
    {
        // TODO: or delete
        super.update(deltaT,g);
    }

    @Override
    public boolean isDead()
    {
        return this.death;
    }
    @Override
    public void aCollisionIsHappened(int res, AbstractActor mainActor){
        mainActor.CollisionHandler(this);
    }

    public void CollisionHandler(AbstractActor hittedActor){

    }

}
