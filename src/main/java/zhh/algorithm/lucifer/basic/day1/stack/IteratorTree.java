package zhh.algorithm.lucifer.basic.day1.stack;

import zhh.algorithm.lucifer.basic.core.TreeNode;

import java.util.Stack;

/**
 * @author zhanghao
 * @date 2021-04-29 07:07
 * @desc 循环遍历二叉树
 * https://www.jianshu.com/p/456af5480cee
 * 思路模板：
 *  while (node != null || !stack.isEmpty()) {
 *      while (node != null) {
 *          stack.push(node);
 *          node = node.left;
 *      }
 *
 *      ...
 *  }
 */
public class IteratorTree {
    public void preOrder(TreeNode root) {
        Stack<TreeNode> stack = new Stack<>();
        TreeNode node = root;
        while (node != null || !stack.isEmpty()) {
            while (node != null) {
                System.out.print(node.val);
                stack.push(node);
                node = node.left;
            }

            if (!stack.isEmpty()) {
                TreeNode pop = stack.pop();
                node = pop.right;
            }
        }
    }

    public void inOrder(TreeNode root) {
        Stack<TreeNode> stack = new Stack<>();
        TreeNode node = root;
        while (node != null || !stack.isEmpty()) {
            while (node != null) {
                stack.push(node);
                node = node.left;
            }

            if (!stack.isEmpty()) {
                TreeNode pop = stack.pop();
                System.out.print(pop.val);
                node = pop.right;
            }
        }
    }

    /**
     * 后序遍历在决定是否可以输出当前节点的值的时候，需要考虑其左右子树是否都已经遍历完成。
     * 所以需要设置一个lastVisit游标。
     * 若lastVisit等于当前考查节点的右子树，表示该节点的左右子树都已经遍历完成，则可以输出当前节点。
     * 并把lastVisit节点设置成当前节点，将当前游标节点node设置为空，下一轮就可以访问栈顶元素。
     * 否者，需要接着考虑右子树，node = node.right。
     * @param root
     */
    public void postOrder(TreeNode root) {
        Stack<TreeNode> stack = new Stack<>();
        TreeNode node = root;
        TreeNode lastVisit = root;
        while (node != null || !stack.isEmpty()) {
            while (node != null) {
                stack.push(node);
                node = node.left;
            }

            node = stack.peek();
            if (node.right == null || node.right == lastVisit) {
                System.out.print(node.val);
                stack.pop();
                lastVisit = node;
                node = null;
            } else {
                node = node.right;
            }
        }
    }

    public static void main(String[] args) {
        TreeNode root = new TreeNode(1);
        TreeNode two = new TreeNode(2);
        root.left = two;
        TreeNode three = new TreeNode(3);
        root.right = three;
        TreeNode four = new TreeNode(4);
        two.left = four;
        TreeNode five = new TreeNode(5);
        three.right = five;
        TreeNode six = new TreeNode(6);
        four.right = six;
        TreeNode seven = new TreeNode(7);
        six.left = seven;
        TreeNode eight = new TreeNode(8);
        six.right = eight;

        IteratorTree it = new IteratorTree();
        it.preOrder(root);
        System.out.println();
        it.inOrder(root);
        System.out.println();
        it.postOrder(root);
        System.out.println();
    }
}
