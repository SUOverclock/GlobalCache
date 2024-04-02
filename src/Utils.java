import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;


public class Utils implements Fractionable {

    private Fractionable object;
    private double val_cache =0;
    private boolean need_refresh = true;
    public Utils(Fractionable object) {

        this.object=object;

    }

    public Utils() {}

    public static Fractionable get_cache(Fractionable obj){
        Class cls = obj.getClass();
        return (Fractionable) Proxy.newProxyInstance(cls.getClassLoader(),
                new Class[]{Fractionable.class},
                new UtilInvHandler(obj));

    }

    @Override
    public double doubleValue() throws NoSuchMethodException {
        //Method m = object.getClass().getMethod("doubleValue",);
        if(Arrays.stream(object.getClass().getMethod("doubleValue",null).getAnnotationsByType(Cache.class)).findAny().isPresent()) {
            if(need_refresh) {
                need_refresh = false;
                val_cache = object.doubleValue();
            };
            return val_cache;
        }
        return object.doubleValue(); //in case we haven't annotation
    }

    @Override
    public void setNum(int num) throws NoSuchMethodException {
        if(Arrays.stream(object.getClass().getMethod("setNum", int.class).getAnnotationsByType(Mutator.class)).findAny().isPresent()) {
            need_refresh = true;

        }
        object.setNum(num);
    }

    @Override
    public void setDenum(int denum) throws NoSuchMethodException {

        if(Arrays.stream(object.getClass().getMethod("setDenum", int.class).getAnnotationsByType(Mutator.class)).findAny().isPresent()) {
            need_refresh = true;

        }
        object.setDenum(denum);
    }


}
