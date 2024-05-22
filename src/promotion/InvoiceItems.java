package promotion;

// Uncomment and correct the package if you have a database connection class
// import connection.database;

public class InvoiceItems {

    private int id;
    private String name;
    private int age;
    private double price; // Adding the price field

    // Constructor
    public InvoiceItems(int id, String name, int age, double price) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.price = price;
    }

    // Getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public double calculateWholesaleDiscount(int itemId, int quantity) {
        // Get item price (normally you would retrieve this from a database)
        double itemPrice = getItemPrice(itemId);
        // Get discount rate based on quantity (hardcoded for simplicity)
        double discountRate = getDiscountRate(quantity);

        double totalPrice = itemPrice * quantity;
        double discount = totalPrice * discountRate;

        double discountedPrice = totalPrice - discount;
        return discountedPrice;
    }

    private double getItemPrice(int itemId) {
        // In a real application, retrieve price from a database
        return this.price; // Assuming this.price is set correctly
    }

    private double getDiscountRate(int quantity) {
        if (quantity >= 100) {
            return 0.20; // 20% discount for 100 or more items
        } else if (quantity >= 50) {
            return 0.10; // 10% discount for 50 or more items
        } else {
            return 0.0; // No discount for less than 50 items
        }
    }

    public static void main(String[] args) {
        // Example usage
        InvoiceItems item = new InvoiceItems(1, "Item_Name", 30, 100.0);
        int itemId = 1;
        int quantity = 100;

        double discountedPrice = item.calculateWholesaleDiscount(itemId, quantity);
        System.out.println("Total price after discount: " + discountedPrice);
    }
}
