package happybit.storage;

import happybit.goal.Goal;
import happybit.goal.GoalType;
import happybit.habit.Habit;
import happybit.interval.Interval;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ImportParser {
    private static final String SLEEP = "[SL]";
    private static final String FOOD = "[FD]";
    private static final String EXERCISE = "[EX]";
    private static final String STUDY = "[SD]";
    private static final String NULL = "null";
    private static final int GOAL_TYPE_INDEX = 2;
    private static final int GOAL_NAME_INDEX = 3;
    private static final int GOAL_START_INDEX = 4;
    private static final int GOAL_END_INDEX = 5;
    private static final int HABIT_NAME_INDEX = 2;
    private static final int HABIT_START_INDEX = 3;
    private static final int HABIT_END_INDEX = 4;
    private static final int HABIT_INTERVAL_INDEX = 5;
    private static final int INTERVAL_START_INDEX = 3;
    private static final int INTERVAL_END_INDEX = 4;
    private static final int INTERVAL_COMPLETE_INDEX = 5;

    protected static Goal goalParser(String[] lineData) throws ParseException {
        GoalType goalType;
        Date dateStart;
        Date dateEnd;

        SimpleDateFormat format = new SimpleDateFormat("ddMMyyyy");
        dateStart = format.parse(lineData[GOAL_START_INDEX]);
        dateEnd = format.parse(lineData[GOAL_END_INDEX]);

        switch (lineData[GOAL_TYPE_INDEX]) {
        case SLEEP:
            goalType = GoalType.SLEEP;
            break;
        case FOOD:
            goalType = GoalType.FOOD;
            break;
        case EXERCISE:
            goalType = GoalType.EXERCISE;
            break;
        case STUDY:
            goalType = GoalType.STUDY;
            break;
        default:
            goalType = GoalType.DEFAULT;
        }

        return new Goal(lineData[GOAL_NAME_INDEX],
                goalType,
                dateStart,
                dateEnd);
    }

    protected static Habit habitParser(String[] lineData) throws NumberFormatException, ParseException {
        SimpleDateFormat format = new SimpleDateFormat("ddMMyyyy");
        Date habitStartDate = format.parse(lineData[HABIT_START_INDEX]);
        Date habitEndDate = format.parse(lineData[HABIT_END_INDEX]);
        String habitName = lineData[HABIT_NAME_INDEX];
        int habitInterval = Integer.parseInt(lineData[HABIT_INTERVAL_INDEX]);

        return new Habit(habitName, habitStartDate, habitEndDate, habitInterval);
    }

    protected static Interval intervalParser(String[] lineData) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("ddMMyyyy");
        Date intervalStartDate = format.parse(lineData[INTERVAL_START_INDEX]);
        Date intervalEndDate = format.parse(lineData[INTERVAL_END_INDEX]);
        Interval interval = new Interval(intervalStartDate, intervalEndDate);
        Date intervalCompletedDate;

        if (!lineData[INTERVAL_COMPLETE_INDEX].equals(NULL)) {
            intervalCompletedDate = format.parse(lineData[INTERVAL_COMPLETE_INDEX]);
            interval.setCompleted(intervalCompletedDate);
        }

        return interval;
    }
}
