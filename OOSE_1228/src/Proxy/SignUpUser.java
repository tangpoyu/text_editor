package Proxy;

public class SignUpUser extends SQLCmd{

    public SignUpUser(String username,String password){
        this.username = username;
        this.result = password;
    }
    @Override
    public void makequery() {
        this.sql = "INSERT INTO EDITOR_USER VALUES ('"+username+"','"+result+"')";
    }

    @Override
    public void processResult() {
        //do nothing
    }
}
