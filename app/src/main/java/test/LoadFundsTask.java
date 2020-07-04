package test;

import java.util.ArrayList;
import java.util.List;

import static test.Fundscacul.cciFormula;
import static test.Fundscacul.getCCIWear;
import static test.Fundscacul.loadFundsInfo;

public   class LoadFundsTask implements Runnable {
    fundInfo fundInfo;
    String startTime;
    String endTime;
    int tryCount = 3;
    LoadFundsCall loadFundsCall;


    public LoadFundsTask(fundInfo fundInfo, String startTime, String endTime, Fundscacul.MyloadFundsCall loadFundsCall) {

        this.fundInfo = fundInfo;
        this.startTime = startTime;
        this.endTime = endTime;
        this.loadFundsCall = loadFundsCall;
    }

    @Override
    public void run() {
        FundsData fundsData = null;
        while (fundsData == null && tryCount > 0) {
            fundsData = loadFundsInfo(fundInfo.code, fundInfo.c, startTime, endTime);
            if (fundsData == null) {
                continue;
            }
            fundsData.setFundsinfoFloatMap(cciFormula(fundsData, 14));
            fundsData.setCode(fundInfo.code);
            fundsData.setName(fundInfo.name);

            getUnderHunder(fundsData, -100f);
            getCCIWear(fundsData, -100f);
        }
        if (fundsData == null) {
            loadFundsCall.onFailed();
        } else {
            loadFundsCall.onSuccess(fundsData);
        }

    }

    private void getUnderHunder(FundsData fundsData, float v) {

        List<Fundsinfo> fundsinfos = fundsData.getFundsinfos();
        List<Fundsinfo> underHunder = new ArrayList<>();
        for (Fundsinfo fundsinfo : fundsinfos) {
            if (fundsData.getFundsinfoFloatMap().get(fundsinfo) <= v) {
                underHunder.add(fundsinfo);
            }
        }
        fundsData.setUnderHunder(underHunder);
    }


}