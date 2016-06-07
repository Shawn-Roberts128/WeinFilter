package GUI;

import org.apache.commons.lang3.math.NumberUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/** +++++++++++++++++++++++++ SubPanel Class +++++++++++++++++++++++++      **/
public abstract class VarTag extends JPanel implements ActionListener{

    protected String title;
    protected JLabel label;
    public  JTextField input;

    public static void main(String [] ignore){

        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {

                JFrame here = new JFrame("Here") {
                    {

                        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                        this.getContentPane().setLayout(new BorderLayout());


                        this.getContentPane().setBackground(Color.BLUE);

                        this.getContentPane().add(new VarTag("Test", 10) {
                            @Override
                            public void actionPerformed(ActionEvent actionEvent) {
                                System.out.println(this.getVal());
                            }
                        });

                        this.setVisible(true);


                        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

                        this.pack();
                        this.revalidate();
                        this.setSize(screenSize.width/4, screenSize.height/4);
                        this.setLocationRelativeTo(null);
                    }

                };


            }
        });


    }


    /** ~~~~~~~~~~~~~~~~~~~ Initialisation and Constructor ~~~~~~~~~~~~~~~~~~~~~ **/
    public VarTag(String title, double defalut) {
        super(new FlowLayout());
        this.title = title;

        this.init(defalut);

    }

    private void init( double val) {

        this.label = new JLabel(this.title);
        this.input = new JTextField(String.format("%s", val)){
            @Override
            public void setText(String input){
                super.setText(input.substring(0,(input.length()>5)?5:input.length()));
            }
        };
        this.input.addActionListener(this);

        this.add(label);
        this.add(input);
    }

    public double getVal(){
        return NumberUtils.toDouble(this.input.getText());
    }

    @Override
    public void setEnabled(boolean set){
    label.setEnabled(set);
    input.setEnabled(set);
    }
    /** ~~~~~~~~~~~~~ Event Functions ~~~~~~~~~~~~~~~~~~ **/


}
