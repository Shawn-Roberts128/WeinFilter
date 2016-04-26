package Model;

/**
 * Created by ::  llama ::  3/26/16.
 * For :: WienFilter
 *
 *  TODO add documentation for functions
 *
 */
public class WeinFilter {

    /** Fields **/
    private double magnetic;
    private double electric;

    private double c1;
    private double c2;
    private double c3;
    private double c4;

    private double w;

    private Particle fleck;

    private boolean setConstants;

    /** Test Main **/
    static public void main(String [] ignore){

        WeinFilter foo  = new WeinFilter(0 ,0 );

        double massE = 	9.10938356*Math.pow(10,-31);  // kg
        double chargE = -1.602167565*Math.pow(10,-19);; // C
        Particle electron = new Particle(new Cord(0,0,0),  // Starting Position
                                        new Cord(0,0,0),   // starting Velocity
                                        new Cord(0,0,0),  // starting Acceleration
                                        massE, chargE  );


    }


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
        this.setConstants = false;

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
        this .setConstants = false;
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
            if (setConstants){
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
    private void setConsts() throws Initialised {
        if ( (fleck == null)||(setConstants)) throw new Initialised( " Constants not initialised " );

        // set w
        this.w = fleck.charge * this.magnetic / this.electric ;

        // set c1
        this.c1 = -(this.fleck.velocity.z / w) ;

        // set c2
        this.c2 = ( this.fleck.velocity.y - (this.electric / this.magnetic ))/this.w;

        // set c3
        this.c3 = this.fleck.position.y-c1;

        // set c4
        this.c4 = this.fleck.position.z-c2;

        setConstants = true;
    }

    /** Computes the Z value for the cycloid motion at a given time interval
     *
     * @param time The time that the position is set at
     * @return the y value
     * @throws Initialised
     */
    private double yCycloid ( double time) throws Initialised {
        if (!setConstants) throw new Initialised(" Constants not initialised");

        double angle = this.w * time;

        return (c1 * Math.cos( this.w * time ) + c2 * Math.sin( w * time));
    }


    /** Computes the Z value for the cycloid motion at a given time interval
     *
     * @param time The time that the position is set at
     * @return the Z Cycloid value
     * @throws Initialised
     */
    private double zCycloid( double time ) throws Initialised {
        if (!setConstants) throw new Initialised(" Constants not initialised");

        double angle = time * this.w;

        return this.c2 * Math.cos( angle ) - this.c1 * Math.sin( angle ) ;

    }

    /** instant :: outputs the properties of a particle in an electric field at a given instant in time
     *
     * @param time the computed particles instant
     * @return a particle at the given time
     * @throws Initialised
     */
    private Particle instant (double time ) throws Initialised {
        if (!setConstants) throw new Initialised(" Constants not initialised");

        double e_b = this.electric / this.magnetic ;  // calcs the e_b value
        double w_srd = this.w * this.w;     // Calc the w^2 value

        // TODO change this for Pi = P0 + V0 * t + (at^2)/2
        // TODO change for Vi = V0 + a*t
        // TODO change for a = a0 + a;
        return new Particle( new Cord( this.fleck.velocity.x * time+this.fleck.position.x,/** Position **/
                                            this.yCycloid( time ) + ( e_b * time ) + c3 ,
                                            this.zCycloid( time) + c4),
                                new Cord( this.fleck.velocity.x ,   /** velocity **/
                                            this.w*( zCycloid( time )) + ( e_b ) ,
                                            - w * ( yCycloid( time ) ) ),
                                new Cord( 0 ,                   /** Acceleration **/
                                            - w_srd * ( yCycloid( time ) ) ,
                                            - w_srd * ( yCycloid( time ) )  ),
                                this.fleck.mass, this.fleck.charge  ); /** mass and charge **/

    }

    /** Trajectory :: takes in an array of doubles and computes the trajectory over that time interval
     *      This outputs an array of points, representing the particle's information at that given moment
     *
     * @param time [] An array of doubles, should start at 0 and end at some given length
     * @return an array of particles resulting in the acceleration, velocity, and position of the particle
     *      at any given moment.
     * @throws Initialised
     */
    public Particle[] trajectory( double [] time )  throws Initialised {
        if (!setConstants) throw new Initialised(" Constants not initialised");

        Particle traj [] = new Particle[ time.length ];
        traj[0]= new Particle( fleck );

        for ( int i = 1 ; i < time.length ; ++i ){

            traj[i] = instant( time[i]);
        }

        return traj;
    }

}

