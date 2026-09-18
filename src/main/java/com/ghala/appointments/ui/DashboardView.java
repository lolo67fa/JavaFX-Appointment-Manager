package com.ghala.appointments.ui;

import com.ghala.appointments.model.Appointment;
import com.ghala.appointments.model.Category;
import com.ghala.appointments.service.AppointmentService;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.EnumMap;
import java.util.Map;

/**
 * The home screen: per-category counts, a quick-add form, and the full appointment list.
 */
public class DashboardView extends VBox {

    private final AppointmentService service;
    private final Map<Category, Label> countLabels = new EnumMap<>(Category.class);
    private final AppointmentListPanel listPanel;

    private TextField titleField;
    private ComboBox<Category> categoryCombo;
    private DatePicker datePicker;
    private ComboBox<String> hourCombo;
    private ComboBox<String> minuteCombo;

    public DashboardView(AppointmentService service) {
        super(20);
        this.service = service;

        setPadding(new Insets(30));
        setStyle(Styles.PANEL);

        listPanel = new AppointmentListPanel(service, service::findAllSorted);

        getChildren().addAll(
                createHeader(),
                createStatsRow(),
                createQuickAddForm(),
                listPanel);

        // Counts and list stay in step with the store, however the change was made.
        service.getAppointments().addListener((javafx.collections.ListChangeListener<Appointment>) change -> {
            updateCounts();
            listPanel.refresh();
        });

        updateCounts();
    }

    private HBox createHeader() {
        Label title = new Label("Appointment Manager");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        title.setTextFill(Color.web(Styles.TEXT_DARK));

        HBox header = new HBox(10, title);
        header.setAlignment(Pos.CENTER_LEFT);
        return header;
    }

    private HBox createStatsRow() {
        HBox row = new HBox(20);
        for (Category category : Category.values()) {
            row.getChildren().add(createStatsCard(category));
        }
        return row;
    }

    private VBox createStatsCard(Category category) {
        Label titleLabel = new Label(category.getDisplayName() + " Appointments");
        titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 13));

        Label countLabel = new Label("0");
        countLabel.setFont(Font.font("Arial", FontWeight.BOLD, 26));
        countLabel.setTextFill(Color.web(Styles.PRIMARY));
        countLabels.put(category, countLabel);

        VBox card = new VBox(8, titleLabel, countLabel);
        card.setPadding(new Insets(15));
        card.setAlignment(Pos.CENTER);
        card.setStyle("-fx-background-color: " + Styles.CARD + "; -fx-background-radius: 8;");
        HBox.setHgrow(card, Priority.ALWAYS);
        card.setMaxWidth(Double.MAX_VALUE);
        return card;
    }

    private VBox createQuickAddForm() {
        Label heading = new Label("Quick Add Appointment");
        heading.setFont(Font.font("Arial", FontWeight.BOLD, 16));

        titleField = new TextField();
        titleField.setPromptText("Title");
        HBox.setHgrow(titleField, Priority.ALWAYS);

        categoryCombo = new ComboBox<>();
        categoryCombo.getItems().addAll(Category.values());
        categoryCombo.setValue(Category.PERSONAL);

        datePicker = new DatePicker(LocalDate.now());

        LocalTime now = LocalTime.now();
        hourCombo = AppointmentEditor.createHourCombo(now.getHour());
        minuteCombo = AppointmentEditor.createMinuteCombo(now.getMinute());

        Button addButton = new Button("Add Appointment");
        addButton.setStyle(Styles.button(Styles.PRIMARY));
        addButton.setOnAction(event -> addAppointment());

        HBox firstRow = new HBox(10, titleField, categoryCombo);
        HBox secondRow = new HBox(10, datePicker,
                new HBox(5, hourCombo, new Label(":"), minuteCombo), addButton);
        secondRow.setAlignment(Pos.CENTER_LEFT);

        return new VBox(10, heading, firstRow, secondRow);
    }

    private void addAppointment() {
        String title = titleField.getText();
        if (title == null || title.isBlank()) {
            AppointmentEditor.showError("The title cannot be empty.");
            return;
        }
        if (datePicker.getValue() == null) {
            AppointmentEditor.showError("Pick a date for the appointment.");
            return;
        }

        LocalTime time = LocalTime.of(
                Integer.parseInt(hourCombo.getValue()),
                Integer.parseInt(minuteCombo.getValue()));

        service.add(new Appointment(
                title.trim(), "", datePicker.getValue(), time, categoryCombo.getValue()));

        titleField.clear();
    }

    private void updateCounts() {
        countLabels.forEach((category, label) ->
                label.setText(String.valueOf(service.countByCategory(category))));
    }
}
