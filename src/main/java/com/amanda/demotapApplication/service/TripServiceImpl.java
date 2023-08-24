package com.amanda.demotapApplication.service;

import com.amanda.demotapApplication.beans.TapsDto;
import com.amanda.demotapApplication.beans.TripsDto;
import com.amanda.demotapApplication.constant.TripStatus;
import com.amanda.demotapApplication.controller.MainController;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Service
public class TripServiceImpl implements TripService{

    private static final Logger logger = LoggerFactory.getLogger(TripServiceImpl.class);


@Override
public List<TripsDto> getTripCharge(MultipartFile file){

    List<TripsDto> tripDetail = new ArrayList<>();


    List<TapsDto> taps = convertToModel(file,TapsDto.class);
    Map<String,TapsDto> onTaps = new HashMap<>();
    Map<String,TapsDto> offTaps = new HashMap<>();


    for(TapsDto tapsDto : taps){

        // get on and off taps divided based on PAN
        if (tapsDto.getTapType().trim().equals("ON")) {
            onTaps.put(tapsDto.getPan().concat(tapsDto.getBusId()),tapsDto);
        } else {
            offTaps.put(tapsDto.getPan().concat(tapsDto.getBusId()),tapsDto);
        }
    }
    // loop  on trips and find off taps with same busid and pan and calculate fair and status

    Iterator iter = onTaps.entrySet().iterator();
    while (iter.hasNext()) {
        Map.Entry entry = (Map.Entry) iter.next();
        // get completed trips
        TapsDto offDto = offTaps.get(entry.getKey());

        // if there is a tap off object that means it's either a cancelled or completed trip
        tripDetail.add(offDto != null ? calculateCompletedTripCharges((TapsDto) entry.getValue(), offDto) : calculateIncompTripCharges((TapsDto) entry.getValue()));

        System.out.println("[Key] : " + entry.getKey() + " [Value] : " + entry.getValue());

    }



    return tripDetail;

}



    private TripsDto calculateCompletedTripCharges(TapsDto onTap,TapsDto offtap){

    TripsDto tripsDto = new TripsDto();

    tripsDto.setStarted(onTap.getDateTime());
    tripsDto.setFinished(offtap.getDateTime());
    tripsDto.setDurationSecs(findDifferenceSec(onTap.getDateTime(), offtap.getDateTime()));
    tripsDto.setFromStopId(onTap.getStopId());
    tripsDto.setToStopId(offtap.getStopId());
    tripsDto.setCompanyId(onTap.getCompanyId());
    tripsDto.setBudId(onTap.getBusId());
    tripsDto.setPan(onTap.getPan());


    // cancelled trip
        if (onTap.getStopId().trim().equals(offtap.getStopId().trim())) {
            tripsDto.setStatus(TripStatus.CANCELLED.getDescription());
            tripsDto.setChargeAmount(0);
    // completed trip
        } else {
            tripsDto.setStatus(TripStatus.COMPLETED.getDescription());
            tripsDto.setChargeAmount(calculateFee(tripsDto));
        }

        return tripsDto;
    };



    private TripsDto calculateIncompTripCharges(TapsDto onTap){

        TripsDto tripsDto = new TripsDto();

        tripsDto.setStarted(onTap.getDateTime());
        tripsDto.setFinished("");
        tripsDto.setDurationSecs(0);
        tripsDto.setFromStopId(onTap.getStopId());
        tripsDto.setToStopId("");
        tripsDto.setCompanyId(onTap.getCompanyId());
        tripsDto.setBudId(onTap.getBusId());
        tripsDto.setPan(onTap.getPan());
        tripsDto.setStatus(TripStatus.INCOMPLETE.getDescription());

       if(onTap.getStopId().equals("Stop1")){
           tripsDto.setChargeAmount(7.30);
       }else if(onTap.getStopId().equals("Stop3")) {
           tripsDto.setChargeAmount(7.30);
       } else {
           tripsDto.setChargeAmount(5.50);
       }

        return tripsDto;
    };

    public static <T> List<T> convertToModel(MultipartFile file, Class<T> responseType) {
        List<T> models;
        try (Reader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            CsvToBean<?> csvToBean = new CsvToBeanBuilder(reader)
                    .withType(responseType)
                    .withIgnoreLeadingWhiteSpace(true)
                    .withIgnoreEmptyLine(true)
                    .build();
            models = (List<T>) csvToBean.parse();
        } catch (Exception ex) {
            logger.error("error parsing csv file {} ", ex);
            throw new IllegalArgumentException(ex.getCause().getMessage());
        }
        return models;
    }


    static long findDifferenceSec(String start_date,
                               String end_date) {
        // SimpleDateFormat converts the
        // string format to date object
        SimpleDateFormat sdf
                = new SimpleDateFormat(
                "dd-MM-yyyy HH:mm:ss");

        // Try Class
        long difference_In_Seconds = 0;
        try {

            // parse method is used to parse
            // the text from a string to
            // produce the date
            Date d1 = sdf.parse(start_date);
            Date d2 = sdf.parse(end_date);

            //Calculate time difference
            // in milliseconds
            long difference_In_Time
                    = d2.getTime() - d1.getTime();

            //Calculate time difference in seconds,

            difference_In_Seconds = TimeUnit.MILLISECONDS
                    .toSeconds(difference_In_Time)
                    % 60;


        } catch (ParseException e) {
            e.printStackTrace();
        }
        return difference_In_Seconds;
    }

    private double calculateFee(TripsDto tripsDto){

        double fee = 0;



        switch (tripsDto.getFromStopId().trim().concat(tripsDto.getToStopId()).trim()) {
            case "Stop1 Stop2":
               fee = 3.25;
                break;
            case "Stop2 Stop3":
                fee = 5.50;
                break;
            case "Stop1 Stop3":
                 fee = 7.30;
                break;
            case "Stop2 Stop1":
                fee = 3.25;
                break;
            case "Stop3 Stop2":
                fee = 5.50;
                break;
            case "Stop3 Stop1":
                fee = 7.30;
                break;
            default:
                fee = 5.50;
        }
        return fee;
    }
}
