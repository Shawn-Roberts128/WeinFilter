package GUI; /**
 * Created by ::  llama ::  3/15/16.
 * For :: WienFilter
 *
 * Good tutorial :: https://www.youtube.com/watch?v=GfeildLenjMzEGMKfg
 */

import Controler.Controler;
import Model.*;

import org.jzy3d.maths.Coord3d;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import javax.swing.*;
import javax.swing.event.ChangeEvent;

public class UI extends JFrame {

    private Simulation sim;
    private SSlider [] position;
    private SSlider [] velocity;
    private SetPartical partical;
    private SSlider [] chamberVars;

    private Controler control;

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
        control = new Controler();
        sim = new Simulation();


        // make initial Position
        position = new SSlider[3];
        makePos();

        // make initial Velocity
        velocity = new SSlider[3];
        makeVel();

        // initial particle
        makeCustPartical();

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
        addBotFrame();



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
                //TODO add the save file as .txt

            }
        });


        JMenuItem run = new JMenuItem("Run");
        run.setMnemonic(KeyEvent.VK_R);
        run.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
               run();
            }
        });

        JMenu menu = new JMenu("Menu");
        menu.setMnemonic(KeyEvent.VK_M);
        menu.add(quit);
        menu.add(fileSave);

        JMenuBar menuBar = new JMenuBar();
        menuBar.setMargin(new Insets(5,5,5,5));
        menuBar.add(menu);
        menuBar.add(run);
        setJMenuBar(menuBar);


        this.getContentPane().add(menuBar, BorderLayout.PAGE_START);
    }

    /** ~~~~~~~~~~~~~~~~~~~ Left Frame ~~~~~~~~~~~~~~~~~~~ **/
    private void addLeftFrame() {
        int row = 10;
        int col = 2;
        JPanel left = new JPanel(new GridLayout(0 , col, 5, 5));
        this.getContentPane().add(left, BorderLayout.LINE_START);


        // New custom values

        left.add(partical);

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
      for (int i = 0; i < 3; ++i){
            left.add(position[i]);
            left.add(velocity[i]);
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

    /** top Frame **/
    private void addBotFrame(){

        JPanel top = new JPanel(new BorderLayout());
        top.setBackground(Color.RED);

        final JPanel fullFilter = new JPanel(new GridLayout(2,2) );


        JRadioButton simple   = new JRadioButton("Simple Filter");
        JRadioButton ffbutton = new JRadioButton(  "Full Filter");

        final int feildLen = 6;
        chamberVars = new SSlider[feildLen];
        makeChamberVars();

        for (int i = 0; i < feildLen ; ++ i )
            fullFilter.add(chamberVars[i],i);

        ButtonGroup filters = new ButtonGroup();
        filters.add(    simple);
        filters.add(  ffbutton);

        top.add(simple    , BorderLayout.LINE_START);
        top.add(ffbutton  , BorderLayout.    CENTER);
        top.add(fullFilter, BorderLayout.  LINE_END);

        this.add(top,BorderLayout.PAGE_END);



        simple.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                System.out.println("Simple Press");
                fullFilter.setEnabled(false);
                for (int i = 0 ; i < feildLen; ++i)
                    chamberVars[i].setEnabled(false);
                fullFilter.setEnabled(false);

                control.setToIdealAcc();
                control.setToIdealChamber();

            }
        });

        ffbutton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                System.out.println("complex action pressed");
                fullFilter.setEnabled(true);
                for (int i = 0 ; i < feildLen; ++i)
                    chamberVars[i].setEnabled(true);

                control.setToRealChamber(   chamberVars[4].valueOf(), // length
                                            chamberVars[5].valueOf()  // radius
                                        );

                control.setToRealAcc    ((float)   chamberVars[0].valueOf()/2, // box height/2
                                         (float)   chamberVars[0].valueOf()/2, // box width/2
                                         (float)   chamberVars[0].valueOf()/2  // box depth/2
                                        );

                control.updateHole      (   chamberVars[2].valueOf(),
                                            chamberVars[3].valueOf(),
                                            chamberVars[1].valueOf()
                                         );
            }
        });

    }

    /** ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~  Action and initialisation of components ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ **/

    public void run(){
        try{
           sim.setTime(control.run());

        } catch (Initialised initialised) {
            initialised.printStackTrace();
            JOptionPane.showMessageDialog(this.getContentPane(),"Please enter all Necessary Values");
        }
    }

    private void makeCustPartical(){

        partical = new SetPartical() {

            @Override
            public void doubleEmmited(double value, ActionEvent event) {
                if (event.getSource() == this.massIn.input){
                    this.mass = value;
                    control.getIntial().setMass(value);
                    System.out.println("Mass :: " + value);
                }
                else if (this.chargeIn.input == event.getSource()){
                    this.charge = value;
                    control.getIntial().setCharge(value);
                    System.out.println("Charge :: "+ value);
                }
                else{
                    System.out.println(event.toString() +" " +value);
                }
            }
        };

    }

    private void makeChamberVars(){

        chamberVars [0]= new SSlider("Box Size") {
            @Override
            public void stateChanged(ChangeEvent changeEvent) {
                    this.updateUI();
                    control.setAccelBox(this.valueOf());
            }

            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                this.updateUI();
                control.setAccelBox(this.valueOf());

            }
        };
        chamberVars [1]= new SSlider("Radius") {
            @Override
            public void stateChanged(ChangeEvent changeEvent) {
                this.updateUI();
                control.setRadius(this.valueOf());
            }

            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                this.updateUI();
                control.setRadius(this.valueOf());
            }
        };
        chamberVars [2]= new SSlider("Chamber Position X") {
            @Override
            public void stateChanged(ChangeEvent changeEvent) {
                this.updateUI();
                control.setCenterX(this.valueOf());
            }

            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                this.updateUI();
                control.setCenterX(this.valueOf());
            }
        };
        chamberVars [3]= new SSlider("Chamber Position y") {
            @Override
            public void stateChanged(ChangeEvent changeEvent) {
                this.updateUI();
                control.setCenterZ(this.valueOf());
            }

            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                this.updateUI();
                control.setCenterZ(this.valueOf());
            }
        };
        chamberVars [4]= new SSlider("Chamber Radius") {
            @Override
            public void stateChanged(ChangeEvent changeEvent) {
                this.updateUI();
                control.setChamberRad(this.valueOf());
            }

            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                this.updateUI();
                control.setChamberRad(this.valueOf());
            }
        };
        chamberVars [5]= new SSlider("Chamber Length") {
            @Override
            public void stateChanged(ChangeEvent changeEvent) {
                this.updateUI();
                control.setChamberLen(this.valueOf());
            }

            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                this.updateUI();
                control.setChamberLen(this.valueOf());
            }
        };

    }

    private void makePos(){

        position[0]= new SSlider("X_0") {
            @Override
            public void stateChanged(ChangeEvent changeEvent) {
                this.updateUI();
                control.getIntial().getPosition().getX(this.valueOf());
            }

            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                this.updateUI();
                control.getIntial().getPosition().setX(this.valueOf());
            }
        };
        position[1]= new SSlider("Y_0") {
            @Override
            public void stateChanged(ChangeEvent changeEvent) {
                this.updateUI();
                control.getIntial().getPosition().setY(this.valueOf());
            }

            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                this.updateUI();
                control.getIntial().getPosition().setY(this.valueOf());
            }
        };
        position[2]= new SSlider("Z_0") {
            @Override
            public void stateChanged(ChangeEvent changeEvent) {
                this.updateUI();
                control.getIntial().getPosition().setZ(this.valueOf());
            }

            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                this.updateUI();
                control.getIntial().getPosition().setZ(this.valueOf());
            }
        };
    }


    private void makeVel(){
         // make initial Velocity
        velocity[0] = new SSlider("V_x0") {
            @Override
            public void stateChanged(ChangeEvent changeEvent) {
                this.updateUI();
                control.getIntial().getVelocity().setX(this.valueOf());
            }

            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                this.updateUI();
                control.getIntial().getVelocity().setX(this.valueOf());
            }
        };
        velocity[1] = new SSlider("V_y0") {
            @Override
            public void stateChanged(ChangeEvent changeEvent) {
                this.updateUI();
                control.getIntial().getVelocity().setY(this.valueOf());
            }

            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                this.updateUI();
                control.getIntial().getVelocity().setY(this.valueOf());
            }
        };
        velocity[2] = new SSlider("V_z0") {
            @Override
            public void stateChanged(ChangeEvent changeEvent) {
                this.updateUI();
                control.getIntial().getVelocity().setZ(this.valueOf());
            }

            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                this.updateUI();
                control.getIntial().getVelocity().setZ(this.valueOf());
            }
        };
    }



}

