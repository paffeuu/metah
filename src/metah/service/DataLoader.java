package metah.service;

import metah.model.DataSet;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class DataLoader {
    public void loadDataSetFromFile(String fileName) {
        List<String> fileContent = loadFileContent(fileName);
        DataSet loadedDataSet = parseFileContent(fileContent);
    }

    private List<String> loadFileContent(String fileName) {
        List<String> lines = new ArrayList<>();
        try (Scanner scanner = new Scanner(new File(fileName))) {
            String line = null;
            while (scanner.hasNextLine()) {
                line = scanner.nextLine();
                lines.add(line);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return lines;
    }

    private DataSet parseFileContent(List<String> fileContent) {
        throw new UnsupportedOperationException();
    }
}
