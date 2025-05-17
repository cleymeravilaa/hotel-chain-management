package edu.unicolombo.HotelChainManagement.infrastructure.errors.exception;

public class BusinessLogicValidationException extends RuntimeException {
    public BusinessLogicValidationException(String message) {
        super(message);
    }
}
