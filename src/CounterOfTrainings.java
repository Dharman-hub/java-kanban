import java.util.Objects;

public class CounterOfTrainings {

    private final Coach coach;
    private int countOfTraining = 0;

    public CounterOfTrainings(Coach coach, int countOfTraining) {
        this.coach = coach;
        this.countOfTraining = countOfTraining;
    }

    public Coach getCoach() {
        return coach;
    }

    public int getCountOfTraining() {
        return countOfTraining;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        CounterOfTrainings that = (CounterOfTrainings) o;
        return countOfTraining == that.countOfTraining && Objects.equals(coach, that.coach);
    }

    @Override
    public int hashCode() {
        return Objects.hash(coach, countOfTraining);
    }

    @Override
    public String toString() {
        return "CounterOfTrainings{" +
                "coach=" + coach +
                ", countOfTraining=" + countOfTraining +
                '}';
    }
}
