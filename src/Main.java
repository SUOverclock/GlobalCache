import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.Random;

import static java.lang.Thread.sleep;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) throws NoSuchMethodException, InterruptedException {
        //TIP Press <shortcut actionId="ShowIntentionActions"/> with your caret at the highlighted text
        // to see how IntelliJ IDEA suggests fixing it.
        //System.out.printf("Hello and welcome!");
        Random rnd = new Random();
        int [] test_value_num = new int[100];
        int [] test_value_denum = new int[100];
        Fraction test = new Fraction(3,4);
        Fractionable test2= get_cache(test);


        // Эмуляция работы кэша, 100 контрольных случайных значений
        for(int a=0;a<100;a++) {
            test_value_num[a] =rnd.nextInt(100);
            test_value_denum[a] =rnd.nextInt(100);
            test2.setNum(test_value_num[a]);
            test2.setDenum(test_value_denum[a]);
            System.out.println(test2.doubleValue());
            System.out.println(test2.tribleValue());
        }

        // Чтение кэша, 1000 раз попадания 100%

        for(int a=0;a<1000;a++) {
            int b=rnd.nextInt(100);
            test2.setNum(test_value_num[b]);
            test2.setDenum(test_value_denum[b]);
            System.out.println(test2.doubleValue());
            System.out.println(test2.tribleValue());
        }

        Thread.sleep(4000); // весь кэш протух

        // создание кэша по новой

        for(int a=0;a<1000;a++) {
            int b=rnd.nextInt(100);
            test2.setNum(test_value_num[b]);
            test2.setDenum(test_value_denum[b]);
            System.out.println(test2.doubleValue());
            System.out.println(test2.tribleValue());
        }

        // заполнение кэша по большом объеме выборки, для тестирования уборщика
        for(int a=0;a<1000;a++) {
            int b=rnd.nextInt(100);
            test2.setNum(test_value_num[b]);
            test2.setDenum(test_value_denum[b]);
            System.out.println(test2.doubleValue());
            System.out.println(test2.tribleValue());
        }



        for(int b=0; b<100;b++) {
            for (int a = 0; a < 1000; a++) {
                double t;
                //int b=rnd.nextInt(100);
                test2.setNum(rnd.nextInt(100));
                test2.setDenum(rnd.nextInt(100));
                //System.out.println(test2.doubleValue());
                //System.out.println(test2.tribleValue());
                t=test2.doubleValue();
                t=test2.tribleValue();

            }
            Thread.sleep(1000);
        }

    }
    public static Fractionable get_cache(Fractionable obj){
        Class cls = obj.getClass();
        return (Fractionable) Proxy.newProxyInstance(cls.getClassLoader(),
                new Class[]{Fractionable.class},
                new UtilInvHandler(obj, 60000));

    }
}