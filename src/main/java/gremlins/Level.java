package gremlins;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Level {
    public final int levelNumber;
    public final String filePath;

    public final int wizardLives;
    public final double wizardCooldown;
    public final double enemyCooldown;
    
    public Level(int levelNumber, String filePath, int wizardLives, double wizardCooldown, double enemyCooldown) {
        this.levelNumber = levelNumber;
        this.filePath = filePath;

        this.wizardLives = wizardLives;
        this.wizardCooldown = wizardCooldown;
        this.enemyCooldown = enemyCooldown;
    }

    public String getBoardAsString() throws IOException {
        return new String(Files.readAllBytes(Paths.get(filePath)));
    }
}
