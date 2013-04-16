
package platformer01;

import java.awt.Image;

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

    
    public Player(Image image, Vector2D size, Vector2D location){
        super(image, size, location);
        movementDirection = new Vector2D(0.0f, 0.0f);
        this.gravity = new Vector2D(0.0f, 5.0f);
        this.isImmovable = false;
        this.jumpspeed = this.gravity.y / 3;
    }
    
    public void move(Controls controls){
        if (controls.direction != 0){
            this.movementDirection.x = controls.direction * speed;
        } else if (this.movementDirection.x != 0) {
            this.movementDirection.x = 0.0f;
        }
        if (controls.keyPressedJump) {
            if (this.isSupported) {
                this.jumpingPeaked = false;
                this.isJumping = true;
            }
            if (this.jumpingPeaked == false) {
            }
        } else {
            this.jumpingPeaked = true;
            this.jumpPhase = this.jumpLimit;
        }
        if (controls.keyPressedDuck) {}
        if ((this.jumpingPeaked || controls.keyPressedJump == false) && this.isSupported == false){
            this.movementDirection.y = this.gravity.y;
        }
    }
}
