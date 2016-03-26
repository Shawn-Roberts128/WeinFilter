/**
 * Created by ::  llama ::  3/15/16.
 * For :: WeinFilder
 *
 * Good tautorial :: https://www.youtube.com/watch?v=G4jMzEGMKfg
 */

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
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

        JMenu menu = new JMenu("Menu");
        JMenuItem item = new JMenuItem("Quit");
        item.setMnemonic(KeyEvent.VK_Q);
        item.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                System.out.println();
                UI.this.setVisible(false);
                UI.this.dispose();
            }
        });
        menu.add(item);

        JMenuBar menuBar = new JMenuBar();
        menuBar.add(menu);
        setJMenuBar( menuBar );
        this.getContentPane().add(menuBar);
//        this.getContentPane().add(x);

        this.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );

        JLabel label = new JLabel(" What up");
        JButton clickMe = new JButton("ClickME");
        clickMe.setBackground( Color.black );
//        Axis x= new Axis();

        this.getContentPane().add(label);
        this.getContentPane().add(clickMe);

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

