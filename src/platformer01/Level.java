package platformer01;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import objects.GameObject;
import objects.LevelExit;
import objects.Player;

public class Level {
    
    /* TODO:
     * 
     * - make and use a collision map
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
    LevelExit levelExit;
    Vector2D bounds,
            subAreaSize,
            spawnpoint,
            exit;
    
    final String text = "Level 00 alpha";
    
    public boolean completed = false;
    
    Platformer01 game;

    public Level(Platformer01 game) throws Exception{
        this(game, "level_01.txt");
    }
    
    public Level(Platformer01 game, String levelFileName) throws Exception {
        this.game = game;
        this.bounds = new Vector2D(600, 400);
        this.spawnpoint = new Vector2D(50, 0);
        this.exit = new Vector2D(-100, -100);
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
            LevelFileReader.readLevelFile("gamedata/levels/" + levelFileName, this);
            } catch (Exception e) {
            throw new Exception("Level file failed to load: " + e);
        }
        try {
            BufferedImage playerImage = ImageIO.read(new File("gamedata/images/player.bmp"));
            player = new Player(playerImage, spawnpoint, new Vector2D(playerImage.getWidth(), playerImage.getHeight()));
        } catch (Exception e) {
            throw new Exception("Player image failed to load: " + e);
        }
        try {
            BufferedImage exitImage = ImageIO.read(new File("gamedata/images/orb.png"));
            this.levelExit = new LevelExit(exitImage, this.exit);
        } catch (Exception e){
            System.out.println("LevelExit image failed to load: " + e);
        }
        

    }

    BufferedImage combinedImage;
    Graphics gb;
    public void render(Graphics g) {
        if (combinedImage == null){
            combinedImage = new BufferedImage(700, 500, BufferedImage.TYPE_INT_RGB);
            g = combinedImage.getGraphics();
            int rivi = 0, indeksi = 0;
            for (ArrayList<LevelSubArea> areaRow : subAreaList) {
                for (LevelSubArea area : areaRow) {
                    for (GameObject gameObject : area.objects) {
                        g.drawImage(gameObject.image, indeksi * 32, rivi * 32, null);
                    }
                    indeksi++;
                }
                rivi++;
            }
        }
        
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
        this.levelExit.render(g);
        player.render(g);
    }
    
    public void update(Controls controls){
        player.move(controls);
        player.isSupported = false;
        player.updatePotentialArea();
        player.updateArea();
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
        if (CollisionCheck.intersects(player, levelExit)){
            this.exitLevel();
        }
        player.update();
    }
    
    private void exitLevel(){
        this.completed = true;
    }
}
