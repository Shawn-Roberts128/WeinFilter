package Model;
import org.jzy3d.maths.Coord3d;
/**
 * By: Shawn Roberts
 * For: CS 202 Projects
 * <p/>
 * <p/>
 * Purpose ::
 */
public class Particle {

    protected Cord position;
    protected Cord velocity;
    protected Cord acceleration;

    double mass;
    double charge;

    public Particle(){

        this.position = new Cord();
        this.velocity = new Cord();
        this.acceleration = new Cord();
        this.mass = Double.NaN;
        this.charge = Double.NaN;
    }

    public Particle( Cord position, Cord velocity,Cord acceleration, double mass, double charge) {
        this.position = position;
        this.velocity = velocity;
        this.acceleration = acceleration;
        this.mass = mass;
        this.charge = charge;
    }


    public Particle( Particle from ) {

        this.position = new Cord (from.position);
        this.velocity = new Cord (from.velocity);
        this.acceleration = new Cord ( from.acceleration);
        this.mass = from. mass;
        this.charge=from.charge;

    }

    public static Particle NaN (){
        return new Particle();
    }

    public Cord getPosition() {
        return position;
    }

    public void setPosition(Cord position) {
        this.position = position;
    }

    public Cord getVelocity() {
        return velocity;
    }

    public void setVelocity(Cord velocity) {
        this.velocity = velocity;
    }

    public Cord getAcceleration() {
        return acceleration;
    }

    public void setAcceleration(Cord acceleration) {
        this.acceleration = acceleration;
    }

    public double getMass() {
        return mass;
    }

    public void setMass(double mass) {
        this.mass = mass;
    }

    public double getCharge() {
        return charge;
    }

    public void setCharge(double charge) {
        this.charge = charge;
    }

    @Override
    public String toString() {
        return super.toString();

    }

    public boolean isequal(Particle from){
        if ( this.charge        !=       from.charge        ) return false;
        if ( this.mass          !=       from.mass          ) return false;
        if ( !this.acceleration.isequal( from.acceleration) ) return false;
        if ( !this.velocity    .isequal( from.velocity    ) ) return false;
        if ( !this.position    .isequal( from.position    ) ) return false;
        return true;
    }
    public Coord3d pathCord(){

        return new Coord3d(this.position.x, this.position.y,this.position.z);

    }
}
