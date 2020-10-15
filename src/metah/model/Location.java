package metah.model;

public abstract class Location {
    private int number;
    private int x;
    private int y;

    public Location(int number, int x, int y) {
        this.number = number;
        this.x = x;
        this.y = y;
    }

    public abstract boolean isDepot();
    public abstract boolean isShop();

    public int getNumber() {
        return number;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
