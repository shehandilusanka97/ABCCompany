package controller;

import tm.CustomerTM;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CustomerCrudController {
    public static ArrayList<String> getCustomerId() throws SQLException, ClassNotFoundException {
        ResultSet result = CrudUtil.execute("SELECT cId FROM Customer" );
        ArrayList<String> idList = new ArrayList<>();
        while (result.next()) {
            idList.add(result.getString(1));
        }
        return idList;
    }

    public static CustomerTM getCustomer(String id) throws SQLException, ClassNotFoundException {
        ResultSet result = CrudUtil.execute("SELECT * FROM Customer WHERE cId=?", id);
        if (result.next()) {
            return new CustomerTM(
                    result.getString(1),
                    result.getString(2),
                    result.getString(3),
                    result.getString(4),
                    result.getString(5)
            );
        }
        return null;
    }
}
