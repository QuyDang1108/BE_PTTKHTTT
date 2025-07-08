package be.be_new.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ScheduleCreationalRequest {
    private String dot;
    private String address;
    private int registrationCount;
    private int maxCount;
    private String nameTest;
    private String categories;
}
