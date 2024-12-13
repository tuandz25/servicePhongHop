package PhongHop;

import java.time.Duration;
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

	public Booking(Room room, LocalDateTime start_time, LocalDateTime end_time, int attendees, String manager) {
		this.booking_id = UUID.randomUUID().toString();
		this.room = room;
		this.start_time = start_time;
		this.end_time = end_time;
		this.attendees = attendees;
		this.manager = manager;
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

	public boolean check_availability(RoomManager roomManager) {
		for (Booking existingBooking : roomManager.getBookingsForRoom(this.room)) {
			if (isTimeOverlapping(existingBooking)) {
				return false;
			}
		}
		return true;
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
		System.out.print("||" + booking_id);

		if (room == null) {
			System.out.println("Phòng: Không xác định (Room is null)");
		} else {
			System.out.print("|" + formatName);
		}

		System.out.println("|" + formatStartTime + "|" + formatEndTime + "|        " + formatAttendees + "|"
				+ formatManager + "||");
		System.out.print(
				"||------------------------------------|------------------|----------------------|----------------------|------------------|---------------||");
	}
}
