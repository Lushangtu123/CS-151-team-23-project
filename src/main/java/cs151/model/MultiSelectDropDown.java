package cs151.model;

import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Popup;
import javafx.geometry.Insets;
import javafx.geometry.Pos;

import java.util.*;
import java.util.stream.Collectors;

public class MultiSelectDropDown extends VBox {
    private final Button dropdownButton = new Button("Multi-Select ▾");
    private final List<CheckBox> checkBoxes = new ArrayList<>();
    private final Popup popup = new Popup();

    public MultiSelectDropDown(List<String> options) {
        dropdownButton.setPrefWidth(140);
        dropdownButton.setOnAction(e -> togglePopup());

        VBox checkboxContainer = new VBox(5);
        checkboxContainer.setPadding(new Insets(10));
        checkboxContainer.setStyle("-fx-background-color: white; -fx-border-color: gray;");
        checkboxContainer.setPrefWidth(200);

        for (String option : options) {
            CheckBox cb = new CheckBox(option);
            checkBoxes.add(cb);
            checkboxContainer.getChildren().add(cb);
        }

        popup.getContent().add(checkboxContainer);
        popup.setAutoHide(true);

        this.getChildren().add(dropdownButton);
        this.setAlignment(Pos.CENTER_LEFT);
    }

    private void togglePopup() {
        if (popup.isShowing()) {
            popup.hide();
        } else {
            popup.show(dropdownButton,
                dropdownButton.localToScreen(0, dropdownButton.getHeight()).getX(),
                dropdownButton.localToScreen(0, dropdownButton.getHeight()).getY());
        }
    }

    public List<String> getSelectedItems() {
        return checkBoxes.stream()
                .filter(CheckBox::isSelected)
                .map(CheckBox::getText)
                .collect(Collectors.toList());
    }

    public void setSelectedItems(List<String> items) {
        for (CheckBox cb : checkBoxes) {
            cb.setSelected(items.contains(cb.getText()));
        }
    }

    public void setDropdownDisabled(boolean disabled) {
        dropdownButton.setDisable(disabled);
        checkBoxes.forEach(cb -> cb.setDisable(disabled));
    }
}
