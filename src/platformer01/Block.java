/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package platformer01;

import java.awt.Image;

/**
 *
 * @author Lagus RKO
 */
public class Block extends GameObject {
    
    public Block(Image image, Vector2D size, Vector2D location){
        super(image, size, location);
        this.isImmovable = true;
    }
    
}
