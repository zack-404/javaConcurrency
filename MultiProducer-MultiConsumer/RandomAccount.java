
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.HashMap;

public class RandomAccount {
    
    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final SecureRandom RANDOM = new SecureRandom();

    int length;
    HashMap<String, String> accountList;
    ArrayList<Request> requests;

    public RandomAccount(int length) {
        this.length = length;
        accountList = new HashMap<>();
        requests = new ArrayList<>(length);

        for(int i=0; i < length; i++) {
            String account = generate(8);
            String password = generate(12);
            accountList.put(account, password);
            Request request = new Request("unkown", account, password);
            requests.add(request);
        }
    }

    public int getLength() { return length; }

    public static String generate(int length) {
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            sb.append(CHARACTERS.charAt(RANDOM.nextInt(CHARACTERS.length())));
        }
        return sb.toString();
    }
}