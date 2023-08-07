package com.TruckBooking.operations.Service;

import com.TruckBooking.Booking.Entities.BookingData;
import com.TruckBooking.TruckBooking.Exception.EntityNotFoundException;
import com.TruckBooking.operations.Constants.OpeartionConstants;
import com.TruckBooking.operations.Dao.OperationDao;
import com.TruckBooking.operations.Entities.OperationData;
import com.TruckBooking.operations.Model.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j

public class OperationServiceImpl implements OperationService{
    @Autowired
    OperationDao operationDao;

    private OpeartionConstants constants;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public OperationPostResponse addOperation(OperationPostRequest request) {
        log.info("addOperation service is started");

        String temp = "";
        OperationData operationData = new OperationData();
        OperationPostResponse response = new OperationPostResponse();

        temp = "operation:" + UUID.randomUUID();
        operationData.setOperationId(temp);
        response.setOperationId(temp);
        log.info("operation id generated");

        temp = request.getBookingId().trim();
        operationData.setBookingId(temp);
        response.setBookingID(temp);
        log.info("booking id entered");

        temp = request.getOperation().trim();
        operationData.setOperation(temp);
        response.setOperation(temp);
        log.info("operation entered");

        if(request.getAmount() != null){
            operationData.setAmount(request.getAmount());
            response.setAmount(request.getAmount());
        }
        temp = request.getTransactionType().trim();
        operationData.setTransactionType(temp);
        response.setTransactionType(temp);
        log.info("transaction type entered");

        temp= request.getTransactionDate().trim();
        operationData.setTransactionDate(temp);
        response.setTransactionDate(temp);
        log.info("transaction date entered");

        try {
            operationDao.save(operationData);
            log.info("Operation Data is saved");
        } catch (Exception ex) {
            log.error("Operation Data is not saved -----" + String.valueOf(ex));
            throw ex;
        }

       try {
           log.info("Post Service Response returned");

           return response;
       } catch (Exception ex) {
           log.error("Post Service Response not returned -----" + String.valueOf(ex));
            throw ex;
       }
    }

    @Override
    public OperationPutResponse updateOperation(String operationId, @Valid OperationPutRequest request) {
        log.info("Update Operation Service started");
        OperationPutResponse response = new OperationPutResponse();
        OperationData data = operationDao.findByOperationId(operationId);

        if(data == null){
            EntityNotFoundException ex = new EntityNotFoundException(OperationData.class, "operationId",
                    operationId.toString());

            log.error(String.valueOf(ex));
            throw ex;
        }

        if(request.getAmount() != null){
            data.setAmount(request.getAmount());
        }
        if(request.getOperation()!= null){
            data.setOperation(request.getOperation());
        }
        if(request.getTransactionDate()!=null){
            data.setTransactionDate(request.getTransactionDate());
        }
        if(request.getTransactionType()!= null){
            data.setTransactionType(request.getTransactionType());
        }
        try {
            operationDao.save(data);
            log.info("Operation Data is updated");
        } catch (Exception ex) {
            log.error("Opeartion Data is not updated -----" + String.valueOf(ex));
            throw ex;

        }
        response.setOperationId(data.getOperationId());
        response.setBookingID(data.getBookingId());
        response.setAmount(data.getAmount());
        response.setOperation(data.getOperation());
        response.setTransactionDate(data.getTransactionDate());
        response.setTransactionType(data.getTransactionType());
        try {
            log.info("Put Service Response returned");
            return response;
        } catch (Exception ex) {
            log.error("Put Service Response not returned -----" + String.valueOf(ex));
            throw ex;

        }

    }

    @Override
    public OperationData getDataById(String Id) {
        OperationData operationData = operationDao.findByOperationId(Id);
        if(operationData == null){
            EntityNotFoundException ex = new EntityNotFoundException(OperationData.class,"operationId", Id.toString());
            log.error(String.valueOf(ex));
            throw ex;
        }
        try{
            log.info("Operation Data Returned");
            return operationData;
        } catch(Exception ex){
            log.error("Operation Data not returned ---" + String.valueOf(ex));
            throw ex;
        }
    }


    @Override
    public List<OperationData> getDataById(String operation, String bookingId, String amount, String transactionType, String transactionDate) {

        if ( bookingId != null ){
            try {
                log.info("Operation Data with params returned");
                return operationDao.findByBookingId(bookingId);
            } catch (Exception ex){
                log.error("Operation Data with params not returned -----"+ String.valueOf(ex));
                throw ex;
            }
        }

        if (transactionDate!=null && transactionType !=null){
            try {
                log.info("Operation Data with params returned");
                return operationDao.findByTransactionTypeAndTransactionDate(transactionType, transactionDate);
            } catch (Exception ex){
                log.error("Operation Data with params not returned -----"+ String.valueOf(ex));
                throw ex;
            }
        }

        try{
            log.info("Operation Data with params returned");
            return operationDao.findAll();
        }catch(Exception ex){
            log.error("Operation Data with params not returned -----");
            throw ex;
        }
    }


    @Override
    public OperationDeleteResponse deleteOperation(String operationId) {
        OperationDeleteResponse response = new OperationDeleteResponse();
        OperationData temp = operationDao.findByOperationId(operationId);

        if (temp == null) {
            com.TruckBooking.Booking.Exception.EntityNotFoundException ex = new com.TruckBooking.Booking.Exception.EntityNotFoundException(BookingData.class, "bookingId",
                    operationId.toString());
            log.error(String.valueOf(ex));
            throw ex;
        }

        try {
            operationDao.deleteById(operationId);
            log.info("Deleted");
        } catch (Exception ex) {
            log.error(String.valueOf(ex));
            throw ex;

        }

        response.setStatus(constants.DELETE_SUCCESS);

        try {
            log.info("Deleted Service Response returned");
            return response;
        } catch (Exception ex) {
            log.error("Deleted Service Response not returned -----" + String.valueOf(ex));
            throw ex;

        }

    }
}
