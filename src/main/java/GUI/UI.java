package GUI; /**
 * Created by ::  llama ::  3/15/16.
 * For :: WienFilter
 *
 * Good tutorial :: https://www.youtube.com/watch?v=GfeildLenjMzEGMKfg
 */

import Controler.Controler;
import Controler.HoldData;
import Model.*;

import com.sun.corba.se.impl.naming.cosnaming.NamingUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.jzy3d.maths.Coord3d;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileWriter;
import java.util.Arrays;
import javax.swing.*;


public class UI extends JFrame {

    private Simulation             sim; // simulator object
    private Controler          control; // controller object to deal with event handling

    private SSlider []        position; // sliders  dealing with position
    private SSlider []        velocity; // ...      dealing with velocity
    private SetPartical       partical; // particle information
    private SSlider []     chamberVars; // ...      dealing with the setup of the chamber
    private VarTag                 mag; // ...      ... magnetic field
    private VarTag                elec; // ...      ... electric field
    private VarTag             timeEnd; // ...      ... how long to simulate for unconstrained system
    private VarTag               dTime; // ...      ... the time step for any simulation


    // Main
    public static void main(String[] args) {

        EventQueue.invokeLater(new Runnable() {

            @Override
            public void run() {
                JFrame.setDefaultLookAndFeelDecorated( false );

                UI ex = new UI(" Shawn'S Amazing Wien Filter Simulator ");

                ex.getContentPane().setBackground( Color.black );
                ex.setVisible(true);
            }
        });
    }

    // The GUI.UI class constructor initialises the UI components and then calls the action setting functions
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

        // actually setup and display window information
        initUI();

    }

    // init the starts imitation of the main window
    private void initUI(){
        this.getContentPane().setLayout(new BorderLayout());
        this.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );

        // setup functions
        setmenuBar();
        addLeftFrame();
        addRightFrame();
        addBotFrame();

        // Main window look and feel
        Container c = this.getContentPane();
        c.setBackground( Color.BLACK );

        this.getContentPane().setBackground(Color.black);
        this.repaint();
        this.setVisible(true);
        this.pack();
        this.revalidate();
        this.setSize(960, 820);
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

                saveFile();



            }
        });

        // set the run button
        JMenuItem run = new JMenuItem("Run");
        run.setMnemonic(KeyEvent.VK_R);
        run.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                run();
            }
        });


        // adding main menue to the menu bar.
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
    // loads the left frame and mostly the particle

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

        left.add(partList);

        left.add(mag);
        left.add(elec);

        JPanel velP= new JPanel(new GridLayout(4,1));
        JPanel posP= new JPanel(new GridLayout(4,1));

        JLabel vel = new JLabel("Initial Velocity");
        JLabel pos = new JLabel("Initial Position");


        velP.add(vel);
        posP.add(pos);

        //left.add(pos);
        //left.add(vel);

        // make initial Position
        for (int i = 0; i < 3; ++i){
            position[i].setMinimumSize(mag.getSize());
            velocity[i].setMinimumSize(mag.getSize());

            left.add(position[i]);
            left.add(velocity[i]);
        }

        left.add(posP);
        left.add(velP);


    }

    private void addRightFrame(){
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

    /** bottom Frame **/
    // this deals with adding most of the physical constrains of the filters chambers
    private void addBotFrame(){
        final int feildLen = 8;
        chamberVars = new SSlider[feildLen];
        makeChamberVars();

        JPanel bot = new JPanel(new BorderLayout());
        bot.setBackground(Color.RED);

        JRadioButton simple   = new JRadioButton("Simple Filter");
        JRadioButton ffbutton = new JRadioButton(  "Full Filter");

        // Panel to hold the simple buttons and time information
        JPanel sim = new JPanel(new GridLayout(3,1));
        sim.add(simple);
        sim.add(timeEnd);
        sim.add(dTime);
        bot.add(sim,BorderLayout.LINE_START);

        final JPanel fullFilter = new JPanel(new GridLayout(4,2) );
        for (int i = 0; i < feildLen ; ++ i )
            fullFilter.add(chamberVars[i],i);

        // group the buttons together
        ButtonGroup filters = new ButtonGroup();
        filters.add(    simple);
        filters.add(  ffbutton);

        // add the buttons to the panel
        bot.add(ffbutton, BorderLayout.CENTER);
        bot.add(fullFilter, BorderLayout.LINE_END);

        this.add(bot,BorderLayout.PAGE_END);

        // if the simple button is pressed
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

        // if the filter button is pressed
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

        simple.doClick();// call the function for simple's click

    }

    /** ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~  Action and initialisation of components ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ **/



    public void run(){ // runs the model and sends the information to the simulator to be graphed
        try{
            sim.setTime(control.run());

        } catch (Initialised initialised) { // if something is not initialised or if there is an error send message
            initialised.printStackTrace();
            JOptionPane.showMessageDialog(this.getContentPane(),"Please enter all Necessary Values");
        }catch ( Exception e){
            JOptionPane.showMessageDialog(this.getContentPane(),"An Error Has Occurred,"+e.getMessage()+" \nPlease don't do that...");
        }
    }

    // code for saving a file
    private void saveFile() {
        try {
            // get the data from the model
            HoldData temp = control.run(1);

            // get save location
            JFileChooser fc = new JFileChooser();
            fc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
            if(JFileChooser.APPROVE_OPTION == fc.showSaveDialog(this)) {
                FileWriter saveTo = new FileWriter(fc.getSelectedFile());

                // Header of the file output type
                saveTo.write("time,x,y,z,vx,vy,vz,ax,ay,az\n");

                // output statement
                for (int i = 0; (i <temp.time.length)&&(i < temp.traj.length);++i ) {
                    saveTo.append(String.valueOf(temp.time[i])).append(",").append(temp.traj[i].toString()).append("\n");
                    //System.out.println(temp.time[i]+","+temp.traj[i].toString()+"\n"); // debug statement
                    if ((i%100) == 0) saveTo.flush();
                }
                saveTo.close();

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    // makes the Particle's initial slider values and then gives them the there response's to being dealt with
    private void makeCustPartical(){

        partical = new SetPartical() {

            @Override
            public void doubleEmmited(double value, ActionEvent event) {
                if (event.getSource() == this.massIn.input){                // mass changed
                    this.mass = value;
                    control.getIntial().setMass(value);
                    run();
                }
                else if (this.chargeIn.input == event.getSource()){         // change changed
                    this.charge = value;
                    control.getIntial().setCharge(value);
                    run();
                }
                else{
                    System.out.println(event.toString() +" " +value);
                }
            }
        };

        this.mag = new VarTag("Magnetic Field", control.getMag()) {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (NumberUtils.isNumber(this.input.getText()))
                    control.setMag(this.getVal());
                run();
            }
        };

        this.elec  = new VarTag("Electric Field",control.getElectric()) {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (NumberUtils.isNumber(this.input.getText()))
                    control.setElectric(this.getVal());
                run();
            }
        };

    }

    private void makeChamberVars(){

        chamberVars [0]= new SSlider("Box Size",1,10,100) {
            @Override
            public void doubleEmmited(double value, ActionEvent event) {
                this.updateUI();
                control.setAccelBox(this.valueOf()/2);
                run();
                sim.box(value,value, value);
            }
        };

        chamberVars [1]= new SSlider("Radius",1,1,100) {
            @Override
            public void doubleEmmited(double value, ActionEvent event) {
                this.updateUI();
                control.setRadius(this.valueOf());
                run();
            }
        };

        chamberVars [2]= new SSlider("Chamber Position X",0,0,100) {
            @Override
            public void doubleEmmited(double value, ActionEvent event) {

                this.updateUI();
                control.setCenterX(this.valueOf());
                run();
            }

        };
        chamberVars [3]= new SSlider("Chamber Position Z",0,0,100) {
            @Override
            public void doubleEmmited(double value, ActionEvent event) {

                this.updateUI();
                control.setCenterZ(this.valueOf());
                run();
            }
        };

        chamberVars [4]= new SSlider("Chamber Radius",1,1,100) {
            @Override
            public void doubleEmmited(double value, ActionEvent event) {

                this.updateUI();
                control.setChamberRad(this.valueOf());
                run();
            }
        };

        chamberVars [5]= new SSlider("Chamber Length",1,10,100) {
            @Override
            public void doubleEmmited(double value, ActionEvent event) {
                this.updateUI();
                control.setChamberLen(this.valueOf());
                run();
            }
        };

        chamberVars [6] = new SSlider("Left Plate Voltage",0,-100,100) {
            @Override
            public void doubleEmmited(double value, ActionEvent event) {
                control.getStage().setLeftVolt(value);
                run();
            }
        };

        chamberVars [7] = new SSlider("Right Plate Voltage",0,-100,100) {
            @Override
            public void doubleEmmited(double value, ActionEvent event) {
                control.getStage().setRightVolt(value);
                run();
            }
        };


        timeEnd = new VarTag("Time Length",10) {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (NumberUtils.isNumber(this.input.getText()));
                control.setTimeEnd(this.getVal());
                run();
            }
        };

        dTime = new VarTag(" Time Step Size",1) {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (NumberUtils.isNumber(this.input.getText()))
                    control.setDtime(this.getVal());
                run();
            }
        };
    }

    private void makePos(){

        position[0]= new SSlider("X_0",0,control.getIntial().getPosition().getX(),100) {
            @Override
            public void doubleEmmited(double value, ActionEvent event) {

                this.updateUI();
                control.getIntial().getPosition().setX(this.valueOf());
                run();
            }
        };

        position[1]= new SSlider("Y_0",0,0,100) {
            @Override
            public void doubleEmmited(double value, ActionEvent event) {

                this.updateUI();
                control.getIntial().getPosition().setY(this.valueOf());
                run();
            }
        };

        position[2]= new SSlider("Z_0",0,0,100) {
            @Override
            public void doubleEmmited(double value, ActionEvent event) {
                this.updateUI();
                control.getIntial().getPosition().setZ(this.valueOf());
                run();
            }
        };
    }


    private void makeVel(){
        // make initial Velocity
        velocity[0] = new SSlider("V_x0",0,0,100) {
            @Override
            public void doubleEmmited(double value, ActionEvent event) {
                this.updateUI();
                control.getIntial().getVelocity().setX(this.valueOf());
                run();
            }
        };

        velocity[1] = new SSlider("V_y0",0,0,100) {
            @Override
            public void doubleEmmited(double value, ActionEvent event) {

                this.updateUI();
                control.getIntial().getVelocity().setY(this.valueOf());
                run();
            }
        };

        velocity[2] = new SSlider("V_z0",0,0,100) {
            @Override
            public void doubleEmmited(double value, ActionEvent event) {

                this.updateUI();
                control.getIntial().getVelocity().setZ(this.valueOf());
                run();
            }
        };
    }

}

