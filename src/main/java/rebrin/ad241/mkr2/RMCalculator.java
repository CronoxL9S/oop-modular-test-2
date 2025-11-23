package rebrin.ad241.mkr2;

public class RMCalculator {

    // Формула Brzycki
    public static double brzycki(double weight, int reps) {
        if (reps == 1) return weight;
        if (reps > 12) throw new IllegalArgumentException(
                "Brzycki formula works for 1-12 reps only");
        return weight * (36.0 / (37.0 - reps));
    }

    // Формула Epley
    public static double epley(double weight, int reps) {
        if (reps == 1) return weight;
        return weight * (1 + reps / 30.0);
    }

    // Формула Lander
    public static double lander(double weight, int reps) {
        if (reps == 1) return weight;
        if (reps > 10) throw new IllegalArgumentException(
                "Lander formula works for 1-10 reps only");
        return (100 * weight) / (101.3 - 2.67123 * reps);
    }

    // Універсальний метод
    public static double calculate(String formula, double weight, int reps) {
        return switch (formula) {
            case "Brzycki" -> brzycki(weight, reps);
            case "Epley" -> epley(weight, reps);
            case "Lander" -> lander(weight, reps);
            default -> throw new IllegalArgumentException("Unknown formula: " + formula);
        };
    }
}
