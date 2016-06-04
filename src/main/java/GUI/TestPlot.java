package GUI;


import org.jzy3d.analysis.AbstractAnalysis;
import org.jzy3d.analysis.AnalysisLauncher;
import org.jzy3d.chart.factories.SwingChartComponentFactory;
import org.jzy3d.colors.Color;
import org.jzy3d.colors.ColorMapper;
import org.jzy3d.colors.colormaps.ColorMapRainbow;
import org.jzy3d.maths.BoundingBox3d;
import org.jzy3d.maths.Coord3d;
import org.jzy3d.maths.Range;
import org.jzy3d.plot3d.builder.Builder;
import org.jzy3d.plot3d.builder.Mapper;
import org.jzy3d.plot3d.builder.concrete.OrthonormalGrid;
import org.jzy3d.plot3d.primitives.*;
import org.jzy3d.plot3d.rendering.canvas.Quality;

/**
 * By: Shawn Roberts
 * For: Ph 316 Final Project
 * <p/>
 * <p/>
 * Purpose ::
 */
public class TestPlot extends AbstractAnalysis {
    public static void main(String[] args) throws Exception {

        AnalysisLauncher.open(new TestPlot());
    }

    @Override
    public void init(){


        float x;
        float y;
        float z;
        float a;

        int size = 100;

        Coord3d[] points = new Coord3d[size];
        Color[]   colors = new Color[size];
/*
        Random r = new Random();
        r.setSeed(0);
        */
        x = 0;
        y = 0;

        for(int i=0; i<size; i++){
            x += 0.01;

            y += 0.01;
            double v = Math.pow(x, 2) + Math.pow(y, 2);
            z = (float)(v);
            points[i] = new Coord3d(x, y, z);
            a = 1;
            colors[i] = new Color( 0, 0, 0, a);
        }
/*
        List
        LineStrip line = new LineStrip()
*/

        // Define a function to plot
        Mapper mapper = new Mapper() {
            public double f(double x, double y) {
                return (50d)-Math.sqrt( Math.pow(x , 2) + Math.pow(y , 2) );
            }
        };

// Define range and precision for the function to plot
        Range range = new Range(-150, 150);
        int steps = 50;

// Create a surface drawing that function
        Shape surface = Builder.buildOrthonormal(new OrthonormalGrid(range, steps, range, steps), mapper);
        surface.setColorMapper(new ColorMapper(new ColorMapRainbow(), surface.getBounds().getZmin(), surface.getBounds().getZmax(), new Color(1, 1, 1, .5f)));
        surface.setFaceDisplayed(true);
        surface.setWireframeDisplayed(false);
        surface.setWireframeColor(Color.BLACK);


        x = 10f;
        y = 0.1f;
        z = 0.1f;
// test points stuff
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

        Polygon poly = new Polygon();
        // face 1
        poly.add(new Point(boxPoints[1]));
        poly.add(new Point(boxPoints[2]));
        poly.add(new Point(boxPoints[4]));
        poly.add(new Point(boxPoints[3]));
        // face 2
        poly.add(new Point(boxPoints[1]));
        poly.add(new Point(boxPoints[3]));
        poly.add(new Point(boxPoints[7]));
        poly.add(new Point(boxPoints[5]));
        // face 3
        poly.add(new Point(boxPoints[5]));
        poly.add(new Point(boxPoints[6]));
        poly.add(new Point(boxPoints[7]));
        poly.add(new Point(boxPoints[8]));
        // face 4
        poly.add(new Point(boxPoints[6]));
        poly.add(new Point(boxPoints[2]));
        poly.add(new Point(boxPoints[8]));
        poly.add(new Point(boxPoints[4]));




        poly.setColor(new Color(0.5f,0.5f,0.5f,0.5f));


        Scatter scatter = new Scatter(points);




        chart = SwingChartComponentFactory.chart(Quality.Nicest,"swing");
        //chart.getView().getCamera().setRenderingSphereRadius(10);
        //chart.add(poly); chart.add(scatter);
        Coord3d center = chart.getView().getCenter();

        Parallelepiped polypp= new Parallelepiped(new BoundingBox3d(-x, x, -x, x, -x, x) );
        polypp.setColor(Color.BLACK);
        chart.add(polypp);
        chart.add(new Point(new Coord3d(0, 0, 0)));








        //chart.getView().setBoundManual(chart.getView().getBounds());


        //chart.getScene().add(new Sphere(new Coord3d(-100,-100,-100),50f,50,new Color(0.5f,0.5f,0.5f,0.5f)));
        //chart.getScene().add(surface);
        chart.getView().setViewPoint(new Coord3d(1,1, 1));
        chart.getView().getCamera().setRenderingSphereRadius(1000);

        //chart.getAxeLayout().setTickLineDisplayed(false);



        //chart.getView().setScale(new Scale(0.1,0.1),true);
        //chart.getView().setViewPoint(new Coord3d(1,1, 1000));
    }
}
