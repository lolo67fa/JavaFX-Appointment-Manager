package com.ghala.appointments.service;

import com.ghala.appointments.model.Appointment;
import com.ghala.appointments.model.Category;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Comparator;
import java.util.List;

/**
 * Holds every appointment in the application and exposes the operations the views need.
 *
 * <p>The backing list is observable, so any view that listens to it refreshes automatically
 * when an appointment is added or removed.</p>
 *
 * <p>Storage is in-memory for now. Swapping this class for a file- or database-backed
 * implementation is the intended next step - no view code would need to change.</p>
 */
public class AppointmentService {

    private final ObservableList<Appointment> appointments = FXCollections.observableArrayList();

    public ObservableList<Appointment> getAppointments() {
        return appointments;
    }

    public void add(Appointment appointment) {
        appointments.add(appointment);
    }

    public void remove(Appointment appointment) {
        appointments.remove(appointment);
    }

    /** All appointments in one category, earliest first. */
    public List<Appointment> findByCategory(Category category) {
        return appointments.stream()
                .filter(appointment -> appointment.getCategory() == category)
                .sorted(Comparator.comparing(Appointment::getDateTime))
                .toList();
    }

    public long countByCategory(Category category) {
        return appointments.stream()
                .filter(appointment -> appointment.getCategory() == category)
                .count();
    }

    /** Every appointment, earliest first. */
    public List<Appointment> findAllSorted() {
        return appointments.stream()
                .sorted(Comparator.comparing(Appointment::getDateTime))
                .toList();
    }

    /** Two example appointments, so the application is not empty on first launch. */
    public void loadSampleData() {
        add(new Appointment(
                "Doctor Appointment",
                "Annual checkup with Dr. Smith",
                LocalDate.now().plusDays(2),
                LocalTime.of(10, 30),
                Category.PERSONAL));

        add(new Appointment(
                "Team Meeting",
                "Quarterly project review",
                LocalDate.now().plusDays(1),
                LocalTime.of(14, 0),
                Category.WORK));
    }
}
