public class Fraction implements Fractionable{
        private int num;
        private int denum;

        public Fraction(int num, int denum) {
            this.num = num;
            this.denum = denum;
        }

        @Mutator
        public void setNum(int num) {
            this.num = num;
        }

        @Mutator
        public void setDenum(int denum) {
            this.denum = denum;
        }

    @Override  // расширен класс примером еще одним методом для кэширования
        @Cache(2500)
        public double tribleValue() {
        System.out.println("invoke trible value");
        return (double) num*denum;
        }

    @Override
        @Cache(1500)
        public double doubleValue() {
            System.out.println("invoke double value");
            return (double) num/denum;
        }
    }