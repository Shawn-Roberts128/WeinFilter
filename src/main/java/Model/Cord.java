package Model;

/**
 * By: Shawn Roberts
 * For: CS 202 Projects
 * <p/>
 * <p/>
 * Purpose ::
 */
public class Cord {
    protected double x = Double.NaN;
    protected double y = Double.NaN;
    protected double z = Double.NaN;



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

        System.out.println("\n :: Multiple :: ");
        System.out.println("\n 0 ");
        a.mult(0).disp();

        System.out.println("\n 1 ");
        a.mult(1.0).disp();

    }

    /** default
     *
     */
    public Cord() {
    }

    /** Constructor
     *
     * Components to be set
     * @param x component
     * @param y component
     * @param z component
     */
    public Cord(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    /** Copy Constructor
     *
     * @param from to copy
     */
    public Cord( Cord from ) {
        this.x = from.x;
        this.y = from.y;
        this.z = from.z;
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
     * @param b the input cord
     * @return an addition of the two Cords
     */
    public Cord add ( Cord b ){
        return new Cord ( this.x + b.x ,  this .y + b.y, this.z + b.z );
    }


    /**returns a negated object
     *
     * @return negated coordinate
     */
    public Cord neg () {
        return new Cord( -this.x, -this.y, -this.z );
    }

    /** returns a multiple Cord
     *
     * @param mul value to be multiplied
     * @return multiplied carbonate
     */
    public Cord mult( double mul){
        return new Cord( mul*this.x, mul*this.y, mul*this.z );
    }

    public double len(){
        return Math.sqrt(Math.pow(x,2) +Math.pow(y,2) +Math.pow(z,2));
    }
    public double lenXZ(){
        return Math.sqrt(Math.pow(x,2) +Math.pow(z,2));
    }
    public void disp(){

        System.out.print( " (" + this.x + ", " + this.y + ", " + this.z+")");
    }

    public boolean isequal(Cord from ){
        if (this.x != from.x) return false;
        if (this.y != from.y) return false;
        if (this.z != from.z) return false;

        return true;
    }

    @Override
    public String toString() {
        return new String("("+this.x+", "+this.y+", "+this.z+")");
    }

    public double getX(double v) {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getZ() {
        return z;
    }

    public void setZ(double z) {
        this.z = z;
    }
}
