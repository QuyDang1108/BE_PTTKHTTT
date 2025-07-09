package be.be_new.repository;

import be.be_new.entity.PaymentReceipt;
import be.be_new.entity.RegisForm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PaymentReceiptRepository extends JpaRepository<PaymentReceipt,Integer> {
    @Override
    Optional<PaymentReceipt> findById(Integer integer);

    Optional<PaymentReceipt> findByRegisForm(RegisForm regisForm);
}
