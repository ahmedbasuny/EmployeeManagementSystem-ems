package com.peopleflow.ems.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.peopleflow.ems.exception.ErrorEntity;

@Service
public class Utils {

    private static final Logger logger = LoggerFactory.getLogger(Utils.class);

    public ResponseEntity<?> returnError(Exception e) {
        logger.error(e.getMessage(), e);
        return new ResponseEntity<>(new ErrorEntity(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
