package be.be_new.dto.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PaymentRequest {
    private float money;
    private String doc;
    private String dop;
    private String status;
    private String lnk;
    private String userId;
    private String formId;
}
