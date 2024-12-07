package phonghop;

import java.util.Scanner;

public class Addservice {
    private service new_service;

    // Constructor để khởi tạo dịch vụ
    public Addservice(int rentalDuration) {
        this.new_service = new service(rentalDuration);
    }

    public void Add_Service() {
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println("-----Dịch vụ-----");
            System.out.println("1. Trà và Cà phê");
            System.out.println("2. Hỗ trợ kỹ thuật");
            System.out.println("3. Wifi tốc độ cao");
            System.out.println("4. Xóa dịch vụ");
            System.out.println("5. Xem tổng chi phí dịch vụ");
            System.out.println("6. Thoát");
            System.out.println("-----------------");
            System.out.print("Nhập lựa chọn của bạn (số): ");
            int n = sc.nextInt();

            switch (n) {
                case 1 -> new_service.addService("Coffee");
                case 2 -> new_service.addService("Tech Support");
                case 3 -> new_service.addService("Wi-Fi");
                case 4 -> {
                    System.out.println("---- Dịch vụ hiện có ----");
                    new_service.showService();
                    System.out.print("Nhập tên dịch vụ muốn xóa: ");
                    sc.nextLine(); // Bỏ qua dòng trống
                    String serviceToRemove = sc.nextLine();
                    new_service.removeService(serviceToRemove);
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
}
