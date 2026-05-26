package org.swetlokognatsk;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.io.IOException;
import org.swetlokognatsk.model.Bit;
import org.swetlokognatsk.ui.BitCell;

public class App extends Application {

    protected static final int WIDTH = 1920;
    protected static final int HEIGHT = 700;

    protected static final int MIN_BITS = 1;
    protected static final int MAX_BITS = 32;
    protected static final int BIT_CELL_SIZE = WIDTH / (MAX_BITS * 2);

    private static Scene scene;

    protected Slider mantissaSizePicker;
    protected Slider exponentSizePicker;

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) throws IOException {
        mantissaSizePicker = buildMantissaSizePicker();
        var mantissaSizePickerBox = new VBox(new Label("mantissa size"), mantissaSizePicker);
        var mantissaBits = buildMantissaBits();
        var mantissaBox = new VBox(mantissaSizePickerBox, mantissaBits);

        exponentSizePicker = buildExponentSizePicker();
        var exponentSizePickerBox = new VBox(new Label("exponent size"), exponentSizePicker);
        var exponentBits = buildExponentBits();
        var exponentBox = new VBox(exponentSizePickerBox, exponentBits);

        var bitsBox = new HBox(mantissaBox, exponentBox);
        bitsBox.setSpacing(0);
        scene = new Scene(bitsBox, WIDTH, HEIGHT);
        stage.setScene(scene);
        stage.show();
    }

    protected Slider buildMantissaSizePicker() {
        var numberSizePicker = buildPicker();
        // TODO handlers
        // numberSizePicker.
        return numberSizePicker;
    }

    protected Slider buildPicker() {
        var picker = new Slider(MIN_BITS, MAX_BITS, 32);
        picker.setPrefWidth(WIDTH / 2);
        return picker;
    }

    protected HBox buildMantissaBits() {
        var bitCells = buildBitCells((int) mantissaSizePicker.getValue());
        var mantissaBits = new HBox(bitCells);

        return mantissaBits;
    }

    protected Slider buildExponentSizePicker() {
        var exponentSizePicker = buildPicker();
        // TODO handlers
        // numberSizePicker.
        return exponentSizePicker;
    }

    protected HBox buildExponentBits() {
        var bitCells = buildBitCells((int) exponentSizePicker.getValue());
        var exponentBits = new HBox(bitCells);

        return exponentBits;
    }

    protected BitCell[] buildBitCells(int size) {
        var bitCells = new BitCell[size];
        for (int i = 0; i < bitCells.length; i++) {
            bitCells[i] = new BitCell(new Bit(0), BIT_CELL_SIZE);
            HBox.setHgrow(bitCells[i], Priority.ALWAYS);
        }
        return bitCells;
    }
}