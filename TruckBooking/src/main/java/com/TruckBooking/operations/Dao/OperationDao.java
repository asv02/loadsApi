package com.TruckBooking.operations.Dao;


import com.TruckBooking.operations.Entities.OperationData;
import com.TruckBooking.operations.Model.OperationPostResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;



import java.awt.print.Pageable;
import java.util.List;

@Repository

public interface OperationDao extends JpaRepository<OperationData, String> {
    List<OperationData> findByBookingId(String bookingId);

    OperationData findByOperationId(String id);

    List<OperationData> findByTransactionTypeAndTransactionDate(String transactionType, String transactionDate);

}
