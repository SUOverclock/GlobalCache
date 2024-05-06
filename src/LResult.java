public class LResult {

    private Object cache;
    private long ttl;
    private long timeout;

    public LResult(Object cache, long timeout) {
        this.cache=cache;
        this.timeout=timeout;
        if(timeout!=0) ttl = System.currentTimeMillis()+timeout;
        else ttl=0;
    }

    public Object getCache() { // отдать значение из кэша, и обновить время
        if(ttl> System.currentTimeMillis()) {
            ttl = System.currentTimeMillis()+timeout;
            return cache;
        }
        return  ttl!=0? null:cache; // если t=0 вечный кэш
    }

    public boolean getCache_Status() {  // если кэш еще действует, вернуть true
        if(ttl> System.currentTimeMillis() || ttl==0) {

            return true;
        }
        return  false;
    }
}
