import classes.Files;
import classes.GameProgress;
import classes.GameSerialize;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        Files.MakeFileStructure();

        GameProgress game1 = new GameProgress(100,2,10,22.2);
        GameProgress game2 = new GameProgress(50,2,9,11.9);
        GameProgress game3 = new GameProgress(10,2,15,99.1);

        GameSerialize gameSerialize = new GameSerialize();
        gameSerialize.saveGame("game1.dot", game1);
        gameSerialize.saveGame("game2.dot", game2);
        gameSerialize.saveGame("game3.dot", game3);

        String file1 = "game1.dot";
        String file2 = "game2.dot";
        String file3 = "game3.dot";
        String archiveFile = "archive.zip";

        Files.zipFiles(archiveFile, file1, file2, file3);

        GameProgress game4 = gameSerialize.loadGame(Files.UnZipFiles(archiveFile).get(0));
        System.out.println(game4);
    }
}