package PhongHop;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Booking {
	private String booking_id;
	private Room room;
	private LocalDateTime start_time;
	private LocalDateTime end_time;
	private int attendees;
	private String manager;

	// Constructor
	public Booking(Room room, LocalDateTime start_time, LocalDateTime end_time, int attendees, String manager) {
		this.booking_id = UUID.randomUUID().toString(); // Tạo ID duy nhất
		this.room = room;
		this.start_time = start_time;
		this.end_time = end_time;
		this.attendees = attendees;
		this.manager = manager;
	}

	// Kiểm tra tính khả dụng của phòng
	public boolean check_availability(RoomManager roomManager) {
		for (Room existingRoom : roomManager.getRooms()) {
			for (Booking existingBooking : roomManager.getBookingsForRoom(existingRoom)) {
				// Kiểm tra xem thời gian đặt có bị trùng không
				if (isTimeOverlapping(existingBooking)) {
					return false;
				}
			}
		}
		return true;
	}

	// Kiểm tra trùng lặp thời gian
	private boolean isTimeOverlapping(Booking existingBooking) {
		return !(this.end_time.isBefore(existingBooking.start_time)
				|| this.start_time.isAfter(existingBooking.end_time));
	}

	// Tìm phòng phù hợp với số lượng người tham dự
	public Room findSuitableRoom(RoomManager roomManager) {
		List<Room> suitableRooms = new ArrayList<>();

		for (Room r : roomManager.getRooms()) {
			if (r.getCapacity() >= this.attendees) {
				suitableRooms.add(r);
			}
		}

		// Chọn phòng có sức chứa gần nhất với số người tham dự
		return suitableRooms.stream().min((r1, r2) -> Integer.compare(Math.abs(r1.getCapacity() - this.attendees),
				Math.abs(r2.getCapacity() - this.attendees))).orElse(null);
	}

	// Tạo đặt phòng mới
	public boolean create_booking(RoomManager roomManager) {
		// Kiểm tra sức chứa và tính sẵn có của phòng
		Room suitableRoom = findSuitableRoom(roomManager);

		if (suitableRoom == null) {
			System.out.println("Không tìm thấy phòng phù hợp.");
			return false;
		}

		if (!check_availability(roomManager)) {
			System.out.println("Phòng đã được đặt trong khung giờ này.");
			return false;
		}

		// Thêm booking vào danh sách quản lý
		roomManager.addBooking(this);

		// Cập nhật thông tin phòng
		this.room = suitableRoom;

		System.out.println("Đặt phòng thành công: " + suitableRoom.getName());
		return true;
	}

	// Cập nhật đặt phòng
	public boolean update_booking(RoomManager roomManager, LocalDateTime new_start_time, LocalDateTime new_end_time,
			int new_attendees) {
		// Lưu lại thông tin cũ để phục hồi nếu cập nhật thất bại
		LocalDateTime oldStartTime = this.start_time;
		LocalDateTime oldEndTime = this.end_time;
		int oldAttendees = this.attendees;

		// Cập nhật thông tin
		this.start_time = new_start_time;
		this.end_time = new_end_time;
		this.attendees = new_attendees;

		// Tìm phòng mới phù hợp
		Room newSuitableRoom = findSuitableRoom(roomManager);

		if (newSuitableRoom == null || !check_availability(roomManager)) {
			// Hoàn tác nếu cập nhật thất bại
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

	// Hủy đặt phòng
	public void cancel_booking(RoomManager roomManager) {
		roomManager.removeBooking(this);
		System.out.println("Hủy đặt phòng thành công.");
	}

	// Các getter và setter
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

	// Phương thức in thông tin đặt phòng
	public void displayBookingInfo() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
		System.out.println("ID Đặt phòng: " + booking_id);

		if (room == null) {
			System.out.println("Phòng: Không xác định (Room is null)");
		} else {
			System.out.println("Phòng: " + room.getName());
		}

		System.out.println("Thời gian bắt đầu: " + start_time.format(formatter));
		System.out.println("Thời gian kết thúc: " + end_time.format(formatter));
		System.out.println("Số người tham dự: " + attendees);
		System.out.println("Người quản lý: " + manager);
	}

}
