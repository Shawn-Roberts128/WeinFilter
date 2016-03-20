/**
 * Created by ::  llama ::  3/15/16.
 * For :: WeinFilder
 */

import java.awt.*;
import javax.swing.*;

public class UI extends JFrame {
    // Main
    public static void main(String[] args) {

        EventQueue.invokeLater(new Runnable() {

            @Override
            public void run() {
                JFrame.setDefaultLookAndFeelDecorated( false );

                UI ex = new UI(" Whatup ");
                ex.getContentPane().setBackground( Color.black );
                ex.setVisible(true);
            }
        });
    }

    // The UI class
    public UI(String string) {
        super(string);

        initUI();
    }

    // init the starts imitation of the window
    private void initUI(){

        this.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );

        JLabel label = new JLabel(" What up");
        JButton clickMe = new JButton("ClickME");
        clickMe.setBackground( Color.black );
//        Axis x= new Axis();

        this.getContentPane().add(label);
        this.getContentPane().add(clickMe);
//        this.getContentPane().add(x);



        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Container c = this.getContentPane();
        c.setBackground( Color.BLACK );
        this.getContentPane().setBackground(Color.black);
        this.repaint();
        this.pack();
        this.setSize(screenSize.width/2, screenSize.height/2);
        this.setLocationRelativeTo(null);
    }


}

