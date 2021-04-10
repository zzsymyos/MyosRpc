package com.myos.writescene;

import com.myos.utils.TreeNode;

import java.util.*;

/**
 * @Author: wu sir
 * @Date: 2020/8/11 11:24 上午
 * 二叉树前序遍历递归非递归
 */
public class TreeQianLoop {

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
        list.add(root.val);
        fuzhu(root.left,list);
        fuzhu(root.right,list);
    }


    private static List<Integer> feidigui(TreeNode root) {
        List<Integer> list = new ArrayList<>();
        Stack<TreeNode> queue = new Stack<>();
        queue.add(root);
        while (!queue.isEmpty()) {
            TreeNode t = queue.pop();
            if (t == null) continue;
            list.add(t.val);
            queue.add(t.right);
            queue.add(t.left);
        }
        return list;
    }
}
