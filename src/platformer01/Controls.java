
package platformer01;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;


public class Controls implements KeyListener{
    
    public boolean keyPressedLeft, 
            keyPressedRight, 
            keyPressedJump,
            keyPressedDuck,
            keyPressedSprint;

    public final int keyLeft = KeyEvent.VK_A,
            keyRight = KeyEvent.VK_D,
            keyJump = KeyEvent.VK_W,
            keyDuck = KeyEvent.VK_S,
            keySprint = KeyEvent.VK_SHIFT;

    public int direction;
    
    public Controls(){
        keyPressedLeft    = false;
        keyPressedRight   = false;
        keyPressedJump    = false;
        keyPressedDuck    = false;
        keyPressedSprint  = false;
        
        direction = 0;
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }
    
           /*      Clean this up        */
          /*                           */
         /*               |           */
        /*                V          */
    
    
    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case keyLeft:
                if (keyPressedLeft == false) {
                    keyPressedLeft = true;
                    this.direction = -1;
                }
                break;
            case keyRight:
                if (keyPressedRight == false) {
                    keyPressedRight = true;
                    this.direction = 1;
                }
                break;
            case keyJump:
                keyPressedJump = true;
                break;
            case keyDuck:
                keyPressedDuck = true;
                break;
            case keySprint:
                keyPressedSprint = true;
                break;
            default:
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
            case keyLeft:
                if (this.keyPressedLeft) {
                    keyPressedLeft = false;
                    if (this.keyPressedRight){
                        this.direction = 1;
                    } else {
                        this.direction = 0;
                    }
                }
                break;
            case keyRight:
                if (this.keyPressedRight) {
                    keyPressedRight = false;
                    if (this.keyPressedLeft) {
                        this.direction = -1;
                    } else {
                        this.direction = 0;
                    }
                }
                break;
            case keyJump:
                keyPressedJump = false;
                break;
            case keyDuck:
                keyPressedDuck = false;
                break;
            case keySprint:
                keyPressedSprint = false;
                break;
            default:
                break;
        }
    }
}
