public interface Fractionable{
    double doubleValue() throws NoSuchMethodException;

    double tribleValue() throws NoSuchMethodException; // расширен интерфейс примером еще одним методом для кэширования
    void setNum(int num) throws NoSuchMethodException;
    void setDenum(int denum) throws NoSuchMethodException;
}
