package com.ghala.appointments.ui;

import com.ghala.appointments.model.Category;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.function.Consumer;

/**
 * Navigation panel on the left: the logo, a dashboard link, and one link per category.
 */
public class Sidebar extends VBox {

    private static final double WIDTH = 200;

    public Sidebar(Runnable onHomeSelected, Consumer<Category> onCategorySelected) {
        super(15);
        setPadding(new Insets(30, 15, 30, 15));
        setStyle("-fx-background-color: " + Styles.SIDEBAR + ";");
        setPrefWidth(WIDTH);
        setMinWidth(WIDTH);

        ImageView logo = Icons.load("logo.png", 80);
        if (logo != null) {
            HBox logoBox = new HBox(logo);
            logoBox.setAlignment(Pos.CENTER);
            logoBox.setPadding(new Insets(0, 0, 15, 0));
            getChildren().add(logoBox);
        }

        Button homeButton = createNavButton("Home", "home.png");
        homeButton.setOnAction(event -> onHomeSelected.run());
        getChildren().add(homeButton);

        for (Category category : Category.values()) {
            Button button = createNavButton(category.getDisplayName(), category.getIconName());
            button.setOnAction(event -> onCategorySelected.accept(category));
            getChildren().add(button);
        }
    }

    private Button createNavButton(String text, String iconName) {
        Button button = new Button(text);

        ImageView icon = Icons.load(iconName, 20);
        if (icon != null) {
            button.setGraphic(icon);
            button.setContentDisplay(ContentDisplay.LEFT);
            button.setGraphicTextGap(10);
        }

        String base = "-fx-background-color: transparent;"
                + " -fx-text-fill: white;"
                + " -fx-font-size: 14px;"
                + " -fx-cursor: hand;"
                + " -fx-background-radius: 6;";

        button.setStyle(base);
        button.setOnMouseEntered(event ->
                button.setStyle(base + " -fx-background-color: rgba(255,255,255,0.12);"));
        button.setOnMouseExited(event -> button.setStyle(base));

        button.setAlignment(Pos.CENTER_LEFT);
        button.setPrefWidth(WIDTH - 30);
        button.setPadding(new Insets(10, 0, 10, 15));
        return button;
    }
}
