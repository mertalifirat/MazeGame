package Actors;

import Components.IRealTimeComponent;
import Components.SpriteComponent;
import Core.GameEngine;
import Util.AABB;
import Util.Position2D;

import java.awt.*;
import java.io.IOError;
import java.io.IOException;
import java.util.ArrayList;

public class Wall extends AbstractActor
{
    public Wall(Position2D<Float> pos, float sizeX, float sizeY, Position2D<Float> center, GameEngine gm){
        super(pos, sizeX, sizeY, "./data/img/wall.png",gm,0);
    }

    @Override
    public void update(float deltaT, Graphics2D g)  {
        super.update(deltaT,g);
    }

    @Override
    public boolean isDead()
    {
        return false;
    }
    public void aCollisionIsHappened(int res, AbstractActor mainActor){

    }
    public void CollisionHandler(AbstractActor hittedActor){
        hittedActor.toggle=true;
        hittedActor.moveIfCollide(this);
    }
}
