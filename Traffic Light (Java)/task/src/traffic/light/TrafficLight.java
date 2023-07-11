package traffic.light;

import java.util.Scanner;
import java.io.IOException;

public class TrafficLight {
	Thread thread;
	Scanner scanner = new Scanner(System.in);
	String state = "";
	int numberOfRoads;
	int numberOfIntervals;
	int time = 0;

	public void start() {
		System.out.println("Welcome to the traffic management system!");

		numberOfRoads = getInput("Input the number of roads: ");
		numberOfIntervals = getInput("Input the interval: ");

		String input;
		do {
			state = "Menu";
			createThread();
			thread.start();

			clearScreen();
			printControlPanel();

			input = scanner.nextLine();
			switch (input) {
				case "1" -> {
					System.out.println("Road added");
					scanner.nextLine();
				}
				case "2" -> {
					System.out.println("Road deleted");
					scanner.nextLine();
				}
				case "3" -> {
					String pattern = "";
					boolean condition;
					do {
						createThread();
						state = "System";
						thread.start();

						condition = scanner.hasNext(pattern);
						sleep(1000);
					} while (!condition);
					clearScreen();
				}
				case "0" -> {
					System.out.println("Bye!");
					return;
				}

				default -> {
					System.out.println("Incorrect option");
					scanner.nextLine();
				}
			}

			sleep(550);
		} while (true);
	}

	private void sleep(int time) {
		try {
			Thread.sleep(time);
		} catch (InterruptedException ignored) {}
	}

	private void createThread() {
		thread = new Thread(() -> {
			try {
				Thread.sleep(1000);
				time++;

				if (state.equals("System")) {
					clearScreen();

					System.out.printf("! %ds. have passed since system startup !\n", time);
					System.out.printf("! Number of roads: %d !\n", numberOfRoads);
					System.out.printf("! Interval: %d !\n", numberOfIntervals);
					System.out.println("! Press \"Enter\" to open menu !");
				}
			} catch (InterruptedException ignored) {}
		});
		thread.setName("QueueThread");
	}

	void printControlPanel() {
		System.out.println("Menu:");
		System.out.println("1. Add road");
		System.out.println("2. Delete road");
		System.out.println("3. Open system");
		System.out.println("0. Quit");
	}

	void clearScreen() {
		try {
			var clearCommand = System.getProperty("os.name").contains("Windows")
				? new ProcessBuilder("cmd", "/c", "cls")
				: new ProcessBuilder("clear");
			clearCommand.inheritIO().start().waitFor();
		}
		catch (IOException | InterruptedException ignored) {}
	}

	int getInput(String message) {
		String stringNumber;
		int number;
		try {
			System.out.print(message);
			stringNumber = scanner.nextLine();
			number = Integer.parseInt(stringNumber);

			if (number < 1) {
				return handleException();
			}
		}
		catch(NumberFormatException ime) {
			return handleException();
		}
		return number;
	}

	int handleException() {
		String stringNumber;
		int number;
		do {
			try {
				System.out.print("Error! Incorrect input. Try again: ");
				stringNumber = scanner.nextLine();
				number = Integer.parseInt(stringNumber);
				if (number >= 1) {
					return number;
				}

			} catch (NumberFormatException ignored) {}
		} while (true);
	}
}
