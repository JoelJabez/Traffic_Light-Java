package traffic.light;

import static traffic.light.TrafficLight.numberOfIntervals;

class CircularQueue {
    private final String ANSI_RED = "\u001B[31m";
    private final String ANSI_YELLOW = "\u001B[33m";
    private final String ANSI_GREEN = "\u001B[32m";
    private final String ANSI_RESET = "\u001B[0m";

    private String[] roadArray;

    private final int capacity;
    private int front = 0;
    private int rear = -1;
    private int frontIndex;
    private int rearIndex;
    int time;
    int longTime = numberOfIntervals;
    int formula = numberOfIntervals;
    int numberOfRoads = 0;

    CircularQueue(int capacity) {
        this.capacity = capacity;
        roadArray = new String[capacity];
    }

    void enqueue(String road) {
        int index = (rear + 1) % capacity;

        if (numberOfRoads == capacity) {
            System.out.println("queue is full");
            return;
        }
        if (front >= capacity) {
            front = 0;
        }

        roadArray[index] = road;
        numberOfRoads++;
        rear++;

        System.out.println(road + " Added!");
    }

    void dequeue() {
        if (numberOfRoads == 0) {
            System.out.println("queue is empty");
            return;
        }
        int index = front % capacity;

        System.out.println(roadArray[index] + " deleted!");

        front++;
        numberOfRoads--;
    }

    void setRange() {
        frontIndex = front;
        rearIndex = rear;
    }

    void listRoads() {
        if (numberOfRoads != 0) {
            if (time < 1 || longTime < 1) {
                frontIndex = (frontIndex + 1) % numberOfRoads;
                rearIndex = (rearIndex + 1) % numberOfRoads;

                if (time < 1) {
                    time = numberOfIntervals;
                    longTime = formula;
                }
            }

            System.out.println();
            if (numberOfRoads == 1) {
                printOpenRoad(front, time);
            } else {
                for (int i = 0; i < numberOfRoads; i++) {
                    if (i == frontIndex) {
                        printOpenRoad(frontIndex, time);
                    } else if (i == rearIndex) {
                        printCloseRoad(rearIndex, longTime);
                    } else {
                        int tempIndex = frontIndex;
                        frontIndex = (frontIndex + 1) % numberOfRoads;
                        do {
                            printCloseRoad(frontIndex, time);
                            frontIndex = (frontIndex + 1) % numberOfRoads;
                        } while (frontIndex != rearIndex);
                        frontIndex = tempIndex;
                    }
                }
            }
            System.out.println();
            longTime--;
            time--;
        }
    }

    private void printOpenRoad(int road, int time) {
        if (time <= 2) {
            System.out.printf("%s will be %sopened for %ds.%s\n",
                    roadArray[road], ANSI_YELLOW, time, ANSI_RESET);
        } else {
            System.out.printf("%s will be %sopened for %ds.%s\n",
                    roadArray[road], ANSI_GREEN, time, ANSI_RESET);
        }
    }

    private void printCloseRoad(int road, int time) {
        System.out.printf("%s will be %sclosed for %ds.%s\n",
                roadArray[road], ANSI_RED, time, ANSI_RESET);
    }
}
