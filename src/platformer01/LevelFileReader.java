/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package platformer01;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import objects.Block;

public class LevelFileReader {
    
    final static char char_block_01 = 'x',
                      char_spawn = '1',
                      char_exit = '2';
    
    final static String string_file_end = "end";
    
    public static void getLevelSize(String filename, Level level){
        Scanner scanner;
        try {
            scanner = new Scanner(new File(filename), "UTF-8");
            int x = 0;
            int y = 0;
            
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (line.contains(string_file_end)){
                    break;
                } else {
                    y++;
                    if (x < line.length()){
                        x = line.length();
                    }
                }
            }
            level.levelSize = new Vector2D(x, y);
        } catch (FileNotFoundException ex) {
            System.out.println("Error - LevelFileReared: " + ex);
        }
    }
    
    public static void readLevelFile(String filename, Level level) {
        try {
            Scanner scanner = new Scanner(new File(filename), "UTF-8");
            BufferedImage image_block_01 = null;
            
            try{
                image_block_01 = ImageIO.read(new File("gamedata/images/block_01.bmp"));
            } catch (IOException ie) {
                System.out.println("Error - LevelFileReader: " + ie);
            }
            
            int lineNumber = 0;
            
            while (scanner.hasNextLine()) {
                
                String line = scanner.nextLine();
                int lineIndex = 0;
                if (line.contains(string_file_end)) {
                    break;
                }
                if (line.contains("" + char_spawn)){
                    level.spawnpoint = new Vector2D(line.indexOf(char_spawn) * 32, lineNumber * 32);
                }
                if (line.contains("" + char_exit)){
                    level.exit = new Vector2D(line.indexOf(char_exit) * 32, lineNumber * 32);
                }
                
                while (line.substring(lineIndex).contains("" + char_block_01) && lineIndex < line.length()) {
                    lineIndex = line.indexOf(char_block_01, lineIndex);
                    level.subAreaList.get(lineNumber / 4).get(lineIndex / 4)
                            .objects.add(new Block(image_block_01, new Vector2D(32 * lineIndex, 32 * lineNumber), 
                            new Vector2D(image_block_01.getWidth(), image_block_01.getHeight())));

                    lineIndex++;
                }
                lineNumber++;
            }
        } catch (FileNotFoundException e) {
            System.out.println("Error - LevelFileReader: " + e);
        }
    }
    
}
