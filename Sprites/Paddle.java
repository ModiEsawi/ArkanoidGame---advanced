/**
 * a part of the sprites package.
 */

package sprites;

import game.Game;
import geometry.Line;
import geometry.Point;
import geometry.Rectangle;
import geometry.Velocity;
import biuoop.DrawSurface;
import biuoop.KeyboardSensor;

import java.awt.Color;
import java.util.Random;

/**
 * sprites.Paddle class.
 * A paddle is a rectangle that is controlled by the arrow keys and moves according to the user keyboard presses.
 *
 * @author : mohammed Elesawi.
 */

public class Paddle implements Sprite, Collidable {
    private Rectangle shape;
    private java.awt.Color color;
    private biuoop.KeyboardSensor keyboard;
    private double movement;
    private double rightBoundrey, leftBoundrey;

    /**
     * Constructor.
     * creating a new sprites.Paddle with a rectangular shape , Keyboard sensor and movement.
     *
     * @param shape         : the paddle's shape.
     * @param color         : the paddle's color.
     * @param keyboard      : the Keyboard Sensor.
     * @param movement      : the paddle's movement.
     * @param leftBoundrey  : the paddles left boundary.
     * @param rightBoundrey : the paddles right boundary.
     */

    public Paddle(Rectangle shape, java.awt.Color color, biuoop.KeyboardSensor keyboard, double movement,
                  double leftBoundrey, double rightBoundrey) {
        this.shape = shape;
        this.color = color;
        this.keyboard = keyboard;
        this.movement = movement;
        this.rightBoundrey = rightBoundrey - leftBoundrey;
        this.leftBoundrey = leftBoundrey;
    }

    /**
     * setting the new upper left point oft he paddle.
     *
     * @param newUpperLeft : the updated upper left point of the paddle.
     */

    public void setUpperLeft(Point newUpperLeft) {
        this.shape = new Rectangle(newUpperLeft, this.shape.getWidth(), this.shape.getHeight());
    }

    /**
     * moving the paddle to the left according to the setted movement.
     */


    public void moveLeft() {
        Point upperLeftAfterChange = new Point(this.shape.getUpperLeft().getX() - movement,
                this.shape.getUpperLeft().getY());
        // if we have passed the left boundary.
        if (upperLeftAfterChange.getX() < this.leftBoundrey) {
            upperLeftAfterChange.setX(leftBoundrey);
        }
        // else we will set the paddle after movement change.
        this.shape = new Rectangle(upperLeftAfterChange, this.shape.getWidth(), this.shape.getHeight());
    }

    /**
     * moving the paddle to the right according to the setted movement.
     */

    public void moveRight() {
        Point upperLeftAfterChange = new Point(this.shape.getUpperLeft().getX() + movement,
                this.shape.getUpperLeft().getY());
        // if we have passed the right boundary.
        if (upperLeftAfterChange.getX() + this.shape.getWidth() > rightBoundrey) {
            upperLeftAfterChange.setX(rightBoundrey - this.shape.getWidth());
        }
        // else we will set the paddle after movement change.
        this.shape = new Rectangle(upperLeftAfterChange, this.shape.getWidth(), this.shape.getHeight());
    }

    /**
     * moving the paddle to the left or to the right according to the key presses using the keyboard sensor.
     */

    public void timePassed() {

        if (keyboard.isPressed(KeyboardSensor.RIGHT_KEY)) {
            moveRight();
        }

        if (keyboard.isPressed(KeyboardSensor.LEFT_KEY)) {
            moveLeft();
        }
    }

    /**
     * drawing the paddle on a given DrawSurface.
     *
     * @param d : the surface to the paddle on
     */

    public void drawOn(DrawSurface d) {
        d.setColor(this.color);

        d.fillRectangle((int) this.shape.getUpperLeft().getX(), (int) this.shape.getUpperLeft().getY(),
                (int) this.shape.getWidth(), (int) this.shape.getHeight());

        d.setColor(Color.BLACK);

        d.drawRectangle((int) this.shape.getUpperLeft().getX(), (int) this.shape.getUpperLeft().getY(),
                (int) this.shape.getWidth(), (int) this.shape.getHeight());
    }

    /**
     * @return returning the paddles shape.
     */

    public Rectangle getCollisionRectangle() {
        return this.shape;
    }

    /**
     * Notify the paddle that we collided with it at a certain collisionPoint with a given velocity. if collided with
     * it from below , then we will turn the vertical direction , if collided with it from left or right , then we will
     * turn the horizontal direction, we divide the upper line of the paddle to five equal parts , if collided with
     * the first part we will bounce back with an angle of -60 degrees. if collided with the second part we will bounce
     * back with an angle of -30 degrees. if collided with the third part we will turn the vertical direction. if
     * collided with the fourth part we will bounce back with an angle of 30 degrees, if collided with the fifth part
     * we will bounce back with an angle of 60 degrees.
     *
     * @param collisionPoint  the point where the collision occurs..
     * @param currentVelocity the objects current velocity.
     * @param hitter          : the ball that is currently hitting the paddle.
     * @return the new velocity depending on where the object has hit the paddle.
     */

    public Velocity hit(Ball hitter, Point collisionPoint, Velocity currentVelocity) {
        // getting the paddle's lengths , and dividing the top line to five equal parts.
        Line upLine = this.shape.getUpLine();
        Line lowLine = this.shape.getLowLine();
        Line rightLine = this.shape.getRightLine();
        Line leftLine = this.shape.getLeftLine();
        Random random = new Random();
        int radnomColorBound = 0xFFFFFF, currentPart = 0;
        double eachPartLength = upLine.length() / 5, edgyAngle = 0.2;
        Velocity updatedVelocity = null;
        double ballSpeed = Math.sqrt(currentVelocity.getdx() * currentVelocity.getdx()
                + currentVelocity.getdY() * currentVelocity.getdY());
        Line[] upLineParts = new Line[5];


        double upDatedDx = currentVelocity.getdx(), updatedDy = currentVelocity.getdY();
        if (upLine.ifPointOnLine(collisionPoint)) {
            //initializing the parts , all parts are in equal length .
            while (currentPart < (upLineParts.length)) {
                Line line = new Line(upLine.start().getX() + (currentPart * eachPartLength), upLine.start().getY(),
                        upLine.start().getX() + ((currentPart + 1) * eachPartLength), upLine.start().getY());
                upLineParts[currentPart] = line;
                currentPart++;
            }

            /* returning a new velocity according to where the object hit the paddle if we hit the edges ,
            (most left or most right) we will increase the balls speed a little bit to make it look
            more real , we will also change the balls and paddles color each time a collision is made*/

            if (upLineParts[0].ifPointOnLine(collisionPoint)) {
                updatedVelocity = Velocity.fromAngleAndSpeed(300, ballSpeed + edgyAngle);
                hitter.setColor(new Color(random.nextInt(radnomColorBound)));
                this.color = new Color(random.nextInt(radnomColorBound));
            } else if (upLineParts[1].ifPointOnLine(collisionPoint)) {
                updatedVelocity = Velocity.fromAngleAndSpeed(330, ballSpeed);
                hitter.setColor(new Color(random.nextInt(radnomColorBound)));
                this.color = new Color(random.nextInt(radnomColorBound));
            } else if (upLineParts[2].ifPointOnLine(collisionPoint)) {
                updatedVelocity = new Velocity(currentVelocity.getdx(), -(currentVelocity.getdY()));
                hitter.setColor(new Color(random.nextInt(radnomColorBound)));
                this.color = new Color(random.nextInt(radnomColorBound));
            } else if (upLineParts[3].ifPointOnLine(collisionPoint)) {
                updatedVelocity = Velocity.fromAngleAndSpeed(30, ballSpeed);
                hitter.setColor(new Color(random.nextInt(radnomColorBound)));
                this.color = new Color(random.nextInt(radnomColorBound));
            } else if (upLineParts[4].ifPointOnLine(collisionPoint)) {
                updatedVelocity = Velocity.fromAngleAndSpeed(60, ballSpeed + edgyAngle);
                hitter.setColor(new Color(random.nextInt(radnomColorBound)));
                this.color = new Color(random.nextInt(radnomColorBound));
            }
            //if the object hits the paddle from below , we will turn the vertical direction.
        } else if (leftLine.ifPointOnLine(collisionPoint) || rightLine.ifPointOnLine(collisionPoint)) {
            this.color = new Color(random.nextInt(radnomColorBound));
            upDatedDx = -1 * (currentVelocity.getdx());
            updatedVelocity = new Velocity(upDatedDx, updatedDy);
            //if the object hit the paddle from right or left , we will turn the horizontal direction.
        } else if (lowLine.ifPointOnLine(collisionPoint)) {
            this.color = new Color(random.nextInt(radnomColorBound));
            updatedDy = -1 * (currentVelocity.getdY());
            updatedVelocity = new Velocity(upDatedDx, updatedDy);
        }

        return updatedVelocity;
    }

    /**
     * adding the paddle to our game (the paddle is also a sprite and a collidable).
     *
     * @param g : our game.
     */

    public void addToGame(Game g) {
        g.addSprite(this);
        g.addCollidable(this);
    }
}
