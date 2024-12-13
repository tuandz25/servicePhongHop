package PhongHop;

import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Date;
import java.util.Calendar;


class MeetingRoom {
    private String name;
    private String type; // "Thường" hoặc "VIP"
    private int capacity;
    private double hourlyRate;
    private String status; // "Trống", "Đã đặt", "Đang sử dụng"
    private int floor;

    public MeetingRoom(String name, String type, int capacity, double hourlyRate, String status, int floor) {
        this.name = name;
        this.type = type;
        this.capacity = capacity;
        this.hourlyRate = hourlyRate;
        this.status = status;
        this.floor = floor;
    }

    public String getName() {
        return name;
    }

    public double getHourlyRate() {
        return hourlyRate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

class Booking {
    private MeetingRoom room;
    private Date startTime;
    private Date endTime;
    private String manager;
    private List<String> services;
    private double totalCost;

    public Booking(MeetingRoom room, Date startTime, Date endTime, String manager, List<String> services, double totalCost) {
        this.room = room;
        this.startTime = startTime;
        this.endTime = endTime;
        this.manager = manager;
        this.services = services;
        this.totalCost = totalCost;
    }

    public MeetingRoom getRoom() {
        return room;
    }

    public Date getStartTime() {
        return startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public double getTotalCost() {
        return totalCost;
    }
}

class RoomManager {
    private List<Booking> bookings;

    public RoomManager() {
        this.bookings = new ArrayList<>();
    }

    public void addBooking(Booking booking) {
        bookings.add(booking);
    }

    public List<Booking> getBookings() {
        return bookings;
    }
}

class ReportGenerator {
    public int getUsageCount(String roomName, List<Booking> bookings) {
        return (int) bookings.stream().filter(b -> b.getRoom().getName().equals(roomName)).count();
    }

    public double getTotalHoursBooked(List<Booking> bookings) {
        return bookings.stream()
                .mapToDouble(b -> (b.getEndTime().getTime() - b.getStartTime().getTime()) / (1000.0 * 60 * 60))
                .sum();
    }

    public double getTotalRevenue(List<Booking> bookings) {
        return bookings.stream().mapToDouble(Booking::getTotalCost).sum();
    }

    public void generateMonthlyReport(List<Booking> bookings, int month, int year) {
        List<Booking> monthlyBookings = new ArrayList<>();
        for (Booking booking : bookings) {
            Calendar cal = Calendar.getInstance();
            cal.setTime(booking.getStartTime());
            if (cal.get(Calendar.MONTH) + 1 == month && cal.get(Calendar.YEAR) == year) {
                monthlyBookings.add(booking);
            }
        }
        System.out.println("Báo cáo tháng " + month + " năm " + year + ":");
        System.out.println("Số lần sử dụng: " + monthlyBookings.size());
        System.out.println("Tổng số giờ thuê: " + getTotalHoursBooked(monthlyBookings));
        System.out.println("Doanh thu: " + getTotalRevenue(monthlyBookings));
    }

    public void generateQuarterlyReport(List<Booking> bookings, int quarter, int year) {
        List<Booking> quarterlyBookings = new ArrayList<>();
        int startMonth = (quarter - 1) * 3 + 1;
        int endMonth = startMonth + 2;

        for (Booking booking : bookings) {
            Calendar cal = Calendar.getInstance();
            cal.setTime(booking.getStartTime());
            int month = cal.get(Calendar.MONTH) + 1;
            if (month >= startMonth && month <= endMonth && cal.get(Calendar.YEAR) == year) {
                quarterlyBookings.add(booking);
            }
        }
        System.out.println("Báo cáo quý " + quarter + " năm " + year + ":");
        System.out.println("Số lần sử dụng: " + quarterlyBookings.size());
        System.out.println("Tổng số giờ thuê: " + getTotalHoursBooked(quarterlyBookings));
        System.out.println("Doanh thu: " + getTotalRevenue(quarterlyBookings));
    }

    public void generateYearlyReport(List<Booking> bookings, int year) {
        List<Booking> yearlyBookings = new ArrayList<>();
        for (Booking booking : bookings) {
            Calendar cal = Calendar.getInstance();
            cal.setTime(booking.getStartTime());
            if (cal.get(Calendar.YEAR) == year) {
                yearlyBookings.add(booking);
            }
        }
        System.out.println("Báo cáo năm " + year + ":");
        System.out.println("Số lần sử dụng: " + yearlyBookings.size());
        System.out.println("Tổng số giờ thuê: " + getTotalHoursBooked(yearlyBookings));
        System.out.println("Doanh thu: " + getTotalRevenue(yearlyBookings));
    }
}

public class MeetingRoomManager {
    public static void main(String[] args) {
        RoomManager roomManager = new RoomManager();
        ReportGenerator reportGenerator = new ReportGenerator();
        Scanner scanner = new Scanner(System.in);

        // Thêm một số phòng họp mẫu
        MeetingRoom room1 = new MeetingRoom("Phòng A", "Thường", 20, 100.0, "Trống", 1);
        MeetingRoom room2 = new MeetingRoom("Phòng B", "VIP", 10, 200.0, "Trống", 2);
        MeetingRoom room3 = new MeetingRoom("Phòng C", "Thường", 30, 150.0, "Trống", 3);
        
        // Giả sử đã có một số booking được thêm vào
        List<String> services1 = new ArrayList<>();
        services1.add("Trà và cà phê");
        Booking booking1 = new Booking(room1, new Date(), new Date(System.currentTimeMillis() + 2 * 60 * 60 * 1000), "Nguyễn Văn A", services1, room1.getHourlyRate() * 2);
        roomManager.addBooking(booking1);

        List<String> services2 = new ArrayList<>();
        services2.add("Hỗ trợ kỹ thuật");
        Booking booking2 = new Booking(room2, new Date(), new Date(System.currentTimeMillis() + 3 * 60 * 60 * 1000), "Trần Thị B", services2, room2.getHourlyRate() * 3);
        roomManager.addBooking(booking2);

        while (true) {
            System.out.println("Chọn loại báo cáo:");
            System.out.println("1. Báo cáo theo tháng");
            System.out.println("2. Báo cáo theo quý");
            System.out.println("3. Báo cáo theo năm");
            System.out.println("0. Thoát");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    System.out.print("Nhập tháng (1-12): ");
                    int month = scanner.nextInt();
                    System.out.print("Nhập năm: ");
                    int year = scanner.nextInt();
                    reportGenerator.generateMonthlyReport(roomManager.getBookings(), month, year);
                    break;

                case 2:
                    System.out.print("Nhập quý (1-4): ");
                    int quarter = scanner.nextInt();
                    System.out.print("Nhập năm: ");
                    year = scanner.nextInt();
                    reportGenerator.generateQuarterlyReport(roomManager.getBookings(), quarter, year);
                    break;

                case 3:
                    System.out.print("Nhập năm: ");
                    year = scanner.nextInt();
                    reportGenerator.generateYearlyReport(roomManager.getBookings(), year);
                    break;

                case 0:
                    System.out.println("Thoát chương trình.");
                    scanner.close();
                    return;

                default:
                    System.out.println("Lựa chọn không hợp lệ. Vui lòng thử lại.");
            }
        }
    }
}
