package com.amanda.demotapApplication.service;

import com.amanda.demotapApplication.beans.TripsDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface TripService {
    List<TripsDto> getTripCharge(MultipartFile file);
}
