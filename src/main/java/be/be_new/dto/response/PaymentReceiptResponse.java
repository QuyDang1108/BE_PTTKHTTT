package be.be_new.dto.response;

import be.be_new.enums.EPaymentType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentReceiptResponse {
    private Integer id;
    private int money;
    private String dop; // ngày thanh toán
    private String status;
    private EPaymentType paymentType;
    private Integer formId;
    private String staffName;
}