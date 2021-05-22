package weather.service;

import org.apache.poi.ss.usermodel.*;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.hibernate.*;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import weather.models.Weather;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;


@Service
public class WeatherService {

    public List<Weather> getWeather(int min, int max) {
        Configuration configuration = new Configuration();
        configuration.configure("hibernate.cfg.xml");
        StandardServiceRegistryBuilder ssrb = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties());
        configuration.buildSessionFactory();
        SessionFactory sessionFactory = configuration.buildSessionFactory();
        Session session = sessionFactory.openSession();
        Query<Weather> query = session.createQuery("FROM Weather", Weather.class);
        query.setFirstResult(min);
        query.setMaxResults(max);
        List<Weather> results = query.list();
        session.close();

        return results;
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

    //достать чисто из экселя
    public static Double getNumericValue(int i, Row row) {
        Cell cell = row.getCell(i);
        try {
            return cell.getNumericCellValue();
        } catch (Exception e) {
            return null;
        }
    }

    //разбирается в экселях, складывает в датабазу
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
                    String date = WeatherService.getStringValue(0, row);
                    weather.setDate(date);

                    String time = WeatherService.getStringValue(1, row);
                    weather.setTime(time);

                    double T = WeatherService.getNumericValue(2, row);
                    weather.setT(T);

                    double vlh = WeatherService.getNumericValue(3, row);
                    weather.setVlh(vlh);

                    double Td = WeatherService.getNumericValue(4, row);
                    weather.setTd(Td);

                    double pressure = WeatherService.getNumericValue(5, row);
                    weather.setPressure(pressure);

                    String wind = WeatherService.getStringValue(6, row);
                    weather.setWind(wind);

                    String speed = WeatherService.getStringValue(7, row);
                    weather.setSpeed(speed);

                    double obl = WeatherService.getNumericValue(8, row);
                    weather.setObl(obl);

                    double h = WeatherService.getNumericValue(9, row);
                    weather.setH(h);

                    String vv = WeatherService.getStringValue(10, row);
                    weather.setVv(vv);

                    String weatherCond = WeatherService.getStringValue(11, row);
                    weather.setWeatherCond(weatherCond);

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

