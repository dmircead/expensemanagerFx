/**
 * 
 */
package expensemanager;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import expensemanager.model.Expense;

/**
 * @author mco
 *
 */
public class Persistence {

	/**
	 * logger for this class
	 */
	public static final Logger LOGGER = Logger.getGlobal();

	private Path file = Paths.get("expenses.data");

	public void saveExpenses(List<Expense> expenses) {
		LOGGER.fine("started saving expenses " + expenses + " to file " + file);
		try (OutputStream os = Files.newOutputStream(file); ObjectOutputStream oos = new ObjectOutputStream(os)) {
			oos.writeObject(expenses);
			LOGGER.info("Expenses list saved");
		} catch (IOException e) {
			LOGGER.warning("problem saving expenses data " + e.getMessage());
		}
	}

	public List<Expense> loadExpenses() {
		LOGGER.fine("started loading list of expenses");
		List<Expense> expenseManager = new ArrayList<>();
		if (Files.exists(file)) {
			try (InputStream is = Files.newInputStream(file); ObjectInputStream ois = new ObjectInputStream(is)) {
				expenseManager = (List<Expense>) ois.readObject();
				LOGGER.info("Expenses list loaded");
			} catch (IOException e) {
				LOGGER.warning("problem loading expenses data " + e.getMessage());
			} catch (ClassNotFoundException e) {
				LOGGER.warning("problem loading expenses data " + e.getMessage());
			}
		}
		return expenseManager;
	}

}
