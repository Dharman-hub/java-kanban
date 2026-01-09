import java.util.Objects;

public class TrainingSession implements Comparable<TrainingSession> {

    //группа
    private final Group group;
    //тренер
    private final Coach coach;
    //день недели
    private final DayOfWeek dayOfWeek;
    //время начала занятия
    private final TimeOfDay timeOfDay;

    public TrainingSession(Group group, Coach coach, DayOfWeek dayOfWeek, TimeOfDay timeOfDay) {
        this.group = group;
        this.coach = coach;
        this.dayOfWeek = dayOfWeek;
        this.timeOfDay = timeOfDay;
    }

    public Coach getCoach() {
        return coach;
    }

    public DayOfWeek getDayOfWeek() {
        return dayOfWeek;
    }

    public TimeOfDay getTimeOfDay() {
        return timeOfDay;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        TrainingSession that = (TrainingSession) o;
        return Objects.equals(group, that.group) && Objects.equals(coach, that.coach) && dayOfWeek == that.dayOfWeek && Objects.equals(timeOfDay, that.timeOfDay);
    }

    @Override
    public int hashCode() {
        return Objects.hash(group, coach, dayOfWeek, timeOfDay);
    }

    @Override
    public String toString() {
        return "TrainingSession{" +
                "group=" + group +
                ", coach=" + coach +
                ", dayOfWeek=" + dayOfWeek +
                ", timeOfDay=" + timeOfDay +
                '}';
    }

    @Override
    public int compareTo(TrainingSession o) {
        if (this.getTimeOfDay().getHours() != o.getTimeOfDay().getHours()) {
            return this.getTimeOfDay().getHours() - o.getTimeOfDay().getHours();
        }
        return this.getTimeOfDay().getMinutes() - o.getTimeOfDay().getMinutes();
    }
}