/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxapplication15;

import javafx.application.Application;
import javafx.geometry.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.scene.image.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class javafxapplication15 extends Application {

    private BorderPane root;
    private StackPane contentPane;
    private VBox sidebar;
    private Map<String, List<Appointment>> appointmentsMap;
    private ListView<Appointment> appointmentsListView;
    private TextArea detailsTextArea;
    private ComboBox<String> categoryComboBox;
    private DatePicker datePicker;
    private ComboBox<String> hourComboBox;
    private ComboBox<String> minuteComboBox;
    private TextField titleTextField;
    private String currentCategory = "Personal";
    private ImageView logoImageView;

    @Override
    public void start(Stage primaryStage) {
        initializeData();
        setupUI(primaryStage);
        showHomePage();
    }

    private void initializeData() {
        appointmentsMap = new HashMap<>();
        appointmentsMap.put("Personal", new ArrayList<>());
        appointmentsMap.put("Work", new ArrayList<>());
        appointmentsMap.put("General", new ArrayList<>());
        
        appointmentsMap.get("Personal").add(new Appointment("Doctor Appointment", 
            "Annual checkup with Dr. Smith", LocalDate.now().plusDays(2), LocalTime.of(10, 30), "doctor.png"));
        appointmentsMap.get("Work").add(new Appointment("Team Meeting", 
            "Quarterly project review", LocalDate.now().plusDays(1), LocalTime.of(14, 0), "meeting.png"));
    }

    private void setupUI(Stage primaryStage) {
        root = new BorderPane();
        root.setStyle("-fx-background-color: #f5f7fa;");
        
        setupSidebar();
        setupContentPane();
        
        Scene scene = new Scene(root, 1000, 700);
        primaryStage.setTitle("Appointment Manager");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void setupSidebar() {
        sidebar = new VBox(20);
        sidebar.setPadding(new Insets(30, 15, 30, 15));
        sidebar.setStyle("-fx-background-color: #2c3e50;");
        sidebar.setPrefWidth(200);
        
        try {
            Image logoImage = new Image(getClass().getResourceAsStream("/images/images.jpeg"));
            logoImageView = new ImageView(logoImage);
            logoImageView.setFitWidth(80);
            logoImageView.setFitHeight(80);
        } catch (Exception e) {
            logoImageView = new ImageView();
            System.out.println("Image not found: " + e.getMessage());
        }
        
        HBox logoBox = new HBox(logoImageView);
        logoBox.setAlignment(Pos.CENTER);
        
        Button homeBtn = createMenuButton("Home", "home.png");
        Button personalBtn = createMenuButton("Personal", "user.png");
        Button workBtn = createMenuButton("Work", "briefcase.png");
        Button generalBtn = createMenuButton("General", "calendar.png");
        
        homeBtn.setOnAction(e -> showHomePage());
        personalBtn.setOnAction(e -> showCategoryAppointments("Personal"));
        workBtn.setOnAction(e -> showCategoryAppointments("Work"));
        generalBtn.setOnAction(e -> showCategoryAppointments("General"));
        
        sidebar.getChildren().addAll(logoBox, homeBtn, personalBtn, workBtn, generalBtn);
        root.setLeft(sidebar);
    }

    private Button createMenuButton(String text, String iconName) {
        Button button = new Button(text);
        try {
            ImageView icon = new ImageView(new Image(getClass().getResourceAsStream("/images/" + iconName)));
            icon.setFitWidth(20);
            icon.setFitHeight(20);
            button.setGraphic(icon);
        } catch (Exception e) {
            System.out.println("Icon not found: " + iconName);
        }
        button.setStyle("-fx-background-color: transparent; -fx-text-fill: white; -fx-font-size: 14px;");
        button.setAlignment(Pos.CENTER_LEFT);
        button.setContentDisplay(ContentDisplay.LEFT);
        button.setPrefWidth(170);
        button.setPadding(new Insets(10, 0, 10, 15));
        return button;
    }

    private void setupContentPane() {
        contentPane = new StackPane();
        root.setCenter(contentPane);
    }

    private void showHomePage() {
        currentCategory = "Home";
        
        GridPane homeGrid = new GridPane();
        homeGrid.setPadding(new Insets(30));
        homeGrid.setHgap(20);
        homeGrid.setVgap(20);
        homeGrid.setStyle("-fx-background-color: white; -fx-background-radius: 10;");
        
        HBox headerBox = new HBox(10);
        try {
            Image headerImage = new Image(getClass().getResourceAsStream("/images/header.png"));
            ImageView headerImageView = new ImageView(headerImage);
            headerImageView.setFitHeight(40);
            headerImageView.setPreserveRatio(true);
            headerBox.getChildren().add(headerImageView);
        } catch (Exception e) {
            System.out.println("Header image not found");
        }
        
        Label headerLabel = new Label("Appointment Manager");
        headerLabel.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        headerLabel.setTextFill(Color.web("#2c3e50"));
        headerBox.getChildren().add(headerLabel);
        headerBox.setAlignment(Pos.CENTER_LEFT);
        
        GridPane.setColumnSpan(headerBox, 2);
        homeGrid.add(headerBox, 0, 0);
        
        addStatsCard(homeGrid, "Personal Appointments", appointmentsMap.get("Personal").size(), 0, 1);
        addStatsCard(homeGrid, "Work Appointments", appointmentsMap.get("Work").size(), 1, 1);
        addStatsCard(homeGrid, "General Appointments", appointmentsMap.get("General").size(), 0, 2);
        
        Label quickAddLabel = new Label("Quick Add Appointment");
        quickAddLabel.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        GridPane.setColumnSpan(quickAddLabel, 2);
        homeGrid.add(quickAddLabel, 0, 3);
        
        titleTextField = new TextField();
        titleTextField.setPromptText("Title");
        homeGrid.add(titleTextField, 0, 4);
        
        categoryComboBox = new ComboBox<>();
        categoryComboBox.getItems().addAll("Personal", "Work", "General");
        categoryComboBox.setValue("Personal");
        homeGrid.add(categoryComboBox, 1, 4);
        
        datePicker = new DatePicker();
        datePicker.setValue(LocalDate.now());
        homeGrid.add(datePicker, 0, 5);
        
        HBox timeBox = new HBox(10);
        hourComboBox = new ComboBox<>();
        minuteComboBox = new ComboBox<>();
        for (int i = 0; i < 24; i++) hourComboBox.getItems().add(String.format("%02d", i));
        for (int i = 0; i < 60; i += 5) minuteComboBox.getItems().add(String.format("%02d", i));
        hourComboBox.setValue("12");
        minuteComboBox.setValue("00");
        timeBox.getChildren().addAll(hourComboBox, new Label(":"), minuteComboBox);
        homeGrid.add(timeBox, 1, 5);
        
        Button addButton = new Button("Add Appointment");
        addButton.setStyle("-fx-background-color: #3498db; -fx-text-fill: white;");
        addButton.setOnAction(e -> addAppointment());
        GridPane.setColumnSpan(addButton, 2);
        homeGrid.add(addButton, 0, 6);
        
        appointmentsListView = new ListView<>();
        appointmentsListView.setCellFactory(param -> new AppointmentListCell());
        updateAppointmentsList();
        appointmentsListView.setPrefHeight(300);
        GridPane.setColumnSpan(appointmentsListView, 2);
        homeGrid.add(appointmentsListView, 0, 7);
        
        detailsTextArea = new TextArea();
        detailsTextArea.setEditable(false);
        detailsTextArea.setWrapText(true);
        GridPane.setColumnSpan(detailsTextArea, 2);
        homeGrid.add(detailsTextArea, 0, 8);
        
        HBox actionBox = new HBox(10);
        Button editButton = new Button("Edit");
        editButton.setStyle("-fx-background-color: #f39c12; -fx-text-fill: white;");
        editButton.setOnAction(e -> editAppointment());
        
        Button deleteButton = new Button("Delete");
        deleteButton.setStyle("-fx-background-color: #e74c3c; -fx-text-fill: white;");
        deleteButton.setOnAction(e -> deleteAppointment());
        
        actionBox.getChildren().addAll(editButton, deleteButton);
        GridPane.setColumnSpan(actionBox, 2);
        homeGrid.add(actionBox, 0, 9);
        
        appointmentsListView.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                detailsTextArea.setText(newVal.getDetails());
            }
        });
        
        contentPane.getChildren().clear();
        contentPane.getChildren().add(homeGrid);
    }

    private class AppointmentListCell extends ListCell<Appointment> {
        @Override
        protected void updateItem(Appointment appointment, boolean empty) {
            super.updateItem(appointment, empty);
            
            if (empty || appointment == null) {
                setText(null);
                setGraphic(null);
            } else {
                HBox cellBox = new HBox(10);
                cellBox.setAlignment(Pos.CENTER_LEFT);
                
                try {
                    ImageView icon = new ImageView(new Image(getClass().getResourceAsStream("/images/" + appointment.getIconName())));
                    icon.setFitWidth(30);
                    icon.setFitHeight(30);
                    cellBox.getChildren().add(icon);
                } catch (Exception e) {
                    System.out.println("Appointment icon not found: " + appointment.getIconName());
                }
                
                VBox textBox = new VBox(5);
                Label titleLabel = new Label(appointment.getTitle());
                titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14));
                
                Label dateLabel = new Label(appointment.getDate() + " at " + appointment.getTime());
                dateLabel.setFont(Font.font("Arial", 12));
                dateLabel.setTextFill(Color.GRAY);
                
                textBox.getChildren().addAll(titleLabel, dateLabel);
                cellBox.getChildren().add(textBox);
                
                setGraphic(cellBox);
            }
        }
    }

    private void addStatsCard(GridPane grid, String title, int count, int col, int row) {
        VBox card = new VBox(10);
        card.setPadding(new Insets(15));
        card.setStyle("-fx-background-color: #ecf0f1; -fx-background-radius: 8;");
        card.setAlignment(Pos.CENTER);
        
        Label titleLabel = new Label(title);
        titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        
        Label countLabel = new Label(String.valueOf(count));
        countLabel.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        countLabel.setTextFill(Color.web("#3498db"));
        
        card.getChildren().addAll(titleLabel, countLabel);
        grid.add(card, col, row);
    }

    private void showCategoryAppointments(String category) {
        currentCategory = category;
        
        VBox categoryBox = new VBox(20);
        categoryBox.setPadding(new Insets(30));
        categoryBox.setStyle("-fx-background-color: white; -fx-background-radius: 10;");
        
        Label headerLabel = new Label(category + " Appointments");
        headerLabel.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        headerLabel.setTextFill(Color.web("#2c3e50"));
        categoryBox.getChildren().add(headerLabel);
        
        appointmentsListView = new ListView<>();
        appointmentsListView.setCellFactory(param -> new AppointmentListCell());
        updateAppointmentsList();
        appointmentsListView.setPrefHeight(400);
        categoryBox.getChildren().add(appointmentsListView);
        
        detailsTextArea = new TextArea();
        detailsTextArea.setEditable(false);
        detailsTextArea.setWrapText(true);
        categoryBox.getChildren().add(detailsTextArea);
        
        HBox actionBox = new HBox(10);
        Button editButton = new Button("Edit");
        editButton.setStyle("-fx-background-color: #f39c12; -fx-text-fill: white;");
        editButton.setOnAction(e -> editAppointment());
        
        Button deleteButton = new Button("Delete");
        deleteButton.setStyle("-fx-background-color: #e74c3c; -fx-text-fill: white;");
        deleteButton.setOnAction(e -> deleteAppointment());
        
        Button backButton = new Button("Back to Home");
        backButton.setStyle("-fx-background-color: #3498db; -fx-text-fill: white;");
        backButton.setOnAction(e -> showHomePage());
        
        actionBox.getChildren().addAll(editButton, deleteButton, backButton);
        categoryBox.getChildren().add(actionBox);
        
        appointmentsListView.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                detailsTextArea.setText(newVal.getDetails());
            }
        });
        
        contentPane.getChildren().clear();
        contentPane.getChildren().add(categoryBox);
    }

    private void updateAppointmentsList() {
        appointmentsListView.getItems().clear();
        if (currentCategory.equals("Home")) {
            List<Appointment> allAppointments = new ArrayList<>();
            allAppointments.addAll(appointmentsMap.get("Personal"));
            allAppointments.addAll(appointmentsMap.get("Work"));
            allAppointments.addAll(appointmentsMap.get("General"));
            allAppointments.sort((a1, a2) -> a1.getDate().compareTo(a2.getDate()));
            appointmentsListView.getItems().addAll(allAppointments);
        } else {
            appointmentsListView.getItems().addAll(appointmentsMap.get(currentCategory));
        }
    }

    private void addAppointment() {
        String title = titleTextField.getText();
        if (title.isEmpty()) {
            showAlert("Error", "Title cannot be empty");
            return;
        }
        
        String category = categoryComboBox.getValue();
        LocalDate date = datePicker.getValue();
        LocalTime time = LocalTime.of(
            Integer.parseInt(hourComboBox.getValue()),
            Integer.parseInt(minuteComboBox.getValue())
        );
        
        String iconName = category.toLowerCase() + ".png";
        Appointment appointment = new Appointment(title, "", date, time, iconName);
        appointmentsMap.get(category).add(appointment);
        
        titleTextField.clear();
        updateAppointmentsList();
    }

    private void editAppointment() {
        Appointment selected = appointmentsListView.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showAlert("Error", "No appointment selected");
            return;
        }
        
        Dialog<Appointment> dialog = new Dialog<>();
        dialog.setTitle("Edit Appointment");
        
        ButtonType saveButtonType = new ButtonType("Save", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(saveButtonType, ButtonType.CANCEL);
        
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));
        
        TextField editTitleField = new TextField(selected.getTitle());
        TextArea editDetailsArea = new TextArea(selected.getDetails());
        DatePicker editDatePicker = new DatePicker(selected.getDate());
        ComboBox<String> editHourCombo = new ComboBox<>();
        ComboBox<String> editMinuteCombo = new ComboBox<>();
        
        for (int i = 0; i < 24; i++) editHourCombo.getItems().add(String.format("%02d", i));
        for (int i = 0; i < 60; i += 5) editMinuteCombo.getItems().add(String.format("%02d", i));
        editHourCombo.setValue(String.format("%02d", selected.getTime().getHour()));
        editMinuteCombo.setValue(String.format("%02d", selected.getTime().getMinute()));
        
        grid.add(new Label("Title:"), 0, 0);
        grid.add(editTitleField, 1, 0);
        grid.add(new Label("Details:"), 0, 1);
        grid.add(editDetailsArea, 1, 1);
        grid.add(new Label("Date:"), 0, 2);
        grid.add(editDatePicker, 1, 2);
        grid.add(new Label("Time:"), 0, 3);
        
        HBox timeBox = new HBox(5);
        timeBox.getChildren().addAll(editHourCombo, new Label(":"), editMinuteCombo);
        grid.add(timeBox, 1, 3);
        
        dialog.getDialogPane().setContent(grid);
        
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == saveButtonType) {
                selected.setTitle(editTitleField.getText());
                selected.setDetails(editDetailsArea.getText());
                selected.setDate(editDatePicker.getValue());
                selected.setTime(LocalTime.of(
                    Integer.parseInt(editHourCombo.getValue()),
                    Integer.parseInt(editMinuteCombo.getValue())
                ));
                return selected;
            }
            return null;
        });
        
        dialog.showAndWait();
        updateAppointmentsList();
    }

    private void deleteAppointment() {
        Appointment selected = appointmentsListView.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showAlert("Error", "No appointment selected");
            return;
        }
        
        for (List<Appointment> appointments : appointmentsMap.values()) {
            if (appointments.remove(selected)) {
                break;
            }
        }
        
        updateAppointmentsList();
        detailsTextArea.clear();
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }

    private static class Appointment {
        private String title;
        private String details;
        private LocalDate date;
        private LocalTime time;
        private String iconName;

        public Appointment(String title, String details, LocalDate date, LocalTime time, String iconName) {
            this.title = title;
            this.details = details;
            this.date = date;
            this.time = time;
            this.iconName = iconName;
        }

        public String getTitle() { return title; }
        public String getDetails() { 
            return String.format("Title: %s\nDate: %s\nTime: %s\nDetails:\n%s",
                title, date.toString(), time.toString(), details);
        }
        public LocalDate getDate() { return date; }
        public LocalTime getTime() { return time; }
        public String getIconName() { return iconName; }
        
        public void setTitle(String title) { this.title = title; }
        public void setDetails(String details) { this.details = details; }
        public void setDate(LocalDate date) { this.date = date; }
        public void setTime(LocalTime time) { this.time = time; }
    }
}