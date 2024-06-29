
import java.util.HashMap;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

public class Server implements Runnable {
    
    HashMap<String, String> accountList;
    BlockingQueue<Request> queue;
    int number;

    public Server(HashMap<String, String> accountList, BlockingQueue<Request> queue, int number) {
        this.accountList = accountList;
        this.queue = queue;
        this.number = number;
    }

    @Override
    public void run() {
        try {
            while (true) { // Loop to continuously process requests
                Request request = queue.poll(2, TimeUnit.SECONDS); // Use poll() with a timeout of 2 seconds

                if (request == null) {
                    // If no request was retrieved within the timeout, break the loop (shutdown condition)
                    System.out.println("No requests received for 2 seconds. Shutting down server " + number);
                    break;
                }

                synchronized (request) { // Synchronize on the request to safely update status
                    if (accountList.get(request.account) != null && accountList.get(request.account).equals(request.password)) {
                        request.status = new StringBuilder("Log in success by ").append(number).toString();
                    } else {
                        request.status = "Log in denied";
                    }
                    request.notifyAll(); // Notify any waiting threads that the status has been updated
                }
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt(); // Restore interrupted status
            System.err.println("Thread was interrupted: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("An error occurred: " + e.getMessage());
        }
    }
}
