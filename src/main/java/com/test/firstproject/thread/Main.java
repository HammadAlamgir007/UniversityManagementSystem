//package com.test.firstproject.thread;
//
//class First implements Runnable {
//    @Override
//    public void run(){
//        System.out.println("first woker thread ");
//    }
//}
//class Second implements Runnable {
//    @Override
//    public void run(){
//
//        System.out.println("Second Worker thread");
//    }
//}
//public class Main {
//
//    public static void main(String[] args) {
//        First t = new First();
//        Thread thread = new Thread(t);
//        thread.start();
//
//        for (int i = 1; i <= 3; i++) {
//            System.out.println(i);
//        }
//
//        Second t1 = new Second();
//        Thread thread1= new Thread(t1);
//        thread1.start();
//
//
//    }
//
//}