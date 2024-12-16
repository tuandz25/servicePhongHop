
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Room {
	private int ID;
	private String roomID;
	private String type;
	private int capacity;
	private double price;
	private String status;
	private String floor;
	private List<String> utility = new ArrayList<>();

	// constructor
	public Room(int ID, String roomID, String type, int capacity, double price, String status, String floor) {
		this.ID = ID;
		this.roomID = roomID;
		this.type = type;
		this.capacity = capacity;
		this.price = price;
		this.status = status;
		this.floor = floor;
		this.utility = new ArrayList<>();
	}

	public Room() {
	}

	// tạo phòng

	// getter and setter
	public int getID() {
		return ID;
	}

	public void setID(int ID) {
		this.ID = ID;
	}

	public String getRoomID() {
		return roomID;
	}

	public void setRoomID(String name) {
		this.roomID = name;
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

	public String getFloor() {
		return floor;
	}

	public void setFloor(String floor) {
		this.floor = floor;
	}

	public List<String> getUtility() {
		return utility;
	}

	public void setUtility(List<String> utility) {
		this.utility = utility;
	}

	public double getDailyRentalPrice() {
		return price; // Trả về giá thuê theo ngày
	}

	public void displayInfo() {
		String formatID = String.format("%-5s", ID);
		String formatName = String.format("%-16s", roomID);
		String formatType = String.format("%-6s", type);
		String formatCapacity = String.format("%-8s", capacity);
		String formatPrice = String.format("%-17s", price);
		String formatStatus = String.format("%-12s", status);
		String formatFloor = String.format("%-4s", floor);
		String formatUtility = String.format("%-63s", String.join(",", utility));
		System.out.println("||   " + formatID + "|" + formatName + "|" + formatType + "|" + formatCapacity + "|"

				+ formatPrice + "|" + formatStatus + "|" + formatFloor + "|" + formatUtility + "||");

		System.out.println(
				"||--------|----------------|------|--------|-----------------|------------|----|---------------------------------------------------------------||");

	}

	public String toCSV() {
        return ID + "," + roomID + "," + type + "," + capacity + "," + price + "," + status + "," + floor + ","
                + String.join(";", utility);
    }

	public static Room fromCSV(String csv) {
		String[] parts = csv.split(",");
		int ID = Integer.parseInt(parts[0]);
		String name = parts[1];
		String type = parts[2];
		int capacity = Integer.parseInt(parts[3]);
		double price = Double.parseDouble(parts[4]);
		String status = parts[5];
		String floor = parts[6];
		List<String> utility = List.of(parts[7].split(";"));

		Room room = new Room(ID, name, type, capacity, price, status, floor);
		room.setUtility(utility);

		return room;
	}

	public void setInfo() {
		// Ten
		Scanner scan = new Scanner(System.in);
		System.out.println("Nhập tên phòng: ");
		this.roomID = scan.nextLine();
		// loai phong
		System.out.println("Chọn loại phòng:");
		System.out.println("1. Thường");
		System.out.println("2. Vip");
		while (true) {
			try {
				int choose = Integer.parseInt(scan.nextLine());
				switch (choose) {
					case 1:
						type = "Thường";
						utility.add("Bảng trắng");
						utility.add("Máy chiếu cơ bản");
						utility.add("Wifi thông thường");
						break;
					case 2:
						type = "Vip";
						utility.clear();
						utility.add("Hệ thống âm thanh hội nghị");
						utility.add("Màn hình LED lớn");
						utility.add("Ánh sáng điều chỉnh");
						break;
					default:
						System.out.println("Lựa chọn không phù hợp, đầu vào bắt buộc là '1' hoặc '2'");
						continue;
				}
			} catch (NumberFormatException e) {
				System.out.println("Đầu vào bắt buộc là số, vui lòng nhập lại!");
				continue;
			}
			break;
		}

		// capacity(suc chua)
		System.out.println("Nhập sức chứa(chứa tối đa bao nhiêu người):");
		while (true) {
			try {
				this.capacity = Integer.parseInt(scan.nextLine());
			} catch (NumberFormatException e) {
				System.out.println("Dữ liệu không hợp lệ, vui lòng nhập số!");
				continue;
			}
			break;
		}

		// hourlyRate(gia thue theo gio)
		System.out.println("NHập giá thuế theo giờ:");
		while (true) {
			try {
				this.price = Double.parseDouble(scan.nextLine());
			} catch (NumberFormatException e) {
				System.out.println("Dữ liệu không hợp lệ, vui lòng nhập số!");
				continue;
			}
			break;
		}

		// status(trang thai)
		System.out.println("Chọn trạng thái phòng:");
		System.out.println("[1. Trống-------]");
		System.out.println("[2. Đã đạt------]");
		System.out.println("[3. Đang sử dụng]");
		while (true) {
			try {
				int choose = Integer.parseInt(scan.nextLine());
				switch (choose) {
					case 1:
						this.status = "Trống";
						break;
					case 2:
						this.status = "Đã đặt";
						break;
					case 3:
						this.status = "Đang sử dụng";
						break;
					default:
						System.out.println("Lựa chọn không phù hợp, đầu vào bắt buộc là '1' hoặc '2' hoặc '3'");
						continue;
				}

			} catch (NumberFormatException e) {
				System.out.println("Dữ liệu không hợp lệ, vui lòng nhập số!");
				continue;
			}
			break;
		}

		// floor(tang)
		System.out.println("Chọn tầng:");
		System.out.println("[1. Tầng trệt-]");
		System.out.println("[2. Tầng 1----]");
		System.out.println("[3. Tầng 2----]");
		while (true) {
			try {
				int choose = Integer.parseInt(scan.nextLine());
				switch (choose) {
					case 1:
						this.floor = "Trệt";
						break;
					case 2:
						this.floor = "1";
						break;
					case 3:
						this.floor = "2";
						break;
					default:
						System.out.println("Lựa chọn không phù hợp, đầu vào bắt buộc là '1' hoặc '2' hoặc '3'");
						continue;
				}
			} catch (NumberFormatException e) {
				System.out.println("Dữ liệu không hợp lệ, vui lòng nhập số!");
				continue;
			}
			break;
		}
	}

}
