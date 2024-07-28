package Components;

import Actors.AbstractActor;

public interface  ICollisionListener
{
    // You can change the interface here (add arguments to the function or
    // change the name etc.)

    public void aCollisionIsHappened(int res, AbstractActor mainActor);
    public void CollisionHandler(AbstractActor hittedActor);

}
