package GUI;

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
public class SetPartical extends JPanel implements ActionListener {

    public double mass;
    public double charge;

    private JLabel title    = null;
    private VarTag massIn   = null;

    private VarTag chargeIn = null ;

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

                        this.getContentPane().add(new SetPartical());


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
        this.chargeIn = new VarTag("Charge", this.charge);
        this.chargeIn.inputIn.addActionListener(this);
        this.add(this.chargeIn, Component.CENTER_ALIGNMENT);
    }

    private void makeMass() {
        this.massIn = new VarTag("Mass", this.mass);
        this.massIn.inputIn.addActionListener(this);
        this.add(this.massIn, Component.CENTER_ALIGNMENT);
    }

    /** ~~~~~~~~~~~~~ Event Functions ~~~~~~~~~~~~~~~~~~ **/

    @Override
    public void actionPerformed(ActionEvent e) {

        String newString = null;
        if (e.getSource() == this.massIn.inputIn){
            newString = this.massIn.inputIn.getText();

            if (NumberUtils.isNumber(newString)){
                this.mass = NumberUtils.toDouble(newString);
            }

        }
        else if (e.getSource() == this.chargeIn.inputIn){
            newString = this.massIn.inputIn.getText();

            if (NumberUtils.isNumber(newString)){
                this.charge = NumberUtils.toDouble(newString);
            }
        }

        System.out.println("Change"+ newString); //TODO delete

    }

}

/**     +++++++++++++++++++++++++ SubPanel Class +++++++++++++++++++++++++      **/
class VarTag extends JPanel {

    protected String title;
    protected JLabel label;
    protected JTextField inputIn;


    /** ~~~~~~~~~~~~~~~~~~~ Initialisation and Constructor ~~~~~~~~~~~~~~~~~~~~~ **/
    public VarTag(String title, double defalut) {
        super(new FlowLayout());
        this.title = title;

        this.init(defalut);

    }

    private void init( double val) {

        this.label = new JLabel(this.title);
        this.inputIn = new JTextField(String.format("%s", val));

        this.add(label);
        this.add(inputIn);
    }

    /** ~~~~~~~~~~~~~ Event Functions ~~~~~~~~~~~~~~~~~~ **/


}