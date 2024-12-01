package PhongHop;

import java.util.Scanner;

public class Main {
	public static void main(String[] argv) {
		Scanner s = new Scanner(System.in);
		RoomManager roomManager = new RoomManager();

		while (true) {
			System.out.println(" -----Chọn chức năng-----");
			System.out.println("|    1.Tạo phòng         |");
			System.out.println("|    2.Hiển thị phòng    |");
			System.out.println("|    3.Xem lịch đặt phòng|");
			System.out.println("|    4.Đặt phòng         |");
			System.out.println("|    5.Dịch vụ phòng     |");
			System.out.println("|    6.Thống kê          |");
			System.out.println("|    7.Thoát             |");
			int n = s.nextInt();
			switch (n) {
			case 1: {
				createRoom(s, roomManager);
				break;
			}
			case 2: {
				roomManager.displayRooms();
				break;
			}
			case 3: {
				break;
			}
			case 4: {
				break;
			}
			case 5: {
				break;
			}
			case 6: {
				break;
			}
			case 7: {
				System.out.println("Thoát chương trình.");
				s.close();
				return;
			}
			default:
				System.out.println("Chức năng không hợp lệ. Vui lòng chọn lại.");
			}
		}
	}

	private static void createRoom(Scanner s, RoomManager roomManager) {
		System.out.println("Nhập tên phòng: ");
		String name = s.next();
		System.out.println("Nhập loại phòng: ");
		String type = s.next();
		System.out.println("Nhập sức chứa : ");
		int capacity = s.nextInt();
		System.out.println("Nhập giá phòng theo giờ: ");
		double price = s.nextDouble();
		System.out.println("Nhập trạng thái phòng: ");
		String status = s.next();
		System.out.println("Nhập số tầng của phòng: ");
		int floor = s.nextInt();

		roomManager.createRoom(name, type, capacity, price, status, floor);

	}
}
