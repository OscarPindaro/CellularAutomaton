package Controller;

/**
 * Object that stores the informations about the odometry of a cell
 */
public class Odometry {

    private float linearVelocity;
    private float angularVelocity;

    public Odometry(){}

    public Odometry(float linearVelocity, float angularVelocity){
        this.linearVelocity = linearVelocity;
        this.angularVelocity = angularVelocity;
    }

    public float getLinearVelocity() {
        return linearVelocity;
    }

    public void setLinearVelocity(float linearVelocity) {
        this.linearVelocity = linearVelocity;
    }

    public float getAngularVelocity() {
        return angularVelocity;
    }

    public void setAngularVelocity(float angularVelocity) {
        this.angularVelocity = angularVelocity;
    }
}
