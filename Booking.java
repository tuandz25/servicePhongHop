package servicePhongHop1;


import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;
import java.util.Map;

public class Booking {
	private String booking_id;
	private Room room;
	private LocalDateTime start_time;
	private LocalDateTime end_time;
	private int attendees;
	private String manager;
	private service new_service;

	public Booking(Room room, LocalDateTime start_time, LocalDateTime end_time, int attendees, String manager,
			service newService) {
		this.booking_id = UUID.randomUUID().toString();
		this.room = room;
		this.start_time = start_time;
		this.end_time = end_time;
		this.attendees = attendees;
		this.manager = manager;
		this.new_service = newService != null ? newService : new service(0);
	}

	public double getDuration() {
		Duration duration = Duration.between(start_time, end_time);
		return duration.toHours(); // Trả về số giờ
	}

	public String getBooking_id() {
		return booking_id;
	}

	public Room getRoom() {
		return room;
	}

	public void setRoom(Room room) {
		this.room = room;
	}

	public LocalDateTime getStart_time() {
		return start_time;
	}

	public void setStart_time(LocalDateTime start_time) {
		this.start_time = start_time;
	}

	public LocalDateTime getEnd_time() {
		return end_time;
	}

	public void setEnd_time(LocalDateTime end_time) {
		this.end_time = end_time;
	}

	public int getAttendees() {
		return attendees;
	}

	public void setAttendees(int attendees) {
		this.attendees = attendees;
	}

	public String getManager() {
		return manager;
	}

	public void setManager(String manager) {
		this.manager = manager;
	}

	public void setService(service newService) {
		this.new_service = newService;
	}

	public service getService() {
		return new_service;
	}

	public boolean check_availability(RoomManager roomManager) {
		for (Booking existingBooking : roomManager.getBookingsForRoom(this.room)) {
			if (isTimeOverlapping(existingBooking)) {
				return false;
			}
		}
		return true;
	}

	public List<Room> findAvailableRooms(RoomManager roomManager) {
		List<Room> availableRooms = new ArrayList<>();

		for (Room r : roomManager.getRooms()) {

			if (r.getCapacity() >= this.attendees) {

				Booking tempBooking = new Booking(r, this.start_time, this.end_time, this.attendees, this.manager,
						this.new_service);
				if (tempBooking.check_availability(roomManager)) {
					availableRooms.add(r);
				}
			}
		}

		return availableRooms;
	}

	// Method to find the most optimal room based on capacity
	public Room findOptimalRoom(List<Room> availableRooms) {
		return availableRooms.stream()
				.min((r1, r2) -> Integer.compare(
						Math.abs(r1.getCapacity() - this.attendees),
						Math.abs(r2.getCapacity() - this.attendees)))
				.orElse(null);
	}

	private boolean isTimeOverlapping(Booking existingBooking) {
		return !(this.end_time.isBefore(existingBooking.getStart_time())
				|| this.start_time.isAfter(existingBooking.getEnd_time()));
	}

	public Room findSuitableRoom(RoomManager roomManager) {
		List<Room> suitableRooms = new ArrayList<>();

		for (Room r : roomManager.getRooms()) {
			if (r.getCapacity() >= this.attendees) {
				suitableRooms.add(r);
			}
		}

		return suitableRooms.stream().min((r1, r2) -> Integer.compare(Math.abs(r1.getCapacity() - this.attendees),
				Math.abs(r2.getCapacity() - this.attendees))).orElse(null);
	}

	public boolean create_booking(RoomManager roomManager) {
		// Kiểm tra tính hợp lệ của thời gian
		if (!validateBookingTimeRange(this.start_time, this.end_time)) {
			return false;
		}

		Room suitableRoom = findSuitableRoom(roomManager);

		if (suitableRoom == null) {
			System.out.println("Không tìm thấy phòng phù hợp.");
			return false;
		}

		this.room = suitableRoom;

		if (!check_availability(roomManager)) {
			System.out.println("Phòng đã được đặt trong khung giờ này.");
			return false;
		}

		roomManager.addBooking(this);
		System.out.println("Đặt phòng thành công: " + suitableRoom.getRoomID());
		return true;
	}

	public boolean update_booking(RoomManager roomManager, LocalDateTime new_start_time, LocalDateTime new_end_time,
			int new_attendees) {
		LocalDateTime oldStartTime = this.start_time;
		LocalDateTime oldEndTime = this.end_time;
		int oldAttendees = this.attendees;

		this.start_time = new_start_time;
		this.end_time = new_end_time;
		this.attendees = new_attendees;

		Room newSuitableRoom = findSuitableRoom(roomManager);

		if (newSuitableRoom == null || !check_availability(roomManager)) {
			this.start_time = oldStartTime;
			this.end_time = oldEndTime;
			this.attendees = oldAttendees;
			System.out.println("Không thể cập nhật đặt phòng. Xung đột lịch hoặc không có phòng phù hợp.");
			return false;
		}

		this.room = newSuitableRoom;
		System.out.println("Cập nhật đặt phòng thành công.");
		return true;
	}

	public void displayBookingInfo() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
		String formatName = String.format("%-18s", room.getRoomID());
		String formatStartTime = String.format("%-22s", start_time.format(formatter));
		String formatEndTime = String.format("%-22s", end_time.format(formatter));
		String formatAttendees = String.format("%-10s", attendees);
		String formatManager = String.format("%-15s", manager);
		String formatService;
		if (new_service != null) {
			formatService = String.format("%-50s", new_service.getSelectedServices());
		} else {
			formatService = String.format("%-50s", "Trống");
		}

		if (room == null) {
			System.out.println("Phòng: Không xác định (Room is null)");
		} else {
			System.out.print("||" + formatName);
		}

		System.out.println("|" + formatStartTime + "|" + formatEndTime + "|        " + formatAttendees + "|"
				+ formatManager + "|" + formatService + "||");
		System.out.print(
				"||------------------|----------------------|----------------------|------------------|---------------|--------------------------------------------------||");
	}

	public void displayAvailabilityRoom() {
		String formatName = String.format("%-18s", this.room.getRoomID());
		String formatAttendees = String.format("%-10s", this.attendees);
		String formatPrice = String.format("%-10s", this.room.getDailyRentalPrice());

		if (room == null) {
			System.out.println("Phòng: Không xác định (Room is null)");
		} else {
			System.out.print("||" + formatName);
		}

		System.out.println("|" + formatAttendees + "|" + formatPrice + "||");
		System.out.print(
				"||------------------|----------------------|----------------------||");
	}

	public void Add_Service() {
		Duration duration = Duration.between(start_time, end_time);
		double rentalDuration = duration.toMinutes() / 60.0;
		new_service = new service(rentalDuration);

		// Bản đồ ánh xạ số lựa chọn với tên dịch vụ (tiếng Việt)
		Map<Integer, String> serviceMap = new HashMap<>();
		serviceMap.put(1, "Trà và Cà phê");
		serviceMap.put(2, "Hỗ trợ kỹ thuật");
		serviceMap.put(3, "Wifi tốc độ cao");

		Scanner sc = new Scanner(System.in);

		while (true) {
			System.out.println("+----------Dịch vụ----------+");
			System.out.println("|1. Trà và Cà phê           |");
			System.out.println("|2. Hỗ trợ kỹ thuật         |");
			System.out.println("|3. Wifi tốc độ cao         |");
			System.out.println("|4. Xóa dịch vụ             |");
			System.out.println("|5. Xem tổng chi phí dịch vụ|");
			System.out.println("|6. Thoát                   |");
			System.out.println("+---------------------------+");
			System.out.print("Nhập lựa chọn của bạn (số): ");

			int n = sc.nextInt();

			switch (n) {
				case 1, 2, 3 -> {
					String serviceName = serviceMap.get(n);
                                        if (!new_service.getSelectedServices().contains(serviceName)) { 
                                                  new_service.addService(serviceName);
                                                 System.out.println("Dịch vụ " + serviceName + " đã được thêm.");
                                            } else {
                                              System.out.println("Bạn đã chọn dịch vụ " + serviceName + " rồi.");
                                                   }
				}
				case 4 -> {
					if (new_service.getSelectedServices().isEmpty()) {
						System.out.println("Bạn chưa chọn dịch vụ nào để xóa.");
						break;
					}

					System.out.println("---- Dịch vụ hiện có ----");
					int index = 1;
					Map<Integer, String> reverseMap = new HashMap<>();
					for (String service : new_service.getSelectedServices()) {
						System.out.println(index + ". " + service);
						reverseMap.put(index, service);
						index++;
					}

					System.out.print("Nhập số tương ứng với dịch vụ muốn xóa: ");
					int removeChoice = sc.nextInt();

					if (reverseMap.containsKey(removeChoice)) {
						new_service.removeService(reverseMap.get(removeChoice));
					} else {
						System.out.println("Lựa chọn không hợp lệ.");
					}
				}
				case 5 -> System.out.println("Tổng chi phí dịch vụ: " + new_service.getTotalServiceCost());
				case 6 -> {
					System.out.println("Thoát chương trình.");
					return;
				}
				default -> System.out.println("Lựa chọn không hợp lệ. Vui lòng chọn lại!");
			}
		}
	}

	public boolean validateBookingTimeRange(LocalDateTime start_time, LocalDateTime end_time) {
		// Kiểm tra nếu thời gian kết thúc là trước thời gian bắt đầu
		if (end_time.isBefore(start_time)) {
			System.out.println("Lỗi: Thời gian kết thúc không được sớm hơn thời gian bắt đầu.");
			return false;
		}

		// Kiểm tra nếu thời gian bắt đầu và kết thúc trùng nhau
		if (start_time.isEqual(end_time)) {
			System.out.println("Lỗi: Thời gian bắt đầu và kết thúc không được trùng nhau.");
			return false;
		}

		// Kiểm tra nếu thời gian đặt phòng nằm trong quá khứ
		if (start_time.isBefore(LocalDateTime.now())) {
			System.out.println("Lỗi: Không thể đặt phòng trong quá khứ.");
			return false;
		}

		return true;
	}

}
