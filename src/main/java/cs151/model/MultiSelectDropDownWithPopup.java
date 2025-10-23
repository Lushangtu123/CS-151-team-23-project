package cs151.model;

import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.geometry.Insets;
import javafx.geometry.Pos;

import java.util.*;

public class MultiSelectDropDownWithPopup extends MultiSelectDropDown {
    private final Map<String, TextField> inputFields = new HashMap<>();

    public MultiSelectDropDownWithPopup(List<String> options) {
        super(); // base layout
        setItems(options);
    }

    @Override
    public void setItems(List<String> options) {
        getCheckboxContainer().getChildren().clear();
        getCheckBoxes().clear();
        inputFields.clear();

        if (options == null || options.isEmpty()) return;

        for (String option : options) {
            CheckBox cb = new CheckBox();
            getCheckBoxes().add(cb);

            Label label = new Label(option);
            TextField field = new TextField();
            field.setPromptText("Enter " + option.toLowerCase());
            field.setPrefWidth(200);
            field.setDisable(true); // initially disabled

            cb.setOnAction(e -> {
                boolean selected = cb.isSelected();
                field.setDisable(!selected);
                if (!selected) field.clear();
            });

            HBox row = new HBox(10, cb, label, field);
            row.setAlignment(Pos.CENTER_LEFT);
            row.setPadding(new Insets(2, 0, 2, 0));

            getCheckboxContainer().getChildren().add(row);
            inputFields.put(option, field);
        }
    }

    public Map<String, String> getSelectedCriteriaWithValues() {
        Map<String, String> result = new HashMap<>();
        for (String key : inputFields.keySet()) {
            TextField field = inputFields.get(key);
            if (!field.isDisabled()) {
                String value = field.getText().trim();
                if (!value.isEmpty()) {
                    result.put(key, value);
                }
            }
        }
        return result;
    }
}
