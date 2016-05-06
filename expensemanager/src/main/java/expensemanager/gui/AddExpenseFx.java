package expensemanager.gui;

import java.util.logging.Level;
import java.util.logging.Logger;

import expensemanager.ExpenseManager;
import expensemanager.model.Periods;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class AddExpenseFx extends VBox {
	private Alert alert;
	public static final Logger LOGGER = Logger.getLogger(ExpenseManager.class.getName());

	public AddExpenseFx(ExpenseManager expenseManager) {
		AddExpense(expenseManager);
	}

	public void AddExpense(ExpenseManager expenseManager) {
		LOGGER.log(Level.FINE, "method started");
		VBox addExpenseVbox = new VBox(10);
		Label nameLabel = new Label("Name:");
		TextField nameField = new TextField();
		nameField.setPromptText("Name of Expense");
		Label valueLabel = new Label("Value:");
		TextField valueField = new TextField();

		Label dateLabel = new Label("Data");
		DatePicker dp = new DatePicker();
		Label typeLabel = new Label("Type:");
		ChoiceBox<Periods> periodsChoice = expenseManager.choicePeriod();

		Button ok = new Button("Add");
		Button cancel = new Button("Cancel");

		ok.setOnAction(e -> {

			if (nameField.getText().isEmpty() | valueField.getText().isEmpty() | dp.getEditor().getText().isEmpty()) {
				alert = new Alert(AlertType.WARNING);
				alert.setTitle("Validate Fields");
				alert.setContentText("Blank Fields. Cannot add expense !");
				alert.showAndWait();
			} else {
				try {
					expenseManager.addExpense(nameField.getText(), Double.parseDouble(valueField.getText()),
							dp.getValue(), periodsChoice.getValue());
					double total = expenseManager.monthBudget(dp.getValue());

					if (!(expenseManager.getBudgetLimit() == 0)) {
						if (expenseManager.getBudgetLimit() < total) {
							alert = new Alert(AlertType.INFORMATION);
							alert.setTitle("Budget Limit");
							alert.setContentText("Budget exceeded with " + (expenseManager.getBudgetLimit() - total));
							alert.showAndWait();
							LOGGER.log(Level.INFO, "budget exceeded");
						}
					}
					nameField.clear();
					valueField.clear();
					dp.getEditor().clear();

				} catch (NumberFormatException e1) {
					alert = new Alert(AlertType.WARNING);
					alert.setTitle("Incorect Number");
					alert.setContentText(e1.getMessage());
					alert.showAndWait();
					LOGGER.log(Level.SEVERE, "invalid number", e1);
				} catch (IllegalArgumentException e2) {
					alert = new Alert(AlertType.WARNING);
					alert.setTitle("Invalid name");
					alert.setContentText(e2.getMessage());
					alert.showAndWait();
					LOGGER.log(Level.SEVERE, "invalid name", e2);
				}

			}
		});
		cancel.setOnAction(e3 -> {
			nameField.clear();
			valueField.clear();
			dp.getEditor().clear();

		});

		HBox okcancleBtn = new HBox(30);
		okcancleBtn.getChildren().addAll(ok, cancel);

		HBox typeBox = new HBox(15);
		typeBox.getChildren().addAll(typeLabel, periodsChoice);

		addExpenseVbox.setStyle("-fx-padding: 5;" + "-fx-border-style: solid inside;" + "-fx-border-width: 2;"
				+ "-fx-border-insets: 5;" + "-fx-border-radius: 5;" + "-fx-border-color: blue;");
		addExpenseVbox.getChildren().addAll(nameLabel, nameField, valueLabel, valueField, dateLabel, dp, typeBox,
				okcancleBtn);

		VBox budgetVbox = new VBox(10);

		Label budgetLabel = new Label("Budget per month");
		TextField budgetTxt = new TextField();
		Button budgetBtn = new Button("Set");
		budgetVbox.setMargin(budgetLabel, new Insets(30, 0, 10, 0));
		budgetBtn.setOnAction(e -> {
			try {
				expenseManager.setBudgetLimit(Double.parseDouble(budgetTxt.getText()));
			} catch (NumberFormatException e1) {
				alert = new Alert(AlertType.WARNING);
				alert.setTitle("Incorect Number");
				alert.setContentText("Not a valid number");
				alert.showAndWait();
				LOGGER.log(Level.SEVERE, "invalid number", e1);
			}
		});
		budgetVbox.setStyle("-fx-padding: 10;" + "-fx-border-style: solid inside;" + "-fx-border-width: 2;"
				+ "-fx-border-insets: 5;" + "-fx-border-radius: 5;" + "-fx-border-color: blue;");
		budgetVbox.getChildren().addAll(budgetLabel, budgetTxt, budgetBtn);
		this.getChildren().addAll(addExpenseVbox, budgetVbox);
		this.setPadding(new Insets(10));
		LOGGER.log(Level.INFO, "method finished");
	}

}
