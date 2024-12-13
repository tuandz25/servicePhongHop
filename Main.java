package PhongHop;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class Main {

	public static void main(String[] argv) {
		Scanner s = new Scanner(System.in);
		RoomManager roomManager = new RoomManager();
		String passCode = "123456";
		boolean result;
		while (true) {
			result = true;
			System.out.println(" +===========================================================+");
			System.out.println("||                                                           ||");
			System.out.println("||                Chào mừng quý khách đến với...             ||");
			System.out.println("||                                                           ||");
			System.out.println("||     _________________                  _________________  ||");
			System.out.println("||    /1.Quản lý phòng /                 /  2.Thuê phòng  /  ||");
			System.out.println("||   /   họp(admin)   /                 /       họp      /   ||");
			System.out.println("||  /________________/                 /________________/    ||");
			System.out.println("||                                                           ||");
			System.out.println("||              >>NHẤN SỐ BẤT KỲ KHÁC ĐỂ THOÁT<<             ||");
			System.out.println("||                                                           ||");
			System.out.println(" +===========================================================+");
			System.out.print("Lựa chọn của bạn là:");
			int num = s.nextInt();
			switch (num) {
			case 1: {
				System.out.print("Mời bạn nhập mật khẩu để vào chương trình dành cho Quản Lý:");
				String enterPassCode = s.next();
				while (!enterPassCode.equals(passCode)) {
					System.out.println("Cảnh báo! Bạn đã nhập sai mật khẩu.");
					System.out.print("Vui lòng bạn nhập lại mật khẩu:");
					enterPassCode = s.next();
				}
				while (result) {
					System.out.println(" +============================================================+");
					System.out.println("||                                                            ||");
					System.out.println("||     @ @ @                                       @ @ @      ||");
					System.out.println("||     |@|@|Chào mừng  đến với chương trình quản lí|@|@|      ||");
					System.out.println("||      | |                                         | |       ||");
					System.out.println("||     _________________                   _________________  ||");
					System.out.println("||    /  1.Tạo phòng   /                  /   2.hiển thị   /  ||");
					System.out.println("||   /      họp       /                  /    phòng họp   /   ||");
					System.out.println("||  /________________/                  /________________/    ||");
					System.out.println("||                    _________________                       ||");
					System.out.println("||                   /                /                       ||");
					System.out.println("||                  /    3.Thoát     /                        ||");
					System.out.println("||                 /________________/                         ||");
					System.out.println("||                                                            ||");
					System.out.println(" +============================================================+");
					System.out.print("Lựa chọn của bạn là:");
					int n = s.nextInt();
					switch (n) {
					case 1: {
						roomManager.createRoom(s);
						break;
					}
					case 2: {
						System.out.println(
								" +=============================================================================================================================================+");
						System.out.println(
								"||ID phòng|   Tên phòng    | Loại |Sức chứa|Giá thuê theo giờ| Trạng Thái |Tầng|                             Tiện ích                          ||");
						System.out.println(
								"||========|================|======|========|=================|============|====|===============================================================||");
						roomManager.displayRooms();
						System.out.println(
								" +=============================================================================================================================================+");
						break;
					}
					case 3: {
						System.out.println("Bạn đã thoát chế độ người quản lí!");
						result = false;
						break;
					}
					default: {
						System.out.println("Chức năng không hợp lệ. Vui lòng chọn lại.");
						break;
					}
					}

				}

			}
			case 2: {
				while (result) {
					System.out.println(" +===========================================================+");
					System.out.println("||               ______________________________              ||");
					System.out.println("||              |                              |             ||");
					System.out.println("||              | >>Chức năng cho người dùng<< |             ||");
					System.out.println("||              |______________________________|             ||");
					System.out.println("||                                                           ||");
					System.out.println("||  [1.Hiển thị phòng----]           [2.Xem lịch đặt phòng]  ||");
					System.out.println("||                                                           ||");
					System.out.println("||  [3.Đặt phòng---------]           [4.Thoát-------------]  ||");
					System.out.println("||                                                           ||");
					System.out.println(" +===========================================================+");
					System.out.print("Lựa chọn của bạn là:");
					int n = s.nextInt();
					switch (n) {
					case 1: {
						System.out.println(
								" +=============================================================================================================================================+");
						System.out.println(
								"||ID phòng|   Tên phòng    | Loại |Sức chứa|Giá thuê theo giờ| Trạng Thái |Tầng|                             Tiện ích                          ||");
						System.out.println(
								"||========|================|======|========|=================|============|====|===============================================================||");
						roomManager.displayRooms();
						System.out.println(
								" +=============================================================================================================================================+");
						break;
					}
					case 2: {
						System.out.println("Danh sách đặt phòng:");
						System.out.println(
								" +========================================================================================================================================+");
						System.out.println(
								"||           ID Đặt phòng             |     Tên phòng    |   Thời gian bắt đầu  |  Thời gian kết thúc  | Số người tham dự | Người quản lý ||");
						System.out.println(
								"||====================================|==================|======================|======================|==================|===============||");
						displayBookings(roomManager);
						System.out.println(
								" +========================================================================================================================================+");
						break;
					}
					case 3: {
						createBooking(s, roomManager);
						break;
					}
					case 4: {
						System.out.println("Bạn đã thoát chế độ người dùng.");
						System.out.println("CHÚC QUÝ KHÁCH NGÀY MỚI VUI VẺ(^~^)<3<3");
						result = false;
						break;
					}
					default:
						System.out.println("Chức năng không hợp lệ. Vui lòng chọn lại.");
						break;
					}
				}
			}
			default:
				return;
			}
		}
	}

	private static void createBooking(Scanner s, RoomManager roomManager) {
		try {
			System.out.println("Nhập tên người quản lý: ");
			String manager = s.next();

			System.out.println("Nhập số lượng người tham dự: ");
			int attendees = s.nextInt();
			s.nextLine(); // Consume newline

			System.out.println("Nhập thời gian bắt đầu (dd/MM/yyyy HH:mm): ");
			String startTimeStr = s.nextLine();
			LocalDateTime startTime = LocalDateTime.parse(startTimeStr,
					DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));

			System.out.println("Nhập thời gian kết thúc (dd/MM/yyyy HH:mm): ");
			String endTimeStr = s.nextLine();
			LocalDateTime endTime = LocalDateTime.parse(endTimeStr, DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));

			// Tìm phòng phù hợp và tạo booking

			Booking booking = new Booking(null, startTime, endTime, attendees, manager);
			if (booking.create_booking(roomManager)) {
				System.out.println("Đặt phòng thành công!");
			} else {
				System.out.println("Đặt phòng thất bại.");
			}
		} catch (Exception e) {
			System.out.println("Đã xảy ra lỗi: " + e.getMessage());
		}
	}

	private static void displayBookings(RoomManager roomManager) {
		for (Booking booking : roomManager.getBookings()) {
			booking.displayBookingInfo();
			System.out.println();
		}
	}
}
