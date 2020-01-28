package org.interlink.grouporder.core.handler;

import org.interlink.grouporder.core.exceptions.BadRequestException;
import org.interlink.grouporder.core.exceptions.ForbiddenException;
import org.interlink.grouporder.core.exceptions.UnprocessableEntityException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

public class ExceptionsHandlerTest {

    @Test
    public void When_HttpStatusBADREQUEST_Except_ThrowBadRequestException() {
        ResponseEntity actualResult = ExceptionsHandler.handleException(new BadRequestException("Exception BAD_REQUEST"));
        ResponseEntity expectedResult = ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Exception BAD_REQUEST");

        assertEquals(actualResult, expectedResult);
    }

    @Test
    public void When_HttpStatusFORBIDDEN_Except_ThrowForbiddenException() {
        ResponseEntity actualResult = ExceptionsHandler.handleException(new ForbiddenException("Exception FORBIDDEN"));
        ResponseEntity expectedResult = ResponseEntity.status(HttpStatus.FORBIDDEN).body("Exception FORBIDDEN");

        assertEquals(actualResult, expectedResult);
    }

    @Test
    public void When_HttpStatusNOTFOUND_Except_ClassNotFoundException() {
        ResponseEntity actualResult = ExceptionsHandler.handleException(new ClassNotFoundException("Exception NOT_FOUND"));
        ResponseEntity expectedResult = ResponseEntity.status(HttpStatus.NOT_FOUND).body("Exception NOT_FOUND");

        assertEquals(actualResult, expectedResult);
    }

    @Test
    public void When_HttpStatusUNPROCESSABLEENTITY_Except_ThrowUnprocessableEntityException() {
        ResponseEntity actualResult = ExceptionsHandler.handleException(new UnprocessableEntityException("Exception UNPROCESSABLE_ENTITY"));
        ResponseEntity expectedResult = ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body("Exception UNPROCESSABLE_ENTITY");

        assertEquals(actualResult, expectedResult);
    }

    @Test
    public void When_HttpStatusINTERNAL_SERVER_ERROR_Except_IndexOutOfBoundsException() {
        ResponseEntity actualResult = ExceptionsHandler.handleException(new IndexOutOfBoundsException("Exception INTERNAL_SERVER_ERROR"));
        ResponseEntity expectedResult = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Exception INTERNAL_SERVER_ERROR");

        assertEquals(actualResult, expectedResult);
    }

    @Test
    public void When_HttpStatusINTERNAL_SERVER_ERROR_Except_Exception() {
        ResponseEntity actualResult = ExceptionsHandler.handleException(new Exception("Exception INTERNAL_SERVER_ERROR"));
        ResponseEntity expectedResult = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Exception INTERNAL_SERVER_ERROR");

        assertEquals(actualResult, expectedResult);
    }
}
