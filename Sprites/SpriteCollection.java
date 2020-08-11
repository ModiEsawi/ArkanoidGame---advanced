package sprites;

import biuoop.DrawSurface;

import java.util.ArrayList;
import java.util.List;

/**
 * A sprites.SpriteCollection class.
 * holds the collection of sprites.
 *
 * @author : mohammed Elesawi.
 */

public class SpriteCollection {
    private List<Sprite> spriteCollection;

    /**
     * Constructor.
     * creating the sprites list.
     */
    public SpriteCollection() {

        this.spriteCollection = new ArrayList<>();

    }

    /**
     * Adding a new sprite to the sprites collection.
     *
     * @param s : a new sprite that needs to be added to the sprites collection.
     */
    public void addSprite(Sprite s) {
        this.spriteCollection.add(s);
    }

    /**
     * removing a sprite from the sprites collection.
     *
     * @param s : the sprite that needs to be removed to the sprites collection.
     */

    public void removeSprite(Sprite s) {
        this.spriteCollection.remove(s);
    }

    /**
     * Calling the  "timePassed" function on all sprites , so each sprite activates its own functions.
     */
    public void notifyAllTimePassed() {
        for (int i = 0; i < spriteCollection.size(); i++) {
            Sprite spriteObject = spriteCollection.get(i);
            spriteObject.timePassed();
        }
    }

    /**
     * calling the "drawOn" function on all the sprites in the sprite collection.
     *
     * @param d : the surface to draw all the sprites on.
     */
    public void drawAllOn(DrawSurface d) {
        for (int j = 0; j < spriteCollection.size(); j++) {
            Sprite spriteObject = spriteCollection.get(j);
            spriteObject.drawOn(d);
        }
    }


}