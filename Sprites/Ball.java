package sprites; /**
 * @author Mohamad Elesawi <esawi442@gmail.com>
 * @since 2019-03-14
 **/

import game.Game;
import geometry.Line;
import geometry.Point;
import geometry.Velocity;
import biuoop.DrawSurface;
import game.GameEnvironment;

import java.awt.Color;

/**
 * class sprites.Ball.
 **/

public class Ball implements Sprite {

    private Point center;
    private int radius;
    private Color color;
    private Velocity velocity;
    private GameEnvironment environment;

    /**
     * Constructor.
     * given the x and y coordinates of the center point of the ball, radius and color.
     *
     * @param x     : ball's center x coordinate point.
     * @param y     : ball's center y coordinate point.
     * @param r     :  ball's radius.
     * @param color : ball's color.
     */
    public Ball(double x, double y, int r, Color color) {
        this.center = new Point(x, y);
        this.radius = r;
        this.color = color;
    }


    /**
     * @return x-coordinate of the ball's center point.
     */
    public double getX() {
        return this.center.getX();
    }

    /**
     * @return y-coordinate of the ball's center point.
     */
    public double getY() {
        return this.center.getY();
    }

    /**
     * setting the ball's game.game environment.
     *
     * @param gameEnvironment : the game environment.
     */

    public void setGameEnvironment(GameEnvironment gameEnvironment) {
        this.environment = gameEnvironment;
    }

    /**
     * @return radius of the ball.
     */

    public int getSize() {
        return this.radius;
    }

    /**
     * @return ball's color.
     */
    public Color getColor() {
        return this.color;
    }

    /**
     * draw the ball on the given DrawSurface.
     *
     * @param surface : draws surface to draw our ball on it.
     */
    public void drawOn(DrawSurface surface) {
        surface.setColor(this.getColor());
        surface.fillCircle((int) this.getX(), (int) this.getY(), this.getSize());
    }

    /**
     * set the balls velocity while the velocity is given .
     *
     * @param v : the velocity.
     */

    public void setVelocity(Velocity v) {
        this.velocity = v;
    }

    /**
     * set the balls velocity while given the change in position of the `x` and the `y` coordinates.
     *
     * @param dx : change in position of the x .
     * @param dy : change in position of the y .
     */
    public void setVelocity(double dx, double dy) {
        this.velocity = new Velocity(dx, dy);
    }

    /**
     * @return ball's velocity.
     */
    public Velocity getVelocity() {
        return this.velocity;
    }


    /**
     * moving the the ball one step according to it's velocity, while taking ball's frame exit into calculation.
     */

    public void moveOneStep() {
        Point p = this.getVelocity().applyToPoint(this.center);
        Line trajectory = new Line(this.center, p);
        CollisionInfo col = this.environment.getClosestCollision(trajectory);
        if (col != null) {
            Point collisionPoint = col.collisionPoint();
            double epsilon = 0.001;

            Line upLine = col.collidable().getCollisionRectangle().getUpLine();
            Line lowLine = col.collidable().getCollisionRectangle().getLowLine();
            Line leftLine = col.collidable().getCollisionRectangle().getLeftLine();
            Line rightLine = col.collidable().getCollisionRectangle().getRightLine();

            if (upLine.ifPointOnLine(collisionPoint)) {
                this.center = new Point(collisionPoint.getX(), collisionPoint.getY() - epsilon - this.radius);
            }
            if (lowLine.ifPointOnLine(collisionPoint)) {
                this.center = new Point(collisionPoint.getX(), collisionPoint.getY() + epsilon + this.radius);
            }
            if (rightLine.ifPointOnLine(collisionPoint)) {
                this.center = new Point(collisionPoint.getX() + epsilon + this.radius, collisionPoint.getY());
            }
            if (leftLine.ifPointOnLine(collisionPoint)) {
                this.center = new Point(collisionPoint.getX() - epsilon - this.radius, collisionPoint.getY());
            }
            //updating the velocity.
            this.velocity = col.collidable().hit(this, collisionPoint, this.velocity);
        } else {
            this.center = this.getVelocity().applyToPoint(this.center);
        }
    }

    /**
     * notifying the ball that time has passed.
     */

    public void timePassed() {
        moveOneStep();
    }

    /**
     * adding the ball to the our game.
     *
     * @param g : our game.
     */

    public void addToGame(Game g) {
        g.addSprite(this);
    }

    /**
     * removing the ball from our game.
     *
     * @param game : our game.
     */
    public void removeFromGame(Game game) {
        game.removeSprite(this);
    }

    /**
     * setting the balls color.
     *
     * @param ballColor : the balls color.
     */
    public void setColor(Color ballColor) {
        this.color = ballColor;
    }
}