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
        configuration.buildSessionFactory();
        //StandardServiceRegistryBuilder ssrb = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties());
        SessionFactory sessionFactory = configuration.buildSessionFactory();

        Workbook workbook = new XSSFWorkbook(file.getInputStream());
        String filePatch = file.getOriginalFilename();
        while (workbook.sheetIterator().hasNext()) {
            int sh = 0;
            Sheet sheet = workbook.getSheetAt(sh);
            sh++;

            for (int i = 7; i < sheet.getLastRowNum(); i++) {

                Row row = (Row) sheet.getRow(i);
                Weather weather = new Weather();
                Row.MissingCellPolicy missingCellPolicy = null;


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


                    row.getCell(7).setCellType(CellType.STRING);
                    String speed = row.getCell(7).getStringCellValue();
                    weather.setSpeed(speed);


                row.getCell(8).setCellType(CellType.NUMERIC);
                    double obl = row.getCell(8).getNumericCellValue();
                    weather.setObl(obl);
                row.getCell(9).setCellType(CellType.NUMERIC);
                    double h = row.getCell(9).getNumericCellValue();
                    weather.setH(h);


                    row.getCell(10).setCellType(CellType.STRING);
                    String vv = row.getCell(10).getStringCellValue();
                    weather.setVv(vv);


                    row.getCell(11).setCellType(CellType.STRING);
                    String weatherCond = row.getCell(11).getStringCellValue();
                    weather.setWeatherCond(weatherCond);


                    Session session = sessionFactory.openSession();
                    session.save(weather);
                    session.close();

            }
        }
        workbook.close();
        file.getInputStream().close();
    }
}

