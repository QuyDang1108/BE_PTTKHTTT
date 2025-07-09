package be.be_new.configuaration;

import be.be_new.entity.Staff;
import be.be_new.enums.EStaffRole;
import be.be_new.repository.StaffRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@Slf4j
public class ApplicationConfig {
    PasswordEncoder passwordEncoder;
    ApplicationConfig(PasswordEncoder passwordEncoder){
        this.passwordEncoder = passwordEncoder;
    }
    @Bean
    ApplicationRunner applicationRunner(StaffRepository userRepository){
        return args -> {
            if(userRepository.findByEmail("admin@gmail.com").isEmpty()){
                var role = EStaffRole.QUAN_LY;
                Staff user = Staff.builder()
                        .name("admin")
                        .phone("123456789")
                        .password(passwordEncoder.encode("123"))
                        .role(role)
                        .address("Ho Chi Minh")
                        .description("Admin cap cao nhat")
                        .dob("1980-11-11")
                        .email("admin@gmail.com")
                        .build();
                userRepository.save(user);
                log.warn("""
                        admin user has been created with information:\
                        
                        email: admin@gmail.com\
                        
                        password: 123""");
            }
        };
    }

    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        return mapper;
    }
}
