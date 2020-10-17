package metah;

import metah.service.DataLoader;

public class Main {
    public static void main(String[] args) {
        DataLoader dataLoader = new DataLoader();
        dataLoader.loadDataSetFromFile("A-n32-k5");
    }
}
