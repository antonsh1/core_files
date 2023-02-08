import classes.Files;
import classes.GameProgress;
import classes.GameSerialize;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        Files.makeFileStructure();

        GameProgress game1 = new GameProgress(100,2,10,22.2);
        GameProgress game2 = new GameProgress(50,2,9,11.9);
        GameProgress game3 = new GameProgress(10,2,15,99.1);

        String fileName1 = "game1.dot";
        String fileName2 = "game2.dot";
        String fileName3 = "game3.dot";
        String archiveFileName = "archive.zip";

        GameSerialize gameSerialize = new GameSerialize();
        gameSerialize.saveGame(fileName1, game1);
        gameSerialize.saveGame(fileName1, game2);
        gameSerialize.saveGame(fileName1, game3);

        Files.zipFiles(archiveFileName, fileName1, fileName2, fileName3);

        GameProgress game4 = gameSerialize.loadGame(Files.unZipFiles(archiveFileName).get(0));
        System.out.println(game4);
    }
}