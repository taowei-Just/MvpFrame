package test;

/**
 * 操作
 */
public class OperateInfo {
    // 日期
    String date;
    String time;
    // 买入/卖出 金额
    float buyIngAmount;
    //  操作 份额
    float buyIngFigure;
    // 操作 手续费
    float serviceCharge;
    // 操作价格
    float buyIngPrice;

    public OperateInfo(String date, float buyIngAmount, float buyIngFigure, float serviceCharge, float buyIngPrice) {
        this.date = date;
        this.buyIngAmount = buyIngAmount;
        this.buyIngFigure = buyIngFigure;
        this.serviceCharge = serviceCharge;
        this.buyIngPrice = buyIngPrice;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public float getBuyIngAmount() {
        return buyIngAmount;
    }

    public void setBuyIngAmount(float buyIngAmount) {
        this.buyIngAmount = buyIngAmount;
    }

    public float getBuyIngFigure() {
        return buyIngFigure;
    }

    public void setBuyIngFigure(float buyIngFigure) {
        this.buyIngFigure = buyIngFigure;
    }

    public float getServiceCharge() {
        return serviceCharge;
    }

    public void setServiceCharge(float serviceCharge) {
        this.serviceCharge = serviceCharge;
    }

    public float getBuyIngPrice() {
        return buyIngPrice;
    }

    public void setBuyIngPrice(float buyIngPrice) {
        this.buyIngPrice = buyIngPrice;
    }
}
