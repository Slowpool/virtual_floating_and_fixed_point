package org.swetlokognatsk;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.io.IOException;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.stream.Stream;
import org.swetlokognatsk.model.Bit;
import org.swetlokognatsk.model.Exponent;
import org.swetlokognatsk.model.Mantissa;
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

    protected final TextField basisField;
    protected final HBox basisBox;

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

        var bitsBox = new HBox(mantissaBox, exponentBox);
        bitsBox.setSpacing(0);

        basisField = buildBasisField();
        basisBox = new HBox(new Label("basis:"), basisField);
        basisBox.setSpacing(10);
        basisBox.setAlignment(Pos.CENTER);

        var inputBox = new VBox(bitsBox, basisBox);

        contentArea = new BorderPane();
        contentArea.setTop(inputBox);

        numberLabel = new Label("0");
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
            calculateNumber();
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
        mantissaBits.setAlignment(Pos.CENTER_RIGHT);

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
            calculateNumber();
        });
        return exponentSizePicker;
    }

    protected void rebuildExponentBits() {
        var exponentBits = buildExponentBits();
        var children = exponentBitsBox.getChildren();
        children.setAll(exponentBits);
    }

    protected HBox buildExponentBitsBox() {
        var bitCells = buildExponentBits();
        var exponentBits = new HBox(bitCells);
        exponentBits.setAlignment(Pos.CENTER_RIGHT);

        return exponentBits;
    }

    protected BitCell[] buildExponentBits() {
        return buildBitCells((int) exponentSizePicker.getValue());
    }

    protected BitCell[] buildBitCells(int size) {
        var bitCells = new BitCell[size];
        BitCell bitCell;
        for (int i = 0; i < bitCells.length; i++) {
            bitCell = new BitCell(new Bit(0), BIT_CELL_SIZE);
            bitCell.setOnAction(e -> {
                ((BitCell) e.getTarget()).switchBit();
                calculateNumber();
            });
            bitCells[i] = bitCell;

            HBox.setHgrow(bitCells[i], Priority.ALWAYS);
        }
        return bitCells;
    }

    protected TextField buildBasisField() {
        var basisField = new TextField();
        basisField.textProperty().addListener(e -> {
            calculateNumber();
        });
        return basisField;
    }

    protected void calculateNumber() {
        try {
            var mantissa = calculateMantissa();
            var basis = getBasis();
            var exponent = calculateExponent();

            BigInteger beforePointNumber = calculateBeforePoint(mantissa, basis, exponent);
            BigInteger afterPointNumber = calculateAfterPoint(mantissa, basis, exponent);

            var number = formatFloatingPointParts(beforePointNumber, afterPointNumber);
            numberLabel.setText(number);
        } catch (Exception e) {
        }
    }

    protected int calculateMantissa() {
        var bitCells = mantissaBitsBox.getChildren();
        var mantissaBits = getBitsFromCells(bitCells);

        var exponent = new Mantissa(mantissaBits);

        return exponent.getNumber();
    }

    protected Bit[] getBitsFromCells(ObservableList<Node> bitCells) {
        Stream<BitCell> stream = Stream.of(bitCells.toArray(BitCell[]::new));
        var bits = stream.map((BitCell bitCell) -> bitCell.getBit()).toArray(Bit[]::new);
        return bits;
    }

    protected int getBasis() {
        int basis;
        try {
            basis = Integer.valueOf(basisField.getText());
        } catch (Exception e) {
            var dialog = new Dialog<Void>();
            dialog.show();
            var dialogPane = new DialogPane();
            dialogPane.setContentText("wrong basis");
            dialogPane.getButtonTypes().add(ButtonType.OK);
            dialog.setDialogPane(dialogPane);
            basis = 10;
        }

        return basis;
    }

    protected int calculateExponent() {
        var bitCells = exponentBitsBox.getChildren();
        var exponentBits = getBitsFromCells(bitCells);

        var exponent = new Exponent(exponentBits);

        return exponent.getNumber();
    }

    protected BigInteger calculateBeforePoint(int mantissa, int basis, int exponent) {
        // TODO
        return BigInteger.valueOf((int) (Math.random() * 1000));
    }

    protected BigInteger calculateAfterPoint(int mantissa, int basis, int exponent) {
        // TODO
        return BigInteger.valueOf((int) (Math.random() * 1000));
    }

    protected String formatFloatingPointParts(BigInteger beforePointNumber, BigInteger afterPointValue) {
        var formattedNumber = String.format("%s.%s", beforePointNumber, afterPointValue);
        return formattedNumber;
    }
}