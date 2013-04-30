/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package platformer01;

/**
 *
 * @author Lagus RKO
 */
public class Vector2D {
    public Float x, y;
    
    public Vector2D(float x, float y){
        this.x = new Float(x);
        this.y = new Float(y);
    }
    
    public Vector2D(Vector2D vector){
        this.x = new Float(vector.x);
        this.y = new Float(vector.y);
    }
    
    public Vector2D add(Vector2D v){
        return new Vector2D(this.x + v.x, this.y + v.y);
    }
    
    public Vector2D remove(Vector2D v){
        return new Vector2D(this.x - v.x, this.y - v.y);
    }
    
    public Vector2D addToX(float f){
        return new Vector2D(this.x + f, this.y);
    }
    
    public Vector2D addToY(float f){
        return new Vector2D(this.x, this.y + f);
    }
    
    public int[] toInt(){
        int[] a = {this.x.intValue(), this.y.intValue()}; 
        return a;
    }
    
    @Override
    public String toString(){
        return ("(" + this.x + ", " + this.y + ")");
    }
}
