package geometry; /**
 * @author Mohamad Elesawi <esawi442@gmail.com>
 * @since 2019-03-14
 **/

/**
 * point class.
 */
public class Point {

    private double x;
    private double y;

    /**
     * creates new point.
     *
     * @param x - x position.
     * @param y - y position.
     */
    // constructor
    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * calculates the distance from one point to another.
     * using the known Distance Formula.
     *
     * @param other ,calculates the distance between it and our current point.
     * @return the distance between the two points.
     */

    //distance -- return the distance of this point to the other point
    public double distance(Point other) {
        double x1 = this.x;
        double y1 = this.y;
        double x2 = other.getX();
        double y2 = other.getY();
        return Math.sqrt(((x1 - x2) * (x1 - x2)) + ((y1 - y2) * (y1 - y2)));
    }

    /**
     * check if this point and given are equal.
     *
     * @param other the point to compare with.
     * @return true if they equal , false otherwise.
     */
    //equals -- return true if the points are equal, false otherwise
    public boolean equals(Point other) {
        double x1 = this.x;
        double y1 = this.y;
        double x2 = other.getX();
        double y2 = other.getY();

        if (y1 == y2 && x1 == x2) {
            return true;
        }
        return false;
    }

    //Return the x and y values of this point

    /**
     * x-coordinate.
     *
     * @return x-coordinate of the point.
     */

    public double getX() {
        return this.x;
    }

    /**
     * y-coordinate.
     *
     * @return y-coordinate of the point.
     */


    public double getY() {
        return this.y;
    }

    /**
     * sets a new x to the point.
     *
     * @param updatedX - new X position we want to set.
     */


    public void setX(double updatedX) {

        this.x = updatedX;
    }

    /**
     * sets a new y to the point.
     *
     * @param updatedY - new Y position we want to set.
     */

    public void setY(double updatedY) {

        this.y = updatedY;
    }


}
