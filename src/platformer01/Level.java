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
    
    BufferedImage combinedImage;
    Graphics gb;

    Player player;
    LevelExit levelExit;
    Vector2D levelSize, 
            subAreaSize,
            spawnpoint,
            exit,
            viewLocation;
    
    public boolean completed = false;
    
    Platformer01 game;

    public Level(Platformer01 game) throws Exception{
        this(game, "level_01.txt");
    }
    
    public Level(Platformer01 game, String levelFileName) throws Exception {
        this.game = game;
        this.spawnpoint = new Vector2D(50, 0);
        this.exit = new Vector2D(-100, -100);
        this.viewLocation = new Vector2D(0.0f, 0.0f);
        this.subAreaSize = new Vector2D(32*4, 32*4);
        this.subAreaList = new ArrayList<ArrayList<LevelSubArea>>();
        
        LevelFileReader.getLevelSize("gamedata/levels/" + levelFileName, this);
        this.makeAreaList(levelSize.x.intValue(), levelSize.y.intValue());
        this.levelSize.x = this.levelSize.x * 32;
        this.levelSize.y = this.levelSize.y * 32;
        System.out.println("this.levelSize = " + this.levelSize);

        try {
            LevelFileReader.readLevelFile("gamedata/levels/" + levelFileName, this);
            if (combinedImage == null) {
                combinedImage = new BufferedImage(this.levelSize.x.intValue()/* * 32*/, this.levelSize.y.intValue()/* * 32*/, 
                        BufferedImage.TYPE_INT_RGB);
                gb = combinedImage.getGraphics();
                int rivi = 0, indeksi = 0;
                for (ArrayList<LevelSubArea> areaRow : subAreaList) {
                    for (LevelSubArea area : areaRow) {
                        for (GameObject object : area.objects) {
                            gb.drawImage(object.image, object.location.x.intValue(), object.location.y.intValue(), null);
                        }
                        indeksi++;
                    }
                    rivi++;
                }
            }
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
    
    public void makeAreaList(int x, int y){
        for (int i = 0; i < y; i++){
            this.subAreaList.add(new ArrayList<LevelSubArea>());
            for (int j = 0; j < x; j++){
                Vector2D v = new Vector2D(32 * 4 * j, 32 * 4 * i);
                Vector2D v2 = new Vector2D(32 * 4 * (j + 1), 32 * 4 * (i + 1));
                this.subAreaList.get(i).add(new LevelSubArea(v, v2));
            }
        }
    }

    public void render(Graphics g) {
        
        if (player.location.x > this.game.width / 2) {
            this.viewLocation.x = this.player.location.x - (this.game.width / 2);
        } else {
            this.viewLocation.x = 0.0f;
        }
        g.drawImage(combinedImage, -this.viewLocation.x.intValue(), 0, null);
        

        this.levelExit.render(g, this.viewLocation);
        this.player.render(g, this.viewLocation);
    }
    
    public void update(Controls controls){
        this.player.move(controls);
        this.player.isSupported = false;
        this.player.updatePotentialArea();
        this.player.updateArea();
        for (ArrayList<LevelSubArea> areaColumn : subAreaList) {
            for (LevelSubArea area: areaColumn) {
                if (area.overlaps(this.player.potentialArea)) {
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
        if (player.location.y > this.levelSize.y){
            player.death();
            System.out.println("this.spawnpoint   = " + this.spawnpoint + 
                               "player.spawnpoint = " + this.player.spawnpoint);
            this.player.location = this.player.spawnpoint;
            this.player.targetLocation = this.player.spawnpoint;
            this.player.spawnpoint = new Vector2D(this.player.location);
        }
        player.update();
    }
    
    private void exitLevel(){
        this.completed = true;
    }
}
