package GUI; /**
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

    // The GUI.UI class
    public UI(String string) {
        super(string);

        initUI();
    }

    // init the starts imitation of the window
    private void initUI(){
        getContentPane().setLayout(new BorderLayout());
        this.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );

        setmenuBar();
        setbuttons();


        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Container c = this.getContentPane();
        c.setBackground( Color.BLACK );
        this.getContentPane().setBackground(Color.black);
        this.repaint();

        this.setVisible(true);
        this.pack();this.revalidate();
        this.setSize(screenSize.width/2, screenSize.height/2);
        this.setLocationRelativeTo(null);
    }

    private void setbuttons() {
        JLabel label = new JLabel(" What up");
        JButton clickMe = new JButton("ClickME");
        clickMe.setBackground( Color.black );


        this.getContentPane().add(label,BorderLayout.CENTER);
        this.getContentPane().add(clickMe, BorderLayout.PAGE_END);


    }

    private void setmenuBar() {
        // add the quit button
        JMenuItem quit = new JMenuItem("Quit");
        quit.setMnemonic(KeyEvent.VK_Q);
        quit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                System.out.println();
                UI.this.setVisible(true);
                UI.this.dispose();
            }
        });

        // the file save botton
        JMenuItem fileSave = new JMenuItem("Save Trajectory");
        fileSave.setMnemonic(KeyEvent.VK_S);
        fileSave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                System.out.println("Print out file");
                //TODO add the save file as .txt
            }
        });


        JMenu menu = new JMenu("Menu");
        menu.setMnemonic(KeyEvent.VK_M);
        menu.add(quit);
        menu.add(fileSave);

        JMenuBar menuBar = new JMenuBar();
        menuBar.add(menu);
        setJMenuBar(menuBar);


        this.getContentPane().add(menuBar, BorderLayout.PAGE_START);
    }

}

