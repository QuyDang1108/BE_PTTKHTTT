package be.be_new.dto.request;

import be.be_new.enums.EPaymentType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentRequest {
    private String dop; // Ngày thanh toán
    private String status;
    private int reducePrice;
    private EPaymentType paymentType;
    private Integer staffId;
    private Integer formId;
}