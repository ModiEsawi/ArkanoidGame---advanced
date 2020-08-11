package sprites;

import game.Game;
import geometry.Line;
import geometry.Point;
import geometry.Rectangle;
import geometry.Velocity;
import listeners.HitListener;
import listeners.HitNotifier;
import biuoop.DrawSurface;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * A sprites.Block object, which has a rectangular shape a color , and a hit counter.
 *
 * @author : mohammed Elesawi.
 */

public class Block implements Collidable, Sprite, HitNotifier {
    private List<HitListener> hitListeners;
    private Rectangle shape;
    private java.awt.Color color;
    private int hitsCounter;

    /**
     * the constructor.
     *
     * @param rectangle   : the block's shape.
     * @param color       : the blocks color.
     * @param hitsCounter : marks the number of hits needed to get an "X" mark.
     */
    public Block(Rectangle rectangle, java.awt.Color color, int hitsCounter) {
        this.shape = rectangle;
        this.color = color;
        this.hitsCounter = hitsCounter;
        this.hitListeners = new ArrayList<HitListener>();
    }

    /**
     * @return returning the blocks shape.
     */
    public Rectangle getCollisionRectangle() {
        return this.shape;
    }

    /**
     * Notify the block that we collided with it at a certain collisionPoint with a given velocity , if collided with
     * it from below or above , then we will turn the vertical direction , if collided with it from left or right ,
     * then we will turn the horizontal direction , and if collided with it from the it's angles , then we will turn
     * the vertical and horizontal directions.
     *
     * @param collisionPoint  : the point where the object has hit the ball.
     * @param currentVelocity : the object's current velocity.
     * @param hitter          : the ball that is currently hitting the block.
     * @return the new velocity depending on where the object has hit the block.
     */
    public Velocity hit(Ball hitter, Point collisionPoint, Velocity currentVelocity) {
        // blocks lines.
        Line upLine = this.shape.getUpLine();
        Line lowLine = this.shape.getLowLine();
        Line leftLine = this.shape.getLeftLine();
        Line rightLine = this.shape.getRightLine();
        int radnomColorBound = 0xFFFFFF;
        Random random = new Random();
        double updatedDx = 0, updatedDy = 0;
        double collisionX = collisionPoint.getX(), collisionY = collisionPoint.getY();

        // if  we have hit the block's angles.

        if (collisionX == leftLine.start().getX() && collisionY == upLine.start().getY()
                || collisionX == leftLine.start().getX() && collisionY == lowLine.start().getY()
                || collisionX == rightLine.start().getX() && collisionY == upLine.start().getY()
                || collisionX == rightLine.start().getX() && collisionY == lowLine.start().getY()) {

            updatedDx = currentVelocity.getdx() * -1;
            updatedDy = currentVelocity.getdY() * -1;
            hitter.setColor(new Color(random.nextInt(radnomColorBound)));

            // if we have hit the block from above or below.
        } else if (collisionY == upLine.start().getY() || collisionY == lowLine.start().getY()) {
            updatedDx = currentVelocity.getdx();
            updatedDy = -1 * (currentVelocity.getdY());
            hitter.setColor(new Color(random.nextInt(radnomColorBound)));
            // if we have hit the block from right or left.
        } else if (collisionX == rightLine.start().getX() || collisionX == leftLine.start().getX()) {
            updatedDx = -1 * (currentVelocity.getdx());
            updatedDy = currentVelocity.getdY();
            hitter.setColor(new Color(random.nextInt(radnomColorBound)));
        }

        //decrease the number on the block when collision occurs.
        if (this.hitsCounter >= 1) {
            this.hitsCounter--;
        }
        this.notifyHit(hitter);

        return new Velocity(updatedDx, updatedDy);

    }

    /**
     * notify the block that time has passed.
     */

    public void timePassed() {
    }

    /**
     * drawing the block on a given DrawSurface.
     *
     * @param surface : the surface to draw the block on.
     */

    public void drawOn(DrawSurface surface) {
        surface.setColor(this.color);
        surface.fillRectangle((int) this.shape.getUpperLeft().getX(), (int) this.shape.getUpperLeft().getY(),
                (int) this.shape.getWidth(), (int) this.shape.getHeight());

        surface.setColor(Color.black);
        surface.drawRectangle((int) this.shape.getUpperLeft().getX(), (int) this.shape.getUpperLeft().getY(),
                (int) this.shape.getWidth(), (int) this.shape.getHeight());
        //drawing the frame


        //drawing the hitsCounter on the middle of the block

        Point mid = this.shape.getLeftLine().middle();
        int y = (int) mid.getY();
        int x = (int) mid.getX() + (int) this.shape.getWidth() / 2;

        if (this.hitsCounter == -1) {
            return;
        }

        // drawing meaningful text on the life and "Bonusdeath" blocks
        if (this.hitsCounter == 0) {
            if (this.shape.getLeftLine().start().getX() == 695) {
                surface.setColor(Color.white);
                surface.drawText(x - 30, y + 5, "New Ball!", 17);
                return;
            } else {
                surface.setColor(Color.white);
                surface.drawText(x - 30, y, "Ball Is ", 15);
                surface.drawText(x - 30, y + 15, "Gone!", 15);
                return;
            }
        }
//        drawing the hitsCounter.
        surface.drawText(x, y, Integer.toString(this.hitsCounter), 17);
    }


    /**
     * adding the block to specified game , (the block is also a collidable and a sprite).
     *
     * @param g : our "Arkanoid" game.
     */

    public void addToGame(Game g) {
        g.addSprite(this);
        g.addCollidable(this);
    }

    /**
     * removing the block from specified game , (the block is also a collidable and a sprite).
     *
     * @param game : our "Arkanoid" game.
     */
    public void removeFromGame(Game game) {
        game.removeCollidable(this);
        game.removeSprite(this);
    }

    /**
     * adding a hitListener the the listeners list.
     *
     * @param hl : the listener the will be added to the listeners list.
     */
    public void addHitListener(HitListener hl) {
        this.hitListeners.add(hl);
    }

    /**
     * removing the hitListener from the listeners list.
     *
     * @param hl : the listener the will be removed from the listeners list.
     */

    public void removeHitListener(HitListener hl) {
        this.hitListeners.remove(hl);
    }

    /**
     * Notifying all listeners about a hit event , (we made a copy of te hitListeners before iterating over them to
     * avoid exception errors.
     *
     * @param hitter : the ball the made the hit.
     */
    private void notifyHit(Ball hitter) {
        // Make a copy of the hitListeners before iterating over them.
        List<HitListener> listeners = new ArrayList<HitListener>(this.hitListeners);
        // Notify all listeners about a hit event:
        for (HitListener hl : listeners) {
            hl.hitEvent(this, hitter);
        }
    }

    /**
     * returns the blocks current hitPoints.
     *
     * @return : the blocks current hitPoints.
     */
    public int getHitPoints() {
        return this.hitsCounter;
    }


}
