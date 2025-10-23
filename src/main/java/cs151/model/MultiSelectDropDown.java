package cs151.model;

import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Popup;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.Region;

import java.util.*;
import java.util.stream.Collectors;

public class MultiSelectDropDown extends VBox {
    private final Button dropdownButton = new Button("Multi-Select ▾");
    private final List<CheckBox> checkBoxes = new ArrayList<>();
    private final VBox checkboxContainer = new VBox(8);
    private final Popup popup = new Popup();

    public MultiSelectDropDown() {
        setupLayout();
        setupPopup();
    }

    public MultiSelectDropDown(List<String> options) {
        this();
        setItems(options);
    }

    private void setupLayout() {
        dropdownButton.setPrefWidth(Region.USE_COMPUTED_SIZE);
        dropdownButton.setOnAction(e -> togglePopup());

        this.getChildren().add(dropdownButton);
        this.setAlignment(Pos.CENTER_LEFT);
    }

    private void setupPopup() {
        checkboxContainer.setPadding(new Insets(10));
        checkboxContainer.setSpacing(8);
        checkboxContainer.setStyle("-fx-background-color: white; -fx-border-color: gray;");
        checkboxContainer.setPrefWidth(Region.USE_COMPUTED_SIZE);
        checkboxContainer.setPrefHeight(Region.USE_COMPUTED_SIZE);

        ScrollPane scrollPane = new ScrollPane(checkboxContainer);
        scrollPane.setFitToWidth(true);
        scrollPane.setPrefViewportHeight(250); // Optional: cap height
        scrollPane.setStyle("-fx-background-color: white;");

        popup.getContent().clear();
        popup.getContent().add(scrollPane);
        popup.setAutoHide(true);
        popup.setAutoFix(true);
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

    public void setItems(List<String> options) {
        checkboxContainer.getChildren().clear();
        checkBoxes.clear();

        if (options == null || options.isEmpty()) return;

        for (String option : options) {
            CheckBox cb = new CheckBox(option);
            checkBoxes.add(cb);
            checkboxContainer.getChildren().add(cb);
        }
    }

    public List<String> getSelectedItems() {
        return checkBoxes.stream()
                .filter(CheckBox::isSelected)
                .map(CheckBox::getText)
                .collect(Collectors.toList());
    }

    public void setSelectedItems(List<String> items) {
        if (items == null) return;
        for (CheckBox cb : checkBoxes) {
            cb.setSelected(items.contains(cb.getText()));
        }
    }

    public void setDropdownDisabled(boolean disabled) {
        dropdownButton.setDisable(disabled);
        checkBoxes.forEach(cb -> cb.setDisable(disabled));
    }

    // Protected accessors for subclassing
    protected List<CheckBox> getCheckBoxes() {
        return checkBoxes;
    }

    protected VBox getCheckboxContainer() {
        return checkboxContainer;
    }

    protected Popup getPopup() {
        return popup;
    }

    protected Button getDropdownButton() {
        return dropdownButton;
    }
}
