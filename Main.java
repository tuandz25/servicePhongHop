package servicePhongHop1;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
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
										" +======================================================================================================================================================+");
								System.out.println(
										"||     Tên phòng    |   Thời gian bắt đầu  |  Thời gian kết thúc  | Số người tham dự |Tên khách hàng |                         Dịch vụ                  ||");
								System.out.println(
										"||==================|======================|======================|==================|===============|==================================================||");
								displayBookings(roomManager);
								System.out.println(
										" +======================================================================================================================================================+");
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
			System.out.println("Nhập tên của bạn: ");
			String manager = s.next();

			System.out.println("Nhập số lượng người tham dự: ");
			int attendees = s.nextInt();
			s.nextLine(); // Consume newline

			// Get current time and tomorrow's date
			LocalDateTime now = LocalDateTime.now();
			LocalDateTime tomorrow = now.plusDays(1);
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

			// Display tomorrow's date in the error message
			System.out.println("Nhập thời gian bắt đầu (dd/MM/yyyy): ");
			String startTimeStr = s.nextLine();
			LocalDate startDate = LocalDate.parse(startTimeStr, DateTimeFormatter.ofPattern("dd/MM/yyyy"));

			System.out.println("Nhập thời gian kết thúc (dd/MM/yyyy): ");
			String endTimeStr = s.nextLine();
			LocalDate endDate = LocalDate.parse(endTimeStr, DateTimeFormatter.ofPattern("dd/MM/yyyy"));

			// Create a booking with full day times
			LocalDateTime startTime = startDate.atStartOfDay();
			LocalDateTime endTime = endDate.atTime(23, 59, 59);

			// Validate start time
			if (startTime.isBefore(LocalDateTime.now().plusDays(1).toLocalDate().atStartOfDay())) {
				System.out.println("Lỗi: Bạn chỉ được đặt phòng từ ngày " +
						LocalDateTime.now().plusDays(1).toLocalDate().format(formatter) +
						" trở đi.");
				return;
			}
			// Tìm phòng phù hợp và tạo booking

			Booking booking = new Booking(null, startTime, endTime, attendees, manager, null);
			// Find available rooms
			List<Room> availableRooms = booking.findAvailableRooms(roomManager);

			if (availableRooms.isEmpty()) {
				System.out.println("Không có phòng phù hợp trong thời gian này.");
				return;
			}

			// Find optimal room
			Room optimalRoom = booking.findOptimalRoom(availableRooms);

			// Display available rooms
			System.out.println("Các phòng khả dụng:");
			System.out.println(
					" +================================================+");
			System.out.println(
					"||ID phòng| Tên phòng |Sức chứa|Giá thuê theo ngày||");
			System.out.println(
					"||========|===========|========|==================||");
			for (Room room : availableRooms) {
				String formatName = String.format("%-11s", room.getRoomID());
				String formatAttendees = String.format("%-8s", room.getCapacity());
				String formatPrice = String.format("%-18s", room.getDailyRentalPrice());
				String formatID = String.format("%-8s", room.getID());

				System.out
						.println("||" + formatID + "|" + formatName + "|" + formatAttendees + "|" + formatPrice + "||");
				System.out.println(
						"||--------|-----------|--------|------------------||");
			}
			System.out.println(
					" +=================================================+");
			// Suggest optimal room

			System.out.println("\nPhòng tối ưu được đề xuất: " + optimalRoom.getRoomID());
			System.out.println("Sức chứa: " + optimalRoom.getCapacity() +
					", Giá thuê/ngày: " + optimalRoom.getDailyRentalPrice());
			System.out.println();

			// Ask for confirmation
			System.out.print("Bạn có muốn đặt phòng này không? (Y/N): ");
			String confirm = s.nextLine().trim().toUpperCase();

			while (true) {
				if (confirm.equals("Y")) {
					// Set the optimal room and create booking
					booking = new Booking(optimalRoom, startTime, endTime, attendees, manager, null);
					if (booking.create_booking(roomManager)) {
						// Phần code cũ không thay đổi
						System.out.println("\nThông tin phòng đã đặt:");
						String formatName = String.format("%-67s", optimalRoom.getRoomID());
						String formatAttendees = String.format("%-67s", optimalRoom.getCapacity());
						String formatPrice = String.format("%-67s", optimalRoom.getDailyRentalPrice());
						String formatType = String.format("%-67s", optimalRoom.getType());
						String formatFloor = String.format("%-67s", optimalRoom.getFloor());
						String formatUtility = String.format("%-67s", String.join(", ", optimalRoom.getUtility()));

						System.out.println(
								"+==================================================================================+");
						System.out.println("|Tên phòng     |" + formatName + "|");
						System.out.println(
								"|--------------|-------------------------------------------------------------------|");
						System.out.println("|Loại phòng    |" + formatType + "|");
						System.out.println(
								"|--------------|-------------------------------------------------------------------|");
						System.out.println("|Sức chứa      |" + formatAttendees + "|");
						System.out.println(
								"|--------------|-------------------------------------------------------------------|");
						System.out.println("|Giá thuê/ngày |" + formatPrice + "|");
						System.out.println(
								"|--------------|-------------------------------------------------------------------|");
						System.out.println("|Tầng          |" + formatFloor + "|");
						System.out.println(
								"|--------------|-------------------------------------------------------------------|");
						System.out.println("|Tiện ích      |" + formatUtility + "|");
						System.out.println(
								"+==================================================================================+");

						System.out.println("Bạn có muốn thêm dịch vụ vào phòng họp không?");
						System.out.print("Nhập lựa chọn của bạn Y/N:");
						String c = s.nextLine().trim().toUpperCase();
						if (c.equals("Y")) {
							booking.Add_Service();
						}
						roomManager.addBooking(booking);
						break;
					} else {
						break;
					}
				} else if (confirm.equals("N")) {
					System.out.print("Bạn có muốn đặt phòng khác không? (Y/N): ");
					String confirmAvailability = s.nextLine().trim().toUpperCase();
					if (confirmAvailability.equals("Y")) {
						// Cho phép chọn lại phòng
						System.out.println("Hãy nhập mã phòng từ danh sách phòng khả dụng để đặt phòng.");
						System.out.print("Nhập ID phòng (chỉ được nhập số): ");

						// Kiểm tra đầu vào chỉ là số
						while (!s.hasNextInt()) {
							System.out.println("Lỗi: Vui lòng chỉ nhập số!");
							System.out.print("Nhập ID phòng (chỉ được nhập số): ");
							s.next(); // Loại bỏ đầu vào không hợp lệ
						}

						int selectedRoomId = s.nextInt();
						s.nextLine(); // Consume newline

						// Tìm phòng trong danh sách khả dụng
						Room selectedRoom = availableRooms.stream()
								.filter(room -> room.getID() == selectedRoomId)
								.findFirst()
								.orElse(null);

						if (selectedRoom != null) {
							booking = new Booking(selectedRoom, startTime, endTime, attendees, manager, null);
							if (booking.create_booking(roomManager)) {
								System.out.println("\nThông tin phòng đã đặt:");
								String formatName = String.format("%-67s", selectedRoom.getRoomID());
								String formatAttendees = String.format("%-67s", selectedRoom.getCapacity());
								String formatPrice = String.format("%-67s", selectedRoom.getDailyRentalPrice());
								String formatType = String.format("%-67s", selectedRoom.getType());
								String formatFloor = String.format("%-67s", selectedRoom.getFloor());
								String formatUtility = String.format("%-67s",
										String.join(", ", selectedRoom.getUtility()));

								System.out.println(
										"+==================================================================================+");
								System.out.println("|Tên phòng     |" + formatName + "|");
								System.out.println(
										"|--------------|-------------------------------------------------------------------|");
								System.out.println("|Loại phòng    |" + formatType + "|");
								System.out.println(
										"|--------------|-------------------------------------------------------------------|");
								System.out.println("|Sức chứa      |" + formatAttendees + "|");
								System.out.println(
										"|--------------|-------------------------------------------------------------------|");
								System.out.println("|Giá thuê/ngày |" + formatPrice + "|");
								System.out.println(
										"|--------------|-------------------------------------------------------------------|");
								System.out.println("|Tầng          |" + formatFloor + "|");
								System.out.println(
										"|--------------|-------------------------------------------------------------------|");
								System.out.println("|Tiện ích      |" + formatUtility + "|");
								System.out.println(
										"+==================================================================================+");
								System.out.println("Bạn có muốn thêm dịch vụ vào phòng họp không?");
								System.out.print("Nhập lựa chọn của bạn Y/N:");
								String c = s.nextLine().trim().toUpperCase();
								if (c.equals("Y")) {
									booking.Add_Service();
								}
								roomManager.addBooking(booking);
								break;
							}
						} else {
							System.out.println("Đặt phòng thất bại.");
							break;
						}
					} else {
						System.out.println("Đặt phòng thất bại");
						break;

					}
				} else {
					System.out.println("Lựa chọn không hợp lệ.");
					System.out.print("Bạn có muốn đặt phòng không? (Y/N): ");
					confirm = s.nextLine().trim().toUpperCase();
				}
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
