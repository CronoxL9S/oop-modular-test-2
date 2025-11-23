package rebrin.ad241.mkr2;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

public class WorkoutCalculatorApp extends Application {

    private ObservableList<WorkoutResult> resultsList = FXCollections.observableArrayList();
    private TableView<WorkoutResult> resultsTable;
    private TextField weightField, repsField;
    private ComboBox<String> exerciseCombo, formulaCombo;
    private Label resultLabel, errorLabel;

    @Override
    public void start(Stage primaryStage) {
        BorderPane root = new BorderPane();
        root.setPadding(new Insets(15));
        root.setStyle("-fx-background-color: #f0f0f0;");

        // Заголовок
        Label title = new Label("Калькулятор 1RM (одноразовий максимум)");
        title.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: #333;");
        HBox topBox = new HBox(title);
        topBox.setPadding(new Insets(0, 0, 15, 0));
        root.setTop(topBox);

        // Ліва панель - форма
        VBox inputPanel = createInputPanel();
        root.setLeft(inputPanel);

        // Права панель - таблиця
        VBox tablePanel = createTablePanel();
        root.setCenter(tablePanel);

        Scene scene = new Scene(root, 1000, 600);
        primaryStage.setTitle("Workout Calculator");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private VBox createInputPanel() {
        VBox panel = new VBox(12);
        panel.setPadding(new Insets(15));
        panel.setPrefWidth(320);
        panel.setStyle("-fx-background-color: white; -fx-border-color: #ccc; " +
                "-fx-border-width: 1;");

        // Назва секції
        Label sectionTitle = new Label("Введіть дані:");
        sectionTitle.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: #2c5aa0;");

        // Вправа
        Label exLabel = new Label("Вправа:");
        exLabel.setStyle("-fx-font-weight: bold;");
        exerciseCombo = new ComboBox<>();
        exerciseCombo.getItems().addAll(
                "Жим лежачи", "Присідання", "Станова тяга",
                "Жим стоячи", "Підтягування"
        );
        exerciseCombo.setValue("Жим лежачи");
        exerciseCombo.setMaxWidth(Double.MAX_VALUE);

        // Вага
        Label weightLabel = new Label("Вага (кг):");
        weightLabel.setStyle("-fx-font-weight: bold;");
        weightField = new TextField();
        weightField.setPromptText("Наприклад: 80");

        // Повторення
        Label repsLabel = new Label("Кількість повторень:");
        repsLabel.setStyle("-fx-font-weight: bold;");
        repsField = new TextField();
        repsField.setPromptText("1-12");

        // Формула
        Label formulaLabel = new Label("Формула розрахунку:");
        formulaLabel.setStyle("-fx-font-weight: bold;");
        formulaCombo = new ComboBox<>();
        formulaCombo.getItems().addAll("Brzycki", "Epley", "Lander");
        formulaCombo.setValue("Brzycki");
        formulaCombo.setMaxWidth(Double.MAX_VALUE);

        // Кнопка розрахунку
        Button calculateBtn = new Button("Розрахувати");
        calculateBtn.setMaxWidth(Double.MAX_VALUE);
        calculateBtn.setPrefHeight(35);
        calculateBtn.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; " +
                "-fx-font-size: 14px; -fx-font-weight: bold;");
        calculateBtn.setOnAction(e -> handleCalculate());

        // Результат
        resultLabel = new Label("");
        resultLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; " +
                "-fx-text-fill: #2c5aa0;");
        resultLabel.setWrapText(true);

        // Помилки
        errorLabel = new Label("");
        errorLabel.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
        errorLabel.setWrapText(true);

        // Розділювач
        Separator separator = new Separator();

        // Кнопка експорту
        Button exportBtn = new Button("Експорт у CSV");
        exportBtn.setMaxWidth(Double.MAX_VALUE);
        exportBtn.setStyle("-fx-background-color: #2196F3; -fx-text-fill: white;");
        exportBtn.setOnAction(e -> handleExport());

        panel.getChildren().addAll(
                sectionTitle,
                exLabel, exerciseCombo,
                weightLabel, weightField,
                repsLabel, repsField,
                formulaLabel, formulaCombo,
                calculateBtn,
                resultLabel,
                errorLabel,
                separator,
                exportBtn
        );

        return panel;
    }

    private VBox createTablePanel() {
        VBox panel = new VBox(10);
        panel.setPadding(new Insets(15, 15, 15, 20));

        Label tableTitle = new Label("Історія розрахунків:");
        tableTitle.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: #333;");

        resultsTable = new TableView<>();
        resultsTable.setItems(resultsList);

        // Колонки
        TableColumn<WorkoutResult, String> dateCol = new TableColumn<>("Дата/Час");
        dateCol.setCellValueFactory(new PropertyValueFactory<>("date"));
        dateCol.setPrefWidth(120);

        TableColumn<WorkoutResult, String> exCol = new TableColumn<>("Вправа");
        exCol.setCellValueFactory(new PropertyValueFactory<>("exercise"));
        exCol.setPrefWidth(120);

        TableColumn<WorkoutResult, Double> weightCol = new TableColumn<>("Вага (кг)");
        weightCol.setCellValueFactory(new PropertyValueFactory<>("weight"));
        weightCol.setPrefWidth(80);

        TableColumn<WorkoutResult, Integer> repsCol = new TableColumn<>("Повторення");
        repsCol.setCellValueFactory(new PropertyValueFactory<>("reps"));
        repsCol.setPrefWidth(100);

        TableColumn<WorkoutResult, String> formulaCol = new TableColumn<>("Формула");
        formulaCol.setCellValueFactory(new PropertyValueFactory<>("formula"));
        formulaCol.setPrefWidth(80);

        TableColumn<WorkoutResult, Double> rmCol = new TableColumn<>("1RM (кг)");
        rmCol.setCellValueFactory(new PropertyValueFactory<>("oneRM"));
        rmCol.setPrefWidth(80);

        resultsTable.getColumns().addAll(dateCol, exCol, weightCol, repsCol,
                formulaCol, rmCol);

        panel.getChildren().addAll(tableTitle, resultsTable);
        VBox.setVgrow(resultsTable, Priority.ALWAYS);

        return panel;
    }

    private void handleCalculate() {
        errorLabel.setText("");
        resultLabel.setText("");

        String weightStr = weightField.getText().trim();
        String repsStr = repsField.getText().trim();

        // Валідація
        if (!InputValidator.isValidWeight(weightStr)) {
            errorLabel.setText(InputValidator.getErrorMessage("weight", weightStr));
            return;
        }

        if (!InputValidator.isValidReps(repsStr)) {
            errorLabel.setText(InputValidator.getErrorMessage("reps", repsStr));
            return;
        }

        try {
            double weight = Double.parseDouble(weightStr);
            int reps = Integer.parseInt(repsStr);
            String exercise = exerciseCombo.getValue();
            String formula = formulaCombo.getValue();

            // Розрахунок
            double oneRM = RMCalculator.calculate(formula, weight, reps);

            // Показати результат
            resultLabel.setText(String.format("Ваш 1RM: %.1f кг", oneRM));

            // Додати в таблицю
            WorkoutResult result = new WorkoutResult(exercise, weight, reps,
                    formula, oneRM);
            resultsList.add(result);

            // Очистити поля
            weightField.clear();
            repsField.clear();

        } catch (IllegalArgumentException ex) {
            errorLabel.setText("Помилка: " + ex.getMessage());
        }
    }

    private void handleExport() {
        if (resultsList.isEmpty()) {
            showAlert("Немає даних для експорту");
            return;
        }

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Зберегти результати");
        fileChooser.setInitialFileName("workout_results.csv");
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("CSV Files", "*.csv")
        );

        File file = fileChooser.showSaveDialog(resultsTable.getScene().getWindow());

        if (file != null) {
            try {
                FileExporter.exportToCSV(resultsList, file.getAbsolutePath());
                showAlert("Файл успішно збережено: " + file.getName());
            } catch (Exception e) {
                showAlert("Помилка збереження: " + e.getMessage());
            }
        }
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Повідомлення");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
