package traffic.light;

class CircularQueue {
	private String[] roadArray;
	private final int capacity;
	private int front = 0;
	private int rear = -1;
	private int numberOfRoads = 0;

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
			int index = front;

			System.out.println();
			for (int counter = 0; counter < numberOfRoads; counter++) {
				System.out.println(roadArray[index]);

				index++;
				index %= capacity;
			}
			System.out.println();
		}
	}
}
