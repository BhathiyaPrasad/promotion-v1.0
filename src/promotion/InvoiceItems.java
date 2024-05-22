package promotion;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;
import connection.database; // Ensure the correct package import

public class InvoiceItems {

    private String id;
    private String name;
    private int age;
    private double price; // Adding the price field

    // Constructor
    public InvoiceItems(String id, String name, int age, double price) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.price = price;
    }

    // Getters and setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "InvoiceItems{id=" + id + ", name='" + name + "', age=" + age + ", price=" + price + "}";
    }

    public double calculateWholesaleDiscount(String itemId, int quantity) {
        // Get item price from the database
        double itemPrice = getItemPriceFromDatabase(itemId);

        System.out.println("Item_ID :" + itemId + "Item_Price : " + itemPrice);
        // Get discount rate based on quantity (hardcoded for simplicity)
        double discountRate = getDiscountRate(quantity);

        double totalPrice = itemPrice * quantity;
        double discount = totalPrice * discountRate;

        double discountedPrice = totalPrice - discount;
        return discountedPrice;
    }

    private double getItemPriceFromDatabase(String itemId) {
        double itemPrice = 0.0;
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            // Get database connection
            conn = database.getConnection();

            // Prepare and execute SQL query
            String sql = "SELECT Sales_Price FROM items WHERE item_id = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, itemId); // Set the itemId parameter
            rs = pstmt.executeQuery();

            // Extract item price from result set
            if (rs.next()) {
                itemPrice = rs.getDouble("Sales_Price");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Close resources
            try {
                if (rs != null)
                    rs.close();
                if (pstmt != null)
                    pstmt.close();
                if (conn != null)
                    conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return itemPrice;
    }

    private double getDiscountRate(int quantity) {
        if (quantity == 1) {
            return 0.0; // 0% discount
        } else if (quantity == 2) {
            return 0.05; // 5% discount
        } else if (quantity == 3) {
            return 0.10; // 10% discount
        } else if (quantity >= 4 && quantity <= 5) {
            return 0.15; // 15% discount
        } else if (quantity >= 6 && quantity <= 10) {
            return 0.20; // 20% discount
        } else if (quantity >= 11 && quantity <= 25) {
            return 0.25; // 25% discount
        } else if (quantity > 25) {
            return 0.40; // 40% discount
        } else {
            return 0.0; // No discount
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        String itemId;
        int quantity = 0;

        // Prompt the user for item ID
        System.out.print("Enter item ID: ");
        itemId = scanner.nextLine();

        // Prompt the user for item quantity
        while (true) {
            try {
                System.out.print("Enter item quantity: ");
                quantity = Integer.parseInt(scanner.nextLine());
                break; // Exit loop if input is valid
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid integer for item quantity.");
            }
        }

        // Create an InvoiceItems object (placeholder values for name and age)
        InvoiceItems item = new InvoiceItems(itemId, "Item_Name", 0, 0.0);

        // Calculate the discounted price
        double discountedPrice = item.calculateWholesaleDiscount(itemId, quantity);

        // Output the total price after discount
        System.out.println("Total price after discount: " + discountedPrice);

        // Close the scanner
        scanner.close();
    }
}
