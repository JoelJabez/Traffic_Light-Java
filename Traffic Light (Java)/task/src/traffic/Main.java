package traffic;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to the traffic management system!");

        System.out.print("Input the number of roads: ");
        int numberOfRoads = scanner.nextInt();

        System.out.print("Input the interval: ");
        int interval = scanner.nextInt();

        int input;
        do {
            printControlPanel();
            input = scanner.nextInt();
            switch (input) {
                case 1 -> System.out.println("Road added");
                case 2 -> System.out.println("Road deleted");
                case 3 -> System.out.println("System opened");
            }
        } while (input != 0);
    }

    static void printControlPanel() {
        System.out.println("Menu:");
        System.out.println("1. Add road");
        System.out.println("2. Delete road");
        System.out.println("3. Open system");
        System.out.println("0. Quit");
    }
}
