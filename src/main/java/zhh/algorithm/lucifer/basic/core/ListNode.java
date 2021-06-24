package zhh.algorithm.lucifer.basic.core;

/**
 * @author zhanghao
 * @date 2021/6/25 上午7:09
 * @desc Definition for singly-linked list.
 */
public class ListNode {
    public int val;
    public ListNode next;
    public ListNode() {}
    public ListNode(int val) { this.val = val; }
    public ListNode(int val, ListNode next) { this.val = val; this.next = next; }
}