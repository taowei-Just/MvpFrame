package test;

import com.tao.mvpframe.test.http.api.FundsApi;
import com.tao.mvpbaselibrary.retrofitrx.RetrofitFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 策略 上穿-100 后都有回升空间，可以在上穿后如果下穿 再等下一次上穿 再次买入
 */

public class Fundscacul {
    static String yahooUrl = "https://query1.finance.yahoo.com/";
    static String SZ = ".SZ";
    static String SS = ".SS";
    private String startTime = "979206622";
    private final ExecutorService threadPool;
    String[] ssL = new String[]{"510300", "510050", "512510", "513600", "513100", "512010", "512290", "512660", "512170"};
    String[] szL = new String[]{"159997", "159811", "159943", "159901", "159959", "159928", "159931", "159985", "159922", "159806", "159987", "159954", "159941", "159929", "162411"};


    public Fundscacul() {
        threadPool = Executors.newFixedThreadPool(1);
    }

    public static void main(String[] args) {
        new Fundscacul().start();
    }

    //　第一种计算过程如下：
//            　　CCI（N日）=（TP－MA）÷MD÷0.015
//            　　其中，TP=（最高价+最低价+收盘价）÷3
//            　　MA=近N日收盘价的累计之和÷N
//　　MD=近N日（MA－收盘价）的绝对值累计之和÷N
//　　0.015为计算系数，N为计算周期
    private void start() {
//        FundsData fundsData = loadFundsInfo("510300", SS, startTime, System.currentTimeMillis() / 1000 + "");
//
//        fundsData.setFundsinfoFloatMap(cciFormula(fundsData, 14));
//        for (Fundsinfo fundsinfo : fundsData.getFundsinfos()) {
//            System.err.println(fundsinfo.toString() + " CCI：" + fundsData.getFundsinfoFloatMap().get(fundsinfo));
//        }
//        matchRule(fundsData);

//        List<fundInfo> codeList = readStr(str);
//        MyloadFundsCall loadFundsCall = new MyloadFundsCall();
//        for (fundInfo fundInfo : codeList) {
//            threadPool.submit(new LoadFundsTask(fundInfo, startTime, System.currentTimeMillis() / 1000 + "", loadFundsCall));
//            return;
//        }
        
        
    }

    private List<fundInfo> readStr(String str) {
        List<fundInfo> fundInfos = new ArrayList<>();
        BufferedReader bufferedReader = new BufferedReader(new StringReader(str));
        String line;
        try {
            while ((line = bufferedReader.readLine()) != null) {
                String[] split = line.split("\\|");
                fundInfo fundInfo = new fundInfo();
                fundInfo.code = split[0];
                fundInfo.name = split[1];
                if (fundInfo.code.startsWith("5")) {
                    fundInfo.c = SS;
                } else {
                    fundInfo.c = SZ;
                }
                fundInfos.add(fundInfo);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        return fundInfos;
    }


    /**
     * 匹配cci 规则 在cci 从-100 下方上穿时投入  从 +100 上下穿时卖出
     *
     * @param fundsData
     */
    public static void matchRule(FundsData fundsData) {
        // 1.获cci取上穿-100
        // 2.获取cci穿-100后继续上穿0轴 
        // 3.获取cci穿-100后继续上穿+100轴 
        // 3.获取cci穿-100后继续下穿+100轴 

        List<Fundsinfo> cciWear = getCCIWear(fundsData, -100f);

//        for (int i = 0; i < cciWear.size(); i++) {
////            System.err.println( "CCI上传-100 " + cciWear.get(i) +" CCI:" +fundsData.getFundsinfoFloatMap().get(cciWear.get(i)));
//
//            Fundsinfo fundsinfo = fundsData.getFundsinfos().get( fundsData.getFundsinfos().size()-1);
//
//            profit(cciWear.get(i).getDate()+"至今盈利" , cciWear.get(i),fundsinfo,fundsData);
//        }

        getCCIWearzero(fundsData);
//        System.err.println("上传 -100 后继续上穿 0  数量 ：" + fundsData.getWearZeroInfos().size() + " 比例：" + fundsData.getWearZeroInfos().size() * 100 / cciWear.size() + "%");
//        System.err.println("上传  0 后继续上穿 +100  数量 ：" + fundsData.getWearPlusInfos().size() + " 比例：" + fundsData.getWearPlusInfos().size() * 100 / cciWear.size() + "%");
//        System.err.println("上传  100 后下穿 100  数量 ：" + fundsData.getUnderPlusInfos().size() + " 比例：" + fundsData.getUnderPlusInfos().size() * 100 / cciWear.size() + "%");

    }

    /**
     * 上穿-100 后再上传0 的几率
     * 上传-100 后再上传 100 的几率
     * 上穿100 后再
     *
     * @param fundsData
     * @return
     */

    private static FundsData getCCIWearzero(FundsData fundsData) {
        List<Fundsinfo> wearInfos = fundsData.getWearInfos();

        for (int i = 0; i < wearInfos.size(); i++) {
            Fundsinfo fundsinfo = wearInfos.get(i);
            Fundsinfo matchHundre = matchHundre(fundsinfo, fundsData);
            if (matchHundre != null) {
                matchHundre = underHunderd(matchHundre, fundsData);

                if (matchHundre != null)
                    profit(" 从上穿-100 到上穿 100 之后下穿100", fundsinfo, matchHundre, fundsData);
            }
        }
        return fundsData;
    }

    private static Fundsinfo matchHundre(Fundsinfo fundsinfo, FundsData fundsData) {
        Fundsinfo getzerro = getzerro(fundsinfo, fundsData);
        if (getzerro == null) {
            Fundsinfo fundsinfo1 = under_Hunderd(fundsinfo, fundsData);
            if (fundsinfo1 == null)
                return null;
            return matchHundre(fundsinfo1, fundsData);

        } else {
            Fundsinfo fundsinfo1 = getHundred(getzerro, fundsData);
            return fundsinfo1;
        }
    }

    /**
     * 下穿-100
     *
     * @param wearInfo
     * @param fundsData
     * @return
     */

    private static Fundsinfo under_Hunderd(Fundsinfo wearInfo, FundsData fundsData) {

        if (wearInfo == null)
            return null;
        List<Fundsinfo> fundsinfos = fundsData.getFundsinfos();
        int i = fundsinfos.indexOf(wearInfo);
        for (int i1 = i + 1; i1 < fundsinfos.size(); i1++) {
            Fundsinfo fundsinfo = fundsinfos.get(i1);
            Float cci = fundsData.getFundsinfoFloatMap().get(fundsinfo);
            if (cci < -100) {
                return fundsinfo;
            }
        }
        return null;
    }

    static float total1;
    static float total2;

    /**
     * 盈利计算
     *
     * @param s
     * @param wearInfo
     * @param underZeroinfo
     * @param fundsData
     */
    private static void profit(String s, Fundsinfo wearInfo, Fundsinfo underZeroinfo, FundsData fundsData) {


        float minProfit = (Float.parseFloat(underZeroinfo.getLow()) - Float.parseFloat(wearInfo.getHigh())) / Float.parseFloat(wearInfo.getHigh()) * 100;
        float maxProfit = (Float.parseFloat(underZeroinfo.getHigh()) - Float.parseFloat(wearInfo.getLow())) / Float.parseFloat(wearInfo.getLow()) * 100;
        float closeProfit = (Float.parseFloat(underZeroinfo.getClose()) - Float.parseFloat(wearInfo.getClose())) / Float.parseFloat(wearInfo.getClose()) * 100;

        if (closeProfit < 0) {
            System.err.println("----------------------------------------------------------------------------------------");
            System.err.println("" + s + " 最低盈利:" + minProfit + "% 最大盈利:" + maxProfit + "% 收盘价盈利:" + closeProfit + "%  \n"
                    + wearInfo + " CCI:" + fundsData.getFundsinfoFloatMap().get(wearInfo) + "\n"
                    + underZeroinfo + " CCI:" + fundsData.getFundsinfoFloatMap().get(underZeroinfo));
            total2 += (closeProfit * 10000 / 100 + 10000);
            total1 += 10000;
            System.err.println("----------------------------------------------------------------------------------------");
            System.err.println(" 总投入 ： " + total1 + " 总盈利：" + (total2 - total1) + " 盈利比：" + ((total2 - total1) / total1 * 100) + "%");
        }
    }

    /**
     * 下穿0
     *
     * @param zeroinfo
     * @param fundsData
     * @return
     */
    private static Fundsinfo underZero(Fundsinfo zeroinfo, FundsData fundsData) {
        if (zeroinfo == null)
            return null;
        List<Fundsinfo> fundsinfos = fundsData.getFundsinfos();
        int i = fundsinfos.indexOf(zeroinfo);
        for (int i1 = i; i1 < fundsinfos.size(); i1++) {
            Fundsinfo fundsinfo = fundsinfos.get(i1);
            Float cci = fundsData.getFundsinfoFloatMap().get(fundsinfo);
            if (cci < 0) {
                return fundsinfo;
            } else if (cci >= 100) {
                return null;
            }
        }

        return null;
    }

    private static Fundsinfo underHunderd(Fundsinfo hundredInfo, FundsData fundsData) {
        if (hundredInfo == null)
            return null;
        List<Fundsinfo> fundsinfos = fundsData.getFundsinfos();
        int i = fundsinfos.indexOf(hundredInfo);
        for (int i1 = i + 1; i1 < fundsinfos.size(); i1++) {
            Fundsinfo fundsinfo = fundsinfos.get(i1);
            Float cci = fundsData.getFundsinfoFloatMap().get(fundsinfo);
            if (cci < 100) {
                return fundsinfo;
            }
        }

        return null;
    }

    private static Fundsinfo getHundred(Fundsinfo zeroinfo, FundsData fundsData) {
        if (zeroinfo == null)
            return null;
        List<Fundsinfo> fundsinfos = fundsData.getFundsinfos();

        int i = fundsinfos.indexOf(zeroinfo);
        for (int i1 = i + 1; i1 < fundsinfos.size(); i1++) {
            Fundsinfo fundsinfo = fundsinfos.get(i1);
            Float cci = fundsData.getFundsinfoFloatMap().get(fundsinfo);
            if (cci > 100) {
                return fundsinfo;
            }
        }

        return null;

    }

    /**
     * 上穿0
     *
     * @param wearInfo
     * @param fundsData
     * @return
     */
    private static Fundsinfo getzerro(Fundsinfo wearInfo, FundsData fundsData) {
        if (wearInfo == null)
            return null;
        List<Fundsinfo> fundsinfos = fundsData.getFundsinfos();

        int i = fundsinfos.indexOf(wearInfo);
        for (int i1 = i; i1 < fundsinfos.size(); i1++) {
            Fundsinfo fundsinfo = fundsinfos.get(i1);
            Float cci = fundsData.getFundsinfoFloatMap().get(fundsinfo);
            if (cci > 0) {
                return fundsinfo;
            } else if (cci < -100) {
                return null;
            }
        }

        return null;
    }

    public static List<Fundsinfo> getCCIWear(FundsData fundsData, float v) {
        List<Fundsinfo> weareInfos = new ArrayList<>();
        List<Fundsinfo> fundsinfos = fundsData.getFundsinfos();
        for (int i = 2; i < fundsinfos.size(); i++) {
            Fundsinfo fundsinfo = fundsinfos.get(i);
            Fundsinfo fundsinfo1 = fundsinfos.get(i - 1);
            Float aFloat = fundsData.getFundsinfoFloatMap().get(fundsinfo);
            Float aFloat1 = fundsData.getFundsinfoFloatMap().get(fundsinfo1);
            if (aFloat == Float.MAX_VALUE || aFloat1 == Float.MAX_VALUE)
                continue;
            if (aFloat > v && aFloat1 <= v) {
                weareInfos.add(fundsinfo);
            }
        }
        fundsData.setWearInfos(weareInfos);
        return weareInfos;
    }

    public static Map<Fundsinfo, Float> cciFormula(FundsData fundsData, int n) {
        List<Fundsinfo> fundsinfos = fundsData.getFundsinfos();
        Map<Fundsinfo, Float> cciMap = new HashMap<>();
        for (int i = 0; i < fundsinfos.size(); i++) {
            float tp = 0;
            try {
                tp = getTP(fundsinfos, i, n);

                float ma = getMA(fundsinfos, i, n);
                float md = getMD(ma, fundsinfos, i, n);

                float v = (tp - ma) / md / 0.015f;
                cciMap.put(fundsinfos.get(i), v);
//            System.err.println("cci:" + v + "  " + fundsinfos.get(i));
//                System.err.println("****");
            } catch (Exception e) {
//                e.printStackTrace();
                cciMap.put(fundsinfos.get(i), Float.MAX_VALUE);
            }
        }
        return cciMap;
    }

    private static float getMD(float ma, List<Fundsinfo> fundsinfos, int i, int n) throws Exception {
        if (i < n + 1)
            return 0;
        List<Fundsinfo> fundsinfoList = fundsinfos.subList(i - n, i + 1);
        float md = 0;
        for (Fundsinfo fundsinfo : fundsinfoList) {
            try {
                md += Math.abs(ma - Float.parseFloat(fundsinfo.getClose()));
            } catch (NumberFormatException e) {
//                e.printStackTrace();
            }
        }
        return md / fundsinfoList.size();
    }

    private static float getMA(List<Fundsinfo> fundsinfos, int i, int n) throws Exception {
        if (i < n + 1)
            return 0;
        List<Fundsinfo> fundsinfoList = fundsinfos.subList(i - n, i + 1);
        float close = 0;
        for (Fundsinfo fundsinfo : fundsinfoList) {
            try {
                close += Float.parseFloat(fundsinfo.getClose());
            } catch (NumberFormatException e) {
//                e.printStackTrace();
            }
        }
        return close / fundsinfoList.size();

    }

    private static float getTP(List<Fundsinfo> fundsinfos, int i, int n) throws Exception {
        if (i < n + 1)
            return 0;
        Fundsinfo fundsinfo = fundsinfos.get(i);

        return (Float.parseFloat(fundsinfo.getHigh()) + Float.parseFloat(fundsinfo.getLow()) + Float.parseFloat(fundsinfo.getClose())) / 3;
    }

    public static FundsData loadFundsInfo(String code, String bourse, String startTime, String endTime) {
        try {
            Response<ResponseBody> response = RetrofitFactory.getInstence(yahooUrl)
                    .baseAPI(FundsApi.class)
                    .downloadFunds(code + bourse, startTime, endTime, "1d", "history").execute();
            return new FundsData(FundsCallback.readFundsInfo(response));
        } catch (Exception e) {
//            e.printStackTrace();
        }
        return null;
    }


    public static class MyloadFundsCall implements LoadFundsCall {
        @Override
        public void onSuccess(FundsData fundsData) {

            Fundsinfo fundsinfo = fundsData.getWearInfos().get(fundsData.getWearInfos().size() - 1);
            Float aFloat = fundsData.getFundsinfoFloatMap().get(fundsinfo);

            System.err.println("========================");

            System.err.println("CODE:" + fundsData.getCode() + "最近一次CCI上穿-100 " + fundsinfo.toString() + " CCI:" + aFloat);
            fundsinfo = fundsData.getUnderHunder().get(fundsData.getUnderHunder().size() - 1);
            aFloat = fundsData.getFundsinfoFloatMap().get(fundsinfo);
            System.err.println("CODE:" + fundsData.getCode() + "最近一次CCI小于-100 " + fundsinfo.toString() + " CCI:" + aFloat);
            matchRule(fundsData);
            System.err.println("========================");

        }

        @Override
        public void onFailed() {

        }
    }
 

    public static class FundsCallback implements Callback<ResponseBody> {
        @Override
        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
            try {
                onSuccess(readFundsInfo(response));
            } catch (Exception e) {
                e.printStackTrace();
                onFailure(call, e);
            }
        }

        public static List<Fundsinfo> readFundsInfo(Response<ResponseBody> response) throws IOException {
            BufferedReader bufferedReader = null;
            List<Fundsinfo> fundsinfoList = new ArrayList<>();

            try {
                ResponseBody body = response.body();
                InputStream inputStream = body.source().inputStream();
                bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String str = null;
                while ((str = bufferedReader.readLine()) != null) {
                    try {
//                        System.err.println(str);
                        String[] split = str.split(",");
                        fundsinfoList.add(new Fundsinfo(split[0], split[1], split[2], split[3], split[4], split[5], split[6]));
                    } catch (Exception e) {
                    }
                }
                return fundsinfoList;
            } catch (Exception e) {
                throw e;
            } finally {
                try {
                    if (bufferedReader != null)
                        bufferedReader.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }

        public void onSuccess(List<Fundsinfo> fundsinfoList) {
        }

        @Override
        public void onFailure(Call<ResponseBody> call, Throwable t) {

        }
    }




    static String str = "512290|国泰中证生物医药ETF行情基金吧\n" +
            "518880|华安黄金易ETF行情基金吧\n" +
            "518850|华夏黄金ETF行情基金吧\n" +
            "518800|国泰黄金ETF行情基金吧\n" +
            "518660|工银瑞信黄金ETF行情基金吧\n" +
            "515990|添富中证国企一带一路ETF行情基金吧\n" +
            "515980|华富中证人工智能产业ETF行情基金吧\n" +
            "515960|嘉实医药健康100ETF行情基金吧\n" +
            "515950|富国中证医药50ETF行情基金吧\n" +
            "515930|永赢沪深300ETF行情基金吧\n" +
            "515900|博时央企创新驱动ETF行情基金吧\n" +
            "515890|博时红利ETF行情基金吧\n" +
            "515880|国泰中证全指通信设备ETF行情基金吧\n" +
            "515870|嘉实先进制造100ETF行情基金吧\n" +
            "515860|嘉实新兴科技100ETF行情基金吧\n" +
            "515850|富国中证全指证券公司ETF行情基金吧\n" +
            "515820|富国中证800ETF行情基金吧\n" +
            "515810|易方达中证800ETF行情基金吧\n" +
            "515800|添富中证800ETF行情基金吧\n" +
            "515780|浦银安盛MSCI中国A股ETF行情基金吧\n" +
            "515750|富国中证科技50策略ETF行情基金吧\n" +
            "515700|平安中证新能源汽车产业ETF行情基金吧\n" +
            "515690|鹏华中证高股息龙头ETF行情基金吧\n" +
            "515680|嘉实中证央企创新驱动ETF行情基金吧\n" +
            "515670|中银中证100ETF行情基金吧\n" +
            "515660|国联安沪深300ETF行情基金吧\n" +
            "515650|富国中证消费50ETF行情基金吧\n" +
            "515630|鹏华中证800证保ETF行情基金吧\n" +
            "515620|建信中证800ETF行情基金吧\n" +
            "515600|广发央企创新ETF行情基金吧\n" +
            "515590|前海开源中证500等权ETF行情基金吧\n" +
            "515580|华泰柏瑞中证科技100ETF行情基金吧\n" +
            "515570|山西中证红利ETF行情基金吧\n" +
            "515550|中融中证500ETF行情基金吧\n" +
            "515520|大成MSCI中国A股质优价值100ETF行情基金吧\n" +
            "515510|嘉实中证500成长估值ETF行情基金吧\n" +
            "515450|南方中国A股大盘红利低波50ETF行情基金吧\n" +
            "515390|华安沪深300ETF行情基金吧\n" +
            "515380|泰康沪深300ETF行情基金吧\n" +
            "515360|方正富邦沪深300ETF行情基金吧\n" +
            "515350|民生加银沪深300ETF行情基金吧\n" +
            "515330|天弘沪深300ETF行情基金吧\n" +
            "515310|添富沪深300ETF行情基金吧\n" +
            "515300|嘉实沪深300红利低波动ETF行情基金吧\n" +
            "515280|富国中证银行ETF行情基金吧\n" +
            "515220|国泰中证煤炭ETF行情基金吧\n" +
            "515210|国泰中证钢铁ETF行情基金吧\n" +
            "515200|申万菱信中证研发创新100ETF行情基金吧\n" +
            "515190|中银证券中证500ETF行情基金吧\n" +
            "515180|易方达中证红利ETF行情基金吧\n" +
            "515160|招商MSCI中国A股国际通ETF行情基金吧\n" +
            "515150|富国中证国企一带一路ETF行情基金吧\n" +
            "515130|博时沪深300ETF行情基金吧\n" +
            "515110|易方达中证国企一带一路ETF行情基金吧\n" +
            "515090|博时可持续发展100ETF行情基金吧\n" +
            "515080|招商中证红利ETF行情基金吧\n" +
            "515070|华夏中证人工智能主题ETF行情基金吧\n" +
            "515060|华夏中证全指房地产ETF行情基金吧\n" +
            "515050|华夏中证5G通信主题ETF行情基金吧\n" +
            "515030|华夏中证新能源汽车ETF行情基金吧\n" +
            "515020|华夏中证银行ETF行情基金吧\n" +
            "515010|华夏中证全指证券公司ETF行情基金吧\n" +
            "515000|华宝中证科技龙头ETF行情基金吧\n" +
            "513900|华安CES港股通ETF行情基金吧\n" +
            "513680|建信港股通恒生中国ETF行情基金吧\n" +
            "513090|易方达中证香港证券投资ETF行情基金吧\n" +
            "512990|华夏MSCIA股国际通ETF行情基金吧\n" +
            "512980|广发中证传媒ETF行情基金吧\n" +
            "512970|平安粤港澳大湾区ETF行情基金吧\n" +
            "512960|博时央企结构调整ETF行情基金吧\n" +
            "512950|华夏中证央企ETF行情基金吧\n" +
            "512930|平安人工智能ETF行情基金吧\n" +
            "512920|新华MSCI中国A股国际ETF行情基金吧\n" +
            "512910|广发中证100ETF行情基金吧\n" +
            "512900|南方中证全指证券ETF行情基金吧\n" +
            "512890|华泰柏瑞中证低波动ETF行情基金吧\n" +
            "512880|国泰中证全指证券公司ETF行情基金吧\n" +
            "512870|南华中证杭州湾区ETF行情基金吧\n" +
            "512860|华安MSCI中国ETF行情基金吧\n" +
            "512850|中信建投北京50ETF行情基金吧\n" +
            "512820|汇添富中证银行ETF行情基金吧\n" +
            "512810|华宝中证军工ETF行情基金吧\n" +
            "512800|华宝中证银行ETF行情基金吧\n" +
            "512780|广发中证京津冀发展ETF行情基金吧\n" +
            "512770|华夏战略新兴成指ETF行情基金吧\n" +
            "512760|国泰CES半导体芯片ETF行情基金吧\n" +
            "512750|嘉实中证锐联基本面ETF行情基金吧\n" +
            "512730|鹏华中证银行ETF行情基金吧\n" +
            "512720|国泰中证计算机ETF行情基金吧\n" +
            "512710|富国中证军工龙头ETF行情基金吧\n" +
            "512700|南方中证银行ETF行情基金吧\n" +
            "512690|鹏华中证酒ETF行情基金吧\n" +
            "512680|广发中证军工ETF行情基金吧\n" +
            "512670|鹏华中证国防ETF行情基金吧\n" +
            "512660|国泰中证军工ETF行情基金吧\n" +
            "512650|添富中证长三角ETF行情基金吧\n" +
            "512640|嘉实中证金融地产ETF行情基金吧\n" +
            "512610|嘉实中证医药卫生ETF行情基金吧\n" +
            "512600|嘉实中证主要消费ETF行情基金吧\n" +
            "512590|浦银安盛中证高股息ETF行情基金吧\n" +
            "512580|广发中证环保ETF行情基金吧\n" +
            "512570|易方达中证全指证券ETF行情基金吧\n" +
            "512560|易方达中证军工ETF行情基金吧\n" +
            "512550|嘉实富时中国A50ETF行情基金吧\n" +
            "512530|建信沪深300红利ETF行情基金吧\n" +
            "512520|华泰MSCI中国A股国际通ETF行情基金吧\n" +
            "512510|华泰柏瑞中证500ETF行情基金吧\n" +
            "512500|华夏中证500ETF行情基金吧\n" +
            "512480|国联安中证半导体ETF行情基金吧\n" +
            "512400|南方中证申万有色金属ETF行情基金吧\n" +
            "512390|平安MSCI中国A股ETF行情基金吧\n" +
            "512380|银华MSCI中国A股ETF行情基金吧\n" +
            "512360|平安MSCI中国A股国际ETF行情基金吧\n" +
            "512340|南方中证500原材料ETF行情基金吧\n" +
            "512330|南方中证500信息技术ETF行情基金吧\n" +
            "512310|南方中证500工业ETF行情基金吧\n" +
            "512300|南方中证500医药卫生ETF行情基金吧\n" +
            "512280|景顺MSCI中国A股ETF行情基金吧\n" +
            "512270|华安行业中性低波动ETF行情基金吧\n" +
            "512260|华安中证低波动ETF行情基金吧\n" +
            "512220|景顺中证TMT150ETF行情基金吧\n" +
            "512200|南方中证房地产ETF行情基金吧\n" +
            "512190|浙商之江凤凰ETF行情基金吧\n" +
            "512180|建信MSCI中国A股国际通ETF行情基金吧\n" +
            "512170|华宝中证医疗ETF行情基金吧\n" +
            "512160|南方MSCI中国A股国际通ETF行情基金吧\n" +
            "512150|汇安富时中国A50ETF行情基金吧\n" +
            "512120|华安中证细分医药ETF行情基金吧\n" +
            "512100|南方中证1000ETF行情基金吧\n" +
            "512090|易方达MSCI中国A股ETF行情基金吧\n" +
            "512070|易方达沪深300非银ETF行情基金吧\n" +
            "512040|富国中证价值ETF行情基金吧\n" +
            "512010|易方达沪深300医药ETF行情基金吧\n" +
            "512000|华宝中证全指证券ETF行情基金吧\n" +
            "511380|博时可转债ETF行情基金吧\n" +
            "511310|富国中证10年期国债ETF行情基金吧\n" +
            "511290|广发上证10年期国债ETF行情基金吧\n" +
            "511280|华夏3-5年信用债ETF行情基金吧\n" +
            "511270|海富通上证10年期ETF行情基金吧\n" +
            "511260|国泰上证10年期国债ETF行情基金吧\n" +
            "511220|海富通上证城投债ETF行情基金吧\n" +
            "511060|海富通上证5年期ETF行情基金吧\n" +
            "511030|平安中债债利差因子ETF行情基金吧\n" +
            "511020|平安中证5-10年国债活跃券ETF行情基金吧\n" +
            "511010|国泰上证5年期国债ETF行情基金吧\n" +
            "510890|兴业上证红利低波动ETF行情基金吧\n" +
            "510880|华泰柏瑞上证红利ETF行情基金吧\n" +
            "510850|工银瑞信上证50ETF行情基金吧\n" +
            "510810|汇添富中证上海国企ETF行情基金吧\n" +
            "510800|建信上证50ETF行情基金吧\n" +
            "510710|博时上证50ETF行情基金吧\n" +
            "510680|万家上证50ETF行情基金吧\n" +
            "510660|华夏医药ETF行情基金吧\n" +
            "510650|上证金融地产发起式ETF行情基金吧\n" +
            "510630|华夏消费ETF行情基金吧\n" +
            "510600|申万菱信上证50ETF行情基金吧\n" +
            "510590|平安中证500ETF行情基金吧\n" +
            "510580|易方达中证500ETF行情基金吧\n" +
            "510560|国寿安保中证500ETF行情基金吧\n" +
            "510550|方正富邦中证500ETF行情基金吧\n" +
            "510530|工银中证500ETF行情基金吧\n" +
            "510510|广发中证500ETF行情基金吧\n" +
            "510500|南方中证500ETF行情基金吧\n" +
            "510440|大成中证500沪市ETF行情基金吧\n" +
            "510430|银华上证50等权ETF行情基金吧\n" +
            "510410|博时上证自然资源ETF行情基金吧\n" +
            "510390|平安沪深300ETF行情基金吧\n" +
            "510380|国寿安保沪深300ETF行情基金吧\n" +
            "510360|广发沪深300ETF行情基金吧\n" +
            "510350|工银瑞信沪深300ETF行情基金吧\n" +
            "510330|华夏沪深300ETF行情基金吧\n" +
            "510310|易方达沪深300发起式ETF行情基金吧\n" +
            "510300|华泰柏瑞沪深300ETF行情基金吧\n" +
            "510290|南方上证380ETF行情基金吧\n" +
            "510270|中银上证国企100ETF行情基金吧\n" +
            "510230|国泰上证180金融ETF行情基金吧\n" +
            "510220|华泰柏瑞上证中小盘ETF行情基金吧\n" +
            "510210|上证综指ETF行情基金吧\n" +
            "510200|汇安上证证券ETF行情基金吧\n" +
            "510190|华安上证龙头ETF行情基金吧\n" +
            "510180|华安上证180ETF行情基金吧\n" +
            "510170|国联安上证商品ETF行情基金吧\n" +
            "510160|中证南方小康产业指数ETF行情基金吧\n" +
            "510150|招商上证消费80ETF行情基金吧\n" +
            "510130|中盘ETF行情基金吧\n" +
            "510120|海富通上证非周期ETF行情基金吧\n" +
            "510110|海富通上证周期ETF行情基金吧\n" +
            "510100|易方达上证50ETF行情基金吧\n" +
            "510090|建信责任ETF行情基金吧\n" +
            "510070|民企ETF行情基金吧\n" +
            "510060|工银上证央企ETF行情基金吧\n" +
            "510050|华夏上证50ETF行情基金吧\n" +
            "510030|华宝上证180价值ETF行情基金吧\n" +
            "510020|博时上证超大盘ETF行情基金吧\n" +
            "510010|交银上证180公司治理ETF行情基金吧\n" +
            "159998|天弘中证计算机ETF行情基金吧\n" +
            "159997|天弘中证电子ETF行情基金吧\n" +
            "159996|国泰中证全指家电ETF行情基金吧\n" +
            "159995|华夏国证半导体芯片ETF行情基金吧\n" +
            "159994|银华中证5G通信主题ETF行情基金吧\n" +
            "159993|鹏华国证证券龙头ETF行情基金吧\n" +
            "159992|银华中证创新药产业ETF行情基金吧\n" +
            "159991|招商创业板大盘ETF行情基金吧\n" +
            "159990|银华巨潮小盘价值ETF行情基金吧\n" +
            "159988|平安中债-0-5年地方债ETF行情基金吧\n" +
            "159987|银华中证研发创新100ETF行情基金吧\n" +
            "159986|弘毅远方国证消费100ETF行情基金吧\n" +
            "159985|华夏饲料豆粕期货ETF行情基金吧\n" +
            "159984|南方粤港澳大湾区ETF行情基金吧\n" +
            "159983|华夏粤港澳大湾区创新100ETF行情基金吧\n" +
            "159982|鹏华中证500ETF行情基金吧\n" +
            "159981|建信易盛能源化工期货ETF行情基金吧\n" +
            "159980|大成有色金属期货ETF行情基金吧\n" +
            "159979|广发粤港澳大湾区创新100ETF行情基金吧\n" +
            "159978|建信中证沪港深粤港澳大湾区发展主题ETF行情基金吧\n" +
            "159977|天弘创业板ETF行情基金吧\n" +
            "159976|工银粤港澳大湾区创新100ETF行情基金吧\n" +
            "159975|招商深证100ETF行情基金吧\n" +
            "159974|富国央企创新ETF行情基金吧\n" +
            "159973|弘毅远方民企领先100ETF行情基金吧\n" +
            "159972|鹏华中证5年地债ETF行情基金吧\n" +
            "159971|富国创业板ETF行情基金吧\n" +
            "159970|工银瑞信深证100ETF行情基金吧\n" +
            "159969|银华深证100ETF行情基金吧\n" +
            "159968|博时中证500ETF行情基金吧\n" +
            "159967|华夏创成长ETF行情基金吧\n" +
            "159966|华夏创蓝筹ETF行情基金吧\n" +
            "159965|中融央视财经50ETF行情基金吧\n" +
            "159964|平安创业板ETF行情基金吧\n" +
            "159963|富国恒生中国企业ETF行情基金吧\n" +
            "159962|华夏中证四川国改ETF行情基金吧\n" +
            "159961|方正富邦深证100ETF行情基金吧\n" +
            "159960|平安港股通恒生中国企业ETF行情基金吧\n" +
            "159959|银华中证央企结构调整ETF行情基金吧\n" +
            "159958|工银瑞信创业板ETF行情基金吧\n" +
            "159957|华夏创业板ETF行情基金吧\n" +
            "159956|建信创业板ETF行情基金吧\n" +
            "159955|嘉实创业板ETF行情基金吧\n" +
            "159953|广发中证全指工业ETF行情基金吧\n" +
            "159952|广发创业板ETF行情基金吧\n" +
            "159951|嘉实中关村A股ETF行情基金吧\n" +
            "159949|华安创业板50ETF行情基金吧\n" +
            "159948|南方创业板ETF行情基金吧\n" +
            "159945|广发中证全指能源ETF行情基金吧\n" +
            "159944|广发中证全指原材料ETF行情基金吧\n" +
            "159943|大成深证成份ETF行情基金吧\n" +
            "159940|广发中证全指金融地产ETF行情基金吧\n" +
            "159939|广发中证全指信息技术ETF行情基金吧\n" +
            "159938|广发中证全指医药卫生ETF行情基金吧\n" +
            "159937|博时黄金ETF行情基金吧\n" +
            "159936|广发中证全指可选消费ETF行情基金吧\n" +
            "159935|景顺长城中证500ETF行情基金吧\n" +
            "159934|易方达黄金ETF行情基金吧\n" +
            "159933|国投瑞银金融地产ETF行情基金吧\n" +
            "159932|大成中证500深市ETF行情基金吧\n" +
            "159931|汇添富中证金融地产ETF行情基金吧\n" +
            "159930|汇添富中证能源ETF行情基金吧\n" +
            "159929|汇添富中证医药卫生ETF行情基金吧\n" +
            "159928|汇添富中证主要消费ETF行情基金吧\n" +
            "159926|嘉实中证中期国债ETF行情基金吧\n" +
            "159925|南方沪深300ETF行情基金吧\n" +
            "159923|大成中证100ETF行情基金吧\n" +
            "159922|嘉实中证500ETF行情基金吧\n" +
            "159919|嘉实沪深300ETF行情基金吧\n" +
            "159918|嘉实中创400ETF行情基金吧\n" +
            "159916|深F60ETF行情基金吧\n" +
            "159915|易方达创业板ETF行情基金吧\n" +
            "159913|交银深证300价值ETF行情基金吧\n" +
            "159912|汇添富深证300ETF行情基金吧\n" +
            "159911|鹏华深证民营ETF行情基金吧\n" +
            "159910|嘉实深证基本面120ETF行情基金吧\n" +
            "159909|招商深证TMT50ETF行情基金吧\n" +
            "159908|博时创业板ETF行情基金吧\n" +
            "159907|广发中小板300ETF行情基金吧\n" +
            "159906|大成深证成长40ETF行情基金吧\n" +
            "159905|工银深证红利ETF行情基金吧\n" +
            "159903|南方深证成份ETF行情基金吧\n" +
            "159902|华夏中小板ETF行情基金吧\n" +
            "159901|易方达深证100ETF行情基金吧\n" +
            "159813|鹏华国证半导体芯片ETF行情基金吧\n" +
            "159812|前海开源黄金ETF行情基金吧\n" +
            "159811|博时中证5G产业50ETF行情基金吧\n" +
            "159809|博时大湾区ETF行情基金吧\n" +
            "159807|易方达中证科技50ETF行情基金吧\n" +
            "159806|国泰中证新能源汽车ETF行情基金吧\n" +
            "159805|鹏华中证传媒ETF行情基金吧\n" +
            "159804|国寿安保创精选88ETF行情基金吧\n" +
            "159802|广发中证800ETF行情基金吧\n" +
            "159801|广发国证半导体芯片ETF行情基金吧\n";
}
