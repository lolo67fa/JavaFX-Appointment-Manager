package com.ghala.appointments.model;

/**
 * The categories an appointment can belong to.
 * Each category carries its display name and the icon file used to represent it.
 */
public enum Category {

    PERSONAL("Personal", "personal.png"),
    WORK("Work", "work.png"),
    GENERAL("General", "general.png");

    private final String displayName;
    private final String iconName;

    Category(String displayName, String iconName) {
        this.displayName = displayName;
        this.iconName = iconName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getIconName() {
        return iconName;
    }

    @Override
    public String toString() {
        return displayName;
    }
}
