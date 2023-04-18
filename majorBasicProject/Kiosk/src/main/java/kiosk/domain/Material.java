package kiosk.domain;
public class Material {
    String name;
    int amount;

    public Material(String name, String amount) {
        this.name = name;
        this.amount = Integer.parseInt(amount);
    }

    public String getName() {
        return name;
    }

    public int getAmount() {
        return amount;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}


