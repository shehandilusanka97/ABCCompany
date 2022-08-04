package tm;

public class CartTM {
    private String id;
    private String description;
    private double unitPrice;
    private int qty;
    private double totalCost;

    public CartTM() {
    }

    public CartTM(String id, String description, double unitPrice, int qty, double totalCost) {
        this.id = id;
        this.description = description;
        this.unitPrice = unitPrice;
        this.qty = qty;
        this.totalCost = totalCost;
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

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(double totalCost) {
        this.totalCost = totalCost;
    }

    @Override
    public String toString() {
        return "CartTM{" +
                "id='" + id + '\'' +
                ", description='" + description + '\'' +
                ", unitPrice=" + unitPrice +
                ", qty=" + qty +
                ", totalCost=" + totalCost +
                '}';
    }
}
