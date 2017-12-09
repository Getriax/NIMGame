package NimGame;

public class Stack {
    private int number;
    private String numberBinary;
    Stack(int number) {
        this.number = number;
        numberBinary = Integer.toString(number, 2);
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getNumberBinary() {
        return numberBinary;
    }

    public void setNumberBinary(String numberBinary) {
        this.numberBinary = numberBinary;
    }
    public void substractNumber(int num) {
        this.number -= num;
        numberBinary = Integer.toString(number, 2);
    }
    public boolean exists() {
        return number > 0;
    }
}
