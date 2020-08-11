/**
 * part of the listeners package.
 */
package listeners;

import game.Game;
import game.GameEnvironment;
import geometry.Velocity;
import sprites.Ball;
import sprites.Block;
import sprites.Counter;

import java.awt.Color;

/**
 * adds a ball to the game in the case of hitting the life block.
 */
public class BallAdder implements HitListener {

    private Game game;
    private Counter remainingBalls;
    private GameEnvironment environment;

    /**
     * @param game         : the game to add the ball to.
     * @param removedBalls : counter of the balls.
     * @param environment  : the balls environment.
     */
    public BallAdder(Game game, Counter removedBalls, GameEnvironment environment) {
        this.game = game;
        this.remainingBalls = removedBalls;
        this.environment = environment;
    }

    /**
     * calls the "addBallToGame" function to add a ball to the game, and also increases the number of the balls counter.
     *
     * @param beingHit : the block the is being hit by yhe ball.
     * @param hitter   : the ball the is hitting the "beingHit" block.
     */
    public void hitEvent(Block beingHit, Ball hitter) {
        addBallToGame();
        remainingBalls.increase(1);
    }

    /**
     * creates a ball and adds it to the game.
     */
    public void addBallToGame() {
        // balls center according to the life block placment.
        Ball ball = new Ball(730, 495, 7, Color.white);
        Velocity velocity = Velocity.fromAngleAndSpeed(30, Math.sqrt(50));
        ball.setVelocity(velocity);
        ball.setGameEnvironment(this.environment);
        ball.addToGame(this.game);
    }
}
