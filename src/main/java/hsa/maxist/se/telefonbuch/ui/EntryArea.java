package hsa.maxist.se.telefonbuch.ui;

import hsa.maxist.se.telefonbuch.data.TelefonEntry;
import hsa.maxist.se.telefonbuch.util.FileUtility;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.*;
import javafx.scene.layout.AnchorPane;
import javafx.util.Callback;

import java.util.List;

public class EntryArea {
    private final AnchorPane anchorPane = new AnchorPane();
    private final TableView<TelefonEntry> tableView;

    public EntryArea(ObservableList<TelefonEntry> telefonEntries) {
        tableView = new TableView<>();

        AnchorPane.setLeftAnchor(tableView, 10.0);
        AnchorPane.setRightAnchor(tableView, 10.0);
        AnchorPane.setTopAnchor(tableView, 0.0);
        AnchorPane.setBottomAnchor(tableView, 0.0);
        anchorPane.getChildren().addAll(tableView);

        Callback<TableColumn<TelefonEntry, String>, TableCell<TelefonEntry, String>> cellFactory = p -> new EditingCell();

        TableColumn<TelefonEntry, String> firstNameCol = new TableColumn<>("First Name");
        firstNameCol.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        firstNameCol.setCellFactory(cellFactory);
        firstNameCol.setOnEditCommit(t -> getCurrentRow(t).setFirstName(t.getNewValue()));

        TableColumn<TelefonEntry, String> lastNameCol = new TableColumn<>("Last Name");
        lastNameCol.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        lastNameCol.setCellFactory(cellFactory);
        lastNameCol.setOnEditCommit(t -> getCurrentRow(t).setLastName(t.getNewValue()));

        TableColumn<TelefonEntry, String> emailCol = new TableColumn<>("Phone Number");
        emailCol.setCellValueFactory(new PropertyValueFactory<>("number"));
        emailCol.setCellFactory(cellFactory);
        emailCol.setOnEditCommit(t -> getCurrentRow(t).setNumber(t.getNewValue()));

        tableView.getColumns().add(firstNameCol);
        tableView.getColumns().add(lastNameCol);
        tableView.getColumns().add(emailCol);
        tableView.setItems(telefonEntries);
        tableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        // --Drag Detected
        tableView.setOnDragDetected(this::onDrag);
        // -- Drag Over
        tableView.setOnDragOver(this::onDragOver);
        // --Drag Dropped
        tableView.setOnDragDropped(t -> onDrop(t, telefonEntries));
        tableView.setEditable(true);
    }

    public void onDrag(MouseEvent t) {
        Dragboard db = tableView.startDragAndDrop(TransferMode.MOVE);
        ClipboardContent content = new ClipboardContent();
        content.putString(
                "[" + this + "]"
                        + FileUtility.asJSONString(tableView.getSelectionModel().getSelectedItems())
        );
        db.setContent(content);
        t.consume();
    }

    public void onDragOver(DragEvent t) {
        if (t.getDragboard().hasString()
                && !t.getDragboard().getString().startsWith("[" + this))
            t.acceptTransferModes(TransferMode.MOVE);
        t.consume();
    }

    public void onDrop(DragEvent t, ObservableList<TelefonEntry> telefonEntries) {
        StringBuilder entries = new StringBuilder(t.getDragboard().getString());
        for (int i = 0; i < entries.length(); i++) {
            if (entries.charAt(i) == ']') {
                entries.delete(0, i + 1);
                break;
            }
        }
        telefonEntries.addAll(FileUtility.fromJson(entries.toString()));
        t.consume();
    }


    public void setItems(List<TelefonEntry> items) {
        if (items instanceof ObservableList) {
            tableView.setItems((ObservableList<TelefonEntry>) items);
        } else {
            tableView.setItems(FXCollections.observableList(items));
        }
    }

    public AnchorPane getAnchorPane() {
        return anchorPane;
    }

    public ObservableList<TelefonEntry> getSelectedEntries() {
        return tableView.getSelectionModel().getSelectedItems();
    }

    private static class EditingCell extends TableCell<TelefonEntry, String> {

        private TextField textField;

        private EditingCell() {
        }

        @Override
        public void startEdit() {
            if (!isEmpty()) {
                super.startEdit();
                createTextField();
                setText(null);
                setGraphic(textField);
                textField.selectAll();
            }
        }

        @Override
        public void cancelEdit() {
            super.cancelEdit();

            setText(getItem());
            setGraphic(null);
        }

        @Override
        public void updateItem(String item, boolean empty) {
            super.updateItem(item, empty);

            if (empty) {
                setText(null);
                setGraphic(null);
            } else {
                if (isEditing()) {
                    if (textField != null) {
                        textField.setText(getString());
                    }
                    setText(null);
                    setGraphic(textField);
                } else {
                    setText(getString());
                    setGraphic(null);
                }
            }
        }

        private void createTextField() {
            textField = new TextField(getString());
            textField.setMinWidth(this.getWidth() - this.getGraphicTextGap() * 2);
            textField.focusedProperty().addListener((arg0, arg1, arg2) -> {
                if (!arg2) {
                    commitEdit(textField.getText());
                }
            });
        }

        private String getString() {
            return getItem() == null ? "" : getItem();
        }
    }

    private static TelefonEntry getCurrentRow(TableColumn.CellEditEvent<TelefonEntry, String> t) {
        return t.getTableView().getItems().get(t.getTablePosition().getRow());
    }
}