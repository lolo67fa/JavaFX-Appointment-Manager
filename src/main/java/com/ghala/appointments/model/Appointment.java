package com.ghala.appointments.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * A single appointment: what it is, when it happens, and which category it belongs to.
 * This class holds data only - formatting for display lives in the UI layer.
 */
public class Appointment {

    private String title;
    private String details;
    private LocalDate date;
    private LocalTime time;
    private Category category;

    public Appointment(String title, String details, LocalDate date, LocalTime time, Category category) {
        this.title = title;
        this.details = details;
        this.date = date;
        this.time = time;
        this.category = category;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    /** Date and time combined - used for chronological sorting. */
    public LocalDateTime getDateTime() {
        return LocalDateTime.of(date, time);
    }

    @Override
    public String toString() {
        return title;
    }
}
