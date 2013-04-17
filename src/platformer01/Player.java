
package platformer01;

import java.awt.Image;
import java.awt.Rectangle;

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
    
    Float speed = 5.0f, blockHeight = -3.0f;
    boolean hasJumped = false;
    Rectangle potentialArea;
    
    public Player(Image image, Vector2D size, Vector2D location){
        super(image, size, location);
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
        this.potentialArea = new Rectangle((this.location.x.intValue() - this.speed.intValue() * 2), 
                                           (this.location.y.intValue() - this.gravity.y.intValue()),
                                           (this.size.x.intValue() + this.speed.intValue() * 4),
                                           (this.size.y.intValue() + this.gravity.y.intValue() * 2));
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
