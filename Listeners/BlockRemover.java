/**
 * part of the listeners package.
 */
package listeners;

import game.Game;
import sprites.Ball;
import sprites.Block;
import sprites.Counter;

/**
 * removes a block from the game in case of collision with 0 hitPoints.
 */

public class BlockRemover implements HitListener, HitNotifier {
    private Game game;
    private Counter remainingBlocks;
    private HitListener hitListener;

    /**
     * the constructor.
     *
     * @param game          : the game to remove the block from.
     * @param removedBlocks : counter of the blocks.
     * @param hitListener   : the hitListener.
     */
    public BlockRemover(Game game, Counter removedBlocks, HitListener hitListener) {
        this.game = game;
        this.remainingBlocks = removedBlocks;
        this.hitListener = hitListener;
    }

    /**
     * if there are 0 hitPoints to the block , we will remove it from the game and decrease the block counter.
     *
     * @param beingHit : the block that is being currently hit.
     * @param hitter   : the ball that is hitting the "beingHit" block.
     */
    public void hitEvent(Block beingHit, Ball hitter) {
        if (beingHit.getHitPoints() == 0) {
            beingHit.removeHitListener(this);
            beingHit.removeFromGame(this.game);
            this.remainingBlocks.decrease(1);
        }
        this.hitListener.hitEvent(beingHit, hitter);

    }

    /**
     * @param hl : the hitlistener to add to the list.
     *           Add hl as a listener to hit events.
     */

    public void addHitListener(HitListener hl) {
        this.hitListener = hl;
    }

    /**
     * Remove hl from the list of listeners to hit events.
     *
     * @param hl : the hitlistener to remove from the list.
     */
    public void removeHitListener(HitListener hl) {
        this.hitListener = null;
    }
}
