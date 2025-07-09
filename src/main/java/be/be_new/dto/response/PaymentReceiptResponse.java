package be.be_new.dto.response;

import be.be_new.enums.EPaymentType;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PaymentReceiptResponse {
    private Integer id;
    private float money;
    private String dop; // ngày thanh toán
    private String status;
    private EPaymentType paymentType;
    private Integer formId;
    private String staffName;
}