package model.genetic;

import java.util.*;

public abstract class Node implements Function {

    protected final int MAX_CONSTANT = 100;

    protected int ninputs;
    protected  Node rightChildren;
    protected  Node leftChildren;

    protected static Random generator = new Random();

    protected final static float HIGHERCONST = 100;

    protected static String[] operations = {"add" , "sub", "mul"};

    public Node(){

    }
    public Node(int ninputs){
        this.ninputs = ninputs;
    }

    @Override
    public abstract float compute(List<Float> inputs);

    public static Node createRandomTree(int ninputs){
        return new OperationNode(operations[generator.nextInt(operations.length)], ninputs);
    }

    public abstract int numberOfNodes();

    private Node getFather(Node child){
        Queue<Node> queue = new LinkedList();
        Node currentNode = null;

        //the children is the root, so it doesn't have a father
        if(child == this)
            return null;

        queue.add(this);

        while (!queue.isEmpty()){
            currentNode = queue.remove();
            if(currentNode.rightChildren == child || currentNode.leftChildren == child){
                return currentNode;
            }
            if (currentNode.leftChildren != null)
                queue.add(currentNode.leftChildren);
            if (currentNode.rightChildren != null)
                queue.add(currentNode.rightChildren);

        }

        throw new RuntimeException("No such node inside this tree");
    }

    public Node getNode(int i){
        Queue<Node> queue = new LinkedList();
        int iterations = 0;
        Node currentNode = null;
        queue.add(this);

        while (!queue.isEmpty()){
            currentNode = queue.remove();
            if (iterations == i)
                return currentNode;
            iterations++;
            if(currentNode.leftChildren != null)
                queue.add(currentNode.leftChildren);
            if(currentNode.rightChildren != null)
                queue.add(currentNode.rightChildren);
        }
        return currentNode;
    }

    public abstract Node copyTree(int ninputs);

    public abstract String toStringPython();

    public static Node treeFromString(String treeDefinition, int ninputs){
        System.out.println(treeDefinition);
        treeDefinition = treeDefinition.replace(",", " ")
                .replace("(", " ( ")
                .replace(")", " ) ")
        .replace(".", ",");
        System.out.println(treeDefinition);
        LinkedList<StackElement> stack = new LinkedList<>();
        Scanner scanner = new Scanner(treeDefinition);
        while(scanner.hasNext()){
            StackElement se = null;
            if (scanner.hasNext("add") || scanner.hasNext("mul") || scanner.hasNext("sub")){
                String op =scanner.next();
                System.out.println("Operation " + op);
                stack.addLast(StackElement.createStringOperation(op));
            }
            else if(scanner.hasNext("\\(")){
                scanner.next();
                System.out.println("letta parentesi");
                se = StackElement.createOpenPar();
                stack.addLast(se);
            }
            else if (scanner.hasNextFloat()){
                float value = scanner.nextFloat();
                System.out.println("FLOAT VALUE: " + value);
                stack.addLast(StackElement.createConstantNode(value));
            }
            else if (scanner.hasNext("ARG[0-9]*")){
                String var = scanner.next();
                System.out.println("ARGOMENTO " + var);
                char[] lastChar = {var.charAt(var.length()-1)};
                int index = Integer.parseInt(String.copyValueOf(lastChar));
                stack.addLast(StackElement.createVariableNode(index, ninputs));
            }
            else if (scanner.hasNext("\\)")){
                scanner.next();
                System.out.println("riducendo");
                for(int i = stack.size()-1; i >= 0; i--){
                    if(stack.get(i).isOpenPar()){
                        int toRemove = i-1;
                        String operation = stack.get(i-1).getStringOp();
                        Node node1 = stack.get(i+1).getNode();
                        Node node2 = stack.get(i+2).getNode();
                        for(int index = stack.size()-1; index >=toRemove; index--){
                            stack.remove(index);
                            System.out.println(stack.size());
                        }
                        stack.addLast(StackElement.createOperationNode(operation, node1, node2, ninputs));
                    }
                }
            }
            else{
                System.out.println(scanner.hasNext("ARG[0-9]*"));
                throw new RuntimeException();
            }
        }
        System.out.println(stack.get(0).getStringOp());
        Node tree = stack.get(0).getNode();
        return tree;
    }
}
