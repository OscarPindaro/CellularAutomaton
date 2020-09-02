package model.genetic;

import java.util.Stack;

public class StackElement {

    private Node node;
    private boolean isOpenPar;
    private boolean isClosePar;
    private String stringOp;

    private StackElement(Node node, boolean isOpenPar, boolean isClosePar, String stringOp){
        this.node = node;
        this.isOpenPar = isOpenPar;
        this.isClosePar = isClosePar;
        this.stringOp = stringOp;
    }

    public static StackElement createOperationNode(String operation, Node node1, Node node2, int ninputs){
        Node operNode = new OperationNode(operation, node1, node2, ninputs);
        return new StackElement(operNode, false, false, null);
    }

    public static StackElement createOpenPar(){
        return new StackElement(null, true, false, null);
    }

    public static StackElement createClosePar(){
        return new StackElement(null, false, true,null);
    }

    public static StackElement createStringOperation(String op){
        return new StackElement(null, false, false, op);
    }

    public static StackElement createConstantNode(float value){
        return new StackElement(new ConstantNode(value), false, false, null);
    }

    public static StackElement createVariableNode(int index, int ninputs){
        return new StackElement(new VariableNode(index, ninputs), false, false, null);
    }

    public boolean isNode(){
        return node != null;
    }
    public boolean isOpenPar() {
        return isOpenPar;
    }

    public boolean isClosePar() {
        return isClosePar;
    }

    public String getStringOp() {
        return stringOp;
    }

    public Node getNode() {
        return node;
    }

    public boolean isStringOp(){
        return stringOp != null;
    }
}
