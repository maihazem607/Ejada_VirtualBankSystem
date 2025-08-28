package com.BFFService.BFFService.applications.Exceptons;

import org.springframework.http.HttpStatus;

public class ServiceException extends ApplicationException{
    
    public ServiceException(){
        super("INTERNAL_SERVER_ERROR",HttpStatus.INTERNAL_SERVER_ERROR, "Failed to retrieve dashboard data due to an issue with downstream services.");
    }
}
