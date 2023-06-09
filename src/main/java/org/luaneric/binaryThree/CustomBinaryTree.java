package main.java.org.luaneric.binaryThree;

import main.java.org.luaneric.GenericCustomValue;

import java.util.ArrayList;
import java.util.Comparator;

public class CustomBinaryTree {
    protected Comparator<CustomBinaryNode> functionCompare;
    protected CustomBinaryNode root;

    public CustomBinaryTree(Comparator functionCompare) {
        this.functionCompare = functionCompare;
        this.root = null;
    }

    public void insert(GenericCustomValue value) {
        CustomBinaryNode n = new CustomBinaryNode(value);

        insertRecursive(this.root, n);
    }

    private void insertRecursive (CustomBinaryNode compareNode, CustomBinaryNode insert) {


        if(compareNode == null) {
            this.root = insert;
            return;
        }

        int compareValue = this.functionCompare.compare(insert, compareNode);


        if(compareValue < 0) {
            if(compareNode.getLeft() == null) {
                compareNode.setLeft(insert);
                insert.setUp(compareNode);
                verifyBalance(compareNode);
            } else {
                insertRecursive(compareNode.getLeft(), insert);
            }
        } else if(compareValue > 0) {
            if(compareNode.getRight() == null) {
                compareNode.setRight(insert);
                insert.setUp(compareNode);
                verifyBalance(compareNode);
            } else {
                insertRecursive(compareNode.getRight(), insert);
            }
        } else {
            compareNode.setFrequency(compareNode.getFrequency() + 1);
        }
    }

    public void verifyBalance(CustomBinaryNode node) {
        int balance = node.getBalance();

        if(balance == -2) {

            if(height(node.getLeft().getLeft()) >= height(node.getLeft().getRight())) {
                node = rotateRight(node);
            } else {
                node = doubleRotateRight(node);
            }

        } else if (balance == 2) {

            if(height(node.getRight().getRight()) >= height(node.getRight().getLeft())) {
                node = rotateLeft(node);
            } else {
                node = doubleRotateLeft(node);
            }

        }

        if(node.getUp() != null) {
            verifyBalance(node.getUp());
        } else {
            this.root = node;
        }
    }

    CustomBinaryNode rotateRight(CustomBinaryNode node) {

        CustomBinaryNode left = node.getLeft();
        left.setUp(node.getUp());

        node.setLeft(left.getRight());

        if(node.getLeft() != null) {
            node.getLeft().setUp(node);
        }

        left.setRight(node);
        node.setUp(left);

        if(left.getUp() != null) {
            if(left.getUp().getRight() == node) {
                left.getUp().setRight(left);
            } else if (left.getUp().getLeft() == node) {
                left.getUp().setLeft(left);
            }
        }

        setBalance(node);
        setBalance(left);

        return left;
    }

    CustomBinaryNode doubleRotateRight(CustomBinaryNode node) {
        node.setLeft(rotateLeft(node.getLeft()));
        return rotateRight(node);
    }
    CustomBinaryNode rotateLeft(CustomBinaryNode node) {

        CustomBinaryNode right = node.getRight();
        right.setUp(node.getUp());

        node.setRight(right.getLeft());

        if (node.getRight() != null) {
            node.getRight().setUp(node);
        }

        right.setLeft(node);
        node.setUp(right);

        if (right.getUp() != null) {

            if (right.getUp().getRight() == node) {
                right.getUp().setRight(right);

            } else if (right.getUp().getLeft() == node) {
                right.getUp().setLeft(right);
            }
        }

        setBalance(node);
        setBalance(right);

        return right;


    }

    CustomBinaryNode doubleRotateLeft(CustomBinaryNode node) {
        node.setRight(rotateRight(node.getRight()));
        return rotateLeft(node);
    }

    private void setBalance(CustomBinaryNode node) {
        node.setBalance(height(node.getRight()) - height(node.getLeft()));
    }


    private int height(CustomBinaryNode node) {
        if(node == null) {
            return -1;
        }

        if(node.getLeft() == null && node.getRight() == null) {
            return 0;
        } else if (node.getLeft() == null) {
            return  1 + height(node.getRight());
        } else if (node.getRight() == null) {
            return 1 + height(node.getLeft());
        } else {
            return 1 + Math.max(height(node.getLeft()), height(node.getRight()));
        }
    }


    final protected ArrayList<CustomBinaryNode> inorder() {
        ArrayList<CustomBinaryNode> ret = new ArrayList<CustomBinaryNode>();
        inorder(this.root, ret);
        return ret;
    }

    final public ArrayList<GenericCustomValue> toArray() {
        ArrayList<GenericCustomValue> ret = new ArrayList<>();
        toArray(this.root, ret);
        return ret;
    }

    final private void toArray(CustomBinaryNode node, ArrayList list) {
        if(node == null) {
            return;
        }

        toArray(node.getLeft(), list);
        list.add(node.getNodeValue());
        toArray(node.getRight(), list);
    }

    final protected void inorder(CustomBinaryNode no, ArrayList<CustomBinaryNode> lista) {
        if (no == null) {
            return;
        }
        inorder(no.getLeft(), lista);
        lista.add(no);
        inorder(no.getRight(), lista);
    }

    private void printInOrderRecursive(CustomBinaryNode root, StringBuilder value) {
        if(root == null) {
            return;
        }

        printInOrderRecursive(root.getLeft(), value);
        value.append(root);
        printInOrderRecursive(root.getRight(), value);
    }

    public void setFunctionCompare(Comparator<CustomBinaryNode> functionCompare) {

        ArrayList<CustomBinaryNode> nodes = this.inorder();

        CustomBinaryTree reordered = new CustomBinaryTree(functionCompare);

        for (CustomBinaryNode node: nodes) {
            reordered.insert(node.getNodeValue());
        }

        this.root = reordered.root;

    }

    public String printInOrder() {

        StringBuilder ordered = new StringBuilder();

        printInOrderRecursive(this.root, ordered);

        return ordered.toString().trim();
    }


}


