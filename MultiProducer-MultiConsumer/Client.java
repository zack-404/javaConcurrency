
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CountDownLatch;

public class Client implements Runnable {
    
    Request request;
    BlockingQueue<Request> queue;
    CountDownLatch latch;

    public Client(Request request, BlockingQueue<Request> queue, CountDownLatch latch) {
        this.request = request;
        this.queue = queue;
        this.latch = latch;
    }

    @Override
    public void run() {
        try {
            request.status = "waiting";
            queue.put(request);
            synchronized (request) {
                while (request.status.equals("waiting")) {
                    request.wait(100);  // Use wait with timeout for better synchronization
                }
            }
            System.out.println(request.status);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt(); // Restore interrupted status
            System.err.println("Thread was interrupted: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("An error occurred: " + e.getMessage());
        } finally {
            latch.countDown(); // Decrease the count of the latch
        }
    }
}