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
import javax.imageio.ImageIO;

public class LevelFileReader {
    
    final static char char_block_01 = 'x';
    
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
                
                if (line.contains("end")) {
                    break;
                }
                if (line.contains("" + 1)){
                    level.spawnpoint = new Vector2D(line.indexOf("1") * 32, lineNumber * 32);
                }
                
                while (line.substring(lineIndex).contains("" + char_block_01) && lineIndex < line.length()) {
                    lineIndex = line.indexOf(char_block_01, lineIndex);
                    level.subAreaList.get(lineNumber / 4).get(lineIndex / 4).objects.add(new Block(image_block_01, new Vector2D(image_block_01.getWidth(),
                            image_block_01.getHeight()), new Vector2D(32 * lineIndex, 32 * lineNumber)));

                    lineIndex++;
                }
                lineNumber++;
            }
        } catch (FileNotFoundException e) {
            System.out.println("Error - LevelFileReader: " + e);
        }
    }
    
}
