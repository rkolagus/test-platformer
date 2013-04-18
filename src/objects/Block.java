/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package objects;

import java.awt.Image;
import platformer01.Vector2D;

/**
 *
 * @author Lagus RKO
 */
public class Block extends GameObject {
    
    public Block(Image image, Vector2D location, Vector2D size){
        super(image, location, size);
        this.isImmovable = true;
    }
    
}
