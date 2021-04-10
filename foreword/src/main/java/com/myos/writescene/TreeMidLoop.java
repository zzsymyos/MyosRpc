package com.myos.writescene;

import com.myos.utils.TreeNode;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * @Author: wu sir
 * @Date: 2020/8/11 11:24 上午
 * 二叉树中序遍历 递归非递归
 */
public class TreeMidLoop {

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
        list.add(root.val);
        fuzhu(root.right,list);
    }


    private static List<Integer> feidigui(TreeNode root) {
        List<Integer> list = new ArrayList<>();
        Stack<TreeNode> stack = new Stack<>();
        TreeNode curr = root;
        while (curr != null || !stack.isEmpty()) {
            while (curr != null) {
                stack.push(curr);
                curr = curr.left;
            }
            curr = stack.pop();
            list.add(curr.val);;
            curr = curr.right;
        }
        return list;
    }
}
