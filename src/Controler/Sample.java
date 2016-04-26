package Controler; /** Test the use of Wein filter
 * Created by llama on 3/15/16.
 */



import java.awt.EventQueue;
import javax.swing.JFrame;

public class Sample extends JFrame {

    public Sample() {

        initUI();
    }

    private void initUI() {

        setTitle("Test window");
        setSize(300, 200);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {

        EventQueue.invokeLater(new Runnable() {

            @Override
            public void run() {
                Sample ex = new Sample();
                ex.setVisible(true);
            }
        });
    }
}

