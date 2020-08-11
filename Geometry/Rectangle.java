package geometry;
/**
 * @author Mohamad Elesawi <esawi442@gmail.com>
 * @since 2019-03-28
 */

import java.util.ArrayList;
import java.util.List;

/**
 * a geometry.Rectangle class.
 */
public class Rectangle {

    private Point upperLeft;
    private double width;
    private double height;
    private Line leftLine, rightLine, upLine, lowLine;

    /**
     * creating a new rectangle with with a specific location controlled by his upper left point and a
     * certain height and width.
     *
     * @param upperLeft : the rectangle's upper left point.
     * @param width     : the rectangles width.
     * @param height    : the rectangles height.
     */

    public Rectangle(Point upperLeft, double width, double height) {
        this.upperLeft = upperLeft;
        this.width = width;
        this.height = height;
        this.setLines(); // breaking the rectangle to 4 lines and setting their staring and ending point.
    }

    /**
     * using the given upper left point , we can get calculate each part of the rectangles line length.
     */

    public void setLines() {
        this.upLine = new Line(upperLeft.getX(), upperLeft.getY(), upperLeft.getX() + width, upperLeft.getY());
        this.leftLine = new Line(upperLeft.getX(), upperLeft.getY(), upperLeft.getX(), upperLeft.getY() + height);
        this.rightLine = new Line(upperLeft.getX() + width, upperLeft.getY(),
                upperLeft.getX() + width, upperLeft.getY() + height);
        this.lowLine = new Line(upperLeft.getX(), upperLeft.getY() + height,
                upperLeft.getX() + width, upperLeft.getY() + height);
    }

    /**
     * @return , returning the rectangle's width.
     */

    public double getWidth() {
        return this.width;
    }

    /**
     * @return , returning the rectangle's height.
     */

    public double getHeight() {
        return this.height;
    }

    /**
     * @return , returning the rectangle's upper left point.
     */

    public Point getUpperLeft() {
        return this.upperLeft;
    }

    /**
     * @return , returning the rectangle's left line.
     */

    public Line getLeftLine() {
        return this.leftLine;
    }

    /**
     * @return , returning the rectangle's right line.
     */

    public Line getRightLine() {
        return this.rightLine;
    }

    /**
     * @return , returning the rectangle's upper line.
     */

    public Line getUpLine() {
        return this.upLine;
    }

    /**
     * @return , returning the rectangle's low line.
     */

    public Line getLowLine() {
        return this.lowLine;
    }

    /**
     * finding out the intersection points of a specific line with a rectangle and storing it in a list
     * (possibly empty).
     *
     * @param line : the line to find intersection points between it and the rectangle.
     * @return List of intersection points with the specified line (possibly empty).
     */

    public List<Point> intersectionPoints(Line line) {
        Line[] rectangleLines;
        // an array of the rectangles lines.
        rectangleLines = new Line[]{upLine, lowLine, leftLine, rightLine};
        // the list to store the intersection points in.
        List<Point> rectanglesintersectionPoints = new ArrayList<>();
        int oneEqualPoints = 3, twoEqualPoints = 4, zerouthIndex = 0, firstIndex = 1, secondIndex = 2, thirdIndex = 3,
                twoHits = 2;
        // if there is an intersection , we add the point to the list of intersection Points.
        for (int i = 0; i < rectangleLines.length; i++) {
            if (rectangleLines[i].isIntersecting(line)) {
                rectanglesintersectionPoints.add(rectangleLines[i].intersectionWith(line));
            }
        }
        /*
         * if our list contains four intersection , that means we have two points that already exist, if the list
         * contains 3 points that means we have a point that already exist , and if our list contains 2 points,
         * we must check to see if they are equal, and according to the way that the "rectangleLines" array is built,
         * the equal points will always be the last so we can remove them.
         */
        if (rectanglesintersectionPoints.isEmpty()) {
            return rectanglesintersectionPoints;
        }
        if (rectanglesintersectionPoints.size() == oneEqualPoints) {
            rectanglesintersectionPoints.remove(secondIndex);
        }
        if (rectanglesintersectionPoints.size() == twoEqualPoints) {
            rectanglesintersectionPoints.remove(thirdIndex);
            rectanglesintersectionPoints.remove(secondIndex);
        }
        if (rectanglesintersectionPoints.size() == twoHits) {
            if ((rectanglesintersectionPoints.get(zerouthIndex))
                    .equals(rectanglesintersectionPoints.get(firstIndex))) {
                rectanglesintersectionPoints.remove(firstIndex);
            }
        }
        return rectanglesintersectionPoints;
    }


}