/**
 * a part of the sprites package.
 */

package sprites;

import biuoop.DrawSurface;

import java.awt.Color;

/**
 * A sprite that sits at the top of the screen and indicates the number of current lives left.
 */
public class LivesIndicator implements Sprite {

    private Counter counter;

    /**
     * the constructor.
     *
     * @param counter : counter of lives.
     */
    public LivesIndicator(Counter counter) {
        this.counter = counter;
    }

    /**
     * draws the livesCounter at the top of the screen.
     * @param d : the surface to draw the sprite on
     */
    @Override
    public void drawOn(DrawSurface d) {
        d.setColor(Color.RED);
        d.drawText(130, 20, "Lives:" + this.counter.getValue(), 17);
    }

    /**
     * notifying the sprite that time has passed .
     */
    @Override
    public void timePassed() {

    }
}
