import java.util.Timer;
import java.util.TimerTask;

public class CountdownClock {
    private Timer timer;
    int interval;
    int timerLengthSecs;
    Boolean isPaused = true;
    public CountdownClock()
    {
        timer = new Timer();
        interval = 1000;
        timerLengthSecs = 60;
    }

    public CountdownClock(int secs)
    {
        timer = new Timer();
        timer = new Timer();
        interval = 1000;
        timerLengthSecs = secs;
    }

    public void setSecsLeft(int secs){
        timerLengthSecs = secs;
    }

    public void startTimer(){
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask(){
            public void run(){
                if (!isPaused){
                    System.out.println(setTimeLeft() + " " + getTimeInMinsSecs());
                }
            }
        },0, interval);
    }

    public boolean getIsPaused(){
        return isPaused;
    }

    public Timer getTimer() {
        return timer;
    }

    public void pauseTimer(){
        if (isPaused == true){
            isPaused = false;
        }else{
            isPaused = true;
        }
    }

    public void setMinsSecsToSecs(int mins, int secs){

        timerLengthSecs = (mins*60) + secs;
    }

    public String getTimeInMinsSecs(){
        int timeLeft = timerLengthSecs;
        int mins = timeLeft / 60;
        int secs = timeLeft % 60;
        String minsSecs;
        int length = Integer.toString(secs).length();
        if (length == 1){
            minsSecs = mins + ":0" + secs;
        }else{
            minsSecs = mins + ":" + secs;
        }

        return minsSecs;
    }
    public String getMins(){
        int timeLeft = timerLengthSecs;
        int mins = timeLeft / 60;
        String minsToString = Integer.toString(mins);
        return minsToString;
    }
    public String getSecs(){
        int timeLeft = timerLengthSecs;
        int secs = timeLeft % 60;
        String secsToString = Integer.toString(secs);
        int length = Integer.toString(secs).length();
        if (length == 1){
            secsToString = "0" + secs;
        }

        return secsToString;
    }

    public int setTimeLeft(){
        if (timerLengthSecs == 1){
            timer.cancel();
        }
        timerLengthSecs -= 1;
        return timerLengthSecs;
    }

}
