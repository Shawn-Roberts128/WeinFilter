package Model;

/**
 * Created by ::  llama ::  3/26/16.
 * For :: WienFilter
 *
 *  TODO add documentation for functions
 *
 */
public class WeinFilter {

    /** Feilds **/
    private double magnetic;
    private double electric;

    private double c1;
    private double c2;
    private double c3;
    private double c4;

    private double w;

    Particle fleck;

    private boolean initiatlisation ;




    /**  Methods  **/

    /** Constructor
     *
     * @param magnetic setting these constants
     * @param electric setting the electric constant
     */
    public WeinFilter(double magnetic, double electric) {
        this.magnetic = magnetic;
        this.electric = electric;
        this. w =this.c1 = this.c2 = this.c3 = this.c4 = 0;
        fleck = null;
        this. initiatlisation = false;

    }

    /** Setters and Getters **/
    public double getMagnetic() {
        return magnetic;
    }

    public void setMagnetic(double magnetic) {
        this.magnetic = magnetic;
    }

    public double getElectric() {
        return electric;
    }

    public void setElectric(double electric) {
        this.electric = electric;
    }


    /** Clears the partical specific data for this filter item
     *      This clears C1, C2, C3, C4, w, and fleck
     */
    public void clear () {
        this.c1 = this.c2 = this. c3 = this.c4 = this .w = 0;
        fleck = null;
        this . initiatlisation = false;
    }

    /** Initialises the particle specific consents
     *      if there still const assigned then it will clear and recall
     *
     * @param from makes deep copy of from
     */
    public void init ( Particle from ) {
        try{

            if (from == null) throw new NullPointerException(" Partical is null");
            fleck = new Particle( from );
            this.setConsts();
        }
        catch( Exception e  ){
            if ( initiatlisation){
                this.clear();
                this.init( from);
            }
        }
    }

    /** Sets the constants of this filter object
     *      uses the math from the particle to set the constants:
     *          c1,c2,c3,c4,w
     *
     * @throws Initialised
     */
    private void setConsts() throws Initialised { // TODO finish
        if ( (fleck == null)||( initiatlisation )) throw new Initialised( " Constants not initialised " );

        // set c1
        // set c2
        // set c3
        // set c4
        // set w

        initiatlisation = true;
    }

    /** Computes the Z value for the cycloid motion at a given time interval
     *
     * @param time The time that the position is set at
     * @return the y value
     * @throws Initialised
     */
    private double yCycloid ( double time) throws Initialised {
        if (!initiatlisation ) throw new Initialised(" Constants not initialised");

        return (c1 * Math.cos( w * time ) + c2 * Math.sin( w * time));
    }


    /** Computes the Z value for the cycloid motion at a given time interval
     *
     * @param time The time that the position is set at
     * @return the Z Cycloid value
     * @throws Initialised
     */
    private double zCycloid( double time ) throws Initialised {
        if (!initiatlisation) throw new Initialised(" Constants not initialised");

        return ( 0 );// TODO add the equation for z Cycliod
    }

    /** instant :: outputs the properties of a particle in an electric field at a given instant in time
     *
     * @param time the computed particles instant
     * @return a particle at the given time
     * @throws Initialised
     */
    private Particle instant (double time ) throws Initialised { // TODO write the full value returns a "particle"
        if (!initiatlisation) throw new Initialised(" Constants not initialised");

        Particle current = null;


        return current;
    }

    /** Trajectory :: takes in an array of floats and computes the trajectory over that time interval
     *      This outputs an array of points, representing the particle's information at that given moment
     *
     * @param time [] An array of floats, should start at 0 and end at some given length
     * @return an array of particles resulting in the acceleration, velocity, and position of the particle
     *      at any given moment.
     * @throws Initialised
     */
    public Particle[] trajectory( float [] time )  throws Initialised {
        if (!initiatlisation) throw new Initialised(" Constants not initialised");

        Particle traj [] = new Particle[ time.length ];
        traj[0]= new Particle( fleck );

        for ( int i = 1 ; i < time.length ; ++i ){

            traj[i] = instant( time[i]);
        }

        return traj;
    }

}

