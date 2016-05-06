package expensemanager.gui;

import java.time.Month;
import java.time.Year;
import java.util.logging.Level;
import java.util.logging.Logger;

import expensemanager.ExpenseManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class StatisticPaneFx extends VBox {
	private Year year;
	private Month month;
	public static final Logger LOGGER = Logger.getLogger(ExpenseManager.class.getName());
	
	public StatisticPaneFx(ExpenseManager expenseManager) {
		StatisticPane(expenseManager);
	}

	public void StatisticPane(ExpenseManager expenseManager) {
		LOGGER.log(Level.FINE, "method started");
		VBox wrapperBox = new VBox(10);
		HBox monthHbox = new HBox(10);
		Month[] months = month.values();
		ObservableList<Month> obsMonth = FXCollections.observableArrayList(months);
		ChoiceBox<Month> monthsChoice = new ChoiceBox<>(obsMonth);
		Label monthLabel = new Label("Forecast per month:");
		TextField displayMonthLabel = new TextField();
		displayMonthLabel.setEditable(false);
		monthsChoice.setValue(obsMonth.get(0));
		monthsChoice.setOnAction(e -> displayMonthLabel
				.setText(String.valueOf(expenseManager.monthStatistics(monthsChoice.getValue()))));
		monthHbox.getChildren().addAll(monthsChoice, displayMonthLabel);

		HBox yearBox = new HBox(10);
		Label yearLabel = new Label("Forecast per year:");
		Button yearBtn = new Button("Next year");

		TextField displayYearField = new TextField();
		displayYearField.setEditable(false);
		yearBtn.setOnAction(e -> displayYearField.setText(String.valueOf(expenseManager.yearStatistics(year.now()))));
		yearBox.getChildren().addAll(yearBtn, displayYearField);
		wrapperBox.setPadding(new Insets(10));
		wrapperBox.getChildren().addAll(monthLabel, monthHbox, yearLabel, yearBox);

		this.getChildren().add(wrapperBox);
		LOGGER.log(Level.INFO, "method finished");
	}
}
