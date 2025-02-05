module org.example {
    requires java.naming;
    requires javafx.controls;
    requires javafx.fxml;
    requires jakarta.persistence;
    requires org.hibernate.orm.core;
    requires java.desktop;





    opens org.example to javafx.fxml;
    opens org.example.Entities to javafx.base, org.hibernate.orm.core;
    opens org.example.Utils;

    exports org.example;
    exports org.example.View;


    opens org.example.View to javafx.fxml;
}
