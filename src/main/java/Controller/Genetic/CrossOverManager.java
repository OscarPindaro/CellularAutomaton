package Controller.Genetic;

import Model.Entity;
import Model.Genetic.Individual;
import Model.Genetic.Node;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class CrossOverManager {

    public List<Individual> cross(Individual mother, Individual father){
        Node motherTree = mother.getTree();
        Node fatherTree = father.getTree();
        List<Node> motherList = motherTree.toList();
        List<Node> faterList = fatherTree.toList();
        Node toSwapMother = motherList.get(new Random().nextInt(motherList.size()));
        Node toSwapFather = motherList.get(new Random().nextInt(faterList.size()));

        Node parentMother = Node.getFather(motherTree, toSwapMother);

        Individual newMother = changeTree(mother, parentMother, toSwapMother,toSwapFather);

        /*
        if(parentMother == null){
            mother.setTree(Node.copySubTree(toSwapFather));
        }

        if(parentMother.isLeftChild(toSwapMother)){
            parentMother.setLeftChildren(Node.copySubTree(toSwapFather));
        }
        else{
            parentMother.setRightChildren(Node.copySubTree(toSwapFather));
        }
         */

        Node parentFather = Node.getFather(fatherTree, toSwapFather);
        Individual newFather = changeTree(father, parentFather, toSwapFather, toSwapMother);/*
        if( parentFather == null){
            father.setTree(Node.copySubTree(toSwapMother));
        }
        if(parentFather.isLeftChild(toSwapFather)){
            parentFather.setLeftChildren(Node.copySubTree(toSwapMother));
        }
        else{
            parentFather.setRightChildren(Node.copySubTree(toSwapMother));
        }*/

        List<Individual> children = new ArrayList<>(2);
        children.add(newMother);
        children.add(newFather);
        return children;
    }

    private Individual changeTree(Individual parent, Node parentNode, Node toSwapParent, Node toSwapOther){
        if(parentNode == null){
            parent.setTree(Node.copySubTree(toSwapOther));
            return parent;
        }
        if(parentNode.isLeftChild(toSwapParent)){
            parentNode.setLeftChildren(Node.copySubTree(toSwapOther));
        }
        else {
            parentNode.setRightChildren(Node.copySubTree(toSwapOther));
        }
        return parent;
    }
}
