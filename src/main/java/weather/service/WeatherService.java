package weather.service;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.hibernate.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import weather.config.ConfigureHibernateMethod;
import weather.models.Weather;
import java.io.IOException;
import java.sql.Date;
import java.text.ParseException;
import java.util.Iterator;
import java.util.List;


@Service
public class WeatherService {
    @Autowired
    SessionFactory sessionFactory = ConfigureHibernateMethod.GetSessionFactory();

    public WeatherService(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public List<Weather> getAllWeather() {
        Session session = sessionFactory.openSession();
        try {
            Query<Weather> query = session.createNativeQuery("SELECT w.* FROM public.weather w ORDER BY date, time", Weather.class);
            List<Weather> results = query.list();
            return results;
        } catch (Exception e) {
            System.out.println(e);
            return null;
        } finally {
            if (session.isOpen()) {
                session.close();
            }
        }
    }

    public List<Weather> getWeather(int min, int max) {
        Session session = sessionFactory.openSession();
        try {
            Query<Weather> query = session.createNativeQuery("SELECT w.* FROM public.weather w ORDER BY date, time limit " + max + " offset " + min, Weather.class);
            List<Weather> results = query.getResultList();
            return results;
        } catch (Exception e) {
            System.out.println(e);
            return null;
        } finally {
            if (session.isOpen()) {
                session.close();
            }
        }
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

    //поправить сессии
    public void save(MultipartFile file) throws IOException, ParseException {
        Workbook workbook = new XSSFWorkbook(file.getInputStream());

        String filePatch = file.getOriginalFilename();
        Session session = sessionFactory.openSession();
        int sh = 0;
        while (workbook.sheetIterator().hasNext()) {
            Sheet sheet = workbook.getSheetAt(sh);
            sh++;

            for (int i = 7; i < sheet.getLastRowNum(); i++) {

                Row row = (Row) sheet.getRow(i);
                Weather weather = new Weather();

                try {
                    String stringDate = WeatherService.getStringValue(0, row);
                    String year = stringDate.substring(6);
                    String month = stringDate.substring(3, 5);
                    String day = stringDate.substring(0, 2);
                    String formattedDate = year + "-" + month + "-" + day;
                    Date date = Date.valueOf(formattedDate);
                    //"yyyy-[m]m-[d]d"
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

                    Double speed = WeatherService.getNumericValue(7, row);
                    weather.setSpeed(speed);

                    double obl = WeatherService.getNumericValue(8, row);
                    weather.setObl(obl);

                    double h = WeatherService.getNumericValue(9, row);
                    weather.setH(h);

                    Double vv = WeatherService.getNumericValue(10, row);
                    weather.setVv(vv);

                    String weatherCond = WeatherService.getStringValue(11, row);
                    weather.setWeatherCond(weatherCond);

                    session.beginTransaction();
                    session.save(weather);
                    session.getTransaction().commit();
                } catch (Exception e) {
                    System.out.println(e);
                }
            }
        }
        workbook.close();
        file.getInputStream().close();
        if (session.isOpen()) {
            session.close();
        }
    }
}

