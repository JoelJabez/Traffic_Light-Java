package traffic.light;

class CircularQueue {
	private String[] array;
	private final int capacity;
	private int front = 0;
	private int rear = -1;
	private int count = 0;

	CircularQueue(int capacity) {
		this.capacity = capacity;
		array = new String[capacity];
	}

	void enqueue(String road) {
		int index = (rear + 1) % capacity;

		if (count == capacity) {
			System.out.println("queue is full");
			return;
		}

		array[index] = road;
		count++;
		rear++;

		System.out.println(road + " Added!");
	}

	void dequeue() {
		if (count == 0) {
			System.out.println("queue is empty");
			return;
		}
		int index = front % capacity;

		System.out.println(array[index] + " deleted!");
		front++;
		count--;
	}

	void get() {
		for (int i = front; i <= rear; i++) {
			if (i == capacity) {
				i = 0;
			}

			System.out.println(array[i]);
		}
	}
}
