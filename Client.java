import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;


public class Client {
    private static Socket s;
    private PrintWriter pw;

    public Client(){
        connect();
        if(s.isConnected()){
            new Dialogue(s);
        }

    }

    void connect(){
        System.out.println("Démarrage client .... ");
        try {
            s = new Socket("127.0.0.1",9000);
            System.out.println(" je me suis connecte ");

        } catch (UnknownHostException e) {

            System.out.println("erreur host ? " + e.getMessage());

        }
        catch (IOException e) {
            System.out.println("erreur socket ? " + e.getMessage());

        }
    }

    public static void main(String[] args) {
        new Client();
    }
}
