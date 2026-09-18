# JavaFX Appointment Manager

A desktop application for managing personal and work appointments, built with **JavaFX**.
Appointments are organised into categories, shown in a sortable list, and can be created, edited,
and deleted from a single window.

تطبيق سطح مكتب لإدارة المواعيد الشخصية والعملية، مبني بـ JavaFX — تنظيم المواعيد حسب الفئة مع إمكانية الإضافة والتعديل والحذف.

---

## Features

- **Dashboard** — appointment counts per category at a glance
- **Quick add** — title, category, date picker, and time selector in one form
- **Categories** — Personal, Work, and General, each with its own view
- **Custom list cells** — every appointment renders with an icon, title, date, and time
- **Edit dialog** — change the title, details, date, or time of an existing appointment
- **Delete** — remove an appointment from any view
- **Sidebar navigation** — switch between the dashboard and each category
- **Sorted listing** — the dashboard orders all appointments by date

> **Note:** appointments are held in memory for the duration of the session. Persistent storage
> is the next item on the roadmap.

---

## Screenshots

<!-- Add screenshots here once you have them:
![Dashboard](docs/screenshots/dashboard.png)
![Category view](docs/screenshots/category.png)
-->

*Coming soon.*

---

## Tech Stack

| Component | Detail |
|---|---|
| Language | Java 21 |
| UI toolkit | JavaFX 21 |
| Build tool | Maven |
| Paradigm | Object-oriented — model, view, and cell rendering separated |

---

## Project Structure

```
JavaFX-Appointment-Manager/
│
├── pom.xml                                  # Maven build and JavaFX dependencies
├── README.md
├── .gitignore
│
└── src/main/
    ├── java/com/ghala/appointments/
    │   ├── AppointmentManagerApp.java       # Entry point, window setup, navigation
    │   │
    │   ├── model/
    │   │   ├── Appointment.java             # Appointment data
    │   │   └── Category.java                # Personal / Work / General
    │   │
    │   ├── service/
    │   │   └── AppointmentService.java      # Stores and queries appointments
    │   │
    │   └── ui/
    │       ├── Sidebar.java                 # Navigation panel
    │       ├── DashboardView.java           # Stats cards, quick-add form, full list
    │       ├── CategoryView.java            # Single-category listing
    │       ├── AppointmentListPanel.java    # List + details + edit/delete, shared
    │       ├── AppointmentListCell.java     # Custom ListView cell renderer
    │       ├── AppointmentEditor.java       # Edit dialog and shared form controls
    │       ├── Icons.java                   # Optional icon loading
    │       └── Styles.java                  # Shared colours and button styles
    │
    └── resources/images/                    # Icons and logo
```

### Design notes

- **`AppointmentService` is the single source of truth.** It exposes an observable list, so the
  dashboard counts and every open list refresh automatically when an appointment changes.
- **`AppointmentListPanel` is shared** by the dashboard and the category views, so selection,
  editing, and deletion are written once rather than duplicated per screen.
- **`Category` is an enum**, not a string, so a typo cannot create a phantom category and each
  category owns its own icon name.
- **Model holds data, UI holds formatting.** `Appointment` has no display logic.

---

## Getting Started

### Requirements

- **JDK 21** or newer
- **Maven 3.8** or newer

JavaFX is pulled in by Maven, so no separate SDK installation is needed.

### Run

```bash
git clone https://github.com/lolo67fa/JavaFX-Appointment-Manager.git
cd JavaFX-Appointment-Manager

mvn clean javafx:run
```

### Build a runnable JAR

```bash
mvn clean package
```

---

## Icons

The UI looks for these files in `src/main/resources/images/`:

| File | Used for |
|---|---|
| `logo.png` | Sidebar logo |
| `home.png` | Dashboard navigation button |
| `personal.png` | Personal category |
| `work.png` | Work category |
| `general.png` | General category |

If an icon is missing the application still runs — buttons fall back to text labels.

---

## Roadmap

- [ ] **Persistence** — save appointments to JSON or SQLite so they survive a restart
- [ ] **Notifications** — reminder before an appointment is due
- [ ] **Search** — filter appointments by title or date range
- [ ] **Validation** — block appointments scheduled in the past
- [ ] **Recurring appointments** — daily, weekly, and monthly repeats
- [ ] **Month calendar view** — alongside the current list view
- [ ] **Theme support** — light and dark modes
- [ ] **Unit tests** — cover the service layer

---

## Author

**Ghala Alshreef**

- GitHub: [@lolo67fa](https://github.com/lolo67fa)
- LinkedIn: [ghala-a-670a62380](https://linkedin.com/in/ghala-a-670a62380)
- Portfolio: [try.ka.nz/ai/ghalaalshreef](https://try.ka.nz/ai/ghalaalshreef)

---

## License

Released under the MIT License.
