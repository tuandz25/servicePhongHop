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
			System.out.println("||               	   Xin chào người dùng	                 ||");
			System.out.println("||                    Vui lòng chọn chức năng                ||");
			System.out.println("||   ╔==================╗            ╔==================╗  	 ||");
			System.out.println("||   ∥  1.Quản lý phòng ∥            ∥  2.Thuê phòng     ∥   ||");
			System.out.println("||   ∥     họp(admin)   ∥            ∥      họp          ∥   ||");
			System.out.println("||   ╚==================╝            ╚==================╝    ||");
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
						createRoom(s, roomManager);
						break;
					}
					case 2: {
						System.out.println(
								" +===========================================================================================================================================================+");
						System.out.println(
								"||  ID phòng   |  Loại  |  Sức chứa  |  Giá thuê theo giờ  |  Trạng Thái  |  Tầng  |                                    Tiện ích                             ||");
						System.out.println(
								" +===========================================================================================================================================================||");
						roomManager.displayRooms();
						System.out.println(
								" +===========================================================================================================================================================+");
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
					System.out.println("||  [3.Đặt phòng---------]           [4.Sửa đặt phòng-----]  ||");
					System.out.println("||                                                           ||");
					System.out.println("||  [5.Hủy đặt phòng-----]           [6.Thoát-------------]  ||");
					System.out.println("||                                                           ||");
					System.out.println(" +===========================================================+");
					System.out.print("Lựa chọn của bạn là:");
					int n = s.nextInt();
					switch (n) {
					case 1: {
						System.out.println(
								" +===========================================================================================================================================================+");
						System.out.println(
								"||  ID phòng   |  Loại  |  Sức chứa  |  Giá thuê theo giờ  |  Trạng Thái  |  Tầng  |                                    Tiện ích                             ||");
						System.out.println(
								"||=============|========|============|=====================|==============|========|=========================================================================||");
						roomManager.displayRooms();
						System.out.println(
								" +===========================================================================================================================================================+");
						break;
					}
					case 2: {
						System.out.println(
								" +=======================================================================================================+");
						displayBookings(roomManager);
						System.out.println(
								" +=======================================================================================================+");
						break;
					}
					case 3: {
						createBooking(s, roomManager);
						break;
					}
					case 4: {
						updateBooking(s, roomManager);
						break;
					}
					case 5: {
						cancelBooking(s, roomManager);
						break;
					}
					case 6: {
						System.out.println("Bạn đã thoát chế độ người dùng.");
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
				break;
			}
		}
	}

	private static void createRoom(Scanner s, RoomManager roomManager) {
		System.out.print("Nhập tên phòng: ");
		String name = s.next();
		System.out.print("Nhập loại phòng: ");
		String type = s.next();
		System.out.print("Nhập sức chứa : ");
		int capacity = s.nextInt();
		System.out.print("Nhập giá phòng theo giờ: ");
		double price = s.nextDouble();
		System.out.print("Nhập trạng thái phòng: ");
		String status = s.next();
		System.out.print("Nhập số tầng của phòng: ");
		int floor = s.nextInt();
		roomManager.createRoom(name, type, capacity, price, status, floor);
		System.out.println("Đã tạo thành công 1 phòng.");

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

	private static void updateBooking(Scanner s, RoomManager roomManager) {
		try {
			System.out.println("Nhập ID đặt phòng: ");
			String bookingId = s.next();

			// Tìm booking tương ứng
			Booking booking = roomManager.getBooking(bookingId);
			if (booking == null) {
				System.out.println("Không tìm thấy đặt phòng với ID: " + bookingId);
				return;
			}

			System.out.println("Nhập thời gian bắt đầu mới (dd/MM/yyyy HH:mm): ");
			String startTimeStr = s.nextLine();
			LocalDateTime newStartTime = LocalDateTime.parse(startTimeStr,
					DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));

			System.out.println("Nhập thời gian kết thúc mới (dd/MM/yyyy HH:mm): ");
			String endTimeStr = s.nextLine();
			LocalDateTime newEndTime = LocalDateTime.parse(endTimeStr, DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));

			System.out.println("Nhập số lượng người tham dự mới: ");
			int newAttendees = s.nextInt();
			s.nextLine(); // Consume newline

			if (booking.update_booking(roomManager, newStartTime, newEndTime, newAttendees)) {
				System.out.println("Cập nhật đặt phòng thành công.");
			} else {
				System.out.println("Cập nhật đặt phòng thất bại.");
			}
		} catch (Exception e) {
			System.out.println("Đã xảy ra lỗi: " + e.getMessage());
		}
	}

	private static void cancelBooking(Scanner s, RoomManager roomManager) {
		try {
			System.out.println("Nhập ID đặt phòng: ");
			String bookingId = s.nextLine();

			// Tìm booking tương ứng
			Booking booking = roomManager.getBooking(bookingId);
			if (booking == null) {
				System.out.println("Không tìm thấy đặt phòng với ID: " + bookingId);
				return;
			}

			booking.cancel_booking(roomManager);
			System.out.println("Hủy đặt phòng thành công.");
		} catch (Exception e) {
			System.out.println("Đã xảy ra lỗi: " + e.getMessage());
		}
	}

	private static void displayBookings(RoomManager roomManager) {
		System.out.println("Danh sách đặt phòng:");
		for (Booking booking : roomManager.getBookings()) {
			booking.displayBookingInfo();
			System.out.println();
		}
	}
}
