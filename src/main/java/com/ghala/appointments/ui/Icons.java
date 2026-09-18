package com.ghala.appointments.ui;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.InputStream;

/**
 * Loads icons from {@code src/main/resources/images}.
 *
 * <p>Every lookup is optional: if the file is absent the method returns {@code null} and the
 * caller falls back to a text-only control, so a missing icon never breaks the UI.</p>
 */
public final class Icons {

    private static final String BASE_PATH = "/images/";

    private Icons() {
    }

    /**
     * @param fileName name of the image inside the images folder, e.g. {@code work.png}
     * @param size     width and height in pixels
     * @return a sized ImageView, or {@code null} if the file could not be loaded
     */
    public static ImageView load(String fileName, double size) {
        try (InputStream stream = Icons.class.getResourceAsStream(BASE_PATH + fileName)) {
            if (stream == null) {
                return null;
            }
            ImageView view = new ImageView(new Image(stream));
            view.setFitWidth(size);
            view.setFitHeight(size);
            view.setPreserveRatio(true);
            return view;
        } catch (Exception exception) {
            return null;
        }
    }
}
