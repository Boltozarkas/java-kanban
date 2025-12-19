import java.util.*;

public class Timetable {

    private Map<DayOfWeek, TreeMap<TimeOfDay, List<TrainingSession>>> timetable;

    public Timetable() {
        this.timetable = new HashMap<>();
    }

    public void addNewTrainingSession(TrainingSession trainingSession) {
        //сохраняем занятие в расписании
        DayOfWeek dayOfWeek = trainingSession.getDayOfWeek();
        TimeOfDay timeOfDay = trainingSession.getTimeOfDay();

        if (!timetable.containsKey(dayOfWeek)) {
            timetable.put(dayOfWeek, new TreeMap<>());
        }

        TreeMap<TimeOfDay, List<TrainingSession>> sessionsForDay = timetable.get(dayOfWeek);
        if (!sessionsForDay.containsKey(timeOfDay)) {
            sessionsForDay.put(timeOfDay, new ArrayList<>());
        }

        sessionsForDay.get(timeOfDay).add(trainingSession);
    }

    public List<TrainingSession> getTrainingSessionsForDay(DayOfWeek dayOfWeek) {
        //как реализовать, тоже непонятно, но сложность должна быть О(1)
        List<TrainingSession> sessions = new ArrayList<>();
        TreeMap<TimeOfDay, List<TrainingSession>> sessionsForDay = timetable.get(dayOfWeek);

        if (sessionsForDay != null) {
            NavigableSet<TimeOfDay> keys = sessionsForDay.navigableKeySet();
            for (TimeOfDay timeOfDay : keys) {
                sessions.addAll(sessionsForDay.get(timeOfDay));
            }
        }

        return sessions;
    }

    public List<TrainingSession> getTrainingSessionsForDayAndTime(DayOfWeek dayOfWeek, TimeOfDay timeOfDay) {
        //как реализовать, тоже непонятно, но сложность должна быть О(1)
        Map<TimeOfDay, List<TrainingSession>> sessionsForDay = timetable.get(dayOfWeek);
        if (sessionsForDay == null) {
            return new ArrayList<>(); // Возвращаем пустой список
        }
        List<TrainingSession> sessions = sessionsForDay.get(timeOfDay);
        return sessions != null ? sessions : new ArrayList<>();
    }

    public List<CounterOfTrainings> getCountByCoaches() {
        Map<Coach, Integer> coachTrainingCounts = new HashMap<>();

        for (DayOfWeek day : timetable.keySet()) {
            for (TimeOfDay time : timetable.get(day).keySet()) {
                for (TrainingSession session : timetable.get(day).get(time)) {
                    Coach coach = session.getCoach();
                    coachTrainingCounts.put(coach, coachTrainingCounts.getOrDefault(coach, 0) + 1);
                }
            }
        }

        List<CounterOfTrainings> counterList = new ArrayList<>();
        for (Map.Entry<Coach, Integer> entry : coachTrainingCounts.entrySet()) {
            counterList.add(new CounterOfTrainings(entry.getKey(), entry.getValue()));
        }

        Collections.sort(counterList, new CounterOfTrainingsComparator());

        return counterList;
    }

}
