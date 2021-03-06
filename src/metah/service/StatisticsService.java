package metah.service;

import java.util.Arrays;

public class StatisticsService {
    private int counter;
    private long[] arr;
    private Integer avg;
    private boolean normalized;

    public StatisticsService(int arrSize) {
        arr = new long[arrSize];
        counter = 0;
        normalized = false;
    }

    public void addResult(int result) {
        arr[counter++] = result;
    }

    public int getBest() {
        if (!normalized) {
            normalize();
        }
        return (int) Arrays.stream(arr).min().orElse(-1);
    }

    public int getWorst() {
        if (!normalized) {
            normalize();
        }
        return (int) Arrays.stream(arr).max().orElse(-1);    }

    public int getAvg() {
        if (!normalized) {
            normalize();
        }
        avg = (int) (Arrays.stream(arr).sum() / arr.length);
        return avg;
    }

    public int getStd() {
        if (!normalized) {
            normalize();
        }
        if (avg == null) {
            avg = getAvg();
        }
        return (int) Math.sqrt(
                Arrays.stream(arr)
                .map(d -> d - avg)
                .map(d -> d * d)
                .reduce(Long::sum)
                .getAsLong() / arr.length);
    }

    private void normalize() {
        arr = Arrays.stream(arr).filter(x -> x != 0).toArray();
    }

}
