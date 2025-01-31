module org.example {
    requires java.naming;
    requires javafx.controls;
    requires javafx.fxml;
    requires jakarta.persistence;
    requires org.hibernate.orm.core;
    requires java.desktop;

    // Allow reflection for JavaFX and Hibernate
    opens org.example to javafx.fxml;
    opens org.example.Entities to javafx.base, org.hibernate.orm.core; // Add javafx.base for JavaFX reflection

    // Regular exports
    exports org.example;
    exports org.example.View;

    // Allow reflection for FXML controllers
    opens org.example.View to javafx.fxml;
}
