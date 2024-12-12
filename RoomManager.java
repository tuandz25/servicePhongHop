package PhongHop;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class RoomManager {
	private static final String FILE_NAME = "src\\PhongHop\\rooms";
	private List<Room> rooms;
	private List<Booking> bookings;

	public RoomManager() {
		this.rooms = new ArrayList<>();
		this.bookings = new ArrayList<>();
		loadRooms();
		loadBookings();
	}

	public List<Room> getRooms() {
		return rooms;
	}

	public void createRoom(String name, String type, int capacity, double price, String status, int floor) {
		Room room = new Room(name, type, capacity, price, status, floor);
		rooms.add(room);
		saveRooms(); // Lưu toàn bộ danh sách phòng
	}

	public void displayRooms() {
		for (Room room : rooms) {
			System.out.println("--------------------------");
			room.displayInfo();
			System.out.println("--------------------------");
		}
	}

	private void saveRooms() {
		try (FileWriter fw = new FileWriter(FILE_NAME, false); // Ghi đè file
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
		try (FileReader fr = new FileReader(FILE_NAME); BufferedReader br = new BufferedReader(fr)) {
			String line;
			while ((line = br.readLine()) != null) {
				Room room = Room.fromCSV(line);
				rooms.add(room);
			}
		} catch (FileNotFoundException e) {
			System.out.println("File không tồn tại, tạo file mới: " + FILE_NAME);
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

		try (FileWriter fw = new FileWriter("src\\PhongHop\\bookings", false);
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
		try (FileReader fr = new FileReader("src\\PhongHop\\bookings"); BufferedReader br = new BufferedReader(fr)) {
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
	private String bookingToCSV(Booking booking) {
		return String.join(",", booking.getBooking_id(), booking.getRoom().getName(),
				booking.getStart_time().toString(), booking.getEnd_time().toString(),
				String.valueOf(booking.getAttendees()), booking.getManager());
	}

	// Chuyển chuỗi CSV sang booking
	private Booking bookingFromCSV(String csv) {
		String[] parts = csv.split(",");
		if (parts.length != 6) {
			System.out.println("Dữ liệu không hợp lệ: " + csv);
			return null;
		}

		// Tìm phòng tương ứng
		Room room = rooms.stream().filter(r -> r.getName().equals(parts[1])).findFirst().orElse(null);

		if (room == null) {
			System.out.println("Không tìm thấy phòng có tên: " + parts[1]);
			return null; // Trả về null nếu phòng không tồn tại
		}

		return new Booking(room, LocalDateTime.parse(parts[2]), LocalDateTime.parse(parts[3]),
				Integer.parseInt(parts[4]), parts[5]);
	}

	public Booking getBooking(String bookingId) {
		return bookings.stream().filter(b -> b.getBooking_id().equals(bookingId)).findFirst().orElse(null);
	}
}
