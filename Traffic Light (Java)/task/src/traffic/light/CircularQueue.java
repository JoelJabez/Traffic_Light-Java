package traffic.light;

import static traffic.light.TrafficLight.numberOfIntervals;

class CircularQueue {
    final String ANSI_RED = "\u001B[31m";
    final String ANSI_YELLOW = "\u001B[33m";
    final String ANSI_GREEN = "\u001B[32m";
    final String ANSI_RESET = "\u001B[0m";

    private String[] roadArray;

    private final int capacity;
    private int front = 0;
    private int rear = -1;
    private int numberOfRoads = 0;
    int time = numberOfIntervals;
    int waitTime = numberOfIntervals;
    int frontIndex;
    int rearIndex;

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

        if (numberOfRoads > 2) {
            waitTime = numberOfIntervals * (numberOfRoads - 1);
        }
    }

    void listRoads() {
        if (numberOfRoads != 0) {
            if (time < 1 || waitTime < 1) {
                if (time < 1) {
                    time = numberOfIntervals;
                    waitTime = numberOfIntervals * (numberOfRoads - 1);
                }

                frontIndex = (frontIndex + 1) % numberOfRoads;
                rearIndex = (rearIndex + 1) % numberOfRoads;
            }

            System.out.println();
            if (numberOfRoads == 1) {
                printOpenRoad(front, time);
            } else {
                for (int i = 0; i < numberOfRoads; i++) {
                    if (i == frontIndex) {
                        printOpenRoad(frontIndex, time);
                    } else if (i == rearIndex) {
                        printCloseRoad(rearIndex, waitTime);
                    } else {
                        int index = (i) % numberOfRoads;
                        printCloseRoad(index, time);
                    }
                }
            }
            System.out.println();
            waitTime--;
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
