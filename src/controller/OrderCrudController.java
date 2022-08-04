package controller;

import tm.Order;
import tm.OrderDetails;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class OrderCrudController {
    public boolean saveOrder(Order order) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("INSERT INTO orders VALUES(?,?,?)",
                order.getId(), order.getDate(), order.getCustomerId());
    }
    public boolean saveOrderDetails(ArrayList<OrderDetails> details) throws SQLException, ClassNotFoundException {
        for (OrderDetails det:details
        ) {
            boolean isDetailsSaved=CrudUtil.execute("INSERT INTO orderDetail VALUES(?,?,?)",
                    det.getOrderId(),det.getItemCode(),det.getQty());
            if (isDetailsSaved){
                if (!updateQty(det.getItemCode(), det.getQty())){
                    return false;
                }
            }else {
                return false;
            }
        }
        return true;
    }

    private boolean updateQty(String itemCode, int qty) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("UPDATE item SET qtyOnHand=qtyOnHand-? WHERE itemCode=?", qty,itemCode);
    }

}
