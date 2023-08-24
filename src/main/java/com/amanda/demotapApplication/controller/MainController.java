package com.amanda.demotapApplication.controller;

import com.amanda.demotapApplication.beans.TripsDto;
import com.amanda.demotapApplication.service.TripService;
import com.opencsv.CSVParser;
import com.opencsv.CSVWriter;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
public class MainController {

    @Autowired
    TripService tripService;

    private static final Logger logger = LoggerFactory.getLogger(MainController.class);

    private static String UPLOAD_FOLDER = "C://test//";

    @RequestMapping("/upload")
    public ModelAndView showUpload() {
        return new ModelAndView("upload");
    }

    @PostMapping("/upload")
    public ModelAndView fileUpload(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes) throws IOException {

        if (file.isEmpty()) {
            return new ModelAndView("status", "message", "Please select a file and try again");
        }

        List<TripsDto> tripsDtos = tripService.getTripCharge(file);


        for(TripsDto tripsDto : tripsDtos){
            logger.debug("Final trip data out put{}", tripsDto.toString());

        }




          /*BufferedReader fileReader = new BufferedReader(new
                    InputStreamReader(file.getInputStream(), "UTF-8"));
            CSVParser csvParser = new CSVParser(fileReader,CSVFormat.DEFAULT);



            Iterable<CSVRecord> csvRecords = csvParser.getRecords();


            for (CSVRecord csvRecord : csvRecords) {
                logger.debug("Hello from Logback {}", csvRecord);

               System.out.println(csvRecord);
            }

            // read and write the file to the slelected location-
            byte[] bytes = file.getBytes();
            Path path = Paths.get(UPLOAD_FOLDER + file.getOriginalFilename());
            Files.write(path, bytes);*/



           return new ModelAndView("status", "message", tripsDtos.toString());
    }



    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    public ModelAndView greeting(){
        List<Integer> data = Arrays.asList(1, 2, 3, 4, 5);

        logger.debug("Hello from Logback {}", data);

        return new ModelAndView("status", "message", data);


    }



}
