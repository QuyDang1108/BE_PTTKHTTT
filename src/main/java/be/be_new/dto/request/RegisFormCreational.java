package be.be_new.dto.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RegisFormCreational {
    private String dor; // ngay dang ki
    private String status;
    private String scheduleId;
    private String userId;
    private String registerId;
}
