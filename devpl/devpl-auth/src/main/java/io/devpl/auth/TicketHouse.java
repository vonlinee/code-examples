package io.devpl.auth;

public class TicketHouse implements Runnable {
    int fiveAmount = 2;
    int tenAmount = 0;
    int twentyAmount = 0;

    @Override
    public void run() {
        if (Thread.currentThread().getName().equals("小明")) {
            saleTicket(20);
        } else if (Thread.currentThread().getName().equals("小红")) {
            saleTicket(5);
        }
    }

    private synchronized void saleTicket(int money) {
        String name = Thread.currentThread().getName();
        System.out.println(name + "想买票");
        if (money == 5) { // 小红
            fiveAmount = fiveAmount + 1;
            System.out.println("售票员给" + name + "票," + name + "的钱刚好，不用等售票员找零");
        } else if (money == 20) {  // 小明
            while (fiveAmount < 3) {
                try {
                    // 票价5元一张
                    System.out.println(this);    // TicketHouse@7d23e299
                    System.out.println(name + "没有零钱,等待售票员找零,先让后面的人买票");
                    wait();
                    System.out.println("售票员找到零钱，" + name + "继续买票");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            fiveAmount = fiveAmount - 3;
            twentyAmount = twentyAmount + 1;
            System.out.println("售票员给" + name + "票," + name + "给20，找回15元");
        }
        notifyAll();  // 唤醒等待的所有线程,从上次中断处继续执行（wait处）
    }

    public static void main(String[] args) {
        TicketHouse ticketHouse = new TicketHouse();


        new Thread(ticketHouse, "小红").start();
        new Thread(ticketHouse, "小明").start();

    }
}