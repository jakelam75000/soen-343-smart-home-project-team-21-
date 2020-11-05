public class SHPObserver implements Observer{

    private String timer;
    private int multiplier;
    private static SHPObserver instance;
    private static boolean houseSafe = true;
    private static boolean callCops = false;
    private static boolean turnOffAwayMode = false;
    private static boolean userAlerted = false;
    private static boolean currentAutomatedLightState = false;

    /**
    *lazy constructor for implementation singleton pattern
     */
    private SHPObserver(){
    }
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

        UserTypes someoneHome = shd.isSomeoneHome();
        if(someoneHome == UserTypes.STRANGER) houseSafe = false;
        else if(someoneHome == null) houseSafe = true;
        else shd.disableAwayMode();

        if(!houseSafe && !userAlerted){
            setTimer(shd.getAlertTimer());
            setMultiplier(shd.getMultiplier());
            new AlertConfirmationWindow(this).setVisible(true);
            userAlerted = true;
        }

        if(callCops){
            shd.printToConsole("COPS ARE ON THEIR WAY!!!");
            callCops = false;
        }

        if(turnOffAwayMode){
            houseSafe = true;
            callCops = false;
            turnOffAwayMode = false;
            userAlerted = false;
            currentAutomatedLightState = false;
            shd.disableAwayMode();
        }

        boolean setAutoLights = checkScheduled(shd.getCurrentTime(), shd.getScheduleTime());
        if(setAutoLights != currentAutomatedLightState){
            shd.setAutomatedLights(setAutoLights);
            currentAutomatedLightState = setAutoLights;
        }
    }
}
