package com.ghala.appointments.ui;

import com.ghala.appointments.model.Appointment;
import com.ghala.appointments.model.Category;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

/**
 * Dialogs and small form controls shared by the dashboard and the category views.
 */
public final class AppointmentEditor {

    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("EEEE, d MMMM yyyy");
    private static final DateTimeFormatter TIME_FORMAT = DateTimeFormatter.ofPattern("HH:mm");
    private static final int MINUTE_STEP = 5;

    private AppointmentEditor() {
    }

    /** Hours 00-23. */
    public static ComboBox<String> createHourCombo(int selectedHour) {
        ComboBox<String> combo = new ComboBox<>();
        for (int hour = 0; hour < 24; hour++) {
            combo.getItems().add(String.format("%02d", hour));
        }
        combo.setValue(String.format("%02d", selectedHour));
        return combo;
    }

    /** Minutes in five-minute steps. */
    public static ComboBox<String> createMinuteCombo(int selectedMinute) {
        ComboBox<String> combo = new ComboBox<>();
        for (int minute = 0; minute < 60; minute += MINUTE_STEP) {
            combo.getItems().add(String.format("%02d", minute));
        }
        int rounded = (selectedMinute / MINUTE_STEP) * MINUTE_STEP;
        combo.setValue(String.format("%02d", rounded));
        return combo;
    }

    /** The multi-line summary shown in the details panel. */
    public static String formatDetails(Appointment appointment) {
        return "Title:    " + appointment.getTitle()
                + "\nCategory: " + appointment.getCategory().getDisplayName()
                + "\nDate:     " + appointment.getDate().format(DATE_FORMAT)
                + "\nTime:     " + appointment.getTime().format(TIME_FORMAT)
                + "\n\n" + (appointment.getDetails() == null || appointment.getDetails().isBlank()
                        ? "No further details."
                        : appointment.getDetails());
    }

    /**
     * Opens the edit dialog for an appointment.
     *
     * @return {@code true} if the user saved changes, {@code false} if they cancelled
     */
    public static boolean showEditDialog(Appointment appointment) {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Edit Appointment");
        dialog.setHeaderText("Update the appointment details");

        ButtonType saveButton = new ButtonType("Save", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(saveButton, ButtonType.CANCEL);

        TextField titleField = new TextField(appointment.getTitle());
        TextArea detailsArea = new TextArea(appointment.getDetails());
        detailsArea.setPrefRowCount(4);
        detailsArea.setWrapText(true);

        ComboBox<Category> categoryCombo = new ComboBox<>();
        categoryCombo.getItems().addAll(Category.values());
        categoryCombo.setValue(appointment.getCategory());

        DatePicker datePicker = new DatePicker(appointment.getDate());
        ComboBox<String> hourCombo = createHourCombo(appointment.getTime().getHour());
        ComboBox<String> minuteCombo = createMinuteCombo(appointment.getTime().getMinute());

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20));

        grid.addRow(0, new Label("Title:"), titleField);
        grid.addRow(1, new Label("Category:"), categoryCombo);
        grid.addRow(2, new Label("Date:"), datePicker);
        grid.addRow(3, new Label("Time:"), new HBox(5, hourCombo, new Label(":"), minuteCombo));
        grid.addRow(4, new Label("Details:"), detailsArea);

        dialog.getDialogPane().setContent(grid);

        Optional<ButtonType> result = dialog.showAndWait();
        if (result.isEmpty() || result.get() != saveButton) {
            return false;
        }

        if (titleField.getText() == null || titleField.getText().isBlank()) {
            showError("The title cannot be empty. No changes were saved.");
            return false;
        }

        appointment.setTitle(titleField.getText().trim());
        appointment.setDetails(detailsArea.getText());
        appointment.setCategory(categoryCombo.getValue());
        appointment.setDate(datePicker.getValue());
        appointment.setTime(LocalTime.of(
                Integer.parseInt(hourCombo.getValue()),
                Integer.parseInt(minuteCombo.getValue())));
        return true;
    }

    public static void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static boolean confirmDelete(Appointment appointment) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete Appointment");
        alert.setHeaderText(null);
        alert.setContentText("Delete \"" + appointment.getTitle() + "\"?");
        Optional<ButtonType> result = alert.showAndWait();
        return result.isPresent() && result.get() == ButtonType.OK;
    }
}
