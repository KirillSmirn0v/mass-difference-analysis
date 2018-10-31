package mdCoreElements;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        IonAdduct ionAdduct = (IonAdduct) o;

        if (!name.equals(ionAdduct.name)) return false;
        return ionSign == ionAdduct.ionSign;
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + ionSign.hashCode();
        return result;
    }
}
