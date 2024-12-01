package PhongHop;

import java.util.ArrayList;
import java.util.List;

public class Room {
	private String name;
	private String type;
	private int capacity;
	private double price;
	private String status;
	private int floor;
	private List<String> utility;

	// constructor
	public Room(String name, String type, int capacity, double price, String status, int floor) {
		this.name = name;
		this.type = type;
		this.capacity = capacity;
		this.price = price;
		this.status = status;
		this.floor = floor;
		this.utility = new ArrayList<>();
		initializeUtility();
	}

	// tạo phòng
	private void initializeUtility() {
		if (this.type.equalsIgnoreCase("Thường")) {
			utility.add("Bảng tráng");
			utility.add("Máy chiếu cơ bản");
			utility.add("Máy chiếu thông thường");
		} else if (this.type.equalsIgnoreCase("VIP")) {
			utility.clear();
			utility.add("Hệ thống âm thanh hội nghị");
			utility.add("Màn hình LED lớn");
			utility.add("Ánh sáng điều chỉnh");
		}
	}

	// getter and setter
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getCapacity() {
		return capacity;
	}

	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public int getFloor() {
		return floor;
	}

	public void setFloor(int floor) {
		this.floor = floor;
	}

	public List<String> getUtility() {
		return utility;
	}

	public void setUtility(List<String> utility) {
		this.utility = utility;
	}

	public void displayInfo() {
		System.out.println("Tên phòng: " + name);
		System.out.println("Loại phòng: " + type);
		System.out.println("Sức chứa: " + capacity);
		System.out.println("Giá thuê theo giờ: " + price);
		System.out.println("Trạng thái: " + status);
		System.out.println("Tầng: " + floor);
		System.out.println("Tiện ích: " + String.join(", ", utility));
	}

	public String toCSV() {
		return name + "," + type + "," + capacity + "," + price + "," + status + "," + floor + ","
				+ String.join(";", utility);
	}

	public static Room fromCSV(String csv) {
		String[] parts = csv.split(",");
		String name = parts[0];
		String type = parts[1];
		int capacity = Integer.parseInt(parts[2]);
		double price = Double.parseDouble(parts[3]);
		String status = parts[4];
		int floor = Integer.parseInt(parts[5]);
		List<String> utility = List.of(parts[6].split(";"));

		Room room = new Room(name, type, capacity, price, status, floor);
		room.setUtility(utility);

		return room;
	}
}
