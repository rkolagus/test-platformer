package platformer01;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.LinkedList;

public class LevelSubArea {
    
    /*
     * BUG:
     * 
     * - overlapsWithObject works for top row only!
     * 
     */
    
    Vector2D start, end, size;
    Rectangle area;
    LinkedList<GameObject> objects;
    
    public LevelSubArea(Vector2D start, Vector2D end){
        this.start = start;
        this.end = end;
        this.size = end.remove(start);
        this.objects = new LinkedList<GameObject>();
        this.area = new Rectangle(this.start.x.intValue(), this.start.y.intValue(), 
                                       this.size.x.intValue(), this.size.y.intValue());
    }
    
    public void addObject(GameObject o){
        this.objects.add(o);
    }
    
    public boolean overlapsWithObject(GameObject o){
        Rectangle oArea = new Rectangle(o.location.x.intValue(), o.location.y.intValue(), 
                                        o.size.x.intValue(), o.size.y.intValue());
        if (this.area.intersects(oArea)){
            return true;
        }
        return false;
    }
    
    public void renderBounds(Graphics g){
        g.setColor(Color.red);
        g.drawRect(this.start.x.intValue(), this.start.y.intValue(), this.size.x.intValue(), this.size.y.intValue());
    }
}
