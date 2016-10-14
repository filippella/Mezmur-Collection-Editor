/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mezmurcollectioneditor.utilities;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;

/**
 *
 * @author Filippo
 */
public class FileUtility {

    public String readFile(String path) {
        try {
            File file = new File(path);
            InputStreamReader inputStreamReader = new InputStreamReader(new FileInputStream(file), "UTF-8");
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line;
            StringBuilder builder = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                builder.append(line);
            }
            inputStreamReader.close();
            reader.close();
            String json = builder.toString();
            return json;
        } catch (Exception e) {
            System.err.println("Error -> " + e.getMessage());
        }
        return null;
    }

    public void writeFile(String json) {
        try {
            File file = new File("C:\\Users\\Filippo\\Desktop\\mezmur.json");
            FileWriter writer = new FileWriter(file);
            writer.write(json);
            writer.close();
        } catch (Exception e) {
            System.err.println("Error -> " + e.getMessage());
        }
    }
}
