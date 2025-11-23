package rebrin.ad241.mkr2;

import javafx.collections.ObservableList;

import java.io.FileWriter;
import java.io.IOException;

public class FileExporter {

    public static void exportToCSV(ObservableList<WorkoutResult> results,
                                   String filename) throws IOException {
        try (FileWriter writer = new FileWriter(filename)) {
            // Заголовки
            writer.write("Date,Exercise,Weight(kg),Reps,Formula,1RM(kg)\n");

            // Дані
            for (WorkoutResult result : results) {
                writer.write(result.toCSV() + "\n");
            }
        }
    }
}
