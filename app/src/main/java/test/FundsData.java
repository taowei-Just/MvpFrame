package test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public   class FundsData {
    String code;
    List<Fundsinfo> fundsinfos;
    Map<Fundsinfo, Float> fundsinfoFloatMap;
    List<Fundsinfo> wearInfos;
    List<Fundsinfo> wearZeroInfos = new ArrayList<>();
    List<Fundsinfo> wearPlusInfos = new ArrayList<>();
    List<Fundsinfo> underPlusInfos = new ArrayList<>();
    private List<Fundsinfo> underHunder;
    private String name;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public List<Fundsinfo> getUnderPlusInfos() {
        return underPlusInfos;
    }

    public void setUnderPlusInfos(List<Fundsinfo> underPlusInfos) {
        this.underPlusInfos = underPlusInfos;
    }

    public List<Fundsinfo> getWearZeroInfos() {
        return wearZeroInfos;
    }

    public void setWearZeroInfos(List<Fundsinfo> wearZeroInfos) {
        this.wearZeroInfos = wearZeroInfos;
    }

    public List<Fundsinfo> getWearPlusInfos() {
        return wearPlusInfos;
    }

    public void setWearPlusInfos(List<Fundsinfo> wearPlusInfos) {
        this.wearPlusInfos = wearPlusInfos;
    }

    public List<Fundsinfo> getWearInfos() {
        return wearInfos;
    }

    public void setWearInfos(List<Fundsinfo> wearInfos) {
        this.wearInfos = wearInfos;
    }

    public FundsData(List<Fundsinfo> readFundsInfo) {
        this.fundsinfos = readFundsInfo;
    }

    public Map<Fundsinfo, Float> getFundsinfoFloatMap() {
        return fundsinfoFloatMap;
    }

    public void setFundsinfoFloatMap(Map<Fundsinfo, Float> fundsinfoFloatMap) {
        this.fundsinfoFloatMap = fundsinfoFloatMap;
    }

    public List<Fundsinfo> getFundsinfos() {
        return fundsinfos;
    }

    public void setFundsinfos(List<Fundsinfo> fundsinfos) {
        this.fundsinfos = fundsinfos;
    }

    public void setUnderHunder(List<Fundsinfo> underHunder) {
        this.underHunder = underHunder;
    }

    public List<Fundsinfo> getUnderHunder() {
        return underHunder;
    }

    public void setName(String name) {

        this.name = name;
    }

    public String getName() {
        return name;
    }
}
