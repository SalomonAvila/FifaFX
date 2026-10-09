module org.example.fifafx {
    requires javafx.controls;
    requires javafx.fxml;

    opens org.example.fifafx to javafx.fxml, javafx.base;
    exports org.example.fifafx;
}
