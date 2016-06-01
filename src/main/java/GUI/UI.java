package GUI; /**
 * Created by ::  llama ::  3/15/16.
 * For :: WienFilter
 *
 * Good tutorial :: https://www.youtube.com/watch?v=G4jMzEGMKfg
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

                UI ex = new UI(" What up ");
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
        this.getContentPane().setLayout(new BorderLayout());
        this.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );

        setmenuBar();
        addLeftFrame();
        addRightFrame();


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
        JPanel startCond = new JPanel(new SpringLayout());
        startCond.setBackground(Color.RED);


        JButton clickMe = new JButton("ClickME");
        clickMe.setBackground( Color.BLUE );
        startCond.add(clickMe);

        //this.getContentPane().add(label,BorderLayout.CENTER);
        //this.getContentPane().add(clickMe, BorderLayout.PAGE_END);
        startCond.setVisible(true);
        this.getContentPane().add(startCond, BorderLayout.LINE_START);

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

        // the file save button
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


    private void addLeftFrame() {
        int row = 10;
        int col = 2;
        JPanel left = new JPanel(new GridLayout(0 , col, 5, 5));
        this.getContentPane().add(left, BorderLayout.LINE_START);


        // New custom values
        SetPartical part = new SetPartical();
        left.add(part);

        // Add Pre-made
        String [] preSet =
                new String[]{   "Electron",
                                "Proton",
                                "Neutron",
                                "Alpha",
                                "Beta",
                                "Gama"};

        JComboBox partList = new JComboBox(preSet);
        partList.setSelectedIndex(0);
        //partList.addActionListener(this);
        left.add(partList);


        JLabel vel = new JLabel("Initial Velocity");
        JLabel pos = new JLabel("Initial Position");

        left.add(pos);
        left.add(vel);

        // make initial Position

        SSlider [] position = new SSlider[3];
        position[0]= new SSlider("X_0");
        position[1]= new SSlider("Y_0");
        position[2]= new SSlider("Z_0");

        // make initial Velocity
        SSlider[] velolcity = new SSlider[3];
        velolcity[0] = new SSlider("V_x0");
        velolcity[1] = new SSlider("V_y0");
        velolcity[2] = new SSlider("V_z0");

        for (int i = 0; i < 3; ++i){
            left.add(position[i]);
            left.add(velolcity[i]);
        }




    }

    private void addRightFrame(){

        JPanel right = new JPanel();
        right.setVisible(true);
        right.setSize(100, 100);
        right.setBackground(Color.RED);
        this.getContentPane().add(right);

    }
}

