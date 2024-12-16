package servicePhongHop1;

// package servicePhongHop;

// import java.time.LocalDateTime;
// import java.time.format.DateTimeFormatter;
// import java.util.HashMap;
// import java.util.List;
// import java.util.Map;
// import java.util.Scanner;
// // import java.io.BufferedWriter;
// // import java.io.FileWriter;
// // import java.io.IOException;

// public class StatisticsReport {
// 	private List<Booking> bookings; // Danh sách các booking
// 	private List<Room> rooms; // Danh sách các phòng họp

// 	public StatisticsReport(List<Booking> bookings, List<Room> rooms) {
// 		this.bookings = bookings;
// 		this.rooms = rooms;
// 	}

// 	public StatisticsReport() {
// 	};

// 	public void requestUsageCountInRange() {
// 		Scanner scanner = new Scanner(System.in);
// 		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

// 		System.out.print("Nhập thời gian bắt đầu (dd/MM/yyyy HH:mm): ");
// 		String startInput = scanner.nextLine();
// 		LocalDateTime startDate = LocalDateTime.parse(startInput, formatter);

// 		System.out.print("Nhập thời gian kết thúc (dd/MM/yyyy HH:mm): ");
// 		String endInput = scanner.nextLine();
// 		LocalDateTime endDate = LocalDateTime.parse(endInput, formatter);

// 		Map<Room, Integer> usageCount = getUsageCountInRange(startDate, endDate);

// 		System.out.println("Số lần sử dụng phòng họp trong khoảng thời gian đã cho:");
// 		for (Map.Entry<Room, Integer> entry : usageCount.entrySet()) {
// 			System.out.println("Phòng: " + entry.getKey().getRoomID() + " - Số lần sử dụng: " + entry.getValue());
// 		}
// 	}

// 	// Phương thức để thống kê số lần mỗi phòng họp được sử dụng trong khoảng thời
// 	// gian cụ thể
// 	public Map<Room, Integer> getUsageCountInRange(LocalDateTime startDate, LocalDateTime endDate) {
// 		Map<Room, Integer> usageCount = new HashMap<>();
// 		for (Booking booking : bookings) {
// 			// Kiểm tra xem thời gian đặt phòng có nằm trong khoảng thời gian đã cho không
// 			if ((booking.getStart_time().isEqual(startDate) || booking.getStart_time().isAfter(startDate))
// 					&& (booking.getEnd_time().isEqual(endDate) || booking.getEnd_time().isBefore(endDate))) {
// 				Room room = booking.getRoom();
// 				usageCount.put(room, usageCount.getOrDefault(room, 0) + 1);
// 			}
// 		}
// 		return usageCount;
// 	}

// 	// Phương thức để tính tổng số giờ các phòng họp được thuê
// 	public Map<Room, Double> getTotalHoursRented() {
// 		Map<Room, Double> totalHours = new HashMap<>();
// 		for (Booking booking : bookings) {
// 			Room room = booking.getRoom();
// 			double hours = booking.getDuration(); // Giả sử có phương thức getDuration() trong Booking
// 			totalHours.put(room, totalHours.getOrDefault(room, 0.0) + hours);
// 		}
// 		return totalHours;
// 	}

// 	// Phương thức để tính doanh thu từ việc cho thuê phòng họp và các dịch vụ đi
// 	// kèm
// 	public double getTotalRevenue() {
// 		double totalRevenue = 0.0;
// 		for (Booking booking : bookings) {
// 			totalRevenue += booking.getRoom().getRentalPrice() * booking.getDuration(); // Giá thuê phòng
// 			// Chi phí dịch vụ
// 		}
// 		return totalRevenue;
// 	}

// 	// Phương thức để thống kê số lần mỗi phòng họp được sử dụng
// 	public Map<Room, Integer> getUsageCount() {
// 		Map<Room, Integer> usageCount = new HashMap<>();
// 		for (Booking booking : bookings) {
// 			Room room = booking.getRoom();
// 			usageCount.put(room, usageCount.getOrDefault(room, 0) + 1);
// 		}
// 		return usageCount;
// 	}

// 	// Phương thức để tạo báo cáo theo tháng
// 	public void generateMonthlyReport(int month, int year) {
// 		System.out.println("Báo cáo tháng " + month + " năm " + year);
// 		Map<Room, Integer> usageCount = getUsageCount();
// 		Map<Room, Double> totalHours = getTotalHoursRented();
// 		double totalRevenue = getTotalRevenue();

// 		for (Room room : rooms) {
// 			System.out.println("Phòng: " + room.getRoomID());
// 			System.out.println("Số lần sử dụng: " + usageCount.getOrDefault(room, 0));
// 			System.out.println("Tổng số giờ thuê: " + totalHours.getOrDefault(room, 0.0));
// 		}
// 		System.out.println("Doanh thu tổng: " + totalRevenue);
// 	}

// 	// Phương thức để tạo báo cáo theo quý
// 	public void generateQuarterlyReport(int quarter, int year) {
// 		System.out.println("Báo cáo quý " + quarter + " năm " + year);
// 		int startMonth = (quarter - 1) * 3 + 1;
// 		int endMonth = startMonth + 2;

// 		Map<Room, Integer> usageCount = new HashMap<>();
// 		Map<Room, Double> totalHours = new HashMap<>();
// 		double totalRevenue = 0.0;

// 		for (Booking booking : bookings) {
// 			// Giả sử có phương thức getStartTime() trong Booking để lấy thời gian bắt đầu
// 			int bookingMonth = booking.getStart_time().getMonthValue();
// 			if (bookingMonth >= startMonth && bookingMonth <= endMonth) {
// 				Room room = booking.getRoom();
// 				usageCount.put(room, usageCount.getOrDefault(room, 0) + 1);
// 				double hours = booking.getDuration();
// 				totalHours.put(room, totalHours.getOrDefault(room, 0.0) + hours);
// 				totalRevenue += booking.getRoom().getRentalPrice() * hours;
// 			}
// 		}

// 		for (Room room : rooms) {
// 			System.out.println("Phòng: " + room.getRoomID());
// 			System.out.println("Số lần sử dụng: " + usageCount.getOrDefault(room, 0));
// 			System.out.println("Tổng số giờ thuê: " + totalHours.getOrDefault(room, 0.0));
// 		}
// 		System.out.println("Doanh thu tổng: " + totalRevenue);
// 	}

// 	// Phương thức để tạo báo cáo theo năm
// 	public void generateYearlyReport(int year) {
// 		System.out.println("Báo cáo năm " + year);
// 		Map<Room, Integer> usageCount = new HashMap<>();
// 		Map<Room, Double> totalHours = new HashMap<>();
// 		double totalRevenue = 0.0;

// 		for (Booking booking : bookings) {
// 			// Giả sử có phương thức getStartTime() trong Booking để lấy thời gian bắt đầu
// 			int bookingYear = booking.getStart_time().getYear();
// 			if (bookingYear == year) {
// 				Room room = booking.getRoom();
// 				usageCount.put(room, usageCount.getOrDefault(room, 0) + 1);
// 				double hours = booking.getDuration();
// 				totalHours.put(room, totalHours.getOrDefault(room, 0.0) + hours);
// 				totalRevenue += booking.getRoom().getRentalPrice() * hours;
// 			}
// 		}

// 		for (Room room : rooms) {
// 			System.out.println("Phòng: " + room.getRoomID());
// 			System.out.println("Số lần sử dụng: " + usageCount.getOrDefault(room, 0));
// 			System.out.println("Tổng số giờ thuê: " + totalHours.getOrDefault(room, 0.0));
// 		}
// 		System.out.println("Doanh thu tổng: " + totalRevenue);
// 	}
// }