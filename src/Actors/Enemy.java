package Actors;

import Components.*;
import Core.GameEngine;
import Util.Position2D;

import java.awt.*;
import java.util.ArrayList;


public class Enemy extends AbstractActor
{
    // TODO:
    AbstractPatrolStrategy abstractPatrolStrategy;
    public enum  enemyType{
        VERTICAL,
        HORIZONTAL,
        STATIONARY
    }

    public enemyType T;
    public Enemy(Position2D<Float> pos, float sizeX, float sizeY, Position2D<Float> center, GameEngine gm, enemyType T){
        super(pos, sizeX, sizeY, "./data/img/enemy.png",gm,50);
        this.T=T;
        this.toggle=false;
        if (T==enemyType.VERTICAL){
            this.abstractPatrolStrategy=new VerticalPatrolStrategy(-1);
        }
        else if (T==enemyType.HORIZONTAL){
            this.abstractPatrolStrategy=new HorizontalPatrolStrategy(-1);
        }
        else{
            this.abstractPatrolStrategy=new NoPatrolStrategy(0);
        }
    }
    @Override
    public boolean isDead()
    {
        return this.death;
    }
    @Override
    public void update(float deltaT, Graphics2D g)
    {
        // TODO: or delete
        this.abstractPatrolStrategy.update(this,deltaT);
        super.update(deltaT,g);
    }

    public void aCollisionIsHappened(int res, AbstractActor mainActor){
        mainActor.CollisionHandler(this);
    }

    public void CollisionHandler(AbstractActor hittedActor){
        hittedActor.setDeathness(true);
    }
}
