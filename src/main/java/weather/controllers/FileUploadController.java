package weather.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import weather.dao.WeatherDAO;

import java.io.IOException;
import java.text.ParseException;

@Controller
public class FileUploadController {
    private final String UPLOAD_DIR = "/Users/rrsee/MoscowWeatherArchive/Uploads";
    WeatherDAO weatherDAO;
    public FileUploadController(WeatherDAO weatherDAO) {
        this.weatherDAO = weatherDAO;
    }

    @GetMapping("/upload")
    public String uploadPage() {
        return "fileUploadPage";
    }


    @PostMapping("/upload")
    public String uploadFile(@RequestParam("file") MultipartFile file, RedirectAttributes attributes) throws IOException, ParseException {
        // check if file is empty
        if (file.isEmpty()) {
            attributes.addFlashAttribute("message", "Please select a file to upload.");
            return "redirect:/";
        }
        weatherDAO.save(file);


        // save the file on the local file system
        //try {
       //     Path path = Paths.get(UPLOAD_DIR + "/" + fileName);
       //     Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
       // } catch (IOException e) {
       //     e.printStackTrace();
       // }

        // return success response
        //attributes.addFlashAttribute("message", "You successfully uploaded " + fileName + '!');

        return "redirect:/";
    }
}
