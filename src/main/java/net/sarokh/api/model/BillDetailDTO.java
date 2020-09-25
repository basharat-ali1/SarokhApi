package net.sarokh.api.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@Builder
public class BillDetailDTO {
    private Integer id;
    private String billType;
    private String billCategory;
    private String userType;
    private String billTo;
    private String creationDate;
    private Date startingDate;
    private Date endingDate;
    private Date dueDate;
    private String walletName;
    private String status;
    private String profilePicture;
    List<BillSummaryDTO> billSummary;
}
