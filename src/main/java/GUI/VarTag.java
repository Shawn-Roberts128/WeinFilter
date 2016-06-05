package GUI;

import javax.swing.*;
import java.awt.*;

/**     +++++++++++++++++++++++++ SubPanel Class +++++++++++++++++++++++++      **/
public class VarTag extends JPanel {

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
