package org.swetlokognatsk.ui;

import javafx.geometry.Insets;
import org.swetlokognatsk.model.Bit;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;

public class BitCell extends Button {
    protected static final Background one = new Background(new BackgroundFill(Color.rgb(0x00, 0xB4, 0xD8), CornerRadii.EMPTY, Insets.EMPTY));
    protected static final Background zero = new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY));

    protected Bit bit;

    public Bit getBit() {
        return bit;
    }

    public BitCell(Bit bit, int size) {
        super();
        this.bit = bit;

        setText(bitToText());

        setHeight(size);
        setMaxWidth(size);

        setOnAction(this::switchBit);

        textProperty().addListener(e -> {
            BitCell.this.setBackground(getText().intern() == "1" ? one : zero);
        });

        setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(1))));

    }

    protected String bitToText() {
        return String.valueOf(bit.getValue());
    }

    protected void switchBit(ActionEvent e) {
        bit = new Bit(bit.getValue() == 1 ? 0 : 1);
        setText(bitToText());
    }
}
