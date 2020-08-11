package sprites;

import biuoop.DrawSurface;

/**
 * The sprites.Sprite interface.
 * The sprites can be drawn on the screen, and can be notified that time has passed using the "timePassed" function.
 *
 * @author : mohammed Elesawi.
 */
public interface Sprite {
    /**
     * drawing the sprite on the screen.
     *
     * @param d : the surface to draw the sprite on
     */
    void drawOn(DrawSurface d);

    /**
     * notifying the sprite that time has passed .
     */
    void timePassed();
}