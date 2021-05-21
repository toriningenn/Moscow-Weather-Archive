package weather.controllers;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import weather.service.WeatherService;
import com.fasterxml.jackson.*;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@Controller
public class FileUploadController {
    ObjectMapper mapper = new ObjectMapper();

    private final String UPLOAD_DIR = "MoscowWeatherArchive/Uploads";
    WeatherService weatherService;
    @Autowired
    public FileUploadController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

   // @GetMapping("/upload")
  //  public String uploadPage() {
        //return "fileUploadPage";
  //  }

    @PostMapping(value = "/upload")
    public void uploadFile(@RequestParam("files0") MultipartFile multipartFile,
                             @RequestParam("files1") MultipartFile multipartFile1,
                             @RequestParam("files2") MultipartFile multipartFile2) throws IOException, ParseException {
       weatherService.save(multipartFile);
       weatherService.save(multipartFile1);
       weatherService.save(multipartFile2);
    }
}
