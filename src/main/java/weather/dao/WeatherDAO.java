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

@Component
public class WeatherDAO {

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
                    String date = row.getCell(0).getStringCellValue();
                    weather.setDate(date);


                    String time = row.getCell(1).getStringCellValue();
                    weather.setTime(time);


                    double T = row.getCell(2).getNumericCellValue();
                    weather.setT(T);


                    double vlh = row.getCell(3).getNumericCellValue();
                    weather.setVlh(vlh);


                    double Td = row.getCell(4).getNumericCellValue();
                    weather.setTd(Td);

                    double pressure = row.getCell(5).getNumericCellValue();
                    weather.setPressure(pressure);


                    String wind = row.getCell(6).getStringCellValue();
                    weather.setWind(wind);

                    String speed = row.getCell(7).getStringCellValue();
                    weather.setSpeed(speed);

                    double obl = row.getCell(8).getNumericCellValue();
                    weather.setObl(obl);


                    double h = row.getCell(9).getNumericCellValue();
                    weather.setH(h);


                    String vv = row.getCell(10).getStringCellValue();
                    weather.setVv(vv);


                    String weatherCond = row.getCell(11).getStringCellValue();
                    weather.setWeatherCond(weatherCond);

                    System.out.println(weather.getDate());
                    System.out.println(weather.getTime());
                    System.out.println(weather.getPressure());
                    System.out.println(weather.getWeatherCond());
                } catch (Exception e) {}
                    session.save(weather);
                    session.getTransaction().commit();
                    session.close();
            }
        }
        workbook.close();
        file.getInputStream().close();
    }
}

