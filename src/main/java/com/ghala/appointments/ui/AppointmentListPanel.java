package com.ghala.appointments.ui;

import com.ghala.appointments.model.Appointment;
import com.ghala.appointments.service.AppointmentService;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.List;
import java.util.function.Supplier;

/**
 * The list of appointments, the details panel beneath it, and the Edit / Delete buttons.
 *
 * <p>Both the dashboard and the category views embed this panel, so the selection handling
 * and the edit and delete behaviour are written once.</p>
 */
public class AppointmentListPanel extends VBox {

    private final AppointmentService service;
    private final Supplier<List<Appointment>> source;
    private final ListView<Appointment> listView = new ListView<>();
    private final TextArea detailsArea = new TextArea();

    /**
     * @param service the shared appointment store
     * @param source  supplies the appointments this panel should show
     */
    public AppointmentListPanel(AppointmentService service, Supplier<List<Appointment>> source) {
        super(12);
        this.service = service;
        this.source = source;

        listView.setCellFactory(list -> new AppointmentListCell());
        listView.setPrefHeight(320);
        listView.setPlaceholder(new javafx.scene.control.Label("No appointments yet."));

        detailsArea.setEditable(false);
        detailsArea.setWrapText(true);
        detailsArea.setPrefRowCount(5);
        detailsArea.setPromptText("Select an appointment to see its details.");

        listView.getSelectionModel().selectedItemProperty().addListener((observable, previous, selected) ->
                detailsArea.setText(selected == null ? "" : AppointmentEditor.formatDetails(selected)));

        Button editButton = new Button("Edit");
        editButton.setStyle(Styles.button(Styles.WARNING));
        editButton.setOnAction(event -> editSelected());

        Button deleteButton = new Button("Delete");
        deleteButton.setStyle(Styles.button(Styles.DANGER));
        deleteButton.setOnAction(event -> deleteSelected());

        HBox actions = new HBox(10, editButton, deleteButton);
        actions.setPadding(new Insets(5, 0, 0, 0));

        getChildren().addAll(listView, detailsArea, actions);
        refresh();
    }

    /** Reloads the list from the supplier, keeping the current selection where possible. */
    public final void refresh() {
        Appointment selected = listView.getSelectionModel().getSelectedItem();
        listView.setItems(FXCollections.observableArrayList(source.get()));
        if (selected != null && listView.getItems().contains(selected)) {
            listView.getSelectionModel().select(selected);
        } else {
            detailsArea.clear();
        }
    }

    private void editSelected() {
        Appointment selected = listView.getSelectionModel().getSelectedItem();
        if (selected == null) {
            AppointmentEditor.showError("Select an appointment first.");
            return;
        }
        if (AppointmentEditor.showEditDialog(selected)) {
            refresh();
            detailsArea.setText(AppointmentEditor.formatDetails(selected));
        }
    }

    private void deleteSelected() {
        Appointment selected = listView.getSelectionModel().getSelectedItem();
        if (selected == null) {
            AppointmentEditor.showError("Select an appointment first.");
            return;
        }
        if (AppointmentEditor.confirmDelete(selected)) {
            service.remove(selected);
            detailsArea.clear();
            refresh();
        }
    }
}
