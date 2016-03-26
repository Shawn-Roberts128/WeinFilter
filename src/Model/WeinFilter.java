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
    private double eletric;

    private double c1;
    private double c2;
    private double c3;
    private double c4;

    private double w;

    Particle fleck;

    private boolean initiatlisation ;




    /**  Methods  **/
    public WeinFilter(double magnetic, double eletric) {
        this.magnetic = magnetic;
        this.eletric = eletric;
        this. w =this.c1 = this.c2 = this.c3 = this.c4 = 0;
        fleck = null;
        this. initiatlisation = false;

    }

    public double getMagnetic() {
        return magnetic;
    }

    public void setMagnetic(double magnetic) {
        this.magnetic = magnetic;
    }

    public double getEletric() {
        return eletric;
    }

    public void setEletric(double eletric) {
        this.eletric = eletric;
    }

    public void clear () {
        this.c1 = this.c2 = this. c3 = this.c4 = this .w = 0;
        fleck = null;
        this . initiatlisation = false;
    }

    public void init ( Particle from ){
        fleck = new Particle( from );

        try {
            this.setConsts();
        }
        catch( Exception e  ){
            if ( initiatlisation){
                this.clear();
                this.init( from);
            }
        }
    }

    private void setConsts() throws Initialised { // TODO finish
        if ( (fleck == null)||( initiatlisation )) throw new Initialised( " Constants not initialised " );

        // set c1
        // set c2
        // set c3
        // set c4
    }

    private double yCycloid ( double time) throws Initialised {
        if (!initiatlisation ) throw new Initialised(" Constants not initialised");

        return (c1 * Math.cos( w * time ) + c2 * Math.sin( w * time));
    }

    private double zCycloid( double time ) throws Initialised {
        if (!initiatlisation) throw new Initialised(" Constants not initialised");

        return ( 0 );// TODO add the equation for z Cycliod
    }

    private Particle instant() throws Initialised { // TODO make instance
        return instant(0.0);
    }

    private Particle instant (double time ) throws Initialised { // TODO write the full value returns a "particle"
        if (!initiatlisation) throw new Initialised(" Constants not initialised");

        Particle current = null;


        return current;
    }

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

