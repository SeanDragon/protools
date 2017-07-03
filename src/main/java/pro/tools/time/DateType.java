package pro.tools.time;

public enum DateType {
    YEAR(1),
    MONTH(2),
    DAY(3),
    HOUR(4),
    MINUTES(5),
    SECONDS(6);

    private int mean;

    DateType(int mean) {
        this.mean = mean;
    }

    public int getMean() {
        return mean;
    }

    public DateType setMean(int mean) {
        this.mean = mean;
        return this;
    }
}