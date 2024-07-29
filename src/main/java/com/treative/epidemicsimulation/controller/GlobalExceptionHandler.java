package com.treative.epidemicsimulation.controller;

import com.treative.epidemicsimulation.service.exceptions.*;
import com.treative.epidemicsimulation.service.exceptions.dailysimulationdata.CorruptedDailySimulationDataException;
import com.treative.epidemicsimulation.service.exceptions.simulation.IncorrectInfectionRateException;
import com.treative.epidemicsimulation.service.exceptions.simulation.IncorrectInitialInfectedException;
import com.treative.epidemicsimulation.service.exceptions.simulation.IncorrectMortalityRateException;
import com.treative.epidemicsimulation.service.exceptions.simulation.SimulationNameAlreadyExistsException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static com.treative.epidemicsimulation.utils.ResponseUtil.createErrorResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(SimulationNameAlreadyExistsException.class)
    public ResponseEntity<?> handleSimulationNameAlreadyExistsException(SimulationNameAlreadyExistsException e) {
        return createErrorResponse(HttpStatus.CONFLICT, "errorSimulationName", e.getMessage());
    }

    @ExceptionHandler(IncorrectInitialInfectedException.class)
    public ResponseEntity<?> handleIncorrectInitialInfectedException(IncorrectInitialInfectedException e) {
        return createErrorResponse(HttpStatus.BAD_REQUEST, "errorInitialInfected", e.getMessage());
    }

    @ExceptionHandler(IncorrectMortalityRateException.class)
    public ResponseEntity<?> handleIncorrectMortalityRateException(IncorrectMortalityRateException e) {
        return createErrorResponse(HttpStatus.BAD_REQUEST, "errorMortalityRate", e.getMessage());
    }

    @ExceptionHandler(IncorrectInfectionRateException.class)
    public ResponseEntity<?> handleIncorrectInfectionRateException(IncorrectInfectionRateException e) {
        return createErrorResponse(HttpStatus.BAD_REQUEST, "errorInfectionRate", e.getMessage());
    }

    @ExceptionHandler(NullFieldException.class)
    public ResponseEntity<?> handleNullFieldException(NullFieldException e) {
        return createErrorResponse(HttpStatus.BAD_REQUEST, "errorNullField", e.getMessage());
    }

    @ExceptionHandler(CorruptedDailySimulationDataException.class)
    public ResponseEntity<?> handleCorruptedDailySimulationDataException(CorruptedDailySimulationDataException e) {
        return createErrorResponse(HttpStatus.BAD_REQUEST, "errorCorruptedDailySimulationData", e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleGeneralException(Exception e) {
        return createErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "errorGeneral", "An unexpected error occurred: " + e.getMessage());
    }

}
