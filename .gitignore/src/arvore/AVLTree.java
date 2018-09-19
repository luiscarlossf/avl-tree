/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package arvore;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
public class AVLTree {
 
    private Node root;
 
    private class Node {
        private String key;
        private int balance;
        private int height;
        private ArrayList<Dado> dados= new ArrayList<Dado>();
        private Node left, right, parent;
 
        Node(String k, Node p,Dado dados) {
            key = k;
            parent = p;
            this.dados.add(dados);
        }

        /**
         * @return the dados
         */
        public ArrayList<Dado> getDados() {
            return dados;
        }

        /**
         * @param dados the dados to set
         */
        public void setDados(Dado dados) {
            this.dados.add(dados);
        }
    }
    public Node getRoot(){
        return root;
    }
    public void setDados(Dado dados){
        root.setDados(dados);
    }
 
    public boolean insert(String key, Dado dados) {
        if (root == null)
            root = new Node(key, null, dados);
        else {
            Node n = root;
            Node parent;
            while (true) {
                
                if (n.key.toLowerCase().compareTo(key.toLowerCase())==0){
                    n.getDados().add(dados);
                    return false;
                }
                
                parent = n;
 
                boolean goLeft = n.key.compareTo(key) > 0;
                n = goLeft ? n.left : n.right;
 
                if (n == null) {
                    if (goLeft) {
                        parent.left = new Node(key, parent, dados);
                    } else {
                        parent.right = new Node(key, parent, dados);
                    }
                    rebalance(parent);
                    break;
                }
            }
        }
        return true;
    }
 
    private void delete(Node node){
        if(node.left == null && node.right == null){
            if(node.parent == null) root = null;
            else{
                Node parent = node.parent;
                if(parent.left == node){
                    parent.left = null;
                }else parent.right = null;
                rebalance(parent);
            }
            return;
        }
        if(node.left!=null){
            Node child = node.left;
            while (child.right!=null) child = child.right;
            node.key = child.key;
            delete(child);
        }else{
            Node child = node.right;
            while (child.left!=null) child = child.left;
            node.key = child.key;
            delete(child);
        }
    }
 
    public void delete(String delKey) {
        if (root == null)
            return;
        Node node = root;
        Node child = root;
 
        while (child != null) {
            node = child;
            child = delKey.compareTo(node.key) > 0 || delKey.compareTo(node.key) == 0? node.right : node.left;
            if (delKey == node.key) {
                delete(node);
                return;
            }
        }
    }
 
    private void rebalance(Node n) {
        setBalance(n);
 
        if (n.balance == -2) {
            if (height(n.left.left) >= height(n.left.right))
                n = rotateRight(n);
            else
                n = rotateLeftThenRight(n);
 
        } else if (n.balance == 2) {
            if (height(n.right.right) >= height(n.right.left))
                n = rotateLeft(n);
            else
                n = rotateRightThenLeft(n);
        }
 
        if (n.parent != null) {
            rebalance(n.parent);
        } else {
            root = n;
        }
    }
 
    private Node rotateLeft(Node a) {
 
        Node b = a.right;
        b.parent = a.parent;
 
        a.right = b.left;
 
        if (a.right != null)
            a.right.parent = a;
 
        b.left = a;
        a.parent = b;
 
        if (b.parent != null) {
            if (b.parent.right == a) {
                b.parent.right = b;
            } else {
                b.parent.left = b;
            }
        }
 
        setBalance(a, b);
 
        return b;
    }
 
    private Node rotateRight(Node a) {
 
        Node b = a.left;
        b.parent = a.parent;
 
        a.left = b.right;
 
        if (a.left != null)
            a.left.parent = a;
 
        b.right = a;
        a.parent = b;
 
        if (b.parent != null) {
            if (b.parent.right == a) {
                b.parent.right = b;
            } else {
                b.parent.left = b;
            }
        }
 
        setBalance(a, b);
 
        return b;
    }
 
    private Node rotateLeftThenRight(Node n) {
        n.left = rotateLeft(n.left);
        return rotateRight(n);
    }
 
    private Node rotateRightThenLeft(Node n) {
        n.right = rotateRight(n.right);
        return rotateLeft(n);
    }
 
    private int height(Node n) {
        if (n == null)
            return -1;
        return n.height;
    }
 
    private void setBalance(Node... nodes) {
        for (Node n : nodes){
            reheight(n);
            n.balance = height(n.right) - height(n.left);
        }
    }
 
    public void printBalance() {
        printBalance(root);
    }
 
    private void printBalance(Node n) {
        if (n != null) {
            printBalance(n.left);
            System.out.printf("%s ", n.balance);
            printBalance(n.right);
        }
    }
    
    public void printAllTree(DefaultTableModel dadosFile) {
        printAllTree(root, dadosFile);
    }
    
    private void printAllTree(Node node,DefaultTableModel dadosFile) {
       if(node != null) {
           printAllTree(node.left, dadosFile);
            for(Dado dados: node.getDados())
                    dadosFile.addRow(dados.getDados());
           printAllTree(node.right,dadosFile);
     }
    }
    
  
  public void search(String k, DefaultTableModel dadosFile)
  {
    Node node= search(root,k, dadosFile);
    if(node==null)
        JOptionPane.showMessageDialog(null, k.toUpperCase()+", não foi encontrado", "Que pena!", 0);
  } //search

  
  protected Node search(Node node, String k, DefaultTableModel dadosFile)
  {
    while (node != null) {
            if(node.key.toLowerCase().compareTo(k.toLowerCase())==0){
                for(Dado dados: node.getDados())
                    dadosFile.addRow(dados.getDados());
                break;
            }else if (node.key.toLowerCase().compareTo(k.toLowerCase())>0)
                node = node.left;
            else
                node = node.right;
     } //while

      if (node != null){
        return node;
        }
      else {
           return node;
      }
  } //search
 
    private void reheight(Node node){
        if(node!=null){
            node.height=1 + Math.max(height(node.left), height(node.right));
        }
    }

}