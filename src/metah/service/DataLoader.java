package metah.service;

import metah.exception.ParseException;
import metah.model.DataSet;
import metah.model.Depot;
import metah.model.Location;
import metah.model.Shop;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

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
        Map<Integer, Location> locationMap = new HashMap<>();
        int lineNr = 7;
        while (true) {
            String line = fileContent.get(lineNr++);
            String[] values = line.trim().split(" ");
            if (values.length == 3) {
                int number = Integer.parseInt(values[0]);
                int x = Integer.parseInt(values[1]);
                int y = Integer.parseInt(values[2]);
                Shop newShop = new Shop(number, x, y);
                locationMap.put(number, newShop);
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
                Shop shop = (Shop) locationMap.get(number);
                shop.setDemand(demand);
            }
            else {
                break;
            }
        }
        int depotNr = Integer.parseInt(fileContent.get(lineNr++).trim());
        int endOfDepots = Integer.parseInt(fileContent.get(lineNr++).trim());
        if (endOfDepots != -1) {
            throw new ParseException("More than one depot!");
        }
        Shop falseShop = (Shop) locationMap.get(depotNr);
        Depot newDepot = new Depot(depotNr, falseShop.getX(), falseShop.getY());
        locationMap.put(depotNr, newDepot);

        String eof = fileContent.get(lineNr).trim();
        if (!eof.equals("EOF")) {
            throw new ParseException("No EOF in the last line of file!");
        }

        DataSet newDataSet = new DataSet(name, dimensions, capacity, depotNr, locationMap);
        return newDataSet;
    }

    private String getMetadataFromLine(int lineNr, List<String> fileContent) {
        return fileContent.get(lineNr).split(":")[1].trim();
    }
}
