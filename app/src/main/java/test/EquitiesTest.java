package test;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class EquitiesTest {

    private List<Fundsinfo> fundsinfos;
    private List<OperateInfo> operateInfos;

    public static void main(String[] args) {

        new EquitiesTest().test();
    }

    /**
     * 通过当前持仓
     * 成本  以及盈亏状况计算出下一次卖出的价格
     */

    private void test() {
        prepareFunds();
        prepareBuyIng();
        calculateSelling();

    }

    private void calculateSelling() {
        // 得到最后一笔交易后的 盈利状况
        HoldPosition holdPosition = new HoldPosition();
        for (int i = 0; i < operateInfos.size(); i++) {
            OperateInfo operateInfo = operateInfos.get(i);

            float holdQuotient = getHoldQuotient(operateInfo, holdPosition);
            float holdCost = getHoldCost(operateInfo, holdPosition);
            float holdMarket = getHoldMarket(operateInfo, holdPosition);
            float holdProfit = HoldProfit(operateInfo, holdPosition);
            float sereviceCharge = getSereviceCharge(operateInfo, holdPosition);
            float cashProfit = getCashProfit(operateInfo, holdPosition);
            float totalProfit = getTotalProfit(operateInfo, holdPosition);
            float profitPrice = getProfitPrice(operateInfo, holdPosition, 0.3f, 0.05f);


            holdPosition.setIndex(i);
            holdPosition.setDate(operateInfo.getDate());
            holdPosition.setHoldQuotient(holdQuotient);
            holdPosition.setHoldCost(holdCost);

            holdPosition.setHoldMarket(holdMarket);
            holdPosition.setHoldProfit(holdProfit);
            holdPosition.setCashProfit(holdProfit);
            holdPosition.setSereviceCharge(sereviceCharge);
            holdPosition.setCashProfit(cashProfit);
            holdPosition.setTotalProfit(totalProfit);
            holdPosition.setProfitPrice(profitPrice);
            System.err.println(holdPosition.toString());
        }
    }

    /**
     * @param operateInfo
     * @param holdPosition
     * @param per          止盈百分比
     * @param pp           止损百分比
     * @return
     */
    //计算止盈或止损价格 止盈价格 = （持仓成本 + 持仓盈利 - （持仓盈利*回撤百分比））/持仓数量
    // 止损价格  =    成本价 -     成本价 *止损百分比
    private float getProfitPrice(OperateInfo operateInfo, HoldPosition holdPosition, float per, float pp) {
        float v = HoldProfit(operateInfo, holdPosition);
        float holdCost = getHoldCost(operateInfo, holdPosition);
        float holdQuotient = getHoldQuotient(operateInfo, holdPosition);
        if (v > 0) {
            return per(holdCost / holdQuotient, v / holdQuotient, per);
//            return (holdCost + v - (v * per)) / holdQuotient ;
        } else {
    return  pp( holdCost / holdQuotient,pp) ;
//            return holdCost / holdQuotient * (1 - pp);
        }
    }

    private float pp(float v, float pp) {
        return v*(1-pp);
    }

    /**
     * @param v   成本价
     * @param v1  每份盈利
     * @param per 回撤百分比
     * @return
     */
    private float per(float v, float v1, float per) {
        return v + (v1 * (1 - per));
    }

    private float getTotalProfit(OperateInfo operateInfo, HoldPosition holdPosition) {
        if (operateInfo.getBuyIngFigure() < 0)
            return holdPosition.getTotalProfit() + getCashProfit(operateInfo, holdPosition);
        else return holdPosition.getTotalProfit();
    }

    // 兑现盈利 = （持仓成本- 当前市值）/份额 * 兑现份额
    private float getCashProfit(OperateInfo operateInfo, HoldPosition holdPosition) {
        if (operateInfo.getBuyIngFigure() < 0) {
            float holdQuotient = getHoldQuotient(operateInfo, holdPosition);
            if (holdQuotient != 0)
                return (getHoldCost(operateInfo, holdPosition) - getHoldMarket(operateInfo, holdPosition)) / holdQuotient * operateInfo.getBuyIngFigure();
            else
                return (getHoldCost(operateInfo, holdPosition) - getHoldMarket(operateInfo, holdPosition)) / holdPosition.getHoldQuotient() * operateInfo.getBuyIngFigure();
        }
        return 0;
    }

    // 手续费 = 已付手续费 - 当前操作付出的手续费 
    private float getSereviceCharge(OperateInfo operateInfo, HoldPosition holdPosition) {

        float serviceCharge = operateInfo.getServiceCharge();
        float sereviceCharge = holdPosition.getSereviceCharge();
        return serviceCharge + sereviceCharge;
    }

    // 持仓份额 = 已有份额 + 仓位操作份额
    private float getHoldQuotient(OperateInfo operateInfo, HoldPosition holdPosition) {
        return holdPosition.getHoldQuotient() + operateInfo.getBuyIngFigure();
    }

    // 持仓成本 = 当前的成本 +  仓位操作后的成本( 份额 * 价格 + 手续费)    
    private float getHoldCost(OperateInfo operateInfo, HoldPosition holdPosition) {
        return holdPosition.getHoldCost() + operateInfo.getBuyIngFigure() * operateInfo.getBuyIngPrice() + operateInfo.getServiceCharge();
    }

    // 持仓盈利 = （当前持仓成本价 - 当前市场价格 ） * 持仓份额
    private float HoldProfit(OperateInfo operateInfo, HoldPosition holdPosition) {
        float holdQuotient = getHoldQuotient(operateInfo, holdPosition);
        return (operateInfo.getBuyIngPrice() * holdQuotient - getHoldCost(operateInfo, holdPosition));
    }

    // 持仓市值 = 当前持仓份额 * 当前市场价格
    private float getHoldMarket(OperateInfo operateInfo, HoldPosition holdPosition) {
        float holdQuotient = getHoldQuotient(operateInfo, holdPosition);
        float buyIngPrice = operateInfo.getBuyIngPrice();
        return holdQuotient * buyIngPrice;
    }

    private void prepareBuyIng() {
        operateInfos = new ArrayList<>();
        OperateInfo operateInfo = new OperateInfo("1", 0, 100f, 5f, 10f);
        operateInfos.add(operateInfo);
        operateInfo = new OperateInfo("2", 0, 100f, 5f, 11f);
        operateInfos.add(operateInfo);
        operateInfo = new OperateInfo("3", 0, 100f, 5f, 11f);
        operateInfos.add(operateInfo);
        operateInfo = new OperateInfo("3", 0, -100f, 5f, 12f);
        operateInfos.add(operateInfo);
        operateInfo = new OperateInfo("3", 0, -100f, 5f, 10f);
        operateInfos.add(operateInfo);

    }

    private void prepareFunds() {
        fundsinfos = new ArrayList<>();
        Fundsinfo fundInfo = new Fundsinfo("1", "", "", "", "10", "", "");
        fundsinfos.add(fundInfo);
        fundInfo = new Fundsinfo("2", "", "", "", "11", "", "");
        fundsinfos.add(fundInfo);

        fundInfo = new Fundsinfo("3", "", "", "", "12", "", "");
        fundsinfos.add(fundInfo);

        fundInfo = new Fundsinfo("4", "", "", "", "13", "", "");
        fundsinfos.add(fundInfo);

        fundInfo = new Fundsinfo("5", "", "", "", "14", "", "");
        fundsinfos.add(fundInfo);

        fundInfo = new Fundsinfo("6", "", "", "", "15", "", "");
        fundsinfos.add(fundInfo);

        fundInfo = new Fundsinfo("7", "", "", "", "16", "", "");
        fundsinfos.add(fundInfo);
        fundInfo = new Fundsinfo("8", "", "", "", "17", "", "");
        fundsinfos.add(fundInfo);
        fundInfo = new Fundsinfo("9", "", "", "", "18", "", "");

        // 下跌
        fundsinfos.add(fundInfo);
        fundInfo = new Fundsinfo("10", "", "", "", "17", "", "");
        fundsinfos.add(fundInfo);
        fundInfo = new Fundsinfo("11", "", "", "", "16", "", "");
        fundsinfos.add(fundInfo);
        fundInfo = new Fundsinfo("12", "", "", "", "15", "", "");
        fundsinfos.add(fundInfo);
        fundInfo = new Fundsinfo("13", "", "", "", "14", "", "");
        fundsinfos.add(fundInfo);
        fundInfo = new Fundsinfo("14", "", "", "", "13", "", "");
        fundsinfos.add(fundInfo);
        fundInfo = new Fundsinfo("15", "", "", "", "12", "", "");
        fundsinfos.add(fundInfo);

    }

}
