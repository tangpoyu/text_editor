package Proxy;

import java.sql.SQLException;
import java.util.ArrayList;

public class GetAllUser extends SQLCmd{

    @Override
    public void makequery() {
        this.sql = "SELECT USERNAME FROM EDITOR_USER";
    }

    @Override
    public void processResult() throws SQLException {
        ArrayList<String> usernames = new ArrayList<>();
        while (rows.next()){
           usernames.add(rows.getString("USERNAME"));
        }
        result = usernames;
    }
}
