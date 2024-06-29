
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.LinkedBlockingDeque;

public class testMPMC {
    public static void main(String[] args) {

        int length = 100000;
        RandomAccount random = new RandomAccount(length);
        BlockingQueue<Request> queue = new LinkedBlockingDeque<>();

        Thread server0 = new Thread(new Server(random.accountList, queue, 0));
        Thread server1 = new Thread(new Server(random.accountList, queue, 1));
        Thread server2 = new Thread(new Server(random.accountList, queue, 2));
        Thread server3 = new Thread(new Server(random.accountList, queue, 3));

        server0.start();
        server1.start();
        server2.start();
        server3.start();

        int clientsPerBatch = 10;
        CountDownLatch latch = new CountDownLatch(length / clientsPerBatch * clientsPerBatch);

        for(int i=0; i < length/10; i++){
            Thread client0 = new Thread(new Client(random.requests.get(i*10), queue, latch));
            Thread client1 = new Thread(new Client(random.requests.get(i*10 + 1), queue, latch));
            Thread client2 = new Thread(new Client(random.requests.get(i*10 + 2), queue, latch));
            Thread client3 = new Thread(new Client(random.requests.get(i*10 + 3), queue, latch));
            Thread client4 = new Thread(new Client(random.requests.get(i*10 + 4), queue, latch));
            Thread client5 = new Thread(new Client(random.requests.get(i*10 + 5), queue, latch));
            Thread client6 = new Thread(new Client(random.requests.get(i*10 + 6), queue, latch));
            Thread client7 = new Thread(new Client(random.requests.get(i*10 + 7), queue, latch));
            Thread client8 = new Thread(new Client(random.requests.get(i*10 + 8), queue, latch));
            Thread client9 = new Thread(new Client(random.requests.get(i*10 + 9), queue, latch));

            client0.start();
            client1.start();
            client2.start();
            client3.start();
            client4.start();
            client5.start();
            client6.start();
            client7.start();
            client8.start();
            client9.start();
        }
    }
}
