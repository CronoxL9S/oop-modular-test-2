package rebrin.ad241.mkr2;

public class InputValidator {

    public static boolean isValidWeight(String weightStr) {
        try {
            double weight = Double.parseDouble(weightStr);
            return weight > 0 && weight <= 500; // Розумний діапазон
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean isValidReps(String repsStr) {
        try {
            int reps = Integer.parseInt(repsStr);
            return reps >= 1 && reps <= 12;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static String getErrorMessage(String field, String value) {
        if (field.equals("weight")) {
            if (value.isEmpty()) return "Вага не може бути порожньою";
            try {
                double w = Double.parseDouble(value);
                if (w <= 0) return "Вага має бути більше 0";
                if (w > 500) return "Вага занадто велика";
            } catch (NumberFormatException e) {
                return "Введіть коректне число";
            }
        } else if (field.equals("reps")) {
            if (value.isEmpty()) return "Повторення не можуть бути порожніми";
            try {
                int r = Integer.parseInt(value);
                if (r < 1) return "Мінімум 1 повторення";
                if (r > 12) return "Максимум 12 повторень для 1RM";
            } catch (NumberFormatException e) {
                return "Введіть ціле число";
            }
        }
        return "";
    }
}
