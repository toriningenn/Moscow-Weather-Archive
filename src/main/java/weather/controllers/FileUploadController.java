package weather.controllers;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import weather.models.Weather;
import weather.service.WeatherService;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;


@CrossOrigin(origins = "*", allowedHeaders = "*")
@Controller
public class FileUploadController {
    ObjectMapper mapper = new ObjectMapper();

    WeatherService weatherService;

    @Autowired
    public FileUploadController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    @PostMapping(value = "/upload")
    public void uploadFile(@RequestParam("files0") MultipartFile multipartFile,
                           @RequestParam("files1") MultipartFile multipartFile1,
                           @RequestParam("files2") MultipartFile multipartFile2) throws IOException, ParseException {
        weatherService.save(multipartFile);
        weatherService.save(multipartFile1);
        weatherService.save(multipartFile2);
    }

    @GetMapping(value = "/{page}")
    @ResponseBody
    public List<Weather> getAllData(@PathVariable("page") int page) throws JsonProcessingException {
        System.out.println("OK");
        int max = 25;
        int min = max * page;
       return weatherService.getWeather(min, max);
    }
}
