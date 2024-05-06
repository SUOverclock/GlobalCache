import java.lang.annotation.Annotation;
import java.lang.reflect.Array;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.sql.Timestamp;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class UtilInvHandler implements InvocationHandler {

    private Object obj;

    private int total_cache_use = 0, cache_not_used = 0; // переменные для статистики
    private long demon_ttl=0;
    private long demon_period=60000;
    //private HResult hresult = new HResult();

    ConcurrentHashMap<Method, List<Object>> state = new ConcurrentHashMap<>();  // мапа для записи состояния мутаторов
    ConcurrentHashMap<HashMap<Method, List<Object>>, HResult> cache = new ConcurrentHashMap<>(); //глобальная мапа с состояниями и значениями по каждой из кэшируемых функций

    public UtilInvHandler(Fractionable obj, long demon) {
        this.obj = obj;
        demon_period=demon;
    }


    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Method m = obj.getClass().getMethod(method.getName(), method.getParameterTypes());
        if (System.currentTimeMillis() > demon_ttl) {   // запуск на основании прошедшего времени
            demon_ttl = System.currentTimeMillis() + demon_period;
            new Cleaner().start(); // запуск потока для очистки кэша
        }
        if (m.isAnnotationPresent(Cache.class)) {
            int time = m.getAnnotation(Cache.class).value();
            //System.currentTimeMillis()
            if (cache.get(state) == null || cache.get(state).getCache(m) == null) {  // если кэш протух или его нет
                HResult hresult;
                if (cache.get(state) == null) hresult = new HResult(); // если объекта кэша нет для данного состояния
                else hresult = cache.get(state); // если уже есть кэш для одной из методов, но нет для другого
                hresult.put(m, method.invoke(obj, args), time); // создаем запись кэша

                cache.put(new HashMap<>(state), hresult); // создаем/обновляем запись
                return hresult.getCache(m);  // возвращаем значение

            }
            return cache.get(state).getCache(m);  // кэш есть, возвращаем его


        }

        if (m.isAnnotationPresent(Mutator.class)) {
            state.put(m, Arrays.asList(args)); // записываем статус метода и его аргументы
            return method.invoke(obj, args);
        }

        return method.invoke(obj, args);
    }


    private class Cleaner extends Thread {

        public void run() {

                int poor_cache = 0;
                for (HashMap k : cache.keySet()) {
                    if (cache.get(k).cache_bad()) poor_cache++; // подсчет кол-ва просроченных объектов кэша
                }
                if (cache.size()>1000 && (cache.size()-poor_cache) < cache.size()/3) {
                    //System.out.println("Очистка кэша....");
                    //System.out.println("Всего записей:" + cache.size() + " Просрочено:" + poor_cache+ " Объем попадании:"+ (100-100*poor_cache/cache.size())+"%");
                    ConcurrentHashMap<HashMap<Method, List<Object>>, HResult> cache_new = new ConcurrentHashMap<>();
                    ConcurrentHashMap<HashMap<Method, List<Object>>, HResult> tmp;
                    tmp=cache; // временно сохраняем рабочую мапу
                    cache=cache_new; // переключаем кеш на чистую мапу
                    for (HashMap k : tmp.keySet()) {
                        if (tmp.get(k).cache_bad()) tmp.remove(k); // очистка коллекции
                    }
                    cache.putAll(tmp); //объединяем кэш

                }
                //System.out.println("Всего состояний:" + cache.size());

        }

    }
}