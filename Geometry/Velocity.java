package geometry;

/**
 * geometry.Velocity class
 * geometry.Velocity specifies the change in position on the `x` and the `y` axes.
 *
 * @author : mohammed Elesawi.
 */

public class Velocity {
    private double dx;
    private double dy;

    /**
     * Constructor.
     * given the dx and dy , will be added to the current x and current y to get the change.
     *
     * @param dx : change in position of the x .
     * @param dy : change in position of the y .
     */
    public Velocity(double dx, double dy) {

        this.dx = dx;
        this.dy = dy;

    }

    /**
     * Return the "dx" value of the velocity.
     *
     * @return this.dx.
     */
    public double getdx() {

        return this.dx;
    }

    /**
     * Return the "dy" value of velocity.
     *
     * @return this.dy.
     */
    public double getdY() {

        return this.dy;
    }

    /**
     * set new "dx" to the velocity.
     *
     * @param x - the change we want to create.
     */
    public void setX(double x) {

        this.dx = x;
    }

    /**
     * set new "dy" to the velocity.
     *
     * @param y - the change we want to create.
     */
    public void setY(double y) {

        this.dy = y;
    }


    /**
     * Takes a point with position (x,y) and return a new point with a new position according to the current
     * velocity (x+dx, y+dy).
     *
     * @param p : given point to apply the change to it.
     * @return (x + dx, y + dy) while p=(x,y).
     */

    public Point applyToPoint(Point p) {

        return new Point(p.getX() + this.dx, p.getY() + this.dy);
    }

    /**
     * Constructor.
     * specify the velocity in terms and angle and a speed.
     *
     * @param angle , degree.
     * @param speed , moves to preform.
     * @return the velocity.
     */
    public static Velocity fromAngleAndSpeed(double angle, double speed) {
        // sin and cos functions require radians to get the right result
        double dx = Math.sin(Math.toRadians(angle)) * speed;
        double dy = -1 * Math.cos(Math.toRadians(angle)) * speed;
        return new Velocity(dx, dy);
    }

}

