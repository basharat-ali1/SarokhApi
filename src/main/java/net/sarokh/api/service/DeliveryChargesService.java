package net.sarokh.api.service;

import net.sarokh.api.dao.DeliveryChargesRepository;
import net.sarokh.api.model.entity.DeliveryCharges;
import net.sarokh.api.util.ApiResponse;
import net.sarokh.api.util.ApplicationMessagesUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeliveryChargesService {

    @Autowired
    private DeliveryChargesRepository repository;

    public ApiResponse add(DeliveryCharges deliveryCharges){

        return ApiResponse.builder()
                .data(repository.save(deliveryCharges))
                .status(HttpStatus.OK.value())
                .message(ApplicationMessagesUtil.DELIVERY_CHARGES_SUCCESS)
                .build();
    }

    public ApiResponse getDeliveryByShipperId(Integer shipperId){
        return ApiResponse.builder()
                .data(repository.findByShipperId(shipperId))
                .status(HttpStatus.OK.value())
                .message(ApplicationMessagesUtil.SUCCESS)
                .build();
    }

    public ApiResponse getDeliveryChargesById(Integer id){
        return ApiResponse.builder()
                .data(repository.findById(id))
                .status(HttpStatus.OK.value())
                .message(ApplicationMessagesUtil.SUCCESS)
                .build();
    }

    public ApiResponse getDeliveryChargesList() {
        return ApiResponse.builder()
                .data(repository.findAll())
                .status(HttpStatus.OK.value())
                .message(ApplicationMessagesUtil.SUCCESS)
                .build();
    }

    public ApiResponse getDeliveryChargesByType(String type) {
        return ApiResponse.builder()
                //.data(repository.findByChargesType(type))
                .status(HttpStatus.OK.value())
                .message(ApplicationMessagesUtil.SUCCESS)
                .build();
    }

    public ApiResponse deleteDeliveryCharges(Integer id) {
        repository.deleteById(id);
        return ApiResponse.builder()
                .data(id + "successfully deleted")
                .status(HttpStatus.OK.value())
                .message(ApplicationMessagesUtil.SUCCESS)
                .build();
    }

}
