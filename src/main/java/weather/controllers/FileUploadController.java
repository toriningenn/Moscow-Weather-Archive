package weather.controllers;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import weather.service.WeatherService;
import java.io.IOException;
import java.text.ParseException;


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
    public String getAllData(@PathVariable int page) throws JsonProcessingException {
        System.out.println("OK");
        int max = 25;
        int min = max * page;
        System.out.println(mapper.writeValueAsString(weatherService.getWeather(min, max)));
       return mapper.writeValueAsString(weatherService.getWeather(min, max));
    }
}
