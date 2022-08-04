package controller;


import tm.CustomerTM;
import tm.ItemTM;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ItemCrudController {
    public static ArrayList<String> getItemId() throws SQLException, ClassNotFoundException {
        ResultSet result = CrudUtil.execute("SELECT itemCode FROM item" );
        ArrayList<String> idList = new ArrayList<>();
        while (result.next()) {
            idList.add(result.getString(1));
        }
        return idList;
    }

    public static ItemTM getItems(String id) throws SQLException, ClassNotFoundException {
        ResultSet result = CrudUtil.execute("SELECT * FROM item WHERE itemCode=?", id);
        if (result.next()) {
            return new ItemTM(
                    result.getString(1),
                    result.getString(2),
                    result.getInt(3),
                    result.getDouble(4));
        }
        return null;
    }

}
