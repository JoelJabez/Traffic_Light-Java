package traffic;

import java.io.IOException;
import java.util.Scanner;

public class Main {
    Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        Scanner scanner1 = new Scanner(System.in);
        Main main = new Main();

        System.out.println("Welcome to the traffic management system!");

        main.getInput("Input the number of roads: ");
        main.getInput("Input the interval: ");
        main.clearScreen();

        String input;
        do {
            main.printControlPanel();
            input = scanner1.nextLine();
            switch (input) {
                case "1" -> System.out.println("Road added");
                case "2" -> System.out.println("Road deleted");
                case "3" -> System.out.println("System opened");
                case "0" -> {
                    System.out.println("Bye!");
                    return;
                }
                default -> System.out.println("Incorrect option");
            }

            try {
                scanner1.nextLine();
                Thread.sleep(550);
                main.clearScreen();
            } catch (InterruptedException ignored) {}
        } while (true);
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

    void getInput(String message) {
        String stringNumber;
        int number;
        try {
            System.out.print(message);
            stringNumber = scanner.nextLine();
            number = Integer.parseInt(stringNumber);

            if (number < 1) {
               handleException();
            }
        }
        catch(NumberFormatException ime) {
            handleException();
        }
    }

    void handleException() {
        String stringNumber;
        int number;

        do {
            try {
                System.out.print("Error! Incorrect input. Try again: ");
                stringNumber = scanner.nextLine();
                number = Integer.parseInt(stringNumber);
                if (number >= 1) {
                    return;
                }

            } catch (NumberFormatException ignored) {}
        } while (true);
    }
}
