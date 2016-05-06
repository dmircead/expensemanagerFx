package expensemanager.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.Month;
import java.time.Year;
import java.time.temporal.ChronoUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

import expensemanager.ExpenseManager;


public class Expense implements Serializable{
	private static final long serialVersionUID = 2L;
	private String name;
	private double value;
	private LocalDate date;
	private Periods period;
	private final Year currentYear = Year.now();
	private int totalValue;
	public static final Logger LOGGER = Logger.getLogger(ExpenseManager.class.getName());
	
	public Expense(String name, double value, LocalDate date, Periods period) {
		this.name = name;
		this.value = value;
		this.date = date;
		this.period = period;
		
	}

	public double total() {
		LOGGER.log(Level.FINE, "method started");
		int currYear = currentYear.getValue();
		LocalDate finalDate = LocalDate.of(currYear, Month.DECEMBER, 31);
			double total = 0;
		if (period == Periods.DAILY) {
			long daysBetween = ChronoUnit.DAYS.between(date, finalDate);
			total = value * (int) daysBetween;
			return total;
		}
		if (period == Periods.WEEK) {
			long daysBetween = ChronoUnit.WEEKS.between(date, finalDate);
			total = value * (int) daysBetween;
			return total;
		}
		if (period == Periods.MONTH) {
			long monthBetween = ChronoUnit.MONTHS.between(date, finalDate);
			total = value * (int) monthBetween;
			return total;
		}
		LOGGER.log(Level.INFO, "method finished");
		return total = value;
	}


	
	//////////////////////////////GETTERS AND SETTERS//////////////////////////////////////////////
	
	public String getName() {
		return name;
	}

	public double getValue() {
		return value;
	}

	public LocalDate getDate() {
		return date;
	}

	public Periods getPeriod() {
		return period;
	}

	public double getTotalValue() {
		return total();
	}


}
