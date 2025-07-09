package be.be_new.services;

import be.be_new.dto.request.PaymentRequest;
import be.be_new.dto.response.PaymentReceiptResponse;
import be.be_new.entity.PaymentReceipt;
import be.be_new.entity.RegisForm;
import be.be_new.entity.Staff;
import be.be_new.exception.AppException;
import be.be_new.exception.ErrorCode;
import be.be_new.repository.PaymentReceiptRepository;
import be.be_new.repository.RegisFormRepository;
import be.be_new.repository.StaffRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PaymentReceiptService {

    private final PaymentReceiptRepository paymentReceiptRepository;
    private final RegisFormRepository regisFormRepository;
    private final StaffRepository staffRepository;

    @Transactional
    public PaymentReceiptResponse createFromRegisForm(PaymentRequest request) {
        RegisForm regisForm = regisFormRepository.findById(request.getFormId())
                .orElseThrow(() -> new AppException(ErrorCode.NOT_FOUND_DATA));

        Staff staff = staffRepository.findById(request.getStaffId())
                .orElseThrow(() -> new AppException(ErrorCode.NOT_FOUND_DATA));

        PaymentReceipt receipt = new PaymentReceipt();
        receipt.setMoney(request.getMoney());
        receipt.setDop(request.getDop());
        receipt.setStatus(request.getStatus());
        receipt.setPaymentType(request.getPaymentType());
        receipt.setRegisForm(regisForm);
        receipt.setStaff(staff);

        PaymentReceipt saved = paymentReceiptRepository.save(receipt);
        return toResponse(saved);
    }

    public PaymentReceiptResponse getByRegisFormId(Integer formId) {
        RegisForm form = regisFormRepository.findById(formId)
                .orElseThrow(() -> new AppException(ErrorCode.NOT_FOUND_DATA));

        PaymentReceipt receipt = paymentReceiptRepository.findByRegisForm(form)
                .orElseThrow(() -> new AppException(ErrorCode.NOT_FOUND_DATA));

        return toResponse(receipt);
    }

    public List<PaymentReceiptResponse> getAll() {
        return paymentReceiptRepository.findAll().stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    private PaymentReceiptResponse toResponse(PaymentReceipt receipt) {
        return PaymentReceiptResponse.builder()
                .id(receipt.getId())
                .money(receipt.getMoney())
                .dop(receipt.getDop())
                .status(receipt.getStatus())
                .paymentType(receipt.getPaymentType())
                .staffName(receipt.getStaff().getName())
                .formId(receipt.getRegisForm().getId())
                .build();
    }
}
