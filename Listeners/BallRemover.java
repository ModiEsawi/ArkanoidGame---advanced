/**
 * part of the listeners package.
 */
package listeners;

import game.Game;
import sprites.Ball;
import sprites.Block;
import sprites.Counter;

/**
 * removes a ball from the game in case of collision with the bottom of the screen or the "Bonusdeath" block.
 */

public class BallRemover implements HitListener {

    private Game game;
    private Counter remainingBalls;

    /**
     * the constructor.
     *
     * @param game         : the game to remove the block from.
     * @param removedBalls : counter of the balls.
     */

    public BallRemover(Game game, Counter removedBalls) {
        this.game = game;
        this.remainingBalls = removedBalls;
    }

    /**
     * removing the ball from the game in case of collision with the bottom of the screen or the "Bonusdeath" block,
     * and also updating the ball counter.
     *
     * @param beingHit : the block that is being currently hit.
     * @param hitter   : the ball that is hitting the "beingHit" block.
     */
    public void hitEvent(Block beingHit, Ball hitter) {
        hitter.removeFromGame(game);
        this.remainingBalls.decrease(1);
    }
}
