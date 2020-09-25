package net.sarokh.api.service;

import net.sarokh.api.dao.CityRepository;
import net.sarokh.api.dao.ShipmentIssueReportRepository;
import net.sarokh.api.model.entity.City;
import net.sarokh.api.model.entity.ShipmentIssueReport;
import net.sarokh.api.util.ApiResponse;
import net.sarokh.api.util.ApplicationMessagesUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class ShipmentIssueReportService {

    @Autowired
    private ShipmentIssueReportRepository repository;

    public ApiResponse add(ShipmentIssueReport report){
        return ApiResponse.builder()
                .data(repository.save(report))
                .status(HttpStatus.OK.value())
                .message(ApplicationMessagesUtil.REPORT_COMPLAINT_SUCCESSFULLY)
                .build();
    }

    public ApiResponse getIssueReportById(Integer id){
        return ApiResponse.builder()
                .data(repository.findById(id))
                .status(HttpStatus.OK.value())
                .message(ApplicationMessagesUtil.SUCCESS)
                .build();
    }


    public ApiResponse getIssueReportsList() {
        return ApiResponse.builder()
                .data(repository.findAll())
                .status(HttpStatus.OK.value())
                .message(ApplicationMessagesUtil.SUCCESS)
                .build();
    }

    public ApiResponse deleteIssueReport(Integer id) {
        repository.deleteById(id);
        return ApiResponse.builder()
                .data(id + "successfully deleted")
                .status(HttpStatus.OK.value())
                .message(ApplicationMessagesUtil.SUCCESS)
                .build();
    }

}
