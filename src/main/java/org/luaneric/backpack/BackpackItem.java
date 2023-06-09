package main.java.org.luaneric.backpack;

import main.java.org.luaneric.GenericCustomValue;

public class BackpackItem {

    private GenericCustomValue word;

    private double value;
    private int weight = 0;


    public BackpackItem(GenericCustomValue word, int weight) {
        this.word = word;
        this.weight = weight;
        this.value = this.word.getFrequency();
    }

    public BackpackItem(GenericCustomValue word, int weight, double value) {
        this.word = word;
        this.weight = weight;
        this.value = value;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public GenericCustomValue getWord() {
        return word;
    }

    public void setWord(GenericCustomValue word) {
        this.word = word;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

}
