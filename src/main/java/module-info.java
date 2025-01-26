module org.example {
    requires java.naming;
    requires javafx.controls;
    requires javafx.fxml;
    requires jakarta.persistence;
    requires org.hibernate.orm.core;
    requires java.desktop;

    opens org.example to javafx.fxml;
    exports org.example;

    opens org.example.Entities to org.hibernate.orm.core;
    exports org.example.View;
    opens org.example.View to javafx.fxml;
}
