/**
 * part of the listeners package.
 */
package listeners;

import sprites.Ball;
import sprites.Block;

/**
 * all the Objects that want to be notified of hit events, will implement this interface.
 */
public interface HitListener {
    /**
     * This method is called whenever the "beingHit" object is hit.
     *
     * @param beingHit : the block that is being hit.
     * @param hitter   : the sprites.Ball that's doing the hitting.
     */
    void hitEvent(Block beingHit, Ball hitter);
}