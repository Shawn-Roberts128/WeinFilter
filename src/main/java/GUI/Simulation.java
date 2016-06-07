package GUI;

import org.jzy3d.chart.Chart;
import org.jzy3d.chart.SwingChart;
import org.jzy3d.maths.*;
import org.jzy3d.plot3d.primitives.*;
import org.jzy3d.plot3d.primitives.Point;
import org.jzy3d.plot3d.rendering.canvas.Quality;
import javax.swing.*;
import java.awt.*;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


/**
 * By: Shawn Roberts For: CS 202 Projects
 * <p/>
 * <p/>
 * Purpose ::
 */
public class Simulation extends JPanel implements ActionListener{
    private LineStrip time;
    private Chart space;
    private LineStrip [] box;




    /** ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ Test Main ~~~~~~~~~~~~~~~~~~~~~~~~ **/
     static public void main (String [] ignore){
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {

                new JFrame("Here") {
                    {



                        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                        this.getContentPane().setLayout(new BorderLayout());

                        this.getContentPane().setBackground(Color.BLUE);

                        Simulation sim = new Simulation();

                        this.getContentPane().add(sim,BorderLayout.CENTER);

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


    /** ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ Constructor ~~~~~~~~~~~~~~~~~~~~~~~~ **/
    public Simulation() {
        super( new BorderLayout());
        this.setBackground(Color.RED);


        space = new SwingChart (Quality.Nicest){};
        time = new LineStrip();

        space.addMouseController();
        space.addKeyController();
        space.getView();
        this.box(0.5,0.5,0.5);


        JComponent canvas = (JComponent) space.getCanvas();
        this.add(canvas, BorderLayout.CENTER);
    }





    /** ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ Other Functions ~~~~~~~~~~~~~~~~~~~~~~~~ **/
    public void setTime(Coord3d [] time){

        this.space.removeDrawable(this.time);
        this.time.clear();

        for (int i = 0; i < time.length; ++i){
            this.time.add(new Point(time[i], org.jzy3d.colors.Color.BLACK));
        }

        space.add(this.time);
        space.getView().setMaximized(true);




    }
    public void setTime(){
        Coord3d [] line  = new Coord3d[3];
        line[0] = new Coord3d(-1,0,0);
        line[1] = new Coord3d(0,0,1);
        line[2] = new Coord3d(0,1,1);
        this.setTime(line);
    }

    public void box( double hight, double width, double depth){
       if (box != null)
           for (LineStrip lineStrip : box) space.removeDrawable(lineStrip);
        else
            box = new LineStrip[12];

        hight=hight/2;
        width=width/2;
        depth=depth/2;

        Coord3d [] boxPoints = genBoxCords( hight, width, depth);

        space.add(new Point(boxPoints[0], org.jzy3d.colors.Color.RED)  ) ;





        box[ 0] = addLine( boxPoints[1], boxPoints[2], org.jzy3d.colors.Color.BLUE);
        box[ 1] = addLine( boxPoints[1], boxPoints[3], org.jzy3d.colors.Color.BLUE);
        box[ 2] = addLine( boxPoints[1], boxPoints[5], org.jzy3d.colors.Color.BLUE);

        box[ 3] = addLine( boxPoints[2], boxPoints[4], org.jzy3d.colors.Color.BLUE);
        box[ 4] = addLine( boxPoints[2], boxPoints[6], org.jzy3d.colors.Color.BLUE);

        box[ 5] = addLine( boxPoints[3], boxPoints[4], org.jzy3d.colors.Color.BLUE);
        box[ 6] = addLine( boxPoints[3], boxPoints[7], org.jzy3d.colors.Color.BLUE);

        box[ 7] = addLine( boxPoints[4], boxPoints[8], org.jzy3d.colors.Color.BLUE);

        box[ 8] = addLine( boxPoints[5], boxPoints[6], org.jzy3d.colors.Color.BLUE);
        box[ 9] = addLine( boxPoints[5], boxPoints[7], org.jzy3d.colors.Color.BLUE);

        box[10] = addLine( boxPoints[6], boxPoints[8], org.jzy3d.colors.Color.BLUE);

        box[11] = addLine( boxPoints[7], boxPoints[8], org.jzy3d.colors.Color.BLUE);

    }

    private Coord3d[] genBoxCords(double x, double y, double z){
        Coord3d [] boxPoints = new Coord3d[9];
        boxPoints[0] = new Coord3d(   0,   0,   0);
        boxPoints[1] = new Coord3d(x* 1,y*-1,z* 1);
        boxPoints[2] = new Coord3d(x* 1,y* 1,z* 1);
        boxPoints[3] = new Coord3d(x* 1,y*-1,z*-1);
        boxPoints[4] = new Coord3d(x* 1,y* 1,z*-1);
        boxPoints[5] = new Coord3d(x*-1,y*-1,z* 1);
        boxPoints[6] = new Coord3d(x*-1,y* 1,z* 1);
        boxPoints[7] = new Coord3d(x*-1,y*-1,z*-1);
        boxPoints[8] = new Coord3d(x*-1,y* 1,z*-1);
        return boxPoints;
    }

    private LineStrip addLine(Coord3d a, Coord3d b, org.jzy3d.colors.Color color){
        LineStrip temp = new LineStrip(new Point(a, color),
                new Point(b, color));
        space.add(temp);
        return temp;
    }

    /** ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ Other Functions ~~~~~~~~~~~~~~~~~~~~~~~~ **/

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        System.out.println("\nGraph change\n");

    }




}
