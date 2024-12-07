package phonghop;

import java.util.ArrayList;

public class service {
    private int rentalDuration; // Thời gian thuê (giờ)
    private double totalServiceCost = 0; // Tổng chi phí dịch vụ
    private ArrayList<String> selectedServices = new ArrayList<>(); // Danh sách dịch vụ đã chọn

    // Chi phí cố định cho từng dịch vụ
    private static final double COFFEE_SERVICE_COST = 50.0;
    private static final double TECH_SUPPORT_COST = 100.0;
    private static final double WIFI_COST = 30.0;

    // Constructor
    public service(int rentalDuration) {
        this.rentalDuration = rentalDuration;
    }

    // Thêm dịch vụ
    public void addService(String serviceName) {
        if (!selectedServices.contains(serviceName)) {
            selectedServices.add(serviceName);

            // Cập nhật tổng chi phí
            if (serviceName.equals("Coffee")) totalServiceCost += COFFEE_SERVICE_COST * rentalDuration;
            else if (serviceName.equals("Tech Support")) totalServiceCost += TECH_SUPPORT_COST * rentalDuration;
            else if (serviceName.equals("Wi-Fi")) totalServiceCost += WIFI_COST * rentalDuration;

            System.out.println("Dịch vụ " + serviceName + " đã được thêm.");
        } else {
            System.out.println("Bạn đã chọn dịch vụ " + serviceName + " rồi.");
        }
    }

    // Xóa dịch vụ
    public void removeService(String serviceName) {
        if (selectedServices.contains(serviceName)) {
            selectedServices.remove(serviceName);

            // Cập nhật tổng chi phí
            if (serviceName.equals("Coffee")) totalServiceCost -= COFFEE_SERVICE_COST * rentalDuration;
            else if (serviceName.equals("Tech Support")) totalServiceCost -= TECH_SUPPORT_COST * rentalDuration;
            else if (serviceName.equals("Wi-Fi")) totalServiceCost -= WIFI_COST * rentalDuration;

            System.out.println("Dịch vụ " + serviceName + " đã được xóa.");
        } else {
            System.out.println("Dịch vụ " + serviceName + " không tồn tại trong danh sách.");
        }
    }

    // Hiển thị danh sách dịch vụ đã chọn
    public void showService() {
        if (!selectedServices.isEmpty()) {
            System.out.println("Danh sách dịch vụ đã chọn:");
            int i = 1;
            for (String service : selectedServices) {
                double costPerHour = 0;

                // Xác định chi phí mỗi giờ của dịch vụ
                if (service.equals("Coffee")) costPerHour = COFFEE_SERVICE_COST;
                else if (service.equals("Tech Support")) costPerHour = TECH_SUPPORT_COST;
                else if (service.equals("Wi-Fi")) costPerHour = WIFI_COST;

                System.out.println(i + ". " + service + " - " + costPerHour + "$/giờ (Tổng: " + (costPerHour * rentalDuration) + "$)");
                i++;
            }
        } else {
            System.out.println("Bạn chưa thêm dịch vụ nào.");
        }
    }

    // Lấy tổng chi phí dịch vụ
    public double getTotalServiceCost() {
        return totalServiceCost;
    }

    // Cập nhật thời gian thuê (nếu cần)
    public void updateRentalDuration(int newRentalDuration) {
        if (newRentalDuration > 0) {
            rentalDuration = newRentalDuration;

            // Tái tính tổng chi phí
            totalServiceCost = 0;
            for (String service : selectedServices) {
                if (service.equals("Coffee")) totalServiceCost += COFFEE_SERVICE_COST * rentalDuration;
                else if (service.equals("Tech Support")) totalServiceCost += TECH_SUPPORT_COST * rentalDuration;
                else if (service.equals("Wi-Fi")) totalServiceCost += WIFI_COST * rentalDuration;
            }
        } else {
            System.out.println("Thời gian thuê không hợp lệ.");
        }
    }
}
