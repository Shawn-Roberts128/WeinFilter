package Model;

/**
 * By: Shawn Roberts
 * For: CS 202 Projects
 * <p/>
 * <p/>
 *  Purpose :: This is the voltage accelerator at the begging of a Wein filter.
 *      This will Take in a partial, accelerate it base on the voltage
 *      across the space and into the exit hole.
 *  Outline ::
 *      - Fields
 *      - Test main
 *      - Methods
 *          #
 *
 */
public class VoltAccel {

    /** Fields **/
    private double electric;    // positive is + y, negative is -y direction of field
    private Particle fleck;     // the Particle  to be accelerated
    private double leftVolt;    // the voltage on the left plate ( - y side)
    private double rightVolt;   // the voltage on the right play ( + y side);


    /** Test Main **/
    /** Methods **/

    public VoltAccel(double leftVolt, double rightVolt) {
        this.leftVolt = leftVolt;
        this.rightVolt = rightVolt;
        this.electric = rightVolt - leftVolt; // initialise the electric field
        fleck = null;
    }

    public void setFleck(Particle fleck) {
        this.fleck = fleck;
    }

    public void setLeftVolt(double leftVolt) {
        this.leftVolt = leftVolt;
        this.electric = rightVolt - leftVolt; // reset the Electric field
    }

    public void setRightVolt(double rightVolt) {
        this.rightVolt = rightVolt;
        this.electric = rightVolt - leftVolt; // reset the Electric field
    }
        /** instant :: outputs the properties of a particle in an electric field at a given instant in time
     *
     * @param time the computed particles instant
     * @return a particle at the given time
     * @throws Initialised
     */
    private Particle instant (double time ) throws Initialised {

        this.electric = rightVolt - leftVolt; // reset the Electric field

        /** Acceleration **/
        Cord acc =new Cord (fleck.acceleration.x,   // x
                            electric *fleck.charge /fleck.mass+fleck.acceleration.y,// y axis of movement
                            fleck.acceleration.z);  // z
        /** velocity **/
        Cord vel =new Cord (fleck.velocity.x +acc.x *time, // x
                            fleck.velocity.y +acc.y *time, // y axis of movement
                            fleck.velocity.z +acc.z *time);// z
        /** Position **/
        Cord pos =new Cord (fleck.position.x +fleck.velocity.x *time + (fleck.acceleration.x +acc.x) *time, // x
                            fleck.position.y +fleck.velocity.y *time + (fleck.acceleration.y +acc.y) *time, // y axis of movement
                            fleck.position.z +fleck.velocity.z *time + (fleck.acceleration.z +acc.z) *time);// z


        return new Particle(pos, vel, acc, this.fleck.mass, this.fleck.charge ); /** mass and charge **/

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

        Particle traj [] = new Particle[ time.length ];
        traj[0]= new Particle( fleck );

        for ( int i = 1 ; i < time.length ; ++i ){

            traj[i] = instant( time[i]);
        }

        return traj;
    }

}