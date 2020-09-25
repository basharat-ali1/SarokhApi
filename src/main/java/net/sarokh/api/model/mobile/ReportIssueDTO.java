package net.sarokh.api.model.mobile;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class ReportIssueDTO {
    private String trackingNumber;
    private String complaintAgainst;
    private String complaintAgainstName;
    private String complaintComments;

}
