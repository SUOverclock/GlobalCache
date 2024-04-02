import java.lang.annotation.Annotation;
import java.lang.reflect.Array;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Arrays;

public class UtilInvHandler implements InvocationHandler {

    private Object obj;
    private double val_cache =0;
    private boolean need_refresh = true;

    public UtilInvHandler(Fractionable obj) {
        this.obj = obj;
    }


    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Method m = obj.getClass().getMethod(method.getName(),method.getParameterTypes());

        //Long ann = Arrays.stream(m.getAnnotations()).filter(x-> (x.annotationType().equals(Cache.class))).count();
        if(Arrays.stream(m.getDeclaredAnnotationsByType(Cache.class)).count()>0){

            if(need_refresh) val_cache = (double) method.invoke(obj,args);
            need_refresh= false;
            return val_cache;
        }

        if(Arrays.stream(m.getDeclaredAnnotationsByType(Mutator.class)).count()>0){

            need_refresh = true;
            return method.invoke(obj,args);
        }

        return method.invoke(obj,args);
    }
}
