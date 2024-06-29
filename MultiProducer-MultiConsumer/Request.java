public class Request {
    
    String status;
    String account;
    String password;

    public Request(String status, String account, String password) {
        this.status = status;
        this.account = account;
        this.password = password;
    }

    public String getStatus() { return status; }
    public String getAccount() { return account; }
    public String GetPassword() { return password; }
}
