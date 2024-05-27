package promotion;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;
import connection.database;

public class InvoiceItems {

    private String id;
    private String name;
    private int age;
    private double price;

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

    public double calculateWholesaleDiscount(String item_Id, int quantity) {
        double itemPrice = getItemPriceFromDatabase(item_Id);
        System.out.println("Item_ID: " + item_Id + " Item_Price: " + itemPrice);
        double discount = readPromotion(item_Id, itemPrice, quantity);
        double totalPrice = itemPrice * quantity;
        double discountedPrice = totalPrice - discount;
        return discountedPrice;
    }

    private double getItemPriceFromDatabase(String item_Id) {
        double itemPrice = 0.0;
        try (Connection conn = database.getConnection();
                PreparedStatement pstmt = conn.prepareStatement("SELECT Sales_Price FROM items WHERE item_id = ?")) {
            pstmt.setString(1, item_Id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    itemPrice = rs.getDouble("Sales_Price");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return itemPrice;
    }

    private double readPromotion(String item_Id, double salePrice, double itemQty) {
        double discount = 0.0;
        try (Connection conn = database.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(
                        "SELECT * FROM promotion_items pi " +
                                "JOIN promotion pro ON pro.Promo_ID = pi.Promo_ID " +
                                "WHERE pro.Active = 1 AND pi.Item_ID = ? AND pi.Qty <= ? " +
                                "ORDER BY pi.Qty DESC LIMIT 1")) {
            pstmt.setString(1, item_Id);
            pstmt.setDouble(2, itemQty);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    if ("PERCENTAGE".equals(rs.getString("DiscType"))) {
                        discount = (salePrice * itemQty) * (rs.getDouble("Discount") / 100);
                    } else {
                        discount = rs.getDouble("Discount") * itemQty;
                    }
                }
            }
            System.out.println("== ReadPromotion -> Item_ID: " + item_Id + " | SalePrice: " + salePrice + " | ItemQty: "
                    + itemQty + " | Discount: " + discount);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return discount;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter item ID: ");
        String item_Id = scanner.nextLine();

        int quantity = 0;
        while (true) {
            try {
                System.out.print("Enter item quantity: ");
                quantity = Integer.parseInt(scanner.nextLine());
                break;
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid integer for item quantity.");
            }
        }

        InvoiceItems item = new InvoiceItems(item_Id, "Item_Name", 0, 0.0);
        double discountedPrice = item.calculateWholesaleDiscount(item_Id, quantity);
        System.out.println("Total price after discount: " + discountedPrice);

        scanner.close();
    }
}
