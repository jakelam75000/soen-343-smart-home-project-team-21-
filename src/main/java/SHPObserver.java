/**
 * Observer Class for SHP component.
 */

public class SHPObserver implements Observer{

    private String timer;
    private int multiplier;
    private static SHPObserver instance;
    private boolean houseSafe = true;
    private boolean callCops = false;
    private boolean turnOffAwayMode = false;
    private boolean userAlerted = false;
    private boolean currentAutomatedLightState = false;

    /**
    *lazy constructor for implementation singleton pattern
     */
    private SHPObserver(){
    }

    /**
     * static Constructor
     */
    static {
        instance = new SHPObserver();
    }

    /**
     * returns the sole instance of SHPobserver
     * @return SHPObserver object
     */
    public static SHPObserver getInstance(){
        return instance;
    }

    /**
     * Getter for timer.
     *
     * @return the timer
     */
    public String getTimer(){
        return timer;
    }

    /**
     * setter method
     * @param t desired time
     */
    public void setTimer(String t){
        timer = t;
    }

    /**
     * setter method
     * @param mult multiplier value
     */
    public void setMultiplier(int mult){
        multiplier = mult;
    }

    /**
     * Getter for the multiplier
     *
     * @return the multiplier
     */
    public int getMultiplier(){
        return multiplier;
    }

    /**
     * Sets up all the variables when the user decides to cancel the alert.
     */
    public void cancelAlert(){
        houseSafe = true;
        callCops = false;
        turnOffAwayMode = true;
    }

    /**
     * Sets up all the variables when the user decides to confirm the alert before the timer runs out.
     */
    public void confirmAlert(){
        callCops = true;
        turnOffAwayMode = true;
    }

    /**
     * Checks if the current time is within the scheduled time.
     *
     * @param current String The current time.
     * @param scheduled String the Scheduled time in the form (fromTime:ToTime)
     * @return true if the current time is withing the scheduled time; False otherwise.
     */
    public boolean checkScheduled(String current, String scheduled){
        String[] cTime = current.split(":");
        String[] sTime = scheduled.split(":");

        int cTimeValue = Integer.parseInt(cTime[2]);
        cTimeValue += Integer.parseInt(cTime[1])*100;
        cTimeValue += Integer.parseInt(cTime[0])*10000;

        int fromTimeValue = Integer.parseInt(sTime[2]);
        fromTimeValue += Integer.parseInt(sTime[1])*100;
        fromTimeValue += Integer.parseInt(sTime[0])*10000;

        int toTimeValue = Integer.parseInt(sTime[5]);
        toTimeValue += Integer.parseInt(sTime[4])*100;
        toTimeValue += Integer.parseInt(sTime[3])*10000;

        if((fromTimeValue <= cTimeValue) && (cTimeValue <= toTimeValue)){
            return true;
        }

        return false;
    }

    /**
     * Resets all the attributes of the class.
     */
    public void reset(){
        houseSafe = true;
        callCops = false;
        turnOffAwayMode = false;
        userAlerted = false;
        currentAutomatedLightState = false;
    }

    /**
     * Updates the observers by checking if there are people in the house when away mode is on.
     * (Needs to also check for automated lights)
     *
     * @param o the <code>Observable</code> object that is observed by this <code>Observer</code>.
     */
    @Override
    public void update(Observable o) {
        if(o == null || o.getClass() != SmartHomeDashboard.class)
            return;

        SmartHomeDashboard shd = (SmartHomeDashboard)o;

        String personHome = shd.isSomeoneHome();

        if(personHome != null) {
            String personLocation = UserManager.getUserLocation(personHome);
            if (personLocation.contains("STOOP")) {
                if (UserManager.getUserType(personHome) != UserTypes.STRANGER) {
                    turnOffAwayMode = true;
                }
            } else {
                if (UserManager.getUserType(personHome) == UserTypes.STRANGER) {
                    houseSafe = false;
                } else {
                    turnOffAwayMode = true;
                }
            }
        }

        if(!houseSafe && !userAlerted){
            setTimer(shd.getAlertTimer());
            setMultiplier(shd.getMultiplier());
            if(timer.equals("0:0:0")){
                this.confirmAlert();
            } else {
                new AlertConfirmationWindow(this).setVisible(true);
            }
            userAlerted = true;
        }

        if(callCops){
            shd.printToConsole("COPS ARE ON THEIR WAY!!!");
            callCops = false;
        }

        if(turnOffAwayMode){
            reset();
            if(callCops) shd.disableAwayMode("A Stranger is in the house.");
            else shd.disableAwayMode("There are users in the house.");
        }

        boolean setAutoLights = checkScheduled(shd.getCurrentTime(), shd.getScheduleTime());
        if(setAutoLights != currentAutomatedLightState){
            shd.setAutomatedLights(setAutoLights);
            currentAutomatedLightState = setAutoLights;
        }
    }
}
