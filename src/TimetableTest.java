import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class TimetableTest {

    @BeforeEach
    void cleaning() {
        Timetable.clear();
    }


    @Test
     void testGetTrainingSessionsForDaySingleSession() {
        Group group = new Group("Акробатика для детей", Age.CHILD, 60);
        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");
        TrainingSession singleTrainingSession = new TrainingSession(group, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));

        Map<DayOfWeek, TreeMap<TimeOfDay, List<TrainingSession>>> timetableForExample = new HashMap<>();
        List<TrainingSession> trainingSessions = new ArrayList<>();
        trainingSessions.add(singleTrainingSession);
        timetableForExample.put(singleTrainingSession.getDayOfWeek(), new TreeMap<>());
        timetableForExample.get(singleTrainingSession.getDayOfWeek()).
                put(singleTrainingSession.getTimeOfDay(),trainingSessions);

        Timetable.addNewTrainingSession(singleTrainingSession);

        assertEquals(timetableForExample.get(DayOfWeek.MONDAY), Timetable.getTrainingSessionsForDay(DayOfWeek.MONDAY),
                "TreeMap должны быть одинаковыми");

        assertNull(Timetable.getTrainingSessionsForDay(DayOfWeek.TUESDAY),
                "За вторник должно вернуться null тренировок");
    }

    @Test
    void testGetTrainingSessionsForDayMultipleSessions() {
        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");

        Group groupAdult = new Group("Акробатика для взрослых", Age.ADULT, 90);
        TrainingSession thursdayAdultTrainingSession = new TrainingSession(groupAdult, coach,
                DayOfWeek.THURSDAY, new TimeOfDay(20, 0));

        Timetable.addNewTrainingSession(thursdayAdultTrainingSession);

        Group groupChild = new Group("Акробатика для детей", Age.CHILD, 60);
        TrainingSession mondayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));
        TrainingSession thursdayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.THURSDAY, new TimeOfDay(13, 0));
        TrainingSession saturdayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.SATURDAY, new TimeOfDay(10, 0));

        Timetable.addNewTrainingSession(mondayChildTrainingSession);
        Timetable.addNewTrainingSession(thursdayChildTrainingSession);
        Timetable.addNewTrainingSession(saturdayChildTrainingSession);



        assertEquals(1, Objects.requireNonNull(Timetable.getTrainingSessionsForDay(DayOfWeek.MONDAY)).size(),
                "Должна возвращаться одна тренировка");

        assertEquals(2, Objects.requireNonNull(Timetable.getTrainingSessionsForDay(DayOfWeek.THURSDAY)).size(),
                "Должны возвращаться две тренировки");

        Iterator<TimeOfDay> iterator = Timetable.getTrainingSessionsForDay(DayOfWeek.THURSDAY).keySet().iterator();

        assertEquals(new TimeOfDay(13, 0), iterator.next(), "Первая тренировка в 13:00");
        assertEquals(new TimeOfDay(20, 0), iterator.next(), "Вторая тренировка в 20:00");

        // Проверить, что за понедельник вернулось одно занятие
        // Проверить, что за четверг вернулось два занятия в правильном порядке: сначала в 13:00, потом в 20:00
        // Проверить, что за вторник не вернулось занятий
    }

    @Test
    void testGetTrainingSessionsForDayAndTime() {
        Group group = new Group("Акробатика для детей", Age.CHILD, 60);
        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");
        TrainingSession singleTrainingSession = new TrainingSession(group, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));

        Timetable.addNewTrainingSession(singleTrainingSession);

        assertEquals(1, Timetable.getTrainingSessionsForDayAndTime(DayOfWeek.MONDAY,
                new TimeOfDay(13, 0)).size(), "Должна вернуться одна тренировка");
        assertNull(Timetable.getTrainingSessionsForDayAndTime(DayOfWeek.MONDAY,
                new TimeOfDay(14, 0)), "Должно вернуться null тренировок");
    }

    @Test
    void testGetNumberOfCoaches() {
        Group group = new Group("Акробатика для детей", Age.CHILD, 60);
        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");
        TrainingSession singleTrainingSession = new TrainingSession(group, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));

        Timetable.addNewTrainingSession(singleTrainingSession);

        Timetable.getAllCoaches().put(singleTrainingSession.getCoach(), 0);
        assertEquals(1, Timetable.getCountByCoaches().size(), "В списке должен быть один тренер");
    }

    @Test
    void testGetCountByCoachesEmpty() {
        assertNull(Timetable.getCountByCoaches(), "Если расписание пустое, метод должен возвращать null");
    }

    @Test
    void testGetListByCoaches() {
        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");
        Coach coach1 = new Coach("Петров", "Пётр", "Петрович");
        Group groupChild = new Group("Акробатика для детей", Age.CHILD, 60);
        TrainingSession mondayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));
        TrainingSession thursdayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.THURSDAY, new TimeOfDay(13, 0));
        TrainingSession saturdayChildTrainingSession = new TrainingSession(groupChild, coach1,
                DayOfWeek.SATURDAY, new TimeOfDay(10, 0));

        Timetable.addNewTrainingSession(mondayChildTrainingSession);
        Timetable.addNewTrainingSession(thursdayChildTrainingSession);
        Timetable.addNewTrainingSession(saturdayChildTrainingSession);

        CounterOfTrainings counterOfTrainings = new CounterOfTrainings(coach, 2);
        CounterOfTrainings counterOfTrainings1 = new CounterOfTrainings(coach1, 1);

        Timetable.getAllCoaches().put(coach, 0);
        Timetable.getAllCoaches().put(coach1, 0);

        Iterator<CounterOfTrainings> iterator = Timetable.getCountByCoaches().iterator();

        assertEquals(counterOfTrainings, iterator.next(),
                "Должен быть тренер с наибольшим количеством тренировок");
        assertEquals(counterOfTrainings1, iterator.next(),
                "Должен быть тренер с наименьшим количеством тренировок");
    }
}