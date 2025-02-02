package org.example.View;

public enum Scenes {
    ROOT("/org/example/View/layout.fxml"),
    LOGIN("/org/example/View/login.fxml"),
    HUELLA("/org/example/View/añadirhuella.fxml"),
    HABITO("/org/example/View/añadirhabito.fxml"),
    RECOMENDACION("/org/example/View/recomendaciones.fxml"),
    REGISTER("/org/example/View/register.fxml"),
    PANTALLAPRINCIPAL("/org/example/View/PantallaPrincipal2.fxml"),
    ACTUALIZARUSUARIO("/org/example/View/actualizarusuario.fxml"),
    PRIMARY("/org/example/View/welcome.fxml");

    private String url;

    Scenes(String url) {
        this.url = url;
    }

    public String getURL() {
        return url;
    }
}
