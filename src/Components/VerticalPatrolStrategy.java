package Components;

import Actors.AbstractActor;
import Actors.Enemy;

public class VerticalPatrolStrategy extends AbstractPatrolStrategy
{
    // TODO:
    public VerticalPatrolStrategy(int plusOrMinus){
        super(plusOrMinus);
    }
    @Override
    public void update(AbstractActor actor, float deltaT)
    {
        // TODO:
        super.update(actor,deltaT);
        actor.getPos().y+=actor.getSpeed()*deltaT*this.plusOrMinus;
    }
}
