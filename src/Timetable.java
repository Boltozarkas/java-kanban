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

        timetable.putIfAbsent(dayOfWeek, new TreeMap<>());

        TreeMap<TimeOfDay, List<TrainingSession>> sessionsForDay = timetable.get(dayOfWeek);
        sessionsForDay.putIfAbsent(timeOfDay, new ArrayList<>());

        sessionsForDay.get(timeOfDay).add(trainingSession);
    }

    public List<TrainingSession> getTrainingSessionsForDay(DayOfWeek dayOfWeek) {
        //как реализовать, тоже непонятно, но сложность должна быть О(1)
        List<TrainingSession> sessions = new ArrayList<>();
        TreeMap<TimeOfDay, List<TrainingSession>> sessionsForDay = timetable.getOrDefault(dayOfWeek, new TreeMap<>());

        for (List<TrainingSession> sessionList : sessionsForDay.values()) {
            sessions.addAll(sessionList);
        }

        return sessions;
    }

    public List<TrainingSession> getTrainingSessionsForDayAndTime(DayOfWeek dayOfWeek, TimeOfDay timeOfDay) {
        //как реализовать, тоже непонятно, но сложность должна быть О(1)
        TreeMap<TimeOfDay, List<TrainingSession>> sessionsForDay = timetable.getOrDefault(dayOfWeek, new TreeMap<>());

        return sessionsForDay.getOrDefault(timeOfDay, new ArrayList<>());
    }

    public List<CounterOfTrainings> getCountByCoaches() {
        Map<Coach, Integer> coachTrainingCounts = new HashMap<>();

        for (TreeMap<TimeOfDay, List<TrainingSession>> daySessions : timetable.values()) {
            for (List<TrainingSession> sessions : daySessions.values()) {
                for (TrainingSession session : sessions) {
                    Coach coach = session.getCoach();
                    coachTrainingCounts.put(coach, coachTrainingCounts.getOrDefault(coach, 0) + 1);
                }
            }
        }

        Set<CounterOfTrainings> sortedSet = new TreeSet<>();

        for (Map.Entry<Coach, Integer> entry : coachTrainingCounts.entrySet()) {
            sortedSet.add(new CounterOfTrainings(entry.getKey(), entry.getValue()));
        }

        return new ArrayList<>(sortedSet);
    }

}
