package Model;


import org.jzy3d.analysis.AbstractAnalysis;
import org.jzy3d.analysis.AnalysisLauncher;
import org.jzy3d.chart.Chart;
import org.jzy3d.chart.ChartLauncher;
import org.jzy3d.chart.factories.AWTChartComponentFactory;
import org.jzy3d.chart.factories.SwingChartComponentFactory;
import org.jzy3d.colors.Color;
import org.jzy3d.colors.ColorMapper;
import org.jzy3d.colors.colormaps.ColorMapRainbow;
import org.jzy3d.maths.Coord3d;
import org.jzy3d.maths.Range;
import org.jzy3d.plot3d.builder.Builder;
import org.jzy3d.plot3d.builder.Mapper;
import org.jzy3d.plot3d.builder.concrete.OrthonormalGrid;
import org.jzy3d.plot3d.primitives.Scatter;
import org.jzy3d.plot3d.primitives.Shape;
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
                return 10 * Math.pow(x ,2) * Math.cos(y / 20) * x;
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
        Scatter scatter = new Scatter(points);
        chart = SwingChartComponentFactory.chart(Quality.Advanced, "swing");
        chart.getScene().add(scatter);
        chart.getScene().add(surface);
    }
}
