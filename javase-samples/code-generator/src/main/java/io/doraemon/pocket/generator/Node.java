package io.doraemon.pocket.generator;

public class Node<T extends Comparable<T>> implements Comparable<Node<T>> {
    private Node<T> next;
    private Node<T> prex;
    private T data;

    public Node(T data) {
        this.data = data;
    }

    public Node<T> getNext() {
        return next;
    }

    public void setNext(Node<T> next) {
        this.next = next;
    }

    public Node<T> getPrex() {
        return prex;
    }

    public void setPrex(Node<T> prex) {
        this.prex = prex;
    }

    @Override
    public int compareTo(Node<T> o) {
        return data.compareTo(o.data);
    }
}
