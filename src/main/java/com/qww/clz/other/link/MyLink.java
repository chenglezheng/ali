package com.qww.clz.other.link;

import org.junit.Test;

/**
 * @Author chenglezheng
 * @Date 2020/6/29 14:18
 */
public class MyLink {

    Node head = null; //头节点


    class Node {

        Node next = null;  //节点的引用，指向下一个节点

        int data; //节点对象，即内容

        public Node(int data) {
            this.data = data;
        }
    }

    /**
     * 向链表中插入数据
     *
     * @param d
     */
    public void addNode(int d) {
        Node newNode = new Node(d); //实例化一个节点
        if (head == null) {
            head = newNode;
            return;
        }
        Node tmp = head;
        while (tmp.next != null) {
            tmp = tmp.next;
        }
        tmp.next = newNode;
    }

    /**
     * 删除index节点
     *
     * @param index
     * @return
     */
    public boolean deleteNode(int index) {

        if (index < 1 || index > length()) {
            return false;
        }

        if (index == 1) {
            head = head.next;
            return true;
        }

        int i = 1;
        Node preNode = head;
        Node curNode = preNode.next;
        while (curNode != null) {
            if (i == index) {
                preNode.next = curNode.next;
                return true;
            }
            preNode = curNode;
            curNode = curNode.next;
            i++;
        }
        return false;
    }

    /**
     * 返回节点长度
     *
     * @return
     */
    public int length() {
        int length = 0;
        Node tmp = head;
        while (tmp != null) {
            length++;
            tmp = tmp.next;
        }
        return length;
    }

    /**
     * 不知道头指针情况下删除指定节点
     *
     * @param node
     * @return
     */
    public boolean deleteNodeByNotNodeAddress(Node node) {
        if (node == null || node.next == null) {
            return false;
        }
        //将当前节点的数据替换成下个节点的数据
        int tmp = node.data;
        node.data = node.next.data;
        node.next.data = tmp;
        //然后将当前节点的下个节点指向下下个节点，完成当前节点的删除
        //物理上是删除了当前节点的下一个节点，但是逻辑上是删除了当前节点
        node.next = node.next.next;
        System.out.println("delete successful!");
        return false;
    }

    /**
     * 输出链表数据
     */
    public void printList() {
        Node tmp = head;
        while (tmp != null) {
            System.out.println(tmp.data);
            tmp = tmp.next;
        }
    }





    /**
     * 链表反转
     * @param head
     * @return
     */
    public Node reserveInteratively(Node head) {
        Node pReservedHead = head;
        Node pNode = head;
        Node pPrev = null;
        while (pNode != null) {
            Node pNext = pNode.next;
            if (pNext == null) {
                pReservedHead = pNode;
            }
            pNode.next=pPrev;
            pPrev=pNode;
            pNode=pNext;
        }
        this.head=pReservedHead;
        return this.head;
    }


    /**
     * 排序
     * @return
     */
    public Node orderList(){
        Node nextNode = null;
        int tmp = 0;
        Node curNode = head;
        while (curNode.next != null) {
            nextNode = curNode.next;
            while (nextNode != null) {
                if (curNode.data > nextNode.data) {
                    tmp = curNode.data;
                    curNode.data = nextNode.data;
                    nextNode.data = tmp;
                }
                nextNode = nextNode.next;
            }
            curNode = curNode.next;
        }
        return head;
    }


    /**
     * 判断链表是否有环，单向链表有环时，尾节点相同
     *
     * @param head
     * @return
     */
    public boolean IsLoop(Node head) {
        Node fast = head, slow = head;
        if (fast == null) {
            return false;
        }
        while (fast != null && fast.next != null) {
            fast = fast.next.next;
            slow = slow.next;
            if (fast == slow) {
                System.out.println("该链表有环");
                return true;
            }
        }
        return !(fast == null || fast.next == null);
    }

    /**
     * 找出链表环的入口
     *
     * @param head
     * @return
     */
    public Node FindLoopPort(Node head) {
        Node fast = head, slow = head;
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
            if (slow == fast)
                break;
        }
        if (fast == null || fast.next == null)
            return null;
        slow = head;
        while (slow != fast) {
            slow = slow.next;
            fast = fast.next;
        }
        return slow;
    }


    private static int aa=1;


    @Test
    public void testNode() {
        MyLink link = new MyLink();
        link.addNode(1);
        link.addNode(2);
        link.addNode(3);
        link.addNode(4);
        link.deleteNode(2);
        link.printList();
    }
}
