package com.dawson.jonat.stockers.StockQuote;

public class Ticker {
        private String symbol;
        private int image;

        Ticker(String s, int image) {
            symbol = s;
            this.image = image;
        }

        public String getSymbol(){
            return this.symbol;
        }

        public int getImageSource(){
            return this.image;
        }
    }

