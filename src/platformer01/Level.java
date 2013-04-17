package platformer01;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import javax.imageio.ImageIO;

public class Level {
    
    /* TODO:
     * 
     * - fix wall hugging ( = player inside block while pressing against it)
     * - fix SubArea array size
     * - move methods to appropriate classes
     * - OPTIMIZE. It slows down by the block
     * - scrolling by player position
     * - level exits
     * - collectibles
     * 
     */

    CollisionCheck collisionCheck = new CollisionCheck();
    ArrayList<ArrayList<LevelSubArea>> subAreaList;
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
        this.subAreaList = new ArrayList<ArrayList<LevelSubArea>>();
        
        for (int i = 0; i < 10; i++){
            this.subAreaList.add(new ArrayList<LevelSubArea>());
            for (int j = 0; j < 10; j++){
                Vector2D v = new Vector2D(32 * 4 * j, 32 * 4 * i);
                Vector2D v2 = new Vector2D(32 * 4 * (j + 1), 32 * 4 * (i + 1));
                this.subAreaList.get(i).add(new LevelSubArea(v, v2));
            }
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
        for (ArrayList<LevelSubArea> areaRow : subAreaList) {
            for (LevelSubArea area : areaRow) {
                for (GameObject gameObject : area.objects) {
                    gameObject.render(g);
                }
                /* FOR DEBUG */
                /*
                if (area.overlaps(player)){
                    area.renderBounds(g);
                }
                */
            }
        }
        player.render(g);
    }
    
    public void update(Controls controls){
        player.move(controls);
        player.isSupported = false;
        player.updatePotentialArea();
        for (ArrayList<LevelSubArea> areaColumn : subAreaList) {
            for (LevelSubArea area: areaColumn) {
                if (area.overlaps(player.potentialArea)) {
                    for (GameObject gameObject : area.objects) {
                        //gameObject.update();
                        CollisionCheck.playerCheck(this.player, gameObject);
                    }
                }

            }
        }
        player.update();
    }
}
