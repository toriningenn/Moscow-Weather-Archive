package weather.dao;

import org.apache.poi.ss.usermodel.*;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.hibernate.*;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import weather.models.Weather;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Component
public class WeatherDAO {

    public static List<Weather> showAllWeather() {

        Configuration configuration = new Configuration();
        configuration.configure("hibernate.cfg.xml");
        StandardServiceRegistryBuilder ssrb = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties());
        configuration.buildSessionFactory();
        SessionFactory sessionFactory = configuration.buildSessionFactory();
        Session session = sessionFactory.openSession();
        List<Weather> allWeather= session.createQuery("FROM Weather WHERE date LIKE '17.01.2013' ", Weather.class).getResultList();
            session.close();;
            return allWeather;
        //When you write HQL (or JPQL) queries, you use the names of the types, not the tables!!!
    }

    public static String getStringValue(int i, Row row) {
        Cell cell = row.getCell(i);
                try {
                    return cell.getStringCellValue();
                } catch (Exception e) {
                    return null;
                }
    }

    public static Double getNumericValue(int i, Row row) {
        Cell cell = row.getCell(i);
        try {
            return cell.getNumericCellValue();
        } catch (Exception e) {
            return null;
        }
    }

    public void save(MultipartFile file) throws IOException, ParseException {
        Configuration configuration = new Configuration();
        configuration.configure("hibernate.cfg.xml");
        StandardServiceRegistryBuilder ssrb = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties());
        configuration.buildSessionFactory();
        SessionFactory sessionFactory = configuration.buildSessionFactory();
        Workbook workbook = new XSSFWorkbook(file.getInputStream());

        String filePatch = file.getOriginalFilename();

        while (workbook.sheetIterator().hasNext()) {
            int sh = 0;
            Sheet sheet = workbook.getSheetAt(sh);
            sh++;

            for (int i = 7; i < sheet.getLastRowNum(); i++) {

                Session session = sessionFactory.openSession();
                session.beginTransaction();

                Row row = (Row) sheet.getRow(i);
                Weather weather = new Weather();
                try {
                    String date = WeatherDAO.getStringValue(0,row);
                    weather.setDate(date);


                    String time = WeatherDAO.getStringValue(1,row);
                    weather.setTime(time);


                    double T = WeatherDAO.getNumericValue(2,row);
                    weather.setT(T);


                    double vlh = WeatherDAO.getNumericValue(3,row);;
                    weather.setVlh(vlh);


                    double Td = WeatherDAO.getNumericValue(4,row);;
                    weather.setTd(Td);

                    double pressure = WeatherDAO.getNumericValue(5,row);;
                    weather.setPressure(pressure);


                    String wind = WeatherDAO.getStringValue(6,row);
                    weather.setWind(wind);

                    String speed = WeatherDAO.getStringValue(7,row);
                    weather.setSpeed(speed);

                    double obl = WeatherDAO.getNumericValue(8,row);;
                    weather.setObl(obl);


                    double h = WeatherDAO.getNumericValue(9,row);;
                    weather.setH(h);


                    String vv = WeatherDAO.getStringValue(10,row);
                    weather.setVv(vv);


                    String weatherCond = WeatherDAO.getStringValue(11,row);
                    weather.setWeatherCond(weatherCond);

                    System.out.println(weather.getDate());
                    System.out.println(weather.getTime());
                    System.out.println(weather.getPressure());
                    System.out.println(weather.getWeatherCond());
                } catch (Exception e) {
                }
                session.save(weather);
                session.getTransaction().commit();
                session.close();
            }
        }
        workbook.close();
        file.getInputStream().close();
    }
}

