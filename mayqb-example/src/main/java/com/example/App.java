package com.example;

public class App {

    public static void main(String[] args) {

        selectFrom()
                .where()
                .limit()
                .build()
                .map()
                .list()
                .execute()
    }
}
