//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) throws NoSuchMethodException {
        //TIP Press <shortcut actionId="ShowIntentionActions"/> with your caret at the highlighted text
        // to see how IntelliJ IDEA suggests fixing it.
        //System.out.printf("Hello and welcome!");
        Fraction test = new Fraction(3,4);
        Fractionable test2= Utils.get_cache(test);
        Fractionable test3= new Utils (test);

        System.out.println(test2.doubleValue());
        System.out.println(test2.doubleValue());
        test2.setNum(20);
        System.out.println(test2.doubleValue());
        System.out.println(test2.doubleValue());


        System.out.println(test3.doubleValue());
        System.out.println(test3.doubleValue());
        test3.setNum(10);
        System.out.println(test3.doubleValue());
        System.out.println(test3.doubleValue());


    }
}