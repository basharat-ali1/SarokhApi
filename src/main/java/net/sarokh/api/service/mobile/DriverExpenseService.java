package net.sarokh.api.service.mobile;

import net.sarokh.api.dao.DriverExpenseRepository;
import net.sarokh.api.model.entity.DriverExpense;
import net.sarokh.api.util.ApiResponse;
import net.sarokh.api.util.ApplicationMessagesUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class DriverExpenseService {

    @Autowired
    private DriverExpenseRepository repository;

    public ApiResponse addDriverExpense(DriverExpense driverExpense){
        return ApiResponse.builder()
                .data(repository.save(driverExpense))
                .status(HttpStatus.OK.value())
                .message(ApplicationMessagesUtil.DRIVER_EXPENSE_SUCCESSFULLY_CREATED)
                .build();
    }

    public ApiResponse getDriverExpenseById(Integer id){
        return ApiResponse.builder()
                .data(repository.findById(id))
                .status(HttpStatus.OK.value())
                .message(ApplicationMessagesUtil.SUCCESS)
                .build();
    }

    public ApiResponse getDriverExpensesList() {
        return ApiResponse.builder()
                .data(repository.findAll())
                .status(HttpStatus.OK.value())
                .message(ApplicationMessagesUtil.SUCCESS)
                .build();
    }

    public ApiResponse updateDriverExpense(DriverExpense expense){
        return ApiResponse.builder()
                .data(repository.save(expense))
                .status(HttpStatus.OK.value())
                .message(ApplicationMessagesUtil.DRIVER_EXPENSE_SUCCESSFULLY_CREATED)
                .build();
    }

    public ApiResponse deleteDriverExpense(Integer id){
        repository.deleteById(id);
        return ApiResponse.builder()
                .data("Driver expense successfully deleted.")
                .status(HttpStatus.OK.value())
                .message(ApplicationMessagesUtil.SUCCESS)
                .build();
    }

    
}
