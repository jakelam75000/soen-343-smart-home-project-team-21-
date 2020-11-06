/**
 * Structural pattern WRAPPER structure
 * meant to give the light a "lock" functionality (prevent a light from changing state)
 */
public class LightLockWrapper {
    Light light;
    boolean locked;

    /**
     *default constructor
     */
    public LightLockWrapper(){
        light = null;
        locked = false;
    }

    /**
     * normal constructor
     * @param l light to be wrapped
     */
    public LightLockWrapper(Light l){
        light = l;
    }

    /**
     * setter method for light
     * @param l set light l to be wrapped
     */
    public void setLight(Light l){ light = l; locked = false;}

    /**
     * setter method, sets the light to be locked or unlocked
     * @param l boolean value of desired new lock state
     */
    public void setLocked(Boolean l){
        if (light==null)return;
        else locked = l;
    }

    /**
     * getter method
     * @return light that is being wrapped
     */
    public Light getLight(){return light;}

    /**
     * getter mtehod
     * @return boolean value of locked
     */
    public boolean getLocked(){ return locked;}
}