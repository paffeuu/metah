package metah.model;

import java.util.List;

public class DataSet {
    private String name;
    private int dimensions;
    private int capacity;
    private List<Location> locations;

    public DataSet(String name, int dimensions, int capacity, List<Location> locations) {
        this.name = name;
        this.dimensions = dimensions;
        this.capacity = capacity;
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

    public List<Location> getLocations() {
        return locations;
    }
}
