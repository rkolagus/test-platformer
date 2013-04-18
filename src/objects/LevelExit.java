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
public class LevelExit extends GameObject{
    
    public LevelExit(Image image, Vector2D location){
        super(image, location, new Vector2D(image.getWidth(null), image.getHeight(null)), true);
    }
    
}
