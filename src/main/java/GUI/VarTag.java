package GUI;

import org.apache.commons.lang3.math.NumberUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

/** +++++++++++++++++++++++++ SubPanel Class +++++++++++++++++++++++++      **/
public abstract class VarTag extends JPanel implements ActionListener{

    protected String title;
    protected JLabel label;
    public  JTextField input;


    /** ~~~~~~~~~~~~~~~~~~~ Initialisation and Constructor ~~~~~~~~~~~~~~~~~~~~~ **/
    public VarTag(String title, double defalut) {
        super(new FlowLayout());
        this.title = title;

        this.init(defalut);

    }

    private void init( double val) {

        this.label = new JLabel(this.title);
        this.input = new JTextField(String.format("%s", val));

        this.add(label);
        this.add(input);
    }

    public double getVal(){
        return NumberUtils.toDouble(this.input.getText());
    }

    /** ~~~~~~~~~~~~~ Event Functions ~~~~~~~~~~~~~~~~~~ **/


}
