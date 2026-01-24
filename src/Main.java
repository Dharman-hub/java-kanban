import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            printMenu();
            int menuNumber = scanner.nextInt();
            scanner.nextLine();
            switch (menuNumber) {
                case 1:
                    TrainingSession trainingSession = new TrainingSession(createNewGroup(scanner),
                            createNewCoach(scanner), createNewDay(scanner), createNewTime(scanner));
                    Timetable.addNewTrainingSession(trainingSession);
                    break;
                case 2:
                    DayOfWeek day1 = createNewDay(scanner);
                    System.out.println(Timetable.getTrainingSessionsForDay(day1));

                    break;
                case 3:
                    DayOfWeek day = createNewDay(scanner);
                    TimeOfDay time = createNewTime(scanner);
                    if (Timetable.getTrainingSessionsForDayAndTime(day, time) == null) {
                        System.out.println("В это время нет тренировки");
                    } else {
                        System.out.println(Timetable.getTrainingSessionsForDayAndTime(day, time));
                    }
                    break;
                case 4:
                    System.out.println(Timetable.getCountByCoaches());
                    break;
                case 0:
                    System.out.println("Выход...");
                    return;
            }
        }
    }

    public static void printMenu() {
        System.out.println("Какое действие вы хотите совершить?");
        System.out.println("1- Добавить тренировку");
        System.out.println("2- Посмотреть тренировки в определенный день");
        System.out.println("3- Посмотреть тренировки по времени и дню");
        System.out.println("4- Подсчитать количество тренировок у тренеров");
        System.out.println("0- Выход");
    }

    public static Group createNewGroup(Scanner scanner) {
        System.out.println("Введите название группы");
        String group = scanner.nextLine();
        System.out.println("Выберите возрастную категорию группы");
        System.out.println("1- Взрослая");
        System.out.println("2- Детская");
        int numberOfGroup = scanner.nextInt();
        scanner.nextLine();
        Age groupAge = null;
        switch (numberOfGroup) {
            case 1:
                groupAge = Age.ADULT;
                break;
            case 2:
                groupAge = Age.CHILD;
                break;
            default:
                System.out.println("Введено некорректное значение");
                break;
        }
        System.out.println("Введите продолжительность тренировки в минутах");
        int durationTime = scanner.nextInt();
        scanner.nextLine();
        return new Group(group, groupAge, durationTime);
    }

    public static Coach createNewCoach(Scanner scanner) {
        System.out.println("Введите фамилию тренера");
        String surname = scanner.nextLine();
        System.out.println("Введите имя тренера");
        String name = scanner.nextLine();
        System.out.println("Введите отчество тренера");
        String middleName = scanner.nextLine();
        Coach coach = new Coach(surname, name, middleName);
        if (!Timetable.getAllCoaches().containsKey(coach)) {
            Timetable.getAllCoaches().put(coach, 0);
        }
        return coach;
    }

    public static DayOfWeek createNewDay(Scanner scanner) {
        System.out.println("Выберите день недели");
        System.out.println("1- Понедельник");
        System.out.println("2- Вторник");
        System.out.println("3- Среда");
        System.out.println("4- Четверг");
        System.out.println("5- Пятница");
        System.out.println("6- Суббота");
        System.out.println("7- Воскресенье");
        int dayNumber = scanner.nextInt();
        DayOfWeek trainingDay = null;
        switch (dayNumber) {
            case 1:
                trainingDay = DayOfWeek.MONDAY;
                break;
            case 2:
                trainingDay = DayOfWeek.TUESDAY;
                break;
            case 3:
                trainingDay = DayOfWeek.WEDNESDAY;
                break;
            case 4:
                trainingDay = DayOfWeek.THURSDAY;
                break;
            case 5:
                trainingDay = DayOfWeek.FRIDAY;
                break;
            case 6:
                trainingDay = DayOfWeek.SATURDAY;
                break;
            case 7:
                trainingDay = DayOfWeek.SUNDAY;
                break;
            default:
                System.out.println("Введено некорректное значение");
                break;
        }
        return trainingDay;
    }

    public static TimeOfDay createNewTime(Scanner scanner) {
        System.out.println("Введите время начала тренировки");
        System.out.println("Во сколько часов:");
        int hour = scanner.nextInt();
        System.out.println("Во сколько минут:");
        int minute = scanner.nextInt();
        return new TimeOfDay(hour, minute);
    }
}
