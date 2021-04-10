package com.myos.writescene;

import com.myos.utils.TreeNode;

import java.util.*;

/**
 * @Author: wu sir
 * @Date: 2020/8/11 11:24 上午
 * 二叉树层序遍历递归非递归
 */
public class TreeLevelLoop {

    public static void main(String[] args) {
       // System.out.println(digui(TreeNode.treeNode()));
        System.out.println(feidigui1(TreeNode.treeNode()));
    }

    private static List<Integer> digui(TreeNode root) {
        List<Integer> list = new ArrayList<>();
        fuzhu(root,list);
        return list;
    }

    private static void fuzhu(TreeNode root,List<Integer> list) {
        if (root == null) return;
        /*fuzhu(root.left,list);
        fuzhu(root.right,list);
        list.add(root.val);*/
    }


    private static List<List<Integer>> feidigui(TreeNode root) {
        List<List<Integer>> list = new ArrayList<>();
        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);
        while (!queue.isEmpty()) {
            int size = queue.size();
            List<Integer> temp = new ArrayList<>();
            for (int i = 0; i < size; i++) {
                TreeNode p = queue.poll();
                temp.add(p.val);
                if (p.left != null) queue.add(p.left);
                if (p.right != null) queue.add(p.right);
            }
            list.add(temp);
        }
        return list;
    }

    private static List<Integer> feidigui1(TreeNode root) {
        List<Integer> list = new ArrayList<>();
        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);
        while (!queue.isEmpty()) {
            TreeNode p = queue.poll();
            list.add(p.val);
            if (p.left != null) queue.add(p.left);
            if (p.right != null) queue.add(p.right);
        }
        return list;
    }
}
