package classes;

import java.io.*;

public class GameSerialize {

    public void saveGame(String fileName, GameProgress object) {
        String filePath = Files.saveGameFolder + fileName;
        try (FileOutputStream fos = new FileOutputStream(filePath);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(object);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public GameProgress loadGame(File file) {
        GameProgress gameProgress = null;
        try (FileInputStream fIn = new FileInputStream(file);
             ObjectInputStream oIn = new ObjectInputStream(fIn)) {
            gameProgress = (GameProgress) oIn.readObject();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return gameProgress;
    }
}
