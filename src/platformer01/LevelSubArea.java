package platformer01;

import java.awt.Rectangle;
import java.util.LinkedList;

public class LevelSubArea {
    
    Vector2D start, end, size;
    LinkedList<GameObject> objects;
    
    public LevelSubArea(Vector2D start, Vector2D end){
        this.start = start;
        this.end = end;
        this.size = end.remove(start);
        this.objects = new LinkedList<GameObject>();
    }
    
    public void addObject(GameObject o){
        this.objects.add(o);
    }
    
    public boolean overlapsWithObject(GameObject o){
        Rectangle area = new Rectangle(this.start.x.intValue(), this.start.y.intValue(), 
                                       this.size.x.intValue(), this.size.y.intValue());
        Rectangle oArea = new Rectangle(o.location.x.intValue(), o.location.y.intValue(), 
                                        o.size.x.intValue(), o.size.y.intValue());
        if (area.intersects(oArea)){
            System.out.println("OVERLAPPING:" + this.start + " - " + this.end);
            return true;
        }
        return false;
    }
}
