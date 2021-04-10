package com.myos.writescene;

import com.myos.utils.TreeNode;
import javafx.util.Pair;

import java.util.*;

/**
 * @Author: wu sir
 * @Date: 2020/8/11 11:24 上午
 * 二叉树后序遍历递归非递归
 */
public class TreeHouLoop {

    public static void main(String[] args) {
       // System.out.println(digui(TreeNode.treeNode()));
        System.out.println(feidigui(TreeNode.treeNode()));
    }

    private static List<Integer> digui(TreeNode root) {
        List<Integer> list = new ArrayList<>();
        fuzhu(root,list);
        return list;
    }

    private static void fuzhu(TreeNode root,List<Integer> list) {
        if (root == null) return;
        fuzhu(root.left,list);
        fuzhu(root.right,list);
        list.add(root.val);
    }


    private static List<Integer> feidigui(TreeNode root) {
        List<Integer> list = new ArrayList<>();
        Stack<TreeNode> stack1 = new Stack<>();
        Stack<TreeNode> stack2 = new Stack<>();
        stack1.push(root);
        while (!stack1.isEmpty()) {
            TreeNode node = stack1.pop();
            stack2.push(node);
            if (node.left != null) stack1.push(node.left);
            if (node.right != null) stack1.push(node.right);
        }
        while (!stack2.isEmpty()) {
            list.add(stack2.pop().val);
        }
        return list;
    }
}
