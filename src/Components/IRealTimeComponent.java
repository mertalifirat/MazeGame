package Components;

import Actors.AbstractActor;

import java.awt.*;

// On a real-time system all components may want to update wrt.
// time change
public interface IRealTimeComponent
{
    public void update(float deltaT);
    public void update(AbstractActor actor, float deltaT);

}
