package com.dawson.jonat.stockers.UserStock;

public class StockInformation {

    private String tickerSymbol;
    private String quantity;
    private String purchasePrice;

    /**
     * Constructor that initializes the tickerSymbol, quantity and the purchase price of the
     * user's stock
     *
     * @param tickerSymbol
     * @param quantity
     * @param purchasePrice
     */
    public StockInformation(String tickerSymbol, String quantity, String purchasePrice) {
        this.tickerSymbol = tickerSymbol;
        this.quantity = quantity;
        this.purchasePrice = purchasePrice;
    }


    public String getTickerSymbol() {
        return tickerSymbol;
    }

    public void setTickerSymbol(String tickerSymbol) {
        this.tickerSymbol = tickerSymbol;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getPurchasePrice() {
        return purchasePrice;
    }

    public void setPurchasePrice(String purchasePrice) {
        this.purchasePrice = purchasePrice;
    }
}
