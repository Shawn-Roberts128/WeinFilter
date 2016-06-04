package GUI; /**
 * Created by ::  llama ::  3/15/16.
 * For :: WienFilter
 *
 * Good tutorial :: https://www.youtube.com/watch?v=G4jMzEGMKfg
 */

import Model.Cord;
import Model.Initialised;
import Model.Particle;
import Model.WeinFilter;
import org.jzy3d.maths.Coord3d;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import javax.swing.*;

public class UI extends JFrame {

    private Simulation sim;

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
        sim = new Simulation();



        //sim.setTime();
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
        this.pack();
        this.revalidate();
        this.setSize(screenSize.width/2, screenSize.height/2);
        this.setLocationRelativeTo(null);
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
                try {
                    testRightFrame();
                } catch (Initialised initialised) {
                    initialised.printStackTrace();
                }
                //initUI();
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

        right.add(sim);

        right.setVisible(true);
        right.setSize(400, 400);
        right.setBackground(Color.RED);
        this.getContentPane().add(sim);

    }

    public void testRightFrame() throws Initialised {

        // Initialisation
        int size = 10000;

        int electric = 1;
        int magnetic = 1;

        double mass = 1.0;
        double charge = 1.0;

        Cord not = new Cord(0, 0, 0);

        // Cord vel = new Cord(0, 1, 0); // number 1 test should be a line
        // Cord vel = new Cord(0, 1, 1); // test should be a "bouncing ball"
        Cord vel = new Cord(1, 0, 0);    // test should be a spiral

        // Make filter object
        WeinFilter filter = new WeinFilter( magnetic, electric);
        filter.init(new Particle( not,vel,not, mass,charge));

        // Time and trajectory
        double [] timeLine = new double [size];
        double time = 0.01;

        for (int i = 0; i < size; ++i){
            timeLine[i] = i*time;
        }

        Particle [] traj = filter.trajectory(timeLine);
        Coord3d [] points = new Coord3d[size];

        // convert the trajectory to cords to be plotted
        for (int i = 0; i < size ; ++i) {

            points[i] = traj[i].pathCord();

            // used to output the points
            // System.out.println("T: "+ timeLine[i]+"\tP: "+points[i]);
        }

        sim.setTime(points);
        this.revalidate();
    }

}

