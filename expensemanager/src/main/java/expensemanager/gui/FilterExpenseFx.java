package expensemanager.gui;

import java.util.logging.Level;
import java.util.logging.Logger;

import expensemanager.ExpenseManager;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class FilterExpenseFx extends VBox {
	public static final Logger LOGGER = Logger.getLogger(ExpenseManager.class.getName());

	public FilterExpenseFx(ExpenseManager expenseManager) {
		filterExpense(expenseManager);
	}

	public void filterExpense(ExpenseManager expenseManager) {
		LOGGER.log(Level.FINE, "method started");
		Label firstDateLabel = new Label("First Date");
		DatePicker dp1 = new DatePicker();
		Label secondDateLabel = new Label("Second Date");
		DatePicker dp2 = new DatePicker();
		Button filterBtn = new Button("Filter");
		Label bigExpenseLabel = new Label();
		Label biggestLabel = new Label("Biggest Expense");
		filterBtn.setOnAction(e -> {
			if (dp1.getEditor().getText().isEmpty() | dp2.getEditor().getText().isEmpty()) {
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("Validate Date");
				alert.setContentText("Blank data fields !");
				alert.showAndWait();
				LOGGER.log(Level.INFO, "data fields blank");

			} else {
				try {
					bigExpenseLabel.setText(expenseManager.filterDates(dp1.getValue(), dp2.getValue()));
				} catch (Exception e1) {
					Alert alert = new Alert(AlertType.WARNING);
					alert.setTitle("Incorect Date");
					alert.setContentText("Not a valid date");
					alert.showAndWait();
				}
			}

		});
		this.getChildren().addAll(firstDateLabel, dp1, secondDateLabel, dp2, filterBtn, biggestLabel, bigExpenseLabel);
		this.setPadding(new Insets(10));
		this.setSpacing(8);
	}
}
