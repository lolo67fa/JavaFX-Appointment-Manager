package com.ghala.appointments.ui;

import com.ghala.appointments.model.Appointment;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.time.format.DateTimeFormatter;

/**
 * Renders one appointment inside a ListView: category icon, title, and when it happens.
 */
public class AppointmentListCell extends ListCell<Appointment> {

    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("EEE, d MMM yyyy");
    private static final DateTimeFormatter TIME_FORMAT = DateTimeFormatter.ofPattern("HH:mm");

    @Override
    protected void updateItem(Appointment appointment, boolean empty) {
        super.updateItem(appointment, empty);

        if (empty || appointment == null) {
            setText(null);
            setGraphic(null);
            return;
        }

        HBox row = new HBox(12);
        row.setAlignment(Pos.CENTER_LEFT);

        ImageView icon = Icons.load(appointment.getCategory().getIconName(), 30);
        if (icon != null) {
            row.getChildren().add(icon);
        }

        Label titleLabel = new Label(appointment.getTitle());
        titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14));

        Label whenLabel = new Label(appointment.getDate().format(DATE_FORMAT)
                + " at " + appointment.getTime().format(TIME_FORMAT));
        whenLabel.setFont(Font.font("Arial", 12));
        whenLabel.setTextFill(Color.GRAY);

        Label categoryLabel = new Label(appointment.getCategory().getDisplayName());
        categoryLabel.setFont(Font.font("Arial", 11));
        categoryLabel.setTextFill(Color.web(Styles.PRIMARY));

        VBox textColumn = new VBox(4, titleLabel, whenLabel, categoryLabel);
        row.getChildren().add(textColumn);

        setGraphic(row);
    }
}
