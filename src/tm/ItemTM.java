package tm;

public class ItemTM {
    private String id;
    private String description;
    private int qty;
    private double price;

    public ItemTM(String code, String itemCode) {
    }

    public ItemTM(String id, String description, int qty, double price) {
        this.id = id;
        this.description = description;
        this.qty = qty;
        this.price = price;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "ItemTM{" +
                "id='" + id + '\'' +
                ", description='" + description + '\'' +
                ", qty=" + qty +
                ", price=" + price +
                '}';
    }
}
