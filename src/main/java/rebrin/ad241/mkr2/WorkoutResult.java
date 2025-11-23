package rebrin.ad241.mkr2;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class WorkoutResult {
    private LocalDateTime date;
    private String exercise;
    private double weight;
    private int reps;
    private String formula;
    private double oneRM;

    public WorkoutResult(String exercise, double weight, int reps,
                         String formula, double oneRM) {
        this.date = LocalDateTime.now();
        this.exercise = exercise;
        this.weight = weight;
        this.reps = reps;
        this.formula = formula;
        this.oneRM = oneRM;
    }

    // Getters
    public String getDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
        return date.format(formatter);
    }

    public String getExercise() { return exercise; }
    public double getWeight() { return weight; }
    public int getReps() { return reps; }
    public String getFormula() { return formula; }
    public double getOneRM() { return oneRM; }

    // Для експорту в CSV
    public String toCSV() {
        return String.format("%s,%s,%.1f,%d,%s,%.1f",
                getDate(), exercise, weight, reps, formula, oneRM);
    }
}
