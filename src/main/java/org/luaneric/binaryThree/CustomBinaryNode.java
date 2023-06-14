package main.java.org.luaneric.binaryThree;

import main.java.org.luaneric.GenericCustomValue;

public class CustomBinaryNode {
    private GenericCustomValue value;

    private int balance;
    private CustomBinaryNode left;
    private CustomBinaryNode right;

    private CustomBinaryNode up;

    public GenericCustomValue getNodeValue() {
        return value;
    }

    public int getFrequency() {
        return this.value.getFrequency();
    }

    public void setFrequency(int frequency) {
        this.value.setFrequency(frequency);
    }

    public void addFrequency() {
        this.value.setFrequency(this.value.getFrequency() + 1);
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public CustomBinaryNode getLeft() {
        return left;
    }

    public void setLeft(CustomBinaryNode left) {
        this.left = left;
    }

    public CustomBinaryNode getRight() {
        return right;
    }

    public void setRight(CustomBinaryNode right) {
        this.right = right;
    }

    public CustomBinaryNode getUp() {
        return up;
    }

    public void setUp(CustomBinaryNode up) {
        this.up = up;
    }

    public CustomBinaryNode(GenericCustomValue value) {
        this.value = value;

        this.left = null;
        this.right = null;
        this.up = null;

        this.balance = 0;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < this.value.getFrequency(); i++) {
            sb.append(this.value);
            sb.append(" ");
        }
        return sb.toString();
    }
}
