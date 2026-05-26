package org.swetlokognatsk;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.BorderPane;
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

    protected final Label mantissaSizeLabel;
    protected final Slider mantissaSizePicker;
    protected final HBox mantissaBitsBox;

    protected final Label exponentSizeLabel;
    protected final Slider exponentSizePicker;
    protected final HBox exponentBitsBox;

    protected final Label numberLabel;

    protected BorderPane contentArea;

    public static void main(String[] args) {
        launch();
    }

    public App() {
        mantissaSizePicker = buildMantissaSizePicker();
        mantissaSizeLabel = new Label(formatMantissaSize());

        var mantissaSizePickerBox = new VBox(mantissaSizeLabel, mantissaSizePicker);
        mantissaBitsBox = buildMantissaBitsBox();

        var mantissaBox = new VBox(mantissaSizePickerBox, mantissaBitsBox);

        exponentSizePicker = buildExponentSizePicker();
        exponentSizeLabel = new Label(formatExponentSize());

        var exponentSizePickerBox = new VBox(exponentSizeLabel, exponentSizePicker);
        exponentBitsBox = buildExponentBitsBox();

        var exponentBox = new VBox(exponentSizePickerBox, exponentBitsBox);

        var bitsBox = new HBox(exponentBox, mantissaBox);
        bitsBox.setSpacing(0);

        numberLabel = new Label("1937");

        contentArea = new BorderPane();

        contentArea.setTop(bitsBox);
        contentArea.setCenter(numberLabel);
    }

    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(contentArea, WIDTH, HEIGHT);
        stage.setScene(scene);
        stage.show();
    }

    protected String formatMantissaSize() {
        int mantissaSize = (int) mantissaSizePicker.getValue();
        return "mantissa size: " + mantissaSize;
    }

    protected String formatExponentSize() {
        int exponentSize = (int) exponentSizePicker.getValue();
        return "exponent size: " + exponentSize;
    }

    protected Slider buildMantissaSizePicker() {
        var mantissaSizePicker = buildPicker();
        mantissaSizePicker.valueProperty().addListener(e -> {
            mantissaSizeLabel.setText(formatMantissaSize());

            rebuildMantissaBits();
        });
        return mantissaSizePicker;
    }

    protected void rebuildMantissaBits() {
        var mantissaBits = buildMantissaBits();
        var children = mantissaBitsBox.getChildren();
        children.setAll(mantissaBits);
    }

    protected Slider buildPicker() {
        var picker = new Slider(MIN_BITS, MAX_BITS, 32);
        picker.setPrefWidth(WIDTH / 2);
        return picker;
    }

    protected HBox buildMantissaBitsBox() {
        var bitCells = buildMantissaBits();
        var mantissaBits = new HBox(bitCells);

        return mantissaBits;
    }

    protected BitCell[] buildMantissaBits() {
        return buildBitCells((int) mantissaSizePicker.getValue());
    }

    protected Slider buildExponentSizePicker() {
        var exponentSizePicker = buildPicker();

        exponentSizePicker.valueProperty().addListener(e -> {
            exponentSizeLabel.setText(formatExponentSize());

            rebuildExponentBits();
        });
        return exponentSizePicker;
    }

    protected void rebuildExponentBits() {
        var exponentBits = buildExponentBitsBox();
        var children = exponentBitsBox.getChildren();
        children.setAll(exponentBits);
    }

    protected HBox buildExponentBitsBox() {
        var bitCells = buildExponentBits();
        var exponentBits = new HBox(bitCells);

        return exponentBits;
    }

    protected BitCell[] buildExponentBits() {
        return buildBitCells((int) exponentSizePicker.getValue());
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