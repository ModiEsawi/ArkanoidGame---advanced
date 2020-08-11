import game.Game;

/**
 * The game.
 * two balls bouncing around the screen colliding with blocks and a paddle that controls their angles.
 *
 * @author : mohammed Elesawi.
 */
public class Ass5Game {

    /**
     * creating the final game and running it.
     *
     * @param args : not used.
     */
    public static void main(String[] args) {
        Game game = new Game();
        game.initialize();
        game.run();
    }
}
