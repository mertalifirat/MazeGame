package Components;
import Actors.*;
import Core.GameEngine;
import Core.GameWindow;
import Util.AABB;

import java.awt.*;
import java.util.ArrayList;

public class  CollisionComponent <T extends  AbstractActor> extends Decorators
{
    // TODO:

    protected boolean HitHappened;
    protected ArrayList<ICollisionListener> subscribers;
    public CollisionComponent(){
        super();
        this.subscribers=new ArrayList<ICollisionListener>();

    }

    /**
     * registers the subscribers to that component
     * @param subscribers
     *
     */
    public void registerSubscriber(ArrayList<ICollisionListener> subscribers){

        this.subscribers.addAll(subscribers);
    }

    /**
     * It is the notify function for the observers that has been added up
     * @param mainActor
     * @param res
     * @param hitted
     */
    public void notifySubscribers(AbstractActor mainActor,int res, AbstractActor hitted){
        hitted.aCollisionIsHappened(res, mainActor);
        if (hitted.isDead() && mainActor.isDead()){
            this.subscribers.remove(res);
        }
    }

    /**
     * generic hit checker function runs the collide function
     * @param actor
     * @param actorArrayList
     * @return
     */
    public int HitChecker(AbstractActor actor,ArrayList<ICollisionListener> actorArrayList){
        this.HitHappened=false;
        for (int i=0; i<actorArrayList.size(); i++){
            AbstractActor hittedActor = (AbstractActor) actorArrayList.get(i);
            if(actor.collides(hittedActor)){
                this.HitHappened=true;
                return i;
            }
        }
        return -1;
    }
    @Override
    public void update(float deltaT) {
        this.HitHappened=false;
    }

    /**
     * Handles the hitChecker and notifySubsribers function
     * @param actor
     * @param deltaT
     */
    @Override
    public void update(AbstractActor actor, float deltaT) {
        int res=HitChecker(actor,this.subscribers);
        if(res!=-1){
            notifySubscribers(actor,res,(AbstractActor) this.subscribers.get(res));
        }

    }

}
