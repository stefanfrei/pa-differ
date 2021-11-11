/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.schlibbuz.pa.differ;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Stream;



/**
 *
 * @author Stefan Frei <stefan.a.frei@gmail.com>
 */
public class ModdedEntity {

    private final static Gson gson = new GsonBuilder().setPrettyPrinting().create();
    Map<String, String> config;
    Map<String, FileVersion> fileMappings;
    final Path modFolder;
    final String id;
    final String baseType;
    final Diff diff;


    public ModdedEntity(Path path, Map<String, String> config) {
        this.config = config;
        modFolder = path;
        id = path.getFileName().toString();
        baseType = path.getParent().getFileName().toString();
        printPath(path);
        fileMappings = collectFiles();
        System.out.println("");
        diff = new Diff();
    }

    void printPath(Path path) {
        System.out.println(
                new StringBuilder(path.toString())
                        .append(" (")
                        .append(I18n.get(path.getFileName().toString()))
                        .append(")")
        );
    }

    Map<String, FileVersion> collectFiles() {
        try(Stream<Path> modFiles = Files.list(modFolder)) {
            modFiles.forEach(System.out::println);
        } catch(IOException e) {
            System.err.println(e.getMessage());
        }
        return null;
    }

    Path getOriginalsPath(Path path) {
        String titansPath = path.toString().replace(
                config.get("paModsUnitsPath"),
                config.get("paTitansUnitsPath")
        );
        if (new File(titansPath).exists()) {
            return Paths.get(titansPath);
        }
        String paPath = path.toString().replace(
                config.get("paModsUnitsPath"),
                config.get("paOrigUnitsPath")
        );
        if (new File(paPath).exists()) return Paths.get(paPath);
        return null;
    }

    Diff diff(Path modPath, Path originalPath) {
        Diff diff = new Diff();
        if (originalPath == null) {
            //new feature added
        }
        JsonObject mod = jsonToObject(modPath);
        JsonObject original = jsonToObject(
                getOriginalsPath(modPath)
        );
        if (Objects.equals(mod, original)) {
            return null;
        }
        System.out.println(getEntityName(mod));
        for (Map.Entry<String, JsonElement> entry : mod.getAsJsonObject().entrySet()) {
            if (!Objects.equals(entry.getValue(), original.get(entry.getKey()))) {
                System.out.print(String.format("2-%1$-40s",entry.getKey()));
                System.out.print("\t");
                System.out.print(original.get(entry.getKey()));
                System.out.print(" -> ");
                System.out.println(entry.getValue());
            }
        }
        return diff;
    }

    JsonObject jsonToObject(Path path) {

        try (Reader reader = new FileReader(path.toFile())) {
            // Convert JSON to JsonObject
            return gson.fromJson(reader, JsonObject.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    static String getEntityName(JsonObject mod) {
        if (mod.has("unit_name")) return mod.get("unit_name").getAsString();
        if (mod.has("display_name")) return mod.get("display_name").getAsString();
        return "";
    }
}
