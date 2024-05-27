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

    public double calculateWholesaleDiscount(String itemId, int quantity) {
        double itemPrice = getItemPriceFromDatabase(itemId);
        System.out.println("Item_ID: " + itemId + " Item_Price: " + itemPrice);
        double discountRate = getDiscountRate(quantity);
        double totalPrice = itemPrice * quantity;
        double discount = totalPrice * discountRate;
        double discountedPrice = totalPrice - discount;
        return discountedPrice;
    }

    private double getItemPriceFromDatabase(String itemId) {
        double itemPrice = 0.0;
        try (Connection conn = database.getConnection();
                PreparedStatement pstmt = conn.prepareStatement("SELECT Sales_Price FROM items WHERE item_id = ?")) {
            pstmt.setString(1, itemId);
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

    public void addIntoSaleJtble() {
        String itemId = "00100";
        double salePrice = 2500.00;
        double itemQty = 20;

        // Call into Promotion method
        double givenDiscount = readPromotion(itemId, salePrice, itemQty);

        System.out.println("== AddIntoSaleJtble -> ItemID: " + itemId + " | SalePrice: " + salePrice + " | ItemQty: "
                + itemQty + " | Discount: " + givenDiscount);
        // Normal process
    }

    private double readPromotion(String itemId, double salePrice, double itemQty) {
        double discount = 0.0;
        try (Connection conn = database.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(
                        "SELECT * FROM promotion_items pi " +
                                "JOIN promotion pro ON pro.Promo_ID = pi.Promo_ID " +
                                "WHERE pro.Active = 1 AND pi.Item_ID = ? AND pi.Qty <= ? " +
                                "ORDER BY pi.Qty DESC LIMIT 1")) {
            pstmt.setString(1, itemId);
            pstmt.setDouble(2, itemQty);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    if ("PERCENTAGE".equals(rs.getString("DiscType"))) {
                        discount = (salePrice / 100) * rs.getDouble("Discount");
                    } else {
                        discount = rs.getDouble("Discount");
                    }
                }
            }
            System.out.println("== ReadPromotion -> ItemID: " + itemId + " | SalePrice: " + salePrice + " | ItemQty: "
                    + itemQty + " | Discount: " + discount);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return discount;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter item ID: ");
        String itemId = scanner.nextLine();

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

        InvoiceItems item = new InvoiceItems(itemId, "Item_Name", 0, 0.0);
        double discountedPrice = item.calculateWholesaleDiscount(itemId, quantity);
        System.out.println("Total price after discount: " + discountedPrice);

        item.addIntoSaleJtble();

        scanner.close();
    }
}
