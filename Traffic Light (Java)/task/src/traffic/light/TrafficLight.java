package traffic.light;

import java.util.Scanner;
import java.io.IOException;

public class TrafficLight {
	Thread systemThread;
	Thread menuThread;
	CircularQueue queue;
	Scanner scanner = new Scanner(System.in);

	String exit = "";
	int numberOfRoads;
	static int numberOfIntervals;
	int time = 0;
	boolean stopThread = false;

	public void start() {
		System.out.println("Welcome to the traffic management system!");

		numberOfRoads = getInput("Input the number of roads: ");
		numberOfIntervals = getInput("Input the interval: ");

		queue = new CircularQueue(numberOfRoads);
		queue.time = numberOfIntervals;

		String input;
		createMenuThread();
		menuThread.start();
		do {
			exit = "";

			clearScreen();
			printControlPanel();

			input = scanner.nextLine();
			switch (input) {
				case "1" -> addRoad();
				case "2" -> deleteRoad();
				case "3" -> openSystem();
				case "0" -> {
					exit();
					return;
				}

				default -> {
					System.out.println("Incorrect option");
					scanner.nextLine();
				}
			}
		} while (true);
	}

	private void addRoad() {
		System.out.print("Input road name: ");
		String roadName = scanner.nextLine();

		queue.enqueue(roadName);

		scanner.nextLine();
	}

	private void deleteRoad() {
		queue.dequeue();
		scanner.nextLine();
	}

	private void openSystem() {
		menuThread.interrupt();

		stopThread = false;
		exit = null;

		createSystemThread();
		systemThread.start();

		exit = scanner.nextLine();
		stopThread = true;

		createMenuThread();
		menuThread.start();
	}

	private void exit() {
		System.out.println("Bye!");
	}

	private void sleep() {
		try {
			Thread.sleep(1000);
		} catch (InterruptedException ignored) {
		}
	}

	private void createSystemThread() {
		systemThread = new Thread(() -> {
			while (!stopThread) {
				if (exit != null) {
					systemThread.interrupt();
					break;
				}
				clearScreen();

				System.out.printf("! %ds. have passed since system startup !\n", time);
				System.out.printf("! Number of roads: %d !\n", numberOfRoads);
				System.out.printf("! Interval: %d !\n", numberOfIntervals);
				queue.listRoads();
				System.out.println("! Press \"Enter\" to open menu !");

				timer();
			}
		});
	}

	private void timer() {
		sleep();
		time++;
	}

	private void createMenuThread() {
		menuThread = new Thread(this::timer, "QueueThread");
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
		} catch (IOException | InterruptedException ignored) {
		}
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
		} catch (NumberFormatException ime) {
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

			} catch (NumberFormatException ignored) {
			}
		} while (true);
	}
}
