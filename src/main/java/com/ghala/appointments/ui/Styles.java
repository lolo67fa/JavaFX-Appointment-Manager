package com.ghala.appointments.ui;

/**
 * Shared colours and control styles, kept in one place so the palette stays consistent.
 */
public final class Styles {

    public static final String BACKGROUND = "#f5f7fa";
    public static final String SIDEBAR = "#2c3e50";
    public static final String CARD = "#ecf0f1";
    public static final String PRIMARY = "#3498db";
    public static final String WARNING = "#f39c12";
    public static final String DANGER = "#e74c3c";
    public static final String TEXT_DARK = "#2c3e50";

    public static final String PANEL =
            "-fx-background-color: white; -fx-background-radius: 10;";

    private Styles() {
    }

    /** A filled button style in the given hex colour. */
    public static String button(String colour) {
        return "-fx-background-color: " + colour + ";"
                + " -fx-text-fill: white;"
                + " -fx-font-size: 13px;"
                + " -fx-background-radius: 6;"
                + " -fx-padding: 8 16 8 16;"
                + " -fx-cursor: hand;";
    }
}
