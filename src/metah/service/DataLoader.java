package metah.service;

import metah.exception.ParseException;
import metah.model.DataSet;
import metah.model.Depot;
import metah.model.Location;
import metah.model.Shop;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class DataLoader {
    public DataSet loadDataSetFromFile(String fileName) {
        List<String> fileContent = loadFileContent(fileName);
        DataSet loadedDataSet = parseFileContent(fileContent);
        return loadedDataSet;
    }

    private List<String> loadFileContent(String fileName) {
        String fullFileName = "data/" + fileName + ".vrp";
        List<String> lines = new ArrayList<>();
        try (Scanner scanner = new Scanner(new File(fullFileName))) {
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
        String name = getMetadataFromLine(0, fileContent);
        int dimensions = Integer.parseInt(getMetadataFromLine(3, fileContent));
        int capacity = Integer.parseInt(getMetadataFromLine(5, fileContent));
        List<Location> locations = new ArrayList<>();
        int lineNr = 7;
        while (true) {
            String line = fileContent.get(lineNr++);
            String[] values = line.trim().split(" ");
            if (values.length == 3) {
                int number = Integer.parseInt(values[0]);
                int x = Integer.parseInt(values[1]);
                int y = Integer.parseInt(values[2]);
                Shop newShop = new Shop(number, x, y);
                locations.add(newShop);
            } else {
                break;
            }
        }
        while (true) {
            String line = fileContent.get(lineNr++);
            String[] values = line.trim().split(" ");
            if (values.length == 2) {
                int number = Integer.parseInt(values[0]);
                int demand = Integer.parseInt(values[1]);
                Shop shop = (Shop) locations.get(number - 1);
                shop.setDemand(demand);
            }
            else {
                break;
            }
        }
        int depotX = Integer.parseInt(fileContent.get(lineNr++).trim());
        int depotY = Integer.parseInt(fileContent.get(lineNr++).trim());
        Depot newDepot = new Depot(depotX, depotY);
        locations.add(0, newDepot);

        String eof = fileContent.get(lineNr).trim();
        if (!eof.equals("EOF")) {
            throw new ParseException("No EOF in the last line of file!");
        }

        DataSet newDataSet = new DataSet(name, dimensions, capacity, locations);
        return newDataSet;
    }

    private String getMetadataFromLine(int lineNr, List<String> fileContent) {
        return fileContent.get(lineNr).split(":")[1].trim();
    }
}
