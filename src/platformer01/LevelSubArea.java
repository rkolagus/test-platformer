package platformer01;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.LinkedList;
import objects.GameObject;

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
    
    public final boolean overlaps(Rectangle r) {
        if (this.area.intersects(r)) {
            return true;
        }
        return false;
    }
    public final boolean overlaps(GameObject o){
        Rectangle r = new Rectangle(o.location.x.intValue(), o.location.y.intValue(), 
                                        o.size.x.intValue(), o.size.y.intValue());
        return this.overlaps(r);
    }
    
    public void renderBounds(Graphics g){
        g.setColor(Color.red);
        g.drawRect(this.start.x.intValue(), this.start.y.intValue(), this.size.x.intValue(), this.size.y.intValue());
    }
}
