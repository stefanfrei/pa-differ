/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.schlibbuz.pa.differ;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 *
 * @author Stefan Frei <stefan.a.frei@gmail.com>
 */
public class Main {

    /**
     * @param args the command line arguments
     * @throws java.io.FileNotFoundException
     */
    public static void main(String[] args) throws FileNotFoundException, IOException {
        // TODO code application logic here
        try (InputStream input = Main.class.getClassLoader().getResourceAsStream("app.props")) {
            Properties props = new Properties();
            props.load(input);

            var originals = getFiles(
                    props.getProperty("pa-steamlib")
                    .concat("\\media\\pa_ex1\\units")
            );
            var modded = getFiles(
                    props.getProperty("pa-appdata")
                    .concat("\\server_mods\\com.pa.nik.CompleteBalanceMod\\pa\\units")
            );

            originals.forEach(Main::jsonToObject);
            modded.forEach(Main::jsonToObject);
        }

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

    static void jsonToObject(String path) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try (Reader reader = new FileReader(path)) {

            // Convert JSON to JsonElement, and later to String
            JsonElement json = gson.fromJson(reader, JsonElement.class);

            String jsonInString = gson.toJson(json);

            System.out.println(jsonInString);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
