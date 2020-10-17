package metah.ea.strategy.configuration;

public class RandomStrategyConfiguration {
    private final int attempts;
    private final int repetitions;

    public RandomStrategyConfiguration(int attempts, int repetitions) {
        this.attempts = attempts;
        this.repetitions = repetitions;
    }

    public int getAttempts() {
        return attempts;
    }

    public int getRepetitions() {
        return repetitions;
    }
}
