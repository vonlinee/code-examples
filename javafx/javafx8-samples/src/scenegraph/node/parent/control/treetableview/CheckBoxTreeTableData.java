package scenegraph.node.parent.control.treetableview;

import javafx.application.Application;
import javafx.beans.property.*;
import javafx.beans.value.ObservableValue;
import javafx.scene.Scene;
import javafx.scene.control.CheckBoxTreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;
import javafx.scene.control.cell.CheckBoxTreeTableCell;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.util.Arrays;
import java.util.List;

public class CheckBoxTreeTableData extends Application {

	public static void main(String[] args) {

		launch(args);

	}

	CheckBoxTreeItem<Employee> rootItem = null;
	TreeTableView<Employee> tree = null;

	/**
	 * @param primaryStage
	 * @throws Exception
	 */
	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage.setTitle("Tree View Sample");
		List<Employee> employees = Arrays.asList(new Employee("Emma Jones", "emma.jones@example.com"),
				new Employee("Michael Brown", "michael.brown@example.com"),
				new Employee("Anna Black", "anna.black@example.com"),
				new Employee("Rodger York", "roger.york@example.com"),
				new Employee("Susan Collins", "susan.collins@example.com"));
		TreeTableColumn<Employee, String> empColumn = new TreeTableColumn<>("Employee");
		empColumn.setPrefWidth(150);
		empColumn.setCellValueFactory(
				(TreeTableColumn.CellDataFeatures<Employee, String> param) -> new ReadOnlyStringWrapper(
						param.getValue().getValue().getName()));

		TreeTableColumn<Employee, String> emailColumn = new TreeTableColumn<>("email");
		emailColumn.setPrefWidth(190);
		emailColumn.setCellValueFactory(
				(TreeTableColumn.CellDataFeatures<Employee, String> param) -> new ReadOnlyStringWrapper(
						param.getValue().getValue().getEmail()));
		
		tree = new TreeTableView<>();
		tree.setEditable(true);

		rootItem = new CheckBoxTreeItem<>(new Employee("Ethan Williams", ""));
		rootItem.setExpanded(true);
		for (Employee employee : employees) {
			final CheckBoxTreeItem<Employee> checkBoxTreeItem = new CheckBoxTreeItem<>(employee);
			rootItem.getChildren().add(checkBoxTreeItem);
			checkBoxTreeItem.getChildren().add(new CheckBoxTreeItem<>(employee));
		}
		tree.setRoot(rootItem);

		tree.setShowRoot(false);
		TreeTableColumn<Employee, Boolean> empColumn2 = new TreeTableColumn<>();
		CheckBoxTreeTableCell checkCell = new CheckBoxTreeTableCell();
		empColumn2.setPrefWidth(100);
		empColumn2.setCellValueFactory(
				(TreeTableColumn.CellDataFeatures<Employee, Boolean> param) -> new ReadOnlyBooleanWrapper(
						param.getValue().getValue().getSelectBoolean()));
		empColumn2.setCellFactory(CheckBoxTreeTableCell.forTreeTableColumn(getSelectedProperty));
		rootItem.setIndependent(false);

		tree.getColumns().setAll(empColumn2, empColumn, emailColumn);
		StackPane root = new StackPane();

		root.getChildren().add(tree);

		primaryStage.setScene(new Scene(root, 300, 250));

		primaryStage.show();
		
	}

	Callback<Integer, ObservableValue<Boolean>> getSelectedProperty = new Callback<Integer, ObservableValue<Boolean>>() {
		@Override
		public ObservableValue<Boolean> call(Integer param) {
			return ((CheckBoxTreeItem<?>) tree.getTreeItem(param)).selectedProperty();
		}
	};

	class Employee {
		private BooleanProperty selectBoolean;
		private SimpleStringProperty name;
		private SimpleStringProperty email;

		public SimpleStringProperty nameProperty() {
			if (name == null) {
				name = new SimpleStringProperty(this, "name");
			}
			return name;
		}

		public SimpleStringProperty emailProperty() {
			if (email == null) {
				email = new SimpleStringProperty(this, "email");
			}
			return email;
		}

		public BooleanProperty selectBooleanProperty() {
			if (selectBoolean == null) {
				selectBoolean = new SimpleBooleanProperty(this, "selectBoolean");
			}
			return selectBoolean;
		}

		public Employee(String name, String email) {
			this.name = new SimpleStringProperty(name);
			this.email = new SimpleStringProperty(email);
			this.selectBoolean = new SimpleBooleanProperty(true);
		}

		public String getName() {
			return name.get();
		}

		public void setName(String fName) {
			name.set(fName);
		}

		public String getEmail() {
			return email.get();
		}

		public void setEmail(String fName) {
			email.set(fName);
		}

		public Boolean getSelectBoolean() {
			return selectBoolean.get();
		}

		public void setSelectBoolean(Boolean select) {
			this.selectBoolean.set(select);
		}
	}

}
