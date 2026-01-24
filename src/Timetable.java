import java.util.*;

public class Timetable {
    private static final Map<Coach, Integer> allCoaches = new HashMap<>();
    private static final Map<DayOfWeek, TreeMap<TimeOfDay, List<TrainingSession>>> timetable = new HashMap<>();


    public static void addNewTrainingSession(TrainingSession trainingSession) {
        if (!timetable.containsKey(trainingSession.getDayOfWeek())) {
            timetable.put(trainingSession.getDayOfWeek(), new TreeMap<>());
        }
        if (!timetable.get(trainingSession.getDayOfWeek()).containsKey(trainingSession.getTimeOfDay())) { // Если этого
            // времени нет в таблице
            timetable.get(trainingSession.getDayOfWeek()).put(trainingSession.getTimeOfDay(), new ArrayList<>());
        }

        List<TrainingSession> training = timetable.get(trainingSession.getDayOfWeek())
                .get(trainingSession.getTimeOfDay());
        boolean check = false;
        for (TrainingSession t : training) {
            if (t.equals(trainingSession)) {
                check = true;
                break;
            }
        }

        if (check) {
            System.out.println("Такая тренировка уже есть");
        } else {
            training.add(trainingSession); // Убрал лишние строки кода
            System.out.println("Тренировка была добавлена");
        }
    }

    public static TreeMap<TimeOfDay, List<TrainingSession>> getTrainingSessionsForDay(DayOfWeek dayOfWeek) {
        if (timetable.isEmpty()) {
            System.out.println("Пока что список пуст");
            return new TreeMap<>(); // Заменил null на пустую мапу
        }
        if (!timetable.containsKey(dayOfWeek)) {
            System.out.println("В этот день нет тренировок"); // добавил проверку на конкретный день
            return new TreeMap<>();
        }
        return timetable.get(dayOfWeek);
    }

    public static List<TrainingSession> getTrainingSessionsForDayAndTime(DayOfWeek dayOfWeek, TimeOfDay timeOfDay) {
        if (timetable.isEmpty()) {
            System.out.println("Пока что список пуст");
            return new ArrayList<>(); // Заменил null на пустой лист
        }
        if (timetable.containsKey(dayOfWeek)) {
            return timetable.get(dayOfWeek).getOrDefault(timeOfDay, null);
        } else {
            return new ArrayList<>(); // Заменил null на пустой лист
        }
    }

    public static List<CounterOfTrainings> getCountByCoaches() {
        if (timetable.isEmpty()) {
            System.out.println("Пока что список пуст");
            return new ArrayList<>(); // Заменил null на пустой лист
        }

        for (TreeMap<TimeOfDay, List<TrainingSession>> days : timetable.values()) {
            for (List<TrainingSession> time : days.values()) {
                for (TrainingSession session : time) {
                    for (Map.Entry<Coach, Integer> entry : allCoaches.entrySet()) {
                        if (entry.getKey().equals(session.getCoach())) {
                            allCoaches.put(entry.getKey(), entry.getValue() + 1);
                        }
                    }
                }
            }
        }
        List<CounterOfTrainings> count = new ArrayList<>();
        for (Map.Entry<Coach, Integer> entry : allCoaches.entrySet()) {
            count.add(new CounterOfTrainings(entry.getKey(), entry.getValue()));
        }
        count.sort((a, b) -> b.getCountOfTraining() - a.getCountOfTraining());
        return count;
    }

    public static Map<Coach, Integer> getAllCoaches() {
        return allCoaches;
    }

    public static void clear() {
        timetable.clear();
        allCoaches.clear();
    }
}