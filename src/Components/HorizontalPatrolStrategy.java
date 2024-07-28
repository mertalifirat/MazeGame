package Components;

import Actors.AbstractActor;
import Actors.Enemy;

import java.awt.*;

public class HorizontalPatrolStrategy extends AbstractPatrolStrategy
{

    public HorizontalPatrolStrategy(int plusOrMinus){
        super(plusOrMinus);

    }
    @Override
    public void update(AbstractActor actor, float deltaT)
    {
        // TODO:
        super.update(actor,deltaT);
        actor.getPos().x+=actor.getSpeed()*deltaT*this.plusOrMinus;
    }
}
