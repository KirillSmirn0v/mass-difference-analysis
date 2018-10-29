public class IonAdduct {
    private String name;
    private IonSign ionSign;
    private double mass;

    public IonAdduct(String name, IonSign ionSign, double mass) {
        this.name = name;
        this.ionSign = ionSign;
        this.mass = mass;
    }

    public String getName() {
        return name;
    }

    public IonSign getIonSign() {
        return ionSign;
    }

    public double getMass() {
        return mass;
    }

    public enum IonSign {
        POSITIVE(1),
        NEGATIVE(-1);

        private int sign;

        IonSign(int sign) {
            this.sign = sign;
        }

        public int getSign() {
            return sign;
        }
    }
}
