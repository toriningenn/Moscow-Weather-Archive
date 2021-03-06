package weather.models;



import javax.persistence.*;


@Entity
public class Weather {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String date;
    private String time;
    private double T;
    private double vlh;
    private double Td;
    private double pressure;
    private String wind;
    String speed;
    double obl;
    double h;
    String vv;
    String weatherCond;


    public void setDate(String date) {
        this.date = date;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setT(double t) {
        T = t;
    }

    public void setVlh(double vlh) {
        this.vlh = vlh;
    }

    public void setPressure(double pressure) {
        this.pressure = pressure;
    }

    public void setWind(String wind) {
        this.wind = wind;
    }

    public void setSpeed(String speed) {
        this.speed = speed;
    }

    public void setObl(double obl) {
        this.obl = obl;
    }

    public void setH(double h) {
        this.h = h;
    }

    public void setVv(String vv) {
        this.vv = vv;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public double getT() {
        return T;
    }

    public double getVlh() {
        return vlh;
    }

    public double getPressure() {
        return pressure;
    }

    public String getWind() {
        return wind;
    }

    public String getSpeed() {
        return speed;
    }

    public double getObl() {
        return obl;
    }

    public double getH() {
        return h;
    }

    public String getVv() {
        return vv;
    }


    public void setTd(double td) {
        Td = td;
    }

    public void setWeatherCond(String weatherCond) {
        this.weatherCond = weatherCond;
    }

    public double getTd() {
        return Td;
    }

    public String getWeatherCond() {
        return weatherCond;
    }
}
