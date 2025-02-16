package happybit.goal;

import happybit.habit.Habit;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Goal {

    protected String goalName;
    protected GoalType goalType;
    protected Date startDate;
    protected Date endDate;
    protected ArrayList<Habit> habitList;

    /**
     * Constructor for Goal class with goalType and startDate defined.
     *
     * @param goalName  String description of the goal.
     * @param goalType  Type/Category of goal.
     * @param startDate Date to start tracking of the goal.
     * @param endDate   Date to end tracking of the goal.
     */
    public Goal(String goalName, GoalType goalType, Date startDate, Date endDate) {
        this.goalName = goalName;
        this.goalType = goalType;
        this.startDate = startDate;
        this.endDate = endDate;
        this.habitList = new ArrayList<>();
    }

    /**
     * Setter for name of goal.
     * Used to edit the name of the goal.
     *
     * @param goalName New name the goal is to be updated with.
     */
    public void setGoalName(String goalName) {
        this.goalName = goalName;
    }

    /**
     * Getter for name of goal.
     *
     * @return Name of goal.
     */
    public String getGoalName() {
        return goalName;
    }

    /**
     * Getter for description of the goal.
     *
     * @return String containing goal name and type.
     */
    public String getDescription() {
        return getGoalTypeCharacter() + " " + goalName;
    }

    /**
     * Getter for startDate of the goal in string format. (For storage)
     *
     * @return Start date formatted as a string.
     */
    public String getStartDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("ddMMyyyy");
        return dateFormat.format(this.startDate);
    }

    /**
     * Getter for startDate of the goal in string format. (For printing)
     *
     * @return Start date formatted as a string.
     */
    public String getPrintableStartDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
        return dateFormat.format(this.startDate);
    }

    /**
     * Getter for endDate of the goal.
     *
     * @return End date of a goal.
     */
    public Date getEndDate() {
        return this.endDate;
    }

    /**
     * Getter for endDate of the goal in string format. (For storage)
     *
     * @return End date formatted as a string.
     */
    public String getStringEndDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("ddMMyyyy");
        return dateFormat.format(this.endDate);
    }

    /**
     * Getter for endDate of the goal in string format. (For printing)
     *
     * @return End date formatted as a string.
     */
    public String getPrintableEndDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
        return dateFormat.format(this.endDate);
    }

    /**
     * Getter for habitList.
     *
     * @return ArrayList of habits.
     */
    public ArrayList<Habit> getHabitList() {
        return habitList;
    }

    /**
     * Returns the size of the habitList.
     *
     * @return Size of habitList.
     */
    public int getHabitListSize() {
        return habitList.size();
    }

    /**
     * Adds a habit to the goal.
     * From user input, need to addProgress().
     *
     * @param habit Habit to be added to the goal.
     */
    public void addHabit(Habit habit) {
        habitList.add(habit);
        // get newly added habit and add progress
        Habit newHabit = habitList.get(getListLength() - 1);
        //newHabit.addProgress();
    }

    /**
     * Adds a habit to the current goal.
     * From storage, no need to addProgress() as progress obtained from storage.
     *
     * @param habit Habit being added to habitList
     */
    public void addHabitFromStorage(Habit habit) {
        habitList.add(habit);
    }

    /**
     * Removes a habit from the goal.
     *
     * @param habitIndex Index corresponding to habit in the habitList.
     */
    public void removeHabit(int habitIndex) {
        habitList.remove(habitIndex);
    }

    /**
     * Sets a habit at user specified index as done.
     *
     * @param habitIndex Index corresponding to habit in the habitList
     */
    public void doneHabit(int habitIndex) {
        Habit habit = habitList.get(habitIndex);
        // update key value pair in map for current iteration
        Date currDate = new Date();
        habit.completeInterval(currDate);
    }

    /**
     * Gets the number of habits in a goal.
     *
     * @return Integer number of habits associated with the goal.
     */
    public int getListLength() {
        return habitList.size();
    }


    /**
     * Gets the name of the goalType.
     *
     * @return String of the goalType name.
     */
    public String getGoalType() {
        switch (this.goalType) {
        case SLEEP:
            return "Sleep";
        case FOOD:
            return "Food";
        case EXERCISE:
            return "Exercise";
        case STUDY:
            return "Study";
        default:
            return "Default";
        }
    }

    /**
     * Gets the corresponding 2-character code for the goalType.
     *
     * @return String of the goalType 2-character code.
     */
    public String getGoalTypeCharacter() {
        switch (this.goalType) {
        case SLEEP:
            return "[SL]";
        case FOOD:
            return "[FD]";
        case EXERCISE:
            return "[EX]";
        case STUDY:
            return "[SD]";
        default:
            return "[DF]";
        }
    }

    /**
     * Computes the average completion rate of the goal.
     *
     * @return Average completion rate of the goal (as an int).
     */
    public String computeAverageCompletionRate() {
        int habitListSize = getHabitListSize();
        if (habitListSize == 0) {
            return "Not Applicable";
        } else {
            int sum = 0;
            for (Habit habit : this.habitList) {
                sum += habit.computeHabitCompletionRate();
            }
            return sum / getHabitListSize() + "%";
        }
    }

    /**
     * Gets an arraylist of all due habits of the goal.
     *
     * @return Arraylist of all due habits of the goal.
     */
    public ArrayList<String> getDueHabits() {
        ArrayList<String> dueHabits = new ArrayList<>();
        String dueHabitDescription;
        for (int habitIndex = 0; habitIndex < getHabitListSize(); habitIndex++) {
            dueHabitDescription = this.habitList.get(habitIndex).getIntervalDescriptionIfDue();
            if (dueHabitDescription != null) {
                dueHabits.add("H:" + (habitIndex + 1) + " | " + dueHabitDescription);
            }
        }
        return dueHabits;
    }

    /* The following commands will be used for implementing the quick view of goal.
     * isCompleted()
     * getCompletionRates()
     * getHabitNames()
     */

    /**
     * Checks whether the goal is completed.
     *
     * @return True if the goal is completed, false if not.
     */
    public boolean isCompleted() {
        Date currDate = new Date();
        return currDate.after(this.endDate);
    }

    /**
     * Gets the completion rate of each habit and the total average.
     * Total average is saved as the last index in the integer arraylist.
     *
     * @return ArrayList containing a list of all completion rates.
     */
    public ArrayList<Integer> getCompletionRates() {
        ArrayList<Integer> completionRates = new ArrayList<>();
        int sum = 0;
        int completionRate;
        for (Habit habit : this.habitList) {
            completionRate = habit.computeHabitCompletionRate();
            completionRates.add(completionRate);
            sum += completionRate;
        }
        int averageCompletionRate = sum / getHabitListSize();
        completionRates.add(averageCompletionRate);
        return completionRates;
    }

    /**
     * Gets an arrayList of all the habit names.
     *
     * @return Arraylist containing a list of all habit names.
     */
    public ArrayList<String> getHabitNames() {
        ArrayList<String> habitNames = new ArrayList<>();
        for (Habit habit : this.habitList) {
            habitNames.add(habit.getHabitName());
        }
        return habitNames;
    }

    /*
     * NOTE : ==================================================================
     * The following are private methods that are used to implement SLAP for the
     * above public methods. These methods are positioned at the bottom to better
     * visualise the actual methods that can be called from outside this class.
     * =========================================================================
     */

}