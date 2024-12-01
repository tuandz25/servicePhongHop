package PhongHop;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
			System.out.println("|    5.Sửa đặt phòng     |");
            System.out.println("|    6.Hủy đặt phòng     |");
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
				displayBookings(roomManager);
				break;
			}
			case 4: {
				createBooking(s, roomManager);
				break;
			}
			case 5: {
				updateBooking(s, roomManager);
				break;
			}
			case 6: {
				cancelBooking(s, roomManager);
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
	private static void createBooking(Scanner s, RoomManager roomManager) {
        try {
            System.out.println("Nhập tên người quản lý: ");
            String manager = s.nextLine();

            System.out.println("Nhập số lượng người tham dự: ");
            int attendees = s.nextInt();
            s.nextLine(); // Consume newline

            System.out.println("Nhập thời gian bắt đầu (dd/MM/yyyy HH:mm): ");
            String startTimeStr = s.nextLine();
            LocalDateTime startTime = LocalDateTime.parse(startTimeStr, 
                DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));

            System.out.println("Nhập thời gian kết thúc (dd/MM/yyyy HH:mm): ");
            String endTimeStr = s.nextLine();
            LocalDateTime endTime = LocalDateTime.parse(endTimeStr, 
                DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));

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
            String bookingId = s.nextLine();

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
            LocalDateTime newEndTime = LocalDateTime.parse(endTimeStr, 
                DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));

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
