public class Timer {

    private long startTime;

    private long accumulatedTime;
    
    //Constructor
    //===========================================================================================
    public Timer()
    {
        StartTimer();
    }

    //StartTimer
    //===========================================================================================
    public long StartTimer()
    {
        startTime = System.currentTimeMillis();
        return startTime;
    }

    //StopTimer
    //===========================================================================================
    public long StopTimer()
    {
        accumulatedTime = accumulatedTime + (System.currentTimeMillis() - startTime);
        return accumulatedTime;
    }

    //Getters
    //===========================================================================================
    public long GetAccumulatedTime() {

        StopTimer();
        StartTimer();
        return accumulatedTime;
    }
}
