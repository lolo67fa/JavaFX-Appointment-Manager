package com.ghala.appointments;

import com.ghala.appointments.model.Category;
import com.ghala.appointments.service.AppointmentService;
import com.ghala.appointments.ui.CategoryView;
import com.ghala.appointments.ui.DashboardView;
import com.ghala.appointments.ui.Sidebar;
import com.ghala.appointments.ui.Styles;
import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 * Entry point. Builds the window, wires the sidebar to the views, and owns the shared service.
 */
public class AppointmentManagerApp extends Application {

    private static final int WINDOW_WIDTH = 1000;
    private static final int WINDOW_HEIGHT = 700;

    private final AppointmentService service = new AppointmentService();
    private BorderPane root;

    @Override
    public void start(Stage primaryStage) {
        service.loadSampleData();

        root = new BorderPane();
        root.setStyle("-fx-background-color: " + Styles.BACKGROUND + ";");
        root.setLeft(new Sidebar(this::showDashboard, this::showCategory));

        showDashboard();

        Scene scene = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT);
        primaryStage.setTitle("Appointment Manager");
        primaryStage.setScene(scene);
        primaryStage.setMinWidth(800);
        primaryStage.setMinHeight(600);
        primaryStage.show();
    }

    private void showDashboard() {
        setContent(new DashboardView(service));
    }

    private void showCategory(Category category) {
        setContent(new CategoryView(service, category, this::showDashboard));
    }

    private void setContent(Node content) {
        ScrollPane scrollPane = new ScrollPane(content);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background-color: transparent; -fx-background: transparent;");
        BorderPane.setMargin(scrollPane, new javafx.geometry.Insets(20));
        root.setCenter(scrollPane);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
