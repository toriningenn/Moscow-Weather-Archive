package weather.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import weather.dao.WeatherDAO;
import weather.models.Weather;


@Controller
@RequestMapping("/DisplayWeather")
public class WeatherShowController {
    @GetMapping
    public String showWeather(Model model) {
        model.addAttribute("allWeather",WeatherDAO.showAllWeather());
        return "/DisplayWeather";
    }
}
