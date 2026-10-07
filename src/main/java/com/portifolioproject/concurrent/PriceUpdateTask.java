package com.portifolioproject.concurrent;
import java.util.concurrent.Callable;
import com.portifolioproject.model.Stock;

public class PriceUpdateTask implements Callable<Double> 
{
    private Stock stock;
    public PriceUpdateTask(Stock stock)
    {
        this.stock = stock;
    }
    @Override
    public Double call()
    {
        double oldPrice = stock.getCurrentPrice();
        System.out.println("Updating Stock: " + stock.getAssetName()
                + " - Thread: " + Thread.currentThread().getName());
        double newPrice = oldPrice + 10;
        stock.setCurrentPrice(newPrice);
        System.out.println("Old Price: " + oldPrice);
        System.out.println("New Price: " + newPrice);
        System.out.println("Stock update completed for "+ stock.getAssetName());
        return newPrice;
    }
}