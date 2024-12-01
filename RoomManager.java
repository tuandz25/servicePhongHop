package PhongHop;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class RoomManager {
	private static final String FILE_NAME = "src\\PhongHop\\rooms";
	private List<Room> rooms;

	public RoomManager() {
		this.rooms = new ArrayList<>();
		loadRooms();
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
}
