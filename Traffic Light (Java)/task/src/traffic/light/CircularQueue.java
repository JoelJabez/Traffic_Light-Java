package traffic.light;

import static traffic.light.TrafficLight.numberOfIntervals;

class CircularQueue {
    final private String ANSI_RED = "\u001B[31m";
    final private String ANSI_YELLOW = "\u001B[33m";
    final private String ANSI_GREEN = "\u001B[32m";
    final private String ANSI_RESET = "\u001B[0m";

    private String[] roadArray;

    Thread backgroundThread;
    private final int capacity;
    private int front = 0;
    private int rear = -1;
    int numberOfRoads = 0;
    int frontTime;
    int rearTime;
    int frontIndex;
    int rearIndex;

    CircularQueue(int capacity) {
        this.capacity = capacity;
        roadArray = new String[capacity];
    }

    void trafficLightTimer() {
        if (numberOfRoads > 0) {
            frontTime--;
            rearTime--;

            if (frontTime < 1 || rearTime < 1) {
                if (frontTime < 1) {
                    resetTrafficLightTime();
                }

                frontIndex = (frontIndex + 1) % numberOfRoads;
                rearIndex = (rearIndex + 1) % numberOfRoads;
            }
        }
    }

    void resetTrafficLightTime() {
        frontTime = numberOfIntervals;
        rearTime = numberOfIntervals * (numberOfRoads - 1);
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
        setRange();
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

        setRange();
    }

    void setRange() {
        frontIndex = front;
        rearIndex = rear;

        resetTrafficLightTime();
    }

    void listRoads() {
        if (numberOfRoads != 0) {
            if (frontTime < 1 || rearTime < 1) {
                if (frontTime < 1) {
                    frontTime = numberOfIntervals;
                    rearTime = numberOfIntervals * (numberOfRoads - 1);
                }

                frontIndex = (frontIndex + 1) % numberOfRoads;
                rearIndex = (rearIndex + 1) % numberOfRoads;
            }

            System.out.println();
            if (numberOfRoads == 1) {
                printOpenRoad(front, frontTime);
            } else {
                int index = front;
                for (int i = 0; i < numberOfRoads; i++) {
                    if (i == frontIndex) {
                        printOpenRoad(index, frontTime);
                    } else if (i == rearIndex) {
                        printCloseRoad(index, rearTime);
                    } else {
                        printCloseRoad(index, frontTime);
                    }

                    index = (index + 1) % numberOfRoads;
                }
            }
            System.out.println();
        }
    }

    private void printOpenRoad(int road, int frontTime) {
        if (frontTime <= 2) {
            System.out.printf("%s will be %sopened for %ds.%s\n",
                    roadArray[road], ANSI_YELLOW, frontTime, ANSI_RESET);
        } else {
            System.out.printf("%s will be %sopened for %ds.%s\n",
                    roadArray[road], ANSI_GREEN, frontTime, ANSI_RESET);
        }
    }

    private void printCloseRoad(int road, int frontTime) {
        System.out.printf("%s will be %sclosed for %ds.%s\n",
                roadArray[road], ANSI_RED, frontTime, ANSI_RESET);
    }
}
