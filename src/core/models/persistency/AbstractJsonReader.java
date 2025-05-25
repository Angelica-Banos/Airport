/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package core.models.persistency;

/**
 *
 * @author isabc
 */
import org.json.JSONArray;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

public abstract class AbstractJsonReader {

    protected JSONArray readJsonArrayFromFile(String relativePath) throws Exception {
        File file = new File(relativePath);
        if (!file.exists()) {
            throw new Exception("Archivo no encontrado: " + relativePath);
        }
        String content = new String(Files.readAllBytes(file.toPath()), StandardCharsets.UTF_8);
        return new JSONArray(content);
    }
}