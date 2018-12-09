package com.dawson.jonat.stockers.Entity;

import com.dawson.jonat.stockers.R;

import java.io.Serializable;

public class Ticker implements Serializable{
        private String symbol;
        private int delete, search;

        public Ticker(String s) {
            symbol = s;
            this.delete = R.drawable.trash;
            this.search = R.drawable.search;
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

