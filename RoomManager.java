package servicePhongHop1;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class RoomManager {
	private List<Room> rooms;
	private List<Booking> bookings;
	private List<service> services;

	public void createRoom(Scanner s) {
		Room room = new Room();
		room.setInfo();
		room.setID(rooms.size() + 1);
		rooms.add(room);
		saveRooms();
		System.out.println("Đã tạo thành công 1 phòng.");

	}

	public RoomManager() {
		this.rooms = new ArrayList<>();
		this.bookings = new ArrayList<>();
		this.services = new ArrayList<>();
		loadRooms();
		loadBookings();

		// Bắt đầu cập nhật trạng thái phòng
		startPeriodicStatusUpdate();
	}

	public List<Room> getRooms() {
		return rooms;
	}

	public void displayRooms() {
		for (Room room : rooms) {
			room.displayInfo();
		}
	}

	private void saveRooms() {
		try (FileWriter fw = new FileWriter("src//servicePhongHop1//rooms.txt", false); // Ghi đè file
				BufferedWriter bw = new BufferedWriter(fw)) {
			for (Room room : rooms) {
				bw.write(room.toCSV());
				bw.newLine();
			}
			bw.flush();
		} catch (IOException e) {
			System.out.println("Lỗi khi lưu phòng: " + e.getMessage());
		}
	}

	private void loadRooms() {
		try (FileReader fr = new FileReader("src//servicePhongHop1//rooms.txt");
				BufferedReader br = new BufferedReader(fr)) {
			String line;
			while ((line = br.readLine()) != null) {
				Room room = Room.fromCSV(line);
				rooms.add(room);
			}
		} catch (FileNotFoundException e) {
			System.out.println("File không tồn tại, tạo file mới: " + "rooms.txt");
		} catch (IOException e) {
			System.out.println("Lỗi khi tải phòng: " + e.getMessage());
		}
	}

	public void addBooking(Booking booking) {
		bookings.add(booking);
		saveBookings();
	}

	public void removeBooking(Booking booking) {
		bookings.remove(booking);
		saveBookings();
	}

	public Room findAvailableRoom(int attendees, LocalDateTime startTime, LocalDateTime endTime) {
		for (Room room : rooms) {
			if (room.getCapacity() >= attendees && isRoomAvailable(room, startTime, endTime)) {
				return room;
			}
		}
		return null;
	}

	public boolean isRoomAvailable(Room room, LocalDateTime startTime, LocalDateTime endTime) {
		for (Booking booking : bookings) {
			if (booking.getRoom().equals(room)
					&& !(endTime.isBefore(booking.getStart_time()) || startTime.isAfter(booking.getEnd_time()))) {
				return false;
			}
		}
		return true;
	}

	// Lấy danh sách bookings cho một phòng cụ thể
	public List<Booking> getBookingsForRoom(Room room) {
		return bookings.stream().filter(b -> b.getRoom().equals(room)).collect(Collectors.toList());
	}

	// Lấy tất cả các booking
	public List<Booking> getBookings() {
		return new ArrayList<>(bookings);
	}

	// Lưu booking vào file
	private void saveBookings() {
		try (FileWriter fw = new FileWriter("src//servicePhongHop1//bookings.txt", false);
				BufferedWriter bw = new BufferedWriter(fw)) {
			for (Booking booking : bookings) {
				bw.write(bookingToCSV(booking));
				bw.newLine();
			}
			bw.flush();
		} catch (IOException e) {
			System.out.println("Lỗi khi lưu booking: " + e.getMessage());
		}
	}

	// Tải booking từ file
	private void loadBookings() {
		try (FileReader fr = new FileReader("src//servicePhongHop1//bookings.txt");
				BufferedReader br = new BufferedReader(fr)) {
			String line;
			while ((line = br.readLine()) != null) {
				Booking booking = bookingFromCSV(line);
				if (booking != null) {
					bookings.add(booking);
				}
			}
		} catch (FileNotFoundException e) {
			System.out.println("File booking không tồn tại, tạo file mới.");
		} catch (IOException e) {
			System.out.println("Lỗi khi tải booking: " + e.getMessage());
		}
	}

	// Chuyển booking sang chuỗi CSV
	// Chuyển booking sang chuỗi CSV
	// Trong bookingToCSV()
	private String bookingToCSV(Booking booking) {
		return String.join(",",
				booking.getBooking_id(),
				booking.getRoom().getRoomID(),
				booking.getStart_time().toString(),
				booking.getEnd_time().toString(),
				String.valueOf(booking.getAttendees()),
				booking.getManager(),
				booking.getService() != null ? String.join(";", booking.getService().getSelectedServices()) : "");
	}

	// Trong bookingFromCSV()
	private Booking bookingFromCSV(String csv) {
		String[] parts = csv.split(",");
		if (parts.length < 6) {
			System.out.println("Dữ liệu không hợp lệ: " + csv);
			return null;
		}

		// Tìm phòng tương ứng
		Room room = rooms.stream().filter(r -> r.getRoomID().equals(parts[1])).findFirst().orElse(null);

		// Tạo service
		service service = new service(0); // Tạo service với thời gian thuê mặc định
		if (parts.length > 6 && !parts[6].isEmpty()) {
			String[] serviceNames = parts[6].split(";");
			for (String serviceName : serviceNames) {
				service.addService(serviceName);
			}
		}

		if (room == null) {
			System.out.println("Không tìm thấy phòng có tên: " + parts[1]);
			return null; // Trả về null nếu phòng không tồn tại
		}

		return new Booking(room,
				LocalDateTime.parse(parts[2]),
				LocalDateTime.parse(parts[3]),
				Integer.parseInt(parts[4]),
				parts[5],
				service);
	}

	public void updateRoomStatuses() {
		LocalDateTime now = LocalDateTime.now();

		for (Room room : rooms) {
			// Tìm booking hiện tại cho phòng này
			Booking currentBooking = bookings.stream()
					.filter(booking -> booking.getRoom().equals(room))
					.filter(booking -> now.isAfter(booking.getStart_time()) &&
							now.isBefore(booking.getEnd_time()))
					.findFirst()
					.orElse(null);

			// Tìm booking sắp tới
			Booking upcomingBooking = bookings.stream()
					.filter(booking -> booking.getRoom().equals(room))
					.filter(booking -> booking.getStart_time().isAfter(now))
					.findFirst()
					.orElse(null);

			// Cập nhật trạng thái
			if (currentBooking != null) {
				// Đang trong thời gian booking
				room.setStatus("Đang sử dụng");
			} else if (upcomingBooking != null) {
				// Có booking sắp tới
				room.setStatus("Đã đặt");
			} else {
				// Không có booking
				room.setStatus("Trống");
			}
		}

		// Lưu lại trạng thái phòng
		saveRooms();
	}

	// Phương thức để tự động cập nhật định kỳ (có thể gọi theo một khoảng thời gian
	// nhất định)

	public void startPeriodicStatusUpdate() {
		Thread statusUpdateThread = new Thread(() -> {
			while (true) {
				try {
					updateRoomStatuses();
					removeExpiredBookings(); // Add this line
					Thread.sleep(300000); // 5 phút = 300000 milliseconds
				} catch (InterruptedException e) {
					System.out.println("Luồng cập nhật trạng thái phòng bị gián đoạn: " + e.getMessage());
					break;
				}
			}
		});

		statusUpdateThread.setDaemon(true);
		statusUpdateThread.start();
	}

	public void removeExpiredBookings() {
		LocalDateTime now = LocalDateTime.now();

		// Remove bookings that have ended
		bookings.removeIf(booking -> booking.getEnd_time().isBefore(now));

		// Save the updated bookings
		saveBookings();
	}
}
