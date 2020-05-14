package Controller.Genetic;

import Model.Genetic.Individual;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CrossOverManager {

    public List<Individual> cross(Individual mother, Individual father){
        OldNode motherTree = mother.getTree();
        OldNode fatherTree = father.getTree();
        List<OldNode> motherList = motherTree.toList();
        List<OldNode> faterList = fatherTree.toList();
        OldNode toSwapMother = motherList.get(new Random().nextInt(motherList.size()));
        OldNode toSwapFather = motherList.get(new Random().nextInt(faterList.size()));

        OldNode parentMother = OldNode.getFather(motherTree, toSwapMother);

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

        OldNode parentFather = OldNode.getFather(fatherTree, toSwapFather);
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

    private Individual changeTree(Individual parent, OldNode parentNode, OldNode toSwapParent, OldNode toSwapOther){
        if(parentNode == null){
            parent.setTree(OldNode.copySubTree(toSwapOther));
            return parent;
        }
        if(parentNode.isLeftChild(toSwapParent)){
            parentNode.setLeftChildren(OldNode.copySubTree(toSwapOther));
        }
        else {
            parentNode.setRightChildren(OldNode.copySubTree(toSwapOther));
        }
        return parent;
    }
}
