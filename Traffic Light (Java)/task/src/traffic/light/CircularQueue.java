package traffic.light;

import static traffic.light.TrafficLight.numberOfIntervals;

class CircularQueue {
    final String ANSI_RED = "\u001B[31m";
    final String ANSI_YELLOW = "\u001B[32m";
    final String ANSI_GREEN = "\u001B[32m";
    final String ANSI_RESET = "\u001B[0m";

    private String[] roadArray;

    private final int capacity;
    private int front = 0;
    private int rear = -1;
    private int numberOfRoads = 0;
    int time = numberOfIntervals;
    int roadNumber = 0;
    int counter = 0;

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

    void listRoads() {
        if (numberOfRoads != 0) {
            int road = front;

            System.out.println();
            for (int i = 0; i < numberOfRoads; i++) {
                if (time < 1) {
                    time = numberOfIntervals;
                    roadNumber = roadNumber++ % numberOfRoads;

                    if (numberOfRoads > 1) {
                        counter = counter++ % numberOfRoads;
                    }
                }

                switch (numberOfRoads) {
                    case 1 -> printOpenRoad(road);
                    case 2 -> withTwoRoads(road);
                    case 3 -> {
                        switch (counter % 3) {
                            case 0 -> printOpenRoad(road);
                            case 1 -> printCloseRoad(road);
                            case 2 -> {
                                int tempTime = time;
                                time = time++ % numberOfIntervals;

                                printCloseRoad(road);
                                time = tempTime;
                            }
                        }

                        counter = (counter + 2) % numberOfRoads;
                        roadNumber = (roadNumber + 2) % numberOfRoads;
                    }
                }

                road = road++ % capacity;
            }
            System.out.println();
            time--;
        }
    }

    private void withTwoRoads(int road) {
        if (counter % 2 == 0) {
            printOpenRoad(road);
        } else {
            printCloseRoad(road);
        }

        counter = counter++ % numberOfRoads;
    }

    private void printOpenRoad(int road) {
        if (time <= 2) {
            System.out.printf("%s will be %sopened in %ds%s\n",
                    roadArray[road], ANSI_YELLOW, time, ANSI_RESET);
        } else {
            System.out.printf("%s will be %sopened in %ds%s\n",
                    roadArray[road], ANSI_GREEN, time, ANSI_RESET);
        }
    }

    private void printCloseRoad(int road) {
        System.out.printf("%s will be %sclosed in %ds%s\n",
                roadArray[road], ANSI_RED, time, ANSI_RESET);
    }
}
