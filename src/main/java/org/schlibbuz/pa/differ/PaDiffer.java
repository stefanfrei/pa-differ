/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.schlibbuz.pa.differ;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 *
 * @author Stefan Frei <stefan.a.frei@gmail.com>
 */
public class PaDiffer {

    final static String APP_PROPS = "app.props";
    final static String PA_ORIG_UNITS_FOLDER = "media\\pa\\units";
    final static String PA_TITANS_UNITS_FOLDER = "media\\pa_ex1\\units";
    final Map<String, String> config;


    private PaDiffer() throws IOException {
        try (InputStream appPropsStream = PaDiffer.class.getClassLoader()
                .getResourceAsStream(APP_PROPS)) {
            config = new HashMap<>();
            Properties appProps = new Properties();
            appProps.load(appPropsStream);

            String paModName = appProps.getProperty("pa-mod-name");
            config.put("paModName", paModName);

            config.put(
                    "paOrigUnitsPath",
                    appProps.getProperty("pa-game-path")
                    .concat("\\")
                    .concat(PA_ORIG_UNITS_FOLDER)
            );
            config.put(
                    "paTitansUnitsPath",
                    appProps.getProperty("pa-game-path")
                    .concat("\\")
                    .concat(PA_TITANS_UNITS_FOLDER)
            );
            config.put(
                    "paModsUnitsPath",
                    appProps.getProperty("pa-mods-path")
                    .concat("\\server_mods\\")
                    .concat(paModName)
                    .concat("\\pa\\units")
            );
        }
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)  {
        try {
            var paDiffer = new PaDiffer();
            var unitDirs = paDiffer.getModdedEntitiesFolders();
            unitDirs.forEach(paDiffer::diffEntity);
        } catch(IOException e) {
            System.err.println(e.getMessage());
            System.exit(-1);
        }


    }

    private List<Path> getModdedEntitiesFolders() {
        try(Stream<Path> dirs = Files.walk(
                Paths.get(config.get("paModsUnitsPath"))
        )) {
            return dirs.filter(dir -> Files.isDirectory(
                    dir,
                    LinkOption.NOFOLLOW_LINKS
            ) && !hasSubdirs(dir))
                    .collect(Collectors.toList());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return null;
    }



    static List<String> getFiles(String dir) {
        File directory = new File(dir);
        //Verify if it is a valid file name
        if (!directory.exists()) {
            System.out.println(
                    String.format("Directory %s does not exist", dir)
            );
            return null;
        }

        //Verify if it is a directory and not a file path
        if (!directory.isDirectory()) {
            System.out.println(
                    String.format("Provided value %s is not a directory", dir)
            );
            return null;
        }
        try (Stream<Path> paths = Files.walk(Paths.get(dir))) {
            // keep only `.json` files
            List<String> files = paths.map(Path::toString)
                    .filter(x -> x.endsWith(".json"))
                    .collect(Collectors.toList());

            // print all files
            return files;
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    boolean hasSubdirs(Path dir) {
        try(Stream<Path> files = Files.list(dir)) {
            return files.anyMatch(file -> Files.isDirectory(file, LinkOption.NOFOLLOW_LINKS));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    private void diffEntity(Path path) {
        var moddedEntity = new ModdedEntity(path, config);
    }

}
