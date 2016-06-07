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
        return this.charge +","
                +this.mass +","
                +this.position.toString()+","
                +this.velocity.toString()+","
                +this.acceleration.toString();

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Particle)) return false;

        Particle particle = (Particle) o;

        if (Double.compare(particle.charge, charge) != 0) return false;
        if (Double.compare(particle.mass, mass) != 0) return false;
        if (!acceleration.equals(particle.acceleration)) return false;
        if (!position.equals(particle.position)) return false;
        if (!velocity.equals(particle.velocity)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = position.hashCode();
        result = 31 * result + velocity.hashCode();
        result = 31 * result + acceleration.hashCode();
        temp = Double.doubleToLongBits(mass);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(charge);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    public Coord3d pathCord(){

        return new Coord3d(this.position.x, this.position.y,this.position.z);

    }
}
