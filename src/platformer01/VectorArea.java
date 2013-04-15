package platformer01;

import java.util.LinkedList;

public class VectorArea {

    /* TODO - Clean up this */
    
    public static LinkedList<Vector2D> objectPathToVectors(GameObject object) {
        LinkedList<Vector2D> vectorList = new LinkedList<Vector2D>();
        if (object.movementDirection.x >= 0){
            if (object.movementDirection.y >= 0){
                if (object.movementDirection.x > 0) {
                    vectorList.add(object.getLowerLeftCorner());
                }
                vectorList.add(object.getUpperLeftCorner());
                vectorList.add(object.getUpperRightCorner());
                if (object.movementDirection.x > 0) {
                    vectorList.add(object.targetLocation.addToX(object.size.x));
                }
                vectorList.add(object.targetLocation.add(object.size));
                vectorList.add(object.targetLocation.addToY(object.size.y));
            } else {
                vectorList.add(object.getLowerRightCorner());
                vectorList.add(object.getLowerLeftCorner());
                if (object.movementDirection.x > 0){
                    vectorList.add(object.getUpperLeftCorner());
                }
                /* kohde yläsivu */
                /* alasivu       */
                vectorList.add(null);
            }
        }
        return vectorList;
    }
}
