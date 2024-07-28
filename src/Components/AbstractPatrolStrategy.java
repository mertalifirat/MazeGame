package Components;
import Actors.AbstractActor;
import Actors.Enemy;

import java.awt.*;

/**
 * This class is implemented with using strategy pattern.
 */
public abstract class AbstractPatrolStrategy extends Decorators
{
    // TODO:

    protected int plusOrMinus;

    public AbstractPatrolStrategy(int plusOrMinus){
        this.plusOrMinus=plusOrMinus;
    }
    @Override
    public void update(float deltaT)
    {
        // TODO:
    }

    /**
     * This function will determine the generic case of the horizontal and vertical actions by changing the toggle status of the enemy
     * @param actor
     * @param deltaT
     */
    @Override
    public void update(AbstractActor actor, float deltaT)
    {
        // TODO:
        if (actor.toggle) {
            actor.toggle = false;
            this.plusOrMinus *= -1;
        }

    }

}
