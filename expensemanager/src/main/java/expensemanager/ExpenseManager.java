package expensemanager;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.Month;
import java.time.Year;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import expensemanager.model.Expense;
import expensemanager.model.Periods;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class ExpenseManager implements Serializable {
	private List<Expense> listExpenses = new ArrayList<>();
	private List<Expense> dailylist;
	private ObservableList<Expense> obsListExpenses;
	private FilteredList<Expense> filteredList;
	private TableView<Expense> tableExpense;
	private double budgetLimit;
	static Logger LOGGER = Logger.getLogger(ExpenseManager.class.getName());

	public void addExpense(String name, double value, LocalDate ld, Periods p) {
		LOGGER.log(Level.INFO, "method is started with parameter :" + name + " " + value + " " + ld + " " + p);
		double budgetMonth = 0;
		Pattern pstring = Pattern.compile("[a-zA-Z]+");
		Matcher mstring = pstring.matcher(name);
		if (!(mstring.find() && mstring.group().equals(name)))
			throw new IllegalArgumentException("Please enter a valid name !");
		LOGGER.log(Level.WARNING, "input name not valid");
		if (value < 0) {
			throw new NumberFormatException("Invalid number");
		}

		Expense expense = new Expense(name, value, ld, p);

		listExpenses.add(expense);
		obsListExpenses.add(expense);
		LOGGER.log(Level.INFO, "method addExpense finished");
	}

	public void getDailylist() {
		LOGGER.log(Level.FINE, "method started");
		filteredList.setPredicate(e -> e.getPeriod() == Periods.DAILY);
		tableExpense.setItems(filteredList);
		LOGGER.log(Level.INFO, "method finished");
	}

	public void getWeekList() {
		LOGGER.log(Level.FINE, "method started");
		filteredList.setPredicate(e -> e.getPeriod() == Periods.WEEK);
		tableExpense.setItems(filteredList);
		LOGGER.log(Level.INFO, "method finished");
	}

	public void getMonthList() {
		LOGGER.log(Level.FINE, "method started");
		filteredList.setPredicate(e -> e.getPeriod() == Periods.MONTH);
		tableExpense.setItems(filteredList);
		LOGGER.log(Level.INFO, "method finished");
	}

	public void getYearList() {
		filteredList.setPredicate(e -> e.getPeriod() == Periods.YEAR);
		tableExpense.setItems(filteredList);
		LOGGER.log(Level.INFO, "method finished");
	}

	public void getAllList() {
		LOGGER.log(Level.FINE, "method started");
		filteredList.setPredicate(e -> true);
		tableExpense.setItems(obsListExpenses);
		LOGGER.log(Level.INFO, "method finished");
	}

	public String filterDates(LocalDate ld1, LocalDate ld2) {
		LOGGER.log(Level.INFO, "method started with parameters " + ld1 + " " + ld2);
		filteredList = new FilteredList<>(
				obsListExpenses.filtered(e -> e.getDate().isAfter(ld1) && e.getDate().isBefore(ld2)));
		List<Expense> datesList = filteredList;
		Expense expense = datesList.stream().max((p1, p2) -> Double.compare(p1.getTotalValue(), p2.getTotalValue()))
				.get();
		tableExpense.setItems(filteredList);
		LOGGER.log(Level.INFO, "method finished");
		return expense.getName() + " " + expense.getTotalValue();

	}

	private List<Expense> getListExpenses() {
		return listExpenses;
	}

	public void save() {
		Persistence storage = new Persistence();
		storage.saveExpenses(listExpenses);
	}

	public void load() {
		Persistence storage = new Persistence();
		listExpenses = storage.loadExpenses();
	}

	public TableView<Expense> tableExpenseMangager() {
		LOGGER.log(Level.INFO, "method started");
		tableExpense = new TableView<>(obsListExpenses);

		TableColumn<Expense, String> nameCol = new TableColumn("Name");
		nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));

		TableColumn<Expense, Integer> valCol = new TableColumn("Value");
		valCol.setCellValueFactory(new PropertyValueFactory<>("value"));

		TableColumn<Expense, LocalDate> dateCol = new TableColumn("Date");
		dateCol.setCellValueFactory(new PropertyValueFactory<>("date"));

		TableColumn<Expense, LocalDate> periodCol = new TableColumn("Period");
		periodCol.setCellValueFactory(new PropertyValueFactory<>("period"));

		TableColumn<Expense, Integer> totCol = new TableColumn("Total");
		totCol.setCellValueFactory(new PropertyValueFactory<>("totalValue"));

		setObservableTable();
		tableExpense.setItems(obsListExpenses);
		tableExpense.getColumns().addAll(nameCol, valCol, dateCol, periodCol, totCol);
		LOGGER.log(Level.INFO, "method finished");

		return tableExpense;
	}

	private ObservableList<Expense> setObservableTable() {
		obsListExpenses = FXCollections.observableArrayList(listExpenses);
		filteredList = new FilteredList<>(obsListExpenses, p -> true);
		return obsListExpenses;
	}

	public void setBudgetLimit(double budget) {
		this.budgetLimit = budget;
	}

	public double getBudgetLimit() {
		return budgetLimit;
	}

	public double monthStatistics(Month month) {
		LOGGER.log(Level.FINE, "method started");
		List<Expense> statisticList = obsListExpenses.filtered(e -> e.getDate().getMonth() == month);
		int sum = 0;
		double value;
		for (Expense e : statisticList) {
			sum += e.getTotalValue();
		}
		LOGGER.log(Level.INFO, "method finished");
		return value = (double) sum + (sum * 5 / 100);
	}

	public double yearStatistics(Year year) {
		LOGGER.log(Level.FINE, "method started");
		System.out.println(year);
		List<Expense> statisticList = obsListExpenses.filtered(e -> e.getDate().getYear() == year.getValue());
		double sum = 0;
		double tot = 0;
		for (Expense e : statisticList) {
			sum += e.getTotalValue();
		}
		LOGGER.log(Level.INFO, "method started");
		return tot = sum + (sum * 5 / 100);
	}

	public ChoiceBox<Periods> choicePeriod() {
		ChoiceBox<Periods> values = new ChoiceBox<Periods>();
		values.getItems().add(Periods.DAILY);
		values.getItems().add(Periods.WEEK);
		values.getItems().add(Periods.MONTH);
		values.getItems().add(Periods.YEAR);
		values.setValue(Periods.DAILY);
		return values;
	}

	public double monthBudget(LocalDate ld) {
		LOGGER.log(Level.FINE, "method started");
		List<Expense> budgetList = obsListExpenses.filtered(e -> e.getDate().getMonth() == ld.getMonth());
		double sum = 0;
		for (Expense e : budgetList) {
			sum += e.getTotalValue();
		}
		LOGGER.log(Level.INFO, "method finished");
		return sum;
	}
}
