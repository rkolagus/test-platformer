package platformer01;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

public class Level {
    
    /* TODO:
     * 
     * - fix SubArea array size
     * - set CollisionCheck to subAreas intersecting with player
     * - move methods to appropriate classes
     * - optimize. It slows down by the block
     * - scrolling by player position
     * - level exits
     * - collectibles
     * 
     */

    CollisionCheck collisionCheck = new CollisionCheck();
    LevelSubArea[][] subAreas;
    Player player;
    Vector2D bounds,
            subAreaSize,
            spawnpoint,
            exit;
    final String text = "Level 00 alpha";

    public Level() throws Exception{
        this("gamedata/levels/level_01.txt");
    }
    
    public Level(String levelFileName) throws Exception {
        this.bounds = new Vector2D(600, 400);
        this.spawnpoint = new Vector2D(50, 0);
        this.exit = new Vector2D(550, 350);
        this.subAreaSize = new Vector2D(32*4, 32*4);
        this.subAreas = new LevelSubArea[10][10];
        int saRowIndex = 0, saColumnIndex = 0;
        for (LevelSubArea[] subAreaRow : subAreas) {
            
            for (int a = 0; a < subAreaRow.length; a++) {
                
                Vector2D v = new Vector2D(32 * 4 * saColumnIndex, 32 * 4 * saRowIndex);
                Vector2D v2 = new Vector2D(32 * 4 * (saColumnIndex + 1), 32 * 4 * (saRowIndex + 1));
                subAreaRow[a] = new LevelSubArea(v, v2);
                
                saColumnIndex++;
            }
            saRowIndex++;
        }
        try {
            LevelFileReader.readLevelFile(levelFileName, this);
            } catch (Exception e) {
            throw new Exception("Level file failed to load: " + e);
        }
        try {
            BufferedImage playerImage = ImageIO.read(new File("gamedata/images/player.bmp"));
            player = new Player(playerImage, new Vector2D(playerImage.getWidth(), playerImage.getHeight()), spawnpoint);
        } catch (Exception e) {
            throw new Exception("Player image failed to load!");
        }

    }

    public void render(Graphics g) {
        for (LevelSubArea[] subAreaRow : subAreas) {
            for (LevelSubArea subArea : subAreaRow) {
                for (GameObject gameObject : subArea.objects) {
                    gameObject.render(g);
                }
                if (subArea.overlapsWithObject(player)){
                    subArea.renderBounds(g);
                }
            }
        }
        player.render(g);
    }
    
    public void update(Controls controls){
        player.move(controls);
        player.isSupported = false;
        for (int a = 0; a < subAreas.length; a++) {
            for (int b = 0; b < subAreas[a].length; b++) {
                for (GameObject gameObject : subAreas[b][a].objects) {
                    //gameObject.update();
                    CollisionCheck.playerCheck(this.player, gameObject);
                }

            }
        }
        player.update();
    }
}
