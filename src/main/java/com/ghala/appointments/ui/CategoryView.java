package com.ghala.appointments.ui;

import com.ghala.appointments.model.Appointment;
import com.ghala.appointments.model.Category;
import com.ghala.appointments.service.AppointmentService;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.control.Label;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

/**
 * Shows the appointments of a single category, with a link back to the dashboard.
 */
public class CategoryView extends VBox {

    public CategoryView(AppointmentService service, Category category, Runnable onBack) {
        super(20);
        setPadding(new Insets(30));
        setStyle(Styles.PANEL);

        Label header = new Label(category.getDisplayName() + " Appointments");
        header.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        header.setTextFill(Color.web(Styles.TEXT_DARK));

        AppointmentListPanel listPanel =
                new AppointmentListPanel(service, () -> service.findByCategory(category));

        service.getAppointments().addListener(
                (javafx.collections.ListChangeListener<Appointment>) change -> listPanel.refresh());

        Button backButton = new Button("Back to Home");
        backButton.setStyle(Styles.button(Styles.PRIMARY));
        backButton.setOnAction(event -> onBack.run());

        getChildren().addAll(header, listPanel, new HBox(backButton));
    }
}
