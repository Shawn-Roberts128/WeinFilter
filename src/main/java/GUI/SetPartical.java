package GUI;

import com.sun.org.apache.xpath.internal.SourceTree;
import org.apache.commons.lang3.math.NumberUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * By: Shawn Roberts For: CS 202 Projects
 * <p/>
 * <p/>
 * Purpose ::
 */
public abstract class SetPartical extends JPanel implements ActionListener, DoubleListener {

    public double mass;
    public double charge;

    private JLabel title   = null;

    public VarTag massIn   = null;
    public VarTag chargeIn = null;

    public DoubleListener doubleListener = null;

    /** test Main **/

    static public void main (String [] ignore){
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {

                new JFrame("Here") {
                    {

                        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                        this.getContentPane().setLayout(new BorderLayout());


                        this.getContentPane().setBackground(Color.BLUE);

                        this.getContentPane().add(new SetPartical(){

                            @Override
                            public void doubleEmmited(double value, ActionEvent event) {
                                if (event.getSource() == this.massIn.input){
                                    this.mass = value;
                                    System.out.println("Mass :: "+ value);
                                }
                                else if (this.chargeIn.input == event.getSource()){
                                    this.charge = value;
                                    System.out.println("Charge :: "+ value);
                                }
                                else{
                                    System.out.println(event.toString() +" " +value);
                                }
                            }
                        });


                        this.setVisible(true);


                        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

                        this.pack();
                        this.revalidate();
                        this.setSize(screenSize.width / 4, screenSize.height / 4);
                        this.setLocationRelativeTo(null);
                    }

                };


            }
        });


    }


    /** ~~~~~~~~~~~~~~~~~~~ Initialisation and Constructor ~~~~~~~~~~~~~~~~~~~~~ **/
    public SetPartical() {
        this((double)0, (double)0);
    }
    public SetPartical(double mass, double charge ){

        this.doubleListener = new DoubleListener() {
            @Override
            public void doubleEmmited(double value, ActionEvent event) {
                getDoubleListener().doubleEmmited(value,event);
            }
        };

        this.mass       = mass;
        this.charge     = charge;

        this.massIn     = null;
        this.chargeIn   = null;
        this.title      = null;

        this.init();
    }

    private void init() {
        this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

        this.title = new JLabel("Custom Particle");

        this.add(title,Component.CENTER_ALIGNMENT);

        this.makeMass();
        this.makeCharge();
    }


    /** ~~~~~~~~~~~~~ Content Functions ~~~~~~~~~~~~~~~~~~~~**/
    private void makeCharge() {
        this.chargeIn = new VarTag("Charge", this.charge){

            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                doubleListener.doubleEmmited(this.getVal(), actionEvent);
            }
        };
        this.chargeIn.input.addActionListener(this);
        this.add(this.chargeIn, Component.CENTER_ALIGNMENT);
    }

    private void makeMass() {
        this.massIn = new VarTag("Mass", this.mass) {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                doubleListener.doubleEmmited(this.getVal(), actionEvent);
            }
        };
        this.massIn.input.addActionListener(this);
        this.add(this.massIn, Component.CENTER_ALIGNMENT);
    }

    /** ~~~~~~~~~~~~~ Event Functions ~~~~~~~~~~~~~~~~~~ **/

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource()==massIn.input)
            this.doubleEmmited(massIn.getVal(), e);
        else if (e.getSource()==chargeIn.input)
            this.doubleEmmited(chargeIn.getVal(), e);
        else
            System.out.println(e.getSource().toString());
    }

    public DoubleListener getDoubleListener() {
        return doubleListener;
    }

    public void setDoubleListener(DoubleListener doubleListener) {
        this.doubleListener = doubleListener;
    }
}

