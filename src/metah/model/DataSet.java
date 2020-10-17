package metah.model;

import java.util.Map;

public class DataSet {
    private String name;
    private int dimensions;
    private int capacity;
    private int depotNr;
    private Map<Integer, Location> locations;

    public DataSet(String name, int dimensions, int capacity, int depotNr, Map<Integer, Location> locations) {
        this.name = name;
        this.dimensions = dimensions;
        this.capacity = capacity;
        this.depotNr = depotNr;
        this.locations = locations;
    }

    public String getName() {
        return name;
    }

    public int getDimensions() {
        return dimensions;
    }

    public int getCapacity() {
        return capacity;
    }

    public int getDepotNr() {
        return depotNr;
    }

    public Map<Integer, Location> getLocations() {
        return locations;
    }
}
