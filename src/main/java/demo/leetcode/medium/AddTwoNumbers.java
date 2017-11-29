package demo.leetcode.medium;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * You are given two non-empty linked lists representing two non-negative integers.
 * The digits are stored in reverse order and each of nodes contain a single digit.
 * Add the two numbers and return it as a linked list.
 *
 * You may assume the two numbers do not contain any leading zero, except the number 0 itself.
 *
 * Input: (2 -> 4 -> 3) + (5 -> 6 -> 4)
 * Output: 7 -> 0 -> 8
 */
public class AddTwoNumbers {

    @Test
    public void test() {
        // 从最大位开始
        ListNode l1 = new ListNode(3).setNext(new ListNode(4).setNext(new ListNode(2)));
        ListNode l2 = new ListNode(4).setNext(new ListNode(6).setNext(new ListNode(5)));
        System.out.println(l1);
        System.out.println(l2);
        System.out.println(addTwoNumbers(l1, l2));
    }

    @Test
    public void test1() {
        // 从最小位开始
        ListNode l1 = new ListNode(2).setNext(new ListNode(4).setNext(new ListNode(3)));
        ListNode l2 = new ListNode(5).setNext(new ListNode(6).setNext(new ListNode(4)));
        System.out.println(l1);
        System.out.println(l2);
        System.out.println(addTwoNumbers1(l1, l2));
    }

    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        List<Integer> li1 = l1.toList();
        List<Integer> li2 = l2.toList();
        int li1Size = li1.size();
        int li2Size = li2.size();
        // 循环次数为长的那个
        int size = li1Size >= li2Size ? li1Size : li2Size;
        int up = 0; // 如果本节点有进位
        ListNode result = null;
        for (int i = 0; i < size; i++) {
            Integer i1 = li1Size - 1 - i;
            Integer i2 = li2Size - 1 - i;
            // 暂时保存两节点和
            up = (i1 < 0 ? 0 : li1.get(i1)) + (i2 < 0 ? 0 : li2.get(i2)) + up;
            result = new ListNode(up % 10).setNext(result);
            up = up / 10;
        }
        return result;
    }

    public ListNode addTwoNumbers1(ListNode l1, ListNode l2) {
        int up = 0; // 如果本节点有进位
        ListNode result = null, tmp = null;
        for (ListNode node1 = l1, node2 = l2; node1 != null || node2 != null;) {
            up = (node1 != null ? node1.getVal() : 0) + (node2 != null ? node2.getVal() : 0) + up;
            if (result == null) { result = new ListNode(up % 10); tmp = result; }
            else { tmp.setNext(new ListNode(up % 10)); tmp = tmp.getNext(); }
            up = up / 10;
            node1 = node1 != null ? node1.getNext() : null;
            node2 = node2 != null ? node2.getNext() : null;
        }
        return result;
    }

    static class ListNode {
        int val;
        ListNode next;

        ListNode(int x) {
            val = x;
        }

        public int getVal() {
            return val;
        }

        public ListNode setVal(int val) {
            this.val = val;
            return this;
        }

        public ListNode getNext() {
            return next;
        }

        public ListNode setNext(ListNode next) {
            this.next = next;
            return this;
        }

        @Override
        public String toString() {
            StringBuilder builder = new StringBuilder().append(val);
            ListNode next = this.next;
            while (next != null) {
                builder.append(next.getVal());
                next = next.getNext();
            }
            return builder.toString();
        }

        public List<Integer> toList() {
            List<Integer> list = new ArrayList<>();
            list.add(val);
            ListNode next = this.next;
            while (next != null) {
                list.add(next.getVal());
                next = next.getNext();
            }
            return list;
        }

    }

}
