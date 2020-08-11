package sprites;

import geometry.Point;
import geometry.Rectangle;
import geometry.Velocity;

/**
 * The sprites.Collidable interface.
 * Things that objects can collide with.
 *
 * @author : mohammed Elesawi.
 */
public interface Collidable {
    /**
     * @return returning the shape of the collidable object.
     */
    Rectangle getCollisionRectangle();

    /**
     * letting the object know that we collided with it at a certain collisionPoint with a certain velocity.
     *
     * @param collisionPoint  : the point where the collision occurs.
     * @param currentVelocity : the object's current velocity.
     * @param hitter          : the ball that is currently colliding.
     * @return : the updated velocity after collision depending on which part of the collidable shape the collision had
     * happened.
     */

    Velocity hit(Ball hitter, Point collisionPoint, Velocity currentVelocity);
}