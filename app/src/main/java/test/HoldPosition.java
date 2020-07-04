package test;

/**
 * 持仓信息
 */
public class HoldPosition {
    int index;
    // 时间
    String Date;
    //持仓份额
    float holdQuotient;
    //当前持仓成本
    float holdCost;
    //手续费
    float sereviceCharge;
    //持仓盈利
    float holdProfit;
    //持仓价值
    float holdMarket;
    //兑现盈利
    float cashProfit;
    //总兑现盈利
    float totalProfit;
    // 止盈/止损 价格
    float profitPrice;

    public float getProfitPrice() {
        return profitPrice;
    }

    public void setProfitPrice(float profitPrice) {
        this.profitPrice = profitPrice;
    }

    public float getTotalProfit() {
        return totalProfit;
    }

    public void setTotalProfit(float totalProfit) {
        this.totalProfit = totalProfit;
    }

    public float getCashProfit() {
        return cashProfit;
    }

    public void setCashProfit(float cashProfit) {
        this.cashProfit = cashProfit;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public float getHoldQuotient() {
        return holdQuotient;
    }

    public void setHoldQuotient(float holdQuotient) {
        this.holdQuotient = holdQuotient;
    }

    public float getHoldCost() {
        return holdCost;
    }

    public void setHoldCost(float holdCost) {
        this.holdCost = holdCost;
    }

    public float getSereviceCharge() {
        return sereviceCharge;
    }

    public void setSereviceCharge(float sereviceCharge) {
        this.sereviceCharge = sereviceCharge;
    }

    public float getHoldProfit() {
        return holdProfit;
    }

    public void setHoldProfit(float holdProfit) {
        this.holdProfit = holdProfit;
    }

    public float getHoldMarket() {
        return holdMarket;
    }

    public void setHoldMarket(float holdMarket) {
        this.holdMarket = holdMarket;
    }

    @Override
    public String toString() {
        return "HoldPosition{" +
                "index=" + index +
                ", Date='" + Date + '\'' +
                ", holdQuotient=" + holdQuotient +
                ", holdCost=" + holdCost +
                ", sereviceCharge=" + sereviceCharge +
                ", holdProfit=" + holdProfit +
                ", holdMarket=" + holdMarket +
                ", cashProfit=" + cashProfit +
                ", totalProfit=" + totalProfit +
                ", profitPrice=" + profitPrice +
                '}';
    }
}