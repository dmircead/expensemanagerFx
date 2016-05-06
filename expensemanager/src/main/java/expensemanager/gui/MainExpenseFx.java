package expensemanager.gui;

import java.io.IOException;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import expensemanager.ExpenseManager;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MainExpenseFx extends Application {
	ExpenseManager expenseManager;
	
	public static final Logger LOGGER = Logger.getGlobal();
	
	@Override
	public void start(Stage primaryStage) {
		expenseManager = new ExpenseManager();
		LOGGER.info("ExpenseManager constructed");
		
		expenseManager.load();
		
		
		TabPane first = new TabPane();
		Tab mainTable = new Tab("Expenses Catalog");
		BorderPane borderPane = mainPane(expenseManager);
		mainTable.setContent(borderPane);
		first.getTabs().add(mainTable);

		Scene scene = new Scene(first, 600, 750);
		primaryStage.setTitle("Expense Manager");
		primaryStage.setScene(scene);
		primaryStage.show();
		LOGGER.info("Main Gui constructed");
	}

	@Override
	public void stop() throws Exception {
		expenseManager.save();
		super.stop();
	}

	public static void main(String[] args) {
		configure(LOGGER);
		launch(args);
	}

	public BorderPane mainPane(ExpenseManager expenseManager) {
		BorderPane mainPane = new BorderPane();
		HBox top = new HBox(20);
		VBox table = new TableExpenseFx(expenseManager);
		VBox addEpense = new AddExpenseFx(expenseManager);
		top.getChildren().addAll(table, addEpense);
		mainPane.setTop(top);

		HBox bottom = new HBox(150);
		VBox statistic = new StatisticPaneFx(expenseManager);
		VBox filterExpense = new FilterExpenseFx(expenseManager);

		bottom.setStyle("-fx-padding: 10;" + "-fx-border-style: solid inside;" + "-fx-border-width: 2;"
				+ "-fx-border-insets: 5;" + "-fx-border-radius: 5;" + "-fx-border-color: blue;");
		
		bottom.getChildren().addAll(filterExpense, statistic);
		mainPane.setBottom(bottom);
		return mainPane;
	}
	
	 private static void configure(Logger logger) {
			LOGGER.log(Level.INFO, "method is started with parameter : " + logger.getName());
			Handler consoleHandler;
			Handler fileHandler;
			try {
			    LOGGER.log(Level.WARNING, "Warning level !");
			    consoleHandler = new ConsoleHandler();
			    logger.addHandler(consoleHandler);
			    consoleHandler.setFormatter(new SimpleFormatter());
			    consoleHandler.setLevel(Level.WARNING);
			    fileHandler = new FileHandler("expenses.log");
			    logger.addHandler(fileHandler);
			    fileHandler.setFormatter(new SimpleFormatter());
			    fileHandler.setLevel(Level.ALL);
			} catch (SecurityException | IOException e) {
			    LOGGER.log(Level.SEVERE, "cannot init file logger");
			}
			LOGGER.log(Level.INFO, "method finished");
		    }
}
