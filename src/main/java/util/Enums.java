package util;

public class Enums {


    public enum Sorts {
        Bubble, Merge, Insert

    }

    public enum Configuration {
        A(100),
        B(1000),
        C(10000),
        D(100000),
        E(1000000),
        F(10000000);


        private final long number;

        Configuration(int number) {
            this.number = number;
        }


        public long getNumber() {
            return number;
        }
    }

}
