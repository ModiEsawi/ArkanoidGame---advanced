package game;

import geometry.Line;
import geometry.Point;
import geometry.Rectangle;
import sprites.Collidable;
import sprites.CollisionInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * the game.GameEnvironment class.
 * where all the objects a ball can collide with are stored.
 *
 * @author : mohammed Elesawi.
 */

public class GameEnvironment {

    private List<Collidable> collidables;

    /**
     * initializing the list of collidable objects.
     */

    public GameEnvironment() {
        this.collidables = new ArrayList<Collidable>();
    }

    /**
     * Adding a new collidable to the sprites collection.
     *
     * @param c : a new collidable that needs to be added to the collidables collection.
     */
    public void addCollidable(Collidable c) {
        this.collidables.add(c);
    }
    /**
     * removing a collidable from the sprites collection.
     *
     * @param c : the collidable that needs to be removed from the collidables collection.
     */
    public void removeCollidable(Collidable c) {
        this.collidables.remove(c);
    }


    /**
     * if an the ball is moving in a certain line from point A to point B , it may collide with some objects, if it
     * doesnt, then the list is empty and we return null, else we will return all the information about the closest
     * collision that is about to occur.
     *
     * @param trajectory : the path of the ball.
     * @return the closest collision that is about to occur between the ball and the an object.
     */


    public CollisionInfo getClosestCollision(Line trajectory) {

        Point closestPoint = null;
        Collidable collidingObject = null;
        Point[] collisonPointsArray = new Point[collidables.size()];
        double baseDistance = 0;
        int firstPointCounter;

        //getting the closest intersection point to the start of trajectory foreach collidable object.

        for (int i = 0; i < collidables.size(); i++) {
            Collidable nowColliding = (Collidable) collidables.get(i);
            Rectangle shapeForm = nowColliding.getCollisionRectangle();
            collisonPointsArray[i] = trajectory.closestIntersectionToStartOfLine(shapeForm);
        }

        // getting the closest point to the start of the trajectory line.

        for (firstPointCounter = 0; firstPointCounter < collidables.size(); firstPointCounter++) {
            if (collisonPointsArray[firstPointCounter] != null) {
                // getting a temporary closest point to the start of the line using the "distance" function.
                closestPoint = collisonPointsArray[firstPointCounter];
                baseDistance = trajectory.start().distance(closestPoint);
                collidingObject = (Collidable) collidables.get(firstPointCounter);
                break;
            }
        }

        for (int i = firstPointCounter + 1; i < collidables.size(); i++) {
            // if here is no point , well continue
            if (collisonPointsArray[i] == null) {
                continue;
            }

            // comparing the points to get the closest one using the "distance" function.

            double distanceToCompare = trajectory.start().distance(collisonPointsArray[i]);
            if (distanceToCompare < baseDistance) {
                closestPoint = collisonPointsArray[i];
                collidingObject = (Collidable) collidables.get(i);
                baseDistance = distanceToCompare;
            }
        }
        //there is not any intersection points.
        if (closestPoint == null) {
            return null;
        }
        // else we will return the closest point and colliding object.
        return new CollisionInfo(closestPoint, collidingObject);
    }

}