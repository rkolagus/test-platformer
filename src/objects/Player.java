
package objects;

import java.awt.Image;
import java.awt.Rectangle;
import platformer01.Controls;
import platformer01.Vector2D;

public class Player extends GameObject {
    
    /*
     * TODO:
     * - simplify & gather jumping mechanics into one place
     *      - falling speed from linear to gradual
     * - set movement speed to increase gradually, too
     * - set parameters by input? At least:
     *      - speed
     *      - gravity
     */
    
    public Float speed = 5.0f, blockHeight = -3.0f;
    public boolean hasJumped = false;
    public Rectangle potentialArea;
    
    public Player(Image image, Vector2D location, Vector2D size){
        super(image, location, size);
        movementDirection = new Vector2D(0.0f, 0.0f);
        this.gravity = new Vector2D(0.0f, 10.0f);
        this.isImmovable = false;
        this.isSprinting = false;
        this.jumpspeed = 2.0f;
        this.potentialArea = new Rectangle((this.location.x.intValue() - this.speed.intValue() * 2), 
                                           (this.location.y.intValue() - this.gravity.y.intValue()),
                                           (this.size.x.intValue() + this.speed.intValue() * 4),
                                           (this.size.y.intValue() + this.gravity.y.intValue() * 2));
    }
    
    public void updatePotentialArea(){
        this.potentialArea.setLocation(this.location.x.intValue(), this.location.y.intValue());
        /*
        this.potentialArea = new Rectangle((this.location.x.intValue() - this.speed.intValue() * 2), 
                                           (this.location.y.intValue() - this.gravity.y.intValue()),
                                           (this.size.x.intValue() + this.speed.intValue() * 4),
                                           (this.size.y.intValue() + this.gravity.y.intValue() * 2));
        */
    }
    
    public void move(Controls controls){
        
        if (this.isSupported && this.hasJumped && controls.keyPressedJump == false){
            this.hasJumped = false;
        }
        
        if (controls.direction != 0){
            this.movementDirection.x = controls.direction * speed;
        } else if (this.movementDirection.x != 0) {
            this.movementDirection.x = 0.0f;
        }
        if (controls.keyPressedJump) {
            if (this.isSupported && this.hasJumped == false) {
                this.jumpingPeaked = false;
                this.isJumping = true;
                this.hasJumped = true;
            }
        } else {
            this.jumpingPeaked = true;
            this.jumpPhase = this.jumpLimit;
        }
        if (controls.keyPressedDuck) {
        }
        if (controls.keyPressedSprint) {
            this.isSprinting = true;
        } else {
            this.isSprinting = false;
        }
        if ((this.jumpingPeaked || controls.keyPressedJump == false) && this.isSupported == false){
            this.movementDirection.y = this.gravity.y;
        }
    }
}
