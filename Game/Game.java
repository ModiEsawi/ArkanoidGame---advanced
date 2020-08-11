/**
 * part of the game package.
 */
package game;

import geometry.Point;
import geometry.Rectangle;
import geometry.Velocity;
import listeners.BallAdder;
import listeners.BallRemover;
import listeners.BlockRemover;
import listeners.ScoreTrackingListener;

import biuoop.DrawSurface;
import biuoop.GUI;
import biuoop.KeyboardSensor;
import sprites.Collidable;
import sprites.ScoreIndicator;
import sprites.Sprite;
import sprites.SpriteCollection;
import sprites.Counter;
import sprites.Ball;
import sprites.Block;
import sprites.Paddle;
import sprites.LivesIndicator;

import java.awt.Color;
import java.util.Random;


/**
 * The game class.
 * in charge of the game animation and contains the collection of sprites and collaidables.
 *
 * @author : mohammed Elesawi.
 */

public class Game {
    private Counter remainedBlocks;
    private Counter remainedBalls;
    private Counter score;
    private Counter numberOfLives;
    private SpriteCollection sprites;
    private GameEnvironment environment;
    private GUI gui;


    /**
     * initializing the game environment and sprites.
     */

    public Game() {
        this.environment = new GameEnvironment();
        this.sprites = new SpriteCollection();
        this.remainedBlocks = new Counter();
        this.remainedBalls = new Counter();
        this.score = new Counter();
        this.numberOfLives = new Counter();
    }

    /**
     * adding a collidable object to the game's environment.
     *
     * @param c : the collidable that needs to be added.
     */

    public void addCollidable(Collidable c) {
        this.environment.addCollidable(c);
    }

    /**
     * removing a collidable object to the game's environment.
     *
     * @param c : the collidable that needs to be removed.
     */

    public void removeCollidable(Collidable c) {
        this.environment.removeCollidable(c);
    }


    /**
     * adding a sprite object to the sprite collection.
     *
     * @param s : the sprite object that needs to be added.
     */

    public void addSprite(Sprite s) {
        this.sprites.addSprite(s);
    }

    /**
     * removing a sprite object to the sprite collection.
     *
     * @param s : the sprite object that needs to be removed.
     */

    public void removeSprite(Sprite s) {
        this.sprites.removeSprite(s);
    }

    /**
     * Initializing the game by creating the blocks , two balls and rst of the sprites , and adding them to the game.
     */

    public void initialize() {
        BallRemover ballRemover = new BallRemover(this, this.remainedBalls);
        BallAdder ballAdder = new BallAdder(this, this.remainedBalls, this.environment);
        ScoreTrackingListener scoreTrackingListener = new ScoreTrackingListener(this.score);
        ScoreIndicator scoreIndicator = new ScoreIndicator(scoreTrackingListener.getCurrentScore());
        this.addSprite(scoreIndicator);
        BlockRemover blockRemover = new BlockRemover(this, this.remainedBlocks, scoreTrackingListener);
        blockRemover.addHitListener(scoreTrackingListener);
        LivesIndicator livesIndicator = new LivesIndicator(this.numberOfLives);
        this.addSprite(livesIndicator);
        Random rand = new Random();
        Velocity ballsVelocity = new Velocity(5, 5);
        Ball[] twoBallsArray = new Ball[2];
        int surroundingBlocksWidth = 30, totalBlockRows = 6, blocksInFirstRow = 12, eachBlockWidth = 50;
        int eachBlockHeight = 30, startingYlocation = 145;
        int surfaceHeight = 800, surfaceWidth = 800, drawX = -1, topBlocksHits = 2, otheBlockHits = 1;


        for (int i = 0; i < 2; i++) {
            Ball ball = new Ball(100 * (i + 1), 300 * (i + 1), 7, Color.white);
            // creating balls will increase the current ball counter.
            this.remainedBalls.increase(1);
            ball.setVelocity(ballsVelocity);
            twoBallsArray[i] = ball;
        }
        for (int i = 0; i < twoBallsArray.length; i++) {
            twoBallsArray[i].setGameEnvironment(this.environment);
            twoBallsArray[i].addToGame(this);
        }
        this.gui = new GUI("Arkanoid game.game", surfaceWidth, surfaceHeight);
        for (int i = 0; i < totalBlockRows; blocksInFirstRow--, i++) {
            Color randomColor = new Color(rand.nextInt(0xFFFFFF));
            for (int j = 0; j < blocksInFirstRow; j++) {

                Point upperLeft = new Point(surfaceWidth - (eachBlockWidth * (j + 1)) - surroundingBlocksWidth
                        , startingYlocation + (eachBlockHeight * i));


                if (i != 0) {
                    Block block = new Block(new Rectangle(upperLeft, eachBlockWidth, eachBlockHeight), randomColor,
                            otheBlockHits);
                    // setting the "blockremover" as a listener to all blocks.
                    block.addHitListener(blockRemover);
                    // creating a block will increase the current block counter.
                    this.remainedBlocks.increase(1);
                    block.addToGame(this);

                } else {
                    Block block = new Block(new Rectangle(upperLeft, eachBlockWidth, eachBlockHeight), randomColor,
                            topBlocksHits);
                    // setting the "blockremover" as a listener to all blocks.
                    block.addHitListener(blockRemover);
                    // creating a block will increase the current block counter.
                    this.remainedBlocks.increase(1);
                    block.addToGame(this);

                }
            }
        }


        /* creating the blocks that surround the surface according to screen lengths , adding them to the game
          , and creating the "bonusdeath" and "life" blocks. */

        Block topBlock = new Block(new Rectangle(new Point(0, 30), surfaceWidth, surroundingBlocksWidth),
                Color.GRAY, drawX);

        topBlock.addToGame(this);

        Block deathBlock = new Block(new Rectangle(new Point(surroundingBlocksWidth,
                surfaceHeight), surfaceWidth - surroundingBlocksWidth * 2,
                surroundingBlocksWidth), Color.GRAY, drawX);

        deathBlock.addHitListener(ballRemover);
        deathBlock.addToGame(this);

        Block bonusDeathBlock = new Block(new Rectangle(new Point(surroundingBlocksWidth,
                surfaceHeight - (10 * surroundingBlocksWidth)), eachBlockWidth * 1.5,
                eachBlockHeight), Color.BLACK, 0);

        bonusDeathBlock.addToGame(this);
        bonusDeathBlock.addHitListener(ballRemover);

        Block lifeBlock = new Block(new Rectangle(new Point(surfaceWidth - (3.50 * surroundingBlocksWidth),
                surfaceHeight - (10 * surroundingBlocksWidth)), eachBlockWidth * 1.5,
                eachBlockHeight), Color.BLUE, 0);

        lifeBlock.addHitListener(ballAdder);
        lifeBlock.addToGame(this);

        Block leftBlock = new Block(new Rectangle(new Point(0, surroundingBlocksWidth + 30), surroundingBlocksWidth
                , surfaceHeight - surroundingBlocksWidth), Color.GRAY, drawX);
        leftBlock.addToGame(this);

        Block rightBlock = new Block(new Rectangle(new Point(surfaceWidth - surroundingBlocksWidth,
                surroundingBlocksWidth + 30), surroundingBlocksWidth, surfaceHeight - surroundingBlocksWidth),
                Color.GRAY, drawX);
        rightBlock.addToGame(this);

    }

    /**
     * "run" method that updates the number of lives , and returns accordingly.
     */

    public void run() {
        int surroundingBlocksWidth = 30, paddleWidth = 145, paddleHeight = 10, surfaceHeight = 800, surfaceWidth = 800,
                paddleMove = 16;
        KeyboardSensor keyboard = this.gui.getKeyboardSensor();
        Point paddleUpperleft = new Point(297.5, surfaceHeight - surroundingBlocksWidth - paddleHeight);
        Paddle paddle = new Paddle(new Rectangle(paddleUpperleft, paddleWidth, paddleHeight), Color.YELLOW, keyboard,
                paddleMove, surroundingBlocksWidth, surfaceWidth);
        paddle.addToGame(this);

        // starting the game with 4 lives.
        this.numberOfLives.increase(4);
        //setting the paddle back to the middle and calling the "playOneTurn" method again.
        for (int i = 0; i < 4; i++) {
            if (this.numberOfLives.getValue() != 0) {
                paddle.setUpperLeft(paddleUpperleft);
                playOneTurn();
            } else {
                return;
            }
        }
    }

    /**
     * The playOneTurn() method returns when either there are no more balls or no more blocks.
     * the method also controls a life lost aftermath.
     */

    public void playOneTurn() {
        biuoop.Sleeper sleeper = new biuoop.Sleeper();
        int framesPerSecond = 60;
        int millisecondsPerFrame = 1000 / framesPerSecond;

        while (true) {
            long startTime = System.currentTimeMillis();
            Color backgroundColor = new Color(0.092f, 0.092f, 0.432f);
            DrawSurface d = gui.getDrawSurface();
            d.setColor(backgroundColor);
            // drawing the background.
            d.fillRectangle(0, 0, 800, 800);
            this.sprites.drawAllOn(d);
            gui.show(d);
            this.sprites.notifyAllTimePassed();
            // if there are no more balls, we will decrease the lives counter and start again with two balls .
            if (this.remainedBalls.getValue() == 0) {

                this.numberOfLives.decrease(1);

                if (this.numberOfLives.getValue() == 0) {
                    gui.close();
                }
                Velocity ballsVelocity = new Velocity(5, -5);
                Ball[] twoBallsArray = new Ball[2];
                for (int i = 0; i < 2; i++) {
                    Ball ball = new Ball(100 * (i + 1), 300 * (i + 1), 7, Color.white);
                    this.remainedBalls.increase(1);
                    ball.setVelocity(ballsVelocity);
                    twoBallsArray[i] = ball;
                    twoBallsArray[i].setGameEnvironment(this.environment);
                    twoBallsArray[i].addToGame(this);
                }
                return;
            }
            // if there are no more blocks , we will increase the score by 100 and end the game.
            if (this.remainedBlocks.getValue() == 0) {
                this.score.increase(100);
                gui.close();
                return;
            }
            long usedTime = System.currentTimeMillis() - startTime;
            long milliSecondLeftToSleep = millisecondsPerFrame - usedTime;
            if (milliSecondLeftToSleep > 0) {
                sleeper.sleepFor(milliSecondLeftToSleep);
            }
        }
    }
}