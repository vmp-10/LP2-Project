package common;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SaveToFile {

    private List<String> contents;
    // creating the list


    // creating the constructino.
    public SaveToFile() {
        contents = new ArrayList<>();
    }

    // save content to the list
    public void addContent(String content) {
        contents.add(content);
    }

    // method that when called saves the list content into the desired filename
    public void saveToFile(String filename) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (String line : contents) {
                writer.write(line);
                writer.newLine();
            }
            System.out.println("Contents saved to file: " + filename);
        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
        }
    }
}