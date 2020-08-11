package geometry; /**
 * @author Mohamad Elesawi <esawi442@gmail.com>
 * @since 2019-03-14
 */

import biuoop.DrawSurface;

import java.util.List;
import java.util.Random;

/**
 * class geometry.Line
 * A line segment in coordinate plane.
 * the line connects two points , a starting point and an
 * ending point.
 */
public class Line {
    private Point startingPoint;
    private Point endingPoint;

    /**
     * Constructor.
     * given a start and an end points.
     *
     * @param start : start point.
     * @param end   : end point.
     */
    public Line(Point start, Point end) {

        this.startingPoint = start;
        this.endingPoint = end;

    }

    /**
     * Constructor.
     * given coordinates of the starting and ending point.
     *
     * @param x1 : x-coordinate of the start point.
     * @param y1 : y-coordinate of the start point.
     * @param x2 : x-coordinate of the end point.
     * @param y2 : y-coordinate of the end point.
     */

    public Line(double x1, double y1, double x2, double y2) {
        // creating the points
        this.startingPoint = new Point(x1, y1);
        this.endingPoint = new Point(x2, y2);

    }

    /**
     * using the distance function in class point to calculate the length of the line.
     *
     * @return the length of the line.
     */

    public double length() {
        return startingPoint.distance(endingPoint);
    }

    /**
     * Finding the middle point of the line segment.
     *
     * @return the middle point.
     */

    public Point middle() {
        //calculating the mid point.
        double x = this.startingPoint.getX() + this.endingPoint.getX();
        double y = this.startingPoint.getY() + this.endingPoint.getY();
        return new Point((x / 2), (y / 2));
    }

    /**
     * @return the starting point of the line segment.
     */

    public Point start() {
        return this.startingPoint;
    }

    /**
     * @return the ending point of the line segment.
     */
    public Point end() {
        return this.endingPoint;
    }

    /**
     * Find orientation of ordered triplet (p1, p2, p3).
     * Orientation of an ordered triplet of points in the plane can be one of the three :
     * sprites.Counter Clock-Wise, Clock-Wise and Co-linear.
     * <p>
     * the source i used to get this functions.
     * Source: https://www.geeksforgeeks.org/orientation-3-ordered-points
     *
     * @param p1 : geometry.Point in the plane.
     * @param p2 : geometry.Point in the plane.
     * @param p3 : geometry.Point in the plane.
     * @return 0 if p1,p2 and p3 are Co-linear
     * 1 if p1,p2 and p3 are Clock-Wise.
     * 2 if p1,p2 and p3 are sprites.Counter-Clock-Wise.
     */

    int orientation(Point p1, Point p2, Point p3) {

        //orientation formula.

        double value = (p2.getY() - p1.getY()) * (p3.getX() - p2.getX())
                - (p2.getX() - p1.getX()) * (p3.getY() - p2.getY());

        if (value > 0) {
            return 1; //Clock wise
        }
        if (value == 0) {
            return 0; //co-linear
        } else {
            return 2; //sprites.Counter-clock wise
        }
    }

    /**
     * Given three co-linear points p1,p2 and p3.
     * the function checks if point p2 lies on line segment
     * p1p3.
     *
     * @param p1 : starting point of the segment.
     * @param p2 : the point to check if it lies in the segment.
     * @param p3 : ending point of the segment .
     * @return true if p2 lies on p1p3 segment , false otherwise.
     */
    boolean onSegment(Point p1, Point p2, Point p3) {
        //this is done by checking if the coordinates of p2 ranging from the coordinates of p1 to p3's coordinates.

        if (p2.getX() <= Math.max(p1.getX(), p3.getX()) && p2.getX() >= Math.min(p1.getX(), p3.getX())
                && p2.getY() <= Math.max(p1.getY(), p3.getY()) && p2.getY() >= Math.min(p1.getY(), p3.getY())) {
            return true;
        }
        return false;
    }

    /**
     * checking if a certain point is found on a certain line.
     *
     * @param p2 : the point to check if it lie's on a certain line.
     * @return a boolean stating if the point leis on the line or not.
     */
    public boolean ifPointOnLine(Point p2) {
        Point p1 = this.startingPoint;
        Point p3 = this.endingPoint;
        return onSegment(p1, p2, p3);
    }

    /**
     * Check if the line-segment intersects with other line segment.
     * using Points Orientation way.
     * two segments (p1,p2) and (p3,p4) intersects if and only if
     * one of the two condition is verified :
     * 1.General case:
     * (p1, p2, p3) and (p1, p2, p4) have different orientations and
     * (p3, p4, p1) and (p3, p4, p2) have different orientations.
     * <p>
     * 2.special case:
     * (p1, p2, p3), (p1, p2, p4), (p3, p4, p1), and (p3, p4, p2) are all collinear and
     * the x-projections of (p1, p2) and (p3, p4) intersect.
     * the y-projections of (p1, q1) and (p3, p4) intersect.
     * <p>
     * the source i used to get this information.
     * Source: Computational geometry - Chapter 22 .
     *
     * @param other : line to check intersection with it.
     * @return true if the two lines intersects , false otherwise.
     */

    public boolean isIntersecting(Line other) {
        Point p1 = this.startingPoint;
        Point p2 = this.endingPoint;
        Point p3 = other.startingPoint;
        Point p4 = other.endingPoint;

        //finding the four orientations needed.
        int firstOrientation = orientation(p1, p2, p3);
        int secondOrientation = orientation(p1, p2, p4);
        int thirdOrientation = orientation(p3, p4, p1);
        int fourthOrientation = orientation(p3, p4, p2);

        //General case.
        if (firstOrientation != secondOrientation && thirdOrientation != fourthOrientation) {
            return true;
        }

        //Special cases.

        if (firstOrientation == 0 && onSegment(p1, p3, p2) || secondOrientation == 0 && onSegment(p1, p4, p2)
                || thirdOrientation == 0 && onSegment(p3, p1, p4) || fourthOrientation == 0 && onSegment(p3, p2, p4)) {
            return true;
        }
        return false;
    }

    /**
     * calculating the line slope using Slope-Formula.
     *
     * @return the calculated slope of the line.
     */

    public double getSlope() {
        double x1 = startingPoint.getX();
        double y1 = startingPoint.getY();
        double x2 = endingPoint.getX();
        double y2 = endingPoint.getY();
        //slope formula.
        return (y2 - y1) / (x2 - x1);
    }

    /**
     * get the "b" value of the line (b from the line equation "y=mx+b").
     * line equation : y=mx+b.
     * given one point in the line and the slope of the line , we are able to
     * calculate the b after substitution of the x and y coordinates of the given
     * point in equation.
     *
     * @param m           : line's slope.
     * @param pointOnLine : line's start point.
     * @return the found "b" value.
     */

    public double getB(double m, Point pointOnLine) {
        // b is equal to y - the slope * x
        return (pointOnLine.getY() - m * (pointOnLine.getX()));
    }

    /**
     * finding the intersection geometry.Point with other line.
     * by treating the line-segments as boundless line,
     * because the if lines intersect they will intersect once.
     * first line equation : y1=m1x1+b1.
     * second line equation : y2=m2x2+b2.
     * m1x+b1=m2x+b2 => m1x-m2x=b2-b1 => x=(b1-b2)/(m2-m1)
     * this is intersection point' x-coordinate ,
     * to get the y-coordinate we will substitute the x in one of the equation
     *
     * @param other : line to find the intersection point with out current line.
     * @return the intersection point of the two lines.
     */


    public Point intersectionWith(Line other) {
        double x, y;
        double m1 = this.getSlope();
        double m2 = other.getSlope();
        double b1 = getB(m1, this.startingPoint);
        double b2 = getB(m2, other.startingPoint);

        if (this.startingPoint.equals(other.start()) || this.startingPoint.equals(other.end())) {
            return this.startingPoint;
        }
        if (this.endingPoint.equals(other.end()) || this.endingPoint.equals(other.start())) {
            return this.endingPoint;
        }
        if (this.startingPoint.getX() == this.endingPoint.getX()) {
            x = this.startingPoint.getX();
            y = m2 * x + b2;
        } else if (other.startingPoint.getX() == other.endingPoint.getX()) {
            x = other.startingPoint.getX();
            y = m1 * x + b1;
        } else {
            x = (b1 - b2) / (m2 - m1); // x-coordinate of the intersection point.
            y = (m1 * x) + b1; // placing x into this line equation.
        }
        return new Point(x, y);
    }


    /**
     * check if the two lines segments are equal by checking they're starting and ending points.
     *
     * @param other : other line-segment to compare the starting and ending points with .
     * @return true if the two lines segments are equal , false otherwise.
     */
    public boolean equals(Line other) {
        Point p1 = this.startingPoint;
        Point p2 = this.endingPoint;

        Point p3 = other.start();
        Point p4 = other.end();
        // The two lines-segments are equal if the have the same starting and ending points.
        if ((p1.equals(p3) && p2.equals(p4)) || ((p1.equals(p4) && p2.equals(p3)))) {
            return true;
        }
        return false;
    }

    /**
     * function to generate a random line according to the X and Y bound given in the task.
     *
     * @return a new random line.
     */

    public static Line generateRandomLine() {

        Random rand = new Random(); // a random-number generator.
        int xBound = 400;
        int yBound = 300;
        int x1 = rand.nextInt(xBound) + 1; // get integer in range 1-400
        int y1 = rand.nextInt(yBound) + 1; // get integer in range 1-300
        int x2 = rand.nextInt(xBound) + 1; // get integer in range 1-400
        int y2 = rand.nextInt(yBound) + 1; // get integer in range 1-300
        return new Line(x1, y1, x2, y2);
    }

    /**
     * function to draw the given line according to its starting and ending point.
     *
     * @param line : the line.
     * @param d    : the surface to draw on the line.
     */

    public void drawLine(Line line, DrawSurface d) {
        d.drawLine((int) line.startingPoint.getX(), (int) line.startingPoint.getY(),
                (int) line.endingPoint.getX(), (int) endingPoint.getY());
    }

    /**
     * returning the closest intersiction geometry.Point to the given rectangle with a certain line , by getting the
     * intersection points between this line and rectangle and then calculating the distances between line's starting
     * point and our intersection points.
     *
     * @param rect : the object to check the closest intersection to the line starting point with .
     * @return the closest intersection point to the start of the line.
     */

    public Point closestIntersectionToStartOfLine(Rectangle rect) {
        // getting the all the intersection points.
        List<Point> rectanglesintersectionPoints = rect.intersectionPoints(this);
        // if there is no intersection between the line and the given rectangle , we will of curse return null.
        if (rectanglesintersectionPoints.isEmpty()) {
            return null;
        } else if (rectanglesintersectionPoints.size() == 1) {
            //if there is just one intersection point that must be the one.
            return rectanglesintersectionPoints.get(0);
        }
        /*get the closest point between the two intersection point by calculate the distance to the start of the line
         and returning the smaller outcome */
        if (rectanglesintersectionPoints.size() == 2) {
            if (startingPoint.distance(rectanglesintersectionPoints.get(0))
                    > startingPoint.distance(rectanglesintersectionPoints.get(1))) {
                return rectanglesintersectionPoints.get(1);
            } else {
                return rectanglesintersectionPoints.get(0);
            }
        }
        return rectanglesintersectionPoints.get(0);
    }
}

