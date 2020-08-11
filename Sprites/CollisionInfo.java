package sprites;

import geometry.Point;

/**
 * The sprites.CollisionInfo class.
 * information about collision.
 *
 * @author : mohammed Elesawi.
 */
public class CollisionInfo {
    private Point collisionPoint;
    private Collidable collidable;

    /**
     * returning the information about the collision such as the collision point and the colliding object.
     *
     * @param collisionPoint : the point where the collision occurs.
     * @param collidable     : the collidable object involved in the collision.
     */

    public CollisionInfo(Point collisionPoint, Collidable collidable) {
        this.collisionPoint = collisionPoint;
        this.collidable = collidable;
    }

    /**
     * @return returning the point where the collision occurs.
     */

    public Point collisionPoint() {
        return this.collisionPoint;
    }

    /**
     * @return returning the collidable object involved in the collision.
     */
    public Collidable collidable() {
        return this.collidable;
    }
}