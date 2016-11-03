package httpOld;

public  class TimeManager {
    private int lifeMap;

    public int getT(long s) {
        long t= System.currentTimeMillis();
        long temp = t - s;
        if(temp<60000){
            lifeMap =1;
        }
        return lifeMap;
    }
}
