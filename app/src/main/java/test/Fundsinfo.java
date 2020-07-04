package test;

public   class Fundsinfo {
    // 日期 ，开盘价，最高价，最低价，收盘价，权重 ，成交量
    String Date, Open, High, Low, Close, Adj_Close, Volume;

    public Fundsinfo(String date, String open, String high, String low, String close, String adj_Close, String volume) {
        Date = date;
        Open = open;
        High = high;
        Low = low;
        Close = close;
        Adj_Close = adj_Close;
        Volume = volume;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getOpen() {
        return Open;
    }

    public void setOpen(String open) {
        Open = open;
    }

    public String getHigh() {
        return High;
    }

    public void setHigh(String high) {
        High = high;
    }

    public String getLow() {
        return Low;
    }

    public void setLow(String low) {
        Low = low;
    }

    public String getClose() {
        return Close;
    }

    public void setClose(String close) {
        Close = close;
    }

    public String getAdj_Close() {
        return Adj_Close;
    }

    public void setAdj_Close(String adj_Close) {
        Adj_Close = adj_Close;
    }

    public String getVolume() {
        return Volume;
    }

    public void setVolume(String volume) {
        Volume = volume;
    }

    @Override
    public String toString() {
        return "Fundsinfo{" +
                "Date='" + Date + '\'' +
                ", Open='" + Open + '\'' +
                ", High='" + High + '\'' +
                ", Low='" + Low + '\'' +
                ", Close='" + Close + '\'' +
                ", Adj_Close='" + Adj_Close + '\'' +
                ", Volume='" + Volume + '\'' +
                '}';
    }
}
