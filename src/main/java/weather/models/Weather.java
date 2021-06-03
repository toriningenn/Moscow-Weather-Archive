package weather.models;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Time;

@Entity
public class Weather {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private Date date;
    private Time time;
    private double T;
    private double vlh;
    private double Td;
    private double pressure;
    private String wind;
    Double speed;
    double obl;
    double h;
    Double vv;
    String weatherCond;

    public void setDate(Date date) {
        this.date = date;
    }

    public void setTime(Time time) {
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

    public void setSpeed(Double speed) {
        this.speed = speed;
    }

    public void setObl(double obl) {
        this.obl = obl;
    }

    public void setH(double h) {
        this.h = h;
    }

    public void setVv(Double vv) {
        this.vv = vv;
    }

    public Date getDate() {
        return date;
    }

    public Time getTime() {
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

    public Double getSpeed() {
        return speed;
    }

    public double getObl() {
        return obl;
    }

    public double getH() {
        return h;
    }

    public Double getVv() {
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
