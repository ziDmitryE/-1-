package ПотокиВводаВывода.Установка;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Main {

    public static void main(String[] args) {
        GameProgress gameProgress1 =
                new GameProgress(94, 10, 2, 254.32);
        GameProgress gameProgress2 =
                new GameProgress(84, 5, 3, 290.32);
        GameProgress gameProgress3 =
                new GameProgress(50, 7, 5, 400);

        List<String> source = new ArrayList<>();
        source.add("d:\\Games\\savegames\\save1.dat");
        source.add("d:\\Games\\savegames\\save2.dat");
        source.add("d:\\Games\\savegames\\save3.dat");

        saveGame(source.get(0), gameProgress1);
        saveGame(source.get(1), gameProgress2);
        saveGame(source.get(2), gameProgress3);
        zipFiles("d:\\Games\\savegames\\gameProgress.zip", source);
        delete();
    }

    public static void delete() {
        File dir = new File("d:\\Games\\savegames");
        if(dir.isDirectory()){
            for (File item : dir.listFiles()) {
                if(item.isFile() & !item.getName().equals("gameProgress.zip")){
                    item.delete();
                }
            }
        }
    }

    public static void saveGame(String path, GameProgress gameProgress) {
        try (FileOutputStream fos = new FileOutputStream(path);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(gameProgress);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static void zipFiles(String path, List<String> List) {
        try (ZipOutputStream zout = new ZipOutputStream(new
                FileOutputStream(path))) {
            int c = 1;
            for (String i : List) {
                try (FileInputStream fis = new FileInputStream(i)) {
                    ZipEntry entry = new ZipEntry(c + " packed_save.dat");
                    zout.putNextEntry(entry);
                    byte[] buffer = new byte[fis.available()];
                    fis.read(buffer); // зачем этот метод? Если не сложно поясните пж-та.
                    zout.write(buffer);
                    zout.closeEntry();
                } catch (Exception ex) {
                    System.out.println(ex.getMessage());
                }
                c++;
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
}
