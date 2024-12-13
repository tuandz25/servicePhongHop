package PhongHop;

import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class Report {
    private HashMap<String, Integer> usageCount;
    private HashMap<String, Double> totalHours;
    private HashMap<String, Double> totalRevenue;

    // Methods to generate reports
}

public double calculateRevenue(List<Booking> bookings) {
    double totalRevenue = 0;
    for (Booking booking : bookings) {
        double duration = booking.getEndTime().compareTo(booking.getStartTime());
        double roomCost = booking.getRoom().getPricePerHour() * duration;
        double serviceCost = booking.getServices().stream()
            .mapToDouble(service -> service.getPricePerHour() * duration)
            .sum();
        totalRevenue += roomCost + serviceCost;
    }
    return totalRevenue;
}


public class ReportGenerator {
    public Report monthlyReport(List<Booking> bookings) {
        Report report = new Report();
        for (Booking booking : bookings) {
            String month = booking.getStartTime().toString().substring(0, 7); // YYYY-MM
            report.getUsageCount().merge(month, 1, Integer::sum);
            report.getTotalHours().merge(month, (double) booking.getEndTime().compareTo(booking.getStartTime()), Double::sum);
            report.getTotalRevenue().merge(month, calculateRevenue(List.of(booking)), Double::sum);
        }
        return report;
    }
}
