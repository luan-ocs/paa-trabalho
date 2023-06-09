package main.java.org.luaneric;

public class GenericCustomValue<T> {

    private int frequency;
    public T value;

    public GenericCustomValue(T value) {
        this.value = value;
        this.frequency = 0;
    }

    public T getTreeCustomValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    public int getFrequency() {
        return frequency;
    }

    public void setFrequency(int frequency) {
        this.frequency = frequency;
    }

    @Override
    public String toString() {
        return this.value.toString();
    }
}
