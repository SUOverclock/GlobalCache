import java.lang.reflect.Method;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;


//@EqualsAndHashCode
public class HResult {

    private  ConcurrentHashMap<Method,LResult>results = new ConcurrentHashMap<>();

    public void put(Method m, Object results, int time) {
        LResult tmp=new LResult(results,time);
        this.results.put(m,tmp);
    }

    public Object getCache(Method m) {
        return results.get(m)!=null? results.get(m).getCache():null;
    }  // если кэш протух или его нет, возвращаем null

    public HResult() {

    }

    @Override
    public String toString() {  // для отладки
        String str="";
        for(Method k: results.keySet()) {
            str= str + (k.toString() + "=" + results.get(k).getCache())+ "\n";
        }
        return str;
    }

    public boolean cache_bad() {  // если все значения по кэшируемых функций протухли или их нет, вернуть true, но если хотя бы у одного метода кэш еще действует, то false
        int len=results.size();
        int bad=0;
        for(Method k:results.keySet()) {
            if(!results.get(k).getCache_Status()) bad++;
        }
        return len==bad? true:false;
    }
}
