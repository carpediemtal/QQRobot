package eternal.fire.springbootrobot.covid19;

public class Result {
    public String countryName;
    public String countryEnglishName;
    public int currentConfirmedCount;
    public int confirmedCount;
    public int curedCount;
    public int deadCount;

    @Override
    public String toString() {
        return String.format("国家名称：%s，国家名称（英文）：%s，现存确诊人数：%d，累计确诊人数：%d，治愈人数：%d，死亡人数：%d",
                countryName, countryEnglishName, currentConfirmedCount, confirmedCount, curedCount, deadCount);
    }
}
