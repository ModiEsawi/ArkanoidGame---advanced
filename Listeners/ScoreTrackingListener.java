/**
 * part of the listeners package.
 */

package listeners;

import sprites.Ball;
import sprites.Block;
import sprites.Counter;

/**
 * The ScoreTrackingListener class updates the current score of the player according to the block hit points,
 * a hit is worth 5 points, a block removal is worth an additional 10 (15 in total).
 */

public class ScoreTrackingListener implements HitListener {
    private Counter currentScore;

    /**
     * the constructor.
     *
     * @param scoreCounter : the scores counter.
     */
    public ScoreTrackingListener(Counter scoreCounter) {
        this.currentScore = scoreCounter;
    }

    /**
     * updates the score when a called , a hit is worth 5 points, a block removal is worth an additional 10
     * (15 in total).
     *
     * @param beingHit : the block that is being hit.
     * @param hitter   : the sprites.Ball that's doing the hitting.
     */

    public void hitEvent(Block beingHit, Ball hitter) {
        if (beingHit.getHitPoints() != 0) {
            this.currentScore.increase(5);
        } else {
            this.currentScore.increase(15);
        }
    }

    /**
     * returns the current score.
     *
     * @return : the current score.
     */
    public Counter getCurrentScore() {
        return this.currentScore;
    }
}
