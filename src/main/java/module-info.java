module org.swetlokognatsk {
    requires javafx.controls;
    requires javafx.fxml;

    opens org.swetlokognatsk to javafx.fxml;
    exports org.swetlokognatsk;
}
