package expensemanager.gui;

import java.util.logging.Level;
import java.util.logging.Logger;

import expensemanager.ExpenseManager;
import expensemanager.model.Expense;
import javafx.geometry.Insets;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class TableExpenseFx extends VBox {
	public static final Logger LOGGER = Logger.getLogger(ExpenseManager.class.getName());
	
	public TableExpenseFx(ExpenseManager expenseManager) {
		tableView(expenseManager);
	}

	public void tableView(ExpenseManager expenseManager) {
		LOGGER.log(Level.FINE, "method started");
		VBox left = new VBox(20);
		left.setPadding(new Insets(10));
		TableView<Expense> tabelExpense = expenseManager.tableExpenseMangager();

		RadioButton all = new RadioButton("All");
		RadioButton daily = new RadioButton("Daily");
		RadioButton week = new RadioButton("Weekly");
		RadioButton month = new RadioButton("Monthly");
		RadioButton year = new RadioButton("Year");

		ToggleGroup groupRadio = new ToggleGroup();

		all.setToggleGroup(groupRadio);
		daily.setToggleGroup(groupRadio);
		week.setToggleGroup(groupRadio);
		month.setToggleGroup(groupRadio);
		year.setToggleGroup(groupRadio);

		daily.setOnAction(e -> expenseManager.getDailylist());
		week.setOnAction(e -> expenseManager.getWeekList());
		month.setOnAction(e -> expenseManager.getMonthList());
		year.setOnAction(e -> expenseManager.getYearList());
		all.setOnAction(e -> expenseManager.getAllList());

		HBox groupRadios = new HBox(8);
		groupRadios.getChildren().addAll(all, daily, week, month, year);

		left.getChildren().addAll(tabelExpense, groupRadios);
		this.getChildren().addAll(left);
		LOGGER.log(Level.INFO, "method finished");
	}

}
