package com.dawson.jonat.stockers.Entity;

public class Ticker {
        private String symbol;
        private int delete, search;

        public Ticker(String s, int delete, int search) {
            symbol = s;
            this.delete = delete;
            this.search = search;
        }

        public String getSymbol(){
            return this.symbol;
        }
        public int getImageSourceDelete(){
            return this.delete;
        }
        public int getImageSourceSearch(){
        return this.search;
    }

}

