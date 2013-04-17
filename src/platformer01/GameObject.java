package platformer01;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Polygon;

public abstract class GameObject {

    Image image;
    Vector2D size,
            location,
            spawnpoint,
            movementDirection,
            targetLocation,
            gravity;
    boolean isSupported = false, 
            isImmovable = false,
            isJumping = false,
            jumpingPeaked = true,
            isLeftBlocked = false,
            isRightBlocked = false,
            isUpBlocked = false,
            isSprinting = false;
    
    Float jumpspeed = 0.0f;
    int jumpPhase = 0, jumpLimit = 10;

    public GameObject(Image image, Vector2D size, Vector2D location) {
        this.image = image;
        this.size = size;
        this.location = location;
        this.spawnpoint = location;
        this.movementDirection = new Vector2D(0.0f, 0.0f);
        this.targetLocation = location;
        this.gravity = new Vector2D(0.0f, 0.0f);
    }
    
    public GameObject(Image image, Vector2D size, Vector2D location, boolean isImmovable){
        this(image, size, location);
        this.isImmovable = isImmovable;
        /*if (this.isImmovable == false){
            this.updatePolygon();
        }*/
    }
    
    public void render(Graphics g){
        g.drawImage(image, this.location.x.intValue(), this.location.y.intValue(),
                this.size.x.intValue(), this.size.y.intValue(), null);
    }
    
    public boolean contains(Vector2D vector){
        Polygon checkedArea = new Polygon();
        checkedArea.addPoint(this.getUpperLeftCorner().x.intValue(), this.getUpperLeftCorner().y.intValue());
        checkedArea.addPoint(this.getUpperRightCorner().x.intValue(), this.getUpperRightCorner().y.intValue());
        checkedArea.addPoint(this.getLowerRightCorner().x.intValue(), this.getLowerRightCorner().y.intValue());
        checkedArea.addPoint(this.getLowerLeftCorner().x.intValue(), this.getLowerLeftCorner().y.intValue());
        if (checkedArea.contains(vector.x.intValue(), vector.y.intValue())){
            return true;
        }
        return false;
    }
    public void update(){
        if (this.isImmovable == false) {
            
            //this.updatePolygon();

            if (this.isJumping && this.jumpPhase < this.jumpLimit && this.jumpingPeaked == false){
                this.movementDirection.y = (-this.jumpspeed * (this.jumpLimit - this.jumpPhase));
                this.jumpPhase++;
            } else if (this.jumpPhase >= this.jumpLimit) {
                if (this.jumpingPeaked == false && this.isJumping) {
                    this.jumpingPeaked = true;
                    this.isJumping = false;
                }
                this.jumpPhase = 0;
            }
            this.updateTargetLocation();
            this.location = this.targetLocation;
        }
    }
    
    public void updateTargetLocation(){
        float sprintModifier = 1.0f;
        if (this.isSprinting){
            sprintModifier = 2.0f;
        }
        if (this.isImmovable == false && this.movementDirection.x != 0 || this.movementDirection.y != 0) {
            this.targetLocation.x += this.movementDirection.x * sprintModifier;
            if (this.movementDirection.y < 0 || (this.isSupported == false && this.movementDirection.y > 0)) {
                this.targetLocation.y += this.movementDirection.y;
            } else if (this.isSupported){
                this.targetLocation.y = this.location.y;
            }
        }
        if (this.isSupported == false && this.isImmovable == false){
            this.targetLocation.addToY(this.gravity.y);
        }
    }

    
    public Vector2D getUpperLeftCorner(){
        return this.location;
    }
    public Vector2D getUpperRightCorner(){
        return new Vector2D(this.location.x + this.size.x, this.location.y);
    }
    public Vector2D getLowerLeftCorner(){
        return new Vector2D(this.location.x, this.location.y + this.size.y);
    }
    public Vector2D getLowerRightCorner(){
        return new Vector2D(this.location.x + this.size.x, this.location.y + this.size.y);
    }
    
    
}
