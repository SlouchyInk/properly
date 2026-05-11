package rent.properly.payment;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PaymentMapper {

    @Mapping(target = "leaseId", source = "lease.id")
    PaymentDto toDto(Payment payment);

    @Mapping(target = "lease", ignore = true)
    Payment toEntity(PaymentDto paymentDto);


}