package Proxy;

import java.sql.SQLException;

public class GetUser extends SQLCmd{

    public GetUser(String username){
        this.username=username;
    }
    @Override
    public void makequery() {
        this.sql = " SELECT * FROM EDITOR_USER WHERE username='"+username+"'";
    }

    @Override
    public void processResult() throws SQLException {
        while(rows.next()) {
            result = rows.getString("PASSWORD");
        }
    }
}
