package Model;

import com.sun.org.apache.xpath.internal.SourceTree;

/**
 * By: Shawn Roberts
 * For: CS 202 Projects
 * <p/>
 * <p/>
 * Purpose ::
 */
public class Cord {
    protected double x;
    protected double y;
    protected double z;

    /** Constructor
     *
     * Components to be set
     * @param x componnet
     * @param y componnent
     * @param z componnent
     */
    public Cord(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    /** cross preforms a cross product on the arguments like A x B -> A.cross(B)
     *  out = (  A_y*B_z - A_z*B_y  ) _x
     *      + ( - A_x*B_z + A_z*B_x ) _y
     *      + (  A_y*B_x - A_z*B_x  ) _z
     * @param b to be crossed
     * @return a crossed Cord
     *
     */
    public Cord cross ( Cord b ){
        return new Cord( this.y*b.z - this.z*b.y, -this.x*b.z + this.z*b.x,this.x*b.y - this.y*b.x  );
    }

    /**Adds two Cords
     *
     * @param b
     * @return an addition of the two Cords
     */
    public Cord add ( Cord b ){
        return new Cord ( this.x + b.x ,  this .y + b.y, this.z + b.z );
    }


    /**returns a negated object
     *
     * @return negated cordonnate
     */
    public Cord neg () {
        return new Cord( -this.x, -this.y, -this.z );
    }

    /** returns a multipiled Cord
     *
     * @param mul value to be muliplied
     * @return multiplied cordonate
     */
    public Cord mult( double mul){
        return new Cord( mul*this.x, mul*this.y, mul*this.z );
    }

    public void disp(){

        System.out.print( " (" + this.x + ", " + this.y + ", " + this.z+")");
    }

    /**Test main to make sure the
     *
     */
    public static void main( String [] args) {

        Cord a = new Cord( 0.0,1.0,0.0 );

        a.disp();
        System.out.println("\n ::  Crosses ::  ");
        System.out.println("\n (1,1,1) ");
        a.cross(new Cord( 1.0, 1.0 ,1.0  )).disp();
        System.out.println("\n(0,0,1)");
        a.cross(new Cord( 0, 0 , 1.0  )).disp();

        System.out.println("\n :: Mult :: ");
        System.out.println("\n 0 ");
        a.mult(0).disp();

        System.out.println("\n 1 ");
        a.mult(1.0).disp();

    }

}
