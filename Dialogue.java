
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;



public class Dialogue extends JFrame {


    private final JTextField saisir;
    JPanel center;
    Socket sock;
    String received;

    public Dialogue (Socket s) {

        sock = s;

        this.setSize(500,500);
        this.setLayout(new BorderLayout());
        center = new JPanel();
        JScrollPane js = new JScrollPane(center);
        center.setLayout(new BoxLayout(center, BoxLayout.PAGE_AXIS));
        add(js);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);

        this.setTitle(" * Chat Client * ");
        saisir = new JTextField(10) ;
        JButton btn = new JButton("send");


        Thread rec = new Thread(new Receiver());
        rec.start();


        btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JPanel ser = new JPanel();
                ser.setPreferredSize(new Dimension(450, 50));
                ser.setLayout(new FlowLayout(FlowLayout.RIGHT));

                String str = saisir.getText();
                if (!str.isEmpty()){
                    JLabel txt = new JLabel(str);
                    txt.setForeground(Color.PINK);
                    ser.add(txt);
                    send(str);
                }
                center.add(ser);
                Dialogue.this.revalidate();
            }
        });

        JPanel send = new JPanel();
        send.setLayout(new FlowLayout());
        send.add(saisir);
        send.add(btn);
        this.add(send,BorderLayout.SOUTH) ;


        this.setVisible(true);
    }



    void send ( String s ) {
        try {
            PrintWriter pw;
            pw = new PrintWriter(sock.getOutputStream());
            pw.println(s);
            pw.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }




    class Receiver implements Runnable{

        @Override
        public void run() {
            InputStreamReader input ;

            try {

                while (true) {
                    if (sock.isConnected()) {

                        input = new InputStreamReader(sock.getInputStream());
                        BufferedReader br = new BufferedReader(input);
                        received = br.readLine();

                        if (!received.isEmpty()) {
                            
                        JPanel ser = new JPanel();
                        ser.setPreferredSize(new Dimension(450, 50));
                        ser.setLayout(new FlowLayout(FlowLayout.LEFT));
                        JLabel text = new JLabel(received);
                        ser.add(text);
                        center.add(ser);

                        Dialogue.this.revalidate();
                        }


                    } else {
                        break;
                    }
                }
            }catch (SocketException e){
                System.err.println("connection reset !!");
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }


        }

    }

    public static void main(String[] args) {

    }

}
