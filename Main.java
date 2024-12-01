package PhongHop;

import java.util.Scanner;

public class Main {
	public static void main(String[] argv) {
		Scanner s = new Scanner(System.in);
		RoomManager roomManager = new RoomManager();

		for (int i = 0; i < 100; i++) {
			System.out.println(" -----Chọn chức năng-----");
			System.out.println("|    1.Tạo phòng         |");
			System.out.println("|    2.Hiển thị phòng    |");
			System.out.println("|    3.Phòng còn trống   |");
			System.out.println("|    4.Thống kê          |");
			System.out.println("|    5.Thoát             |");
			int n = s.nextInt();

			if (n == 1) {
				createRoom(s, roomManager);
			} else if (n == 2) {
				roomManager.displayRooms();
			} else if (n == 5) {
				System.out.println("Thoát chương trình.");
				break;
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
