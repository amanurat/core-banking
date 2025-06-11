package com.banking.core.userservice.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.banking.core.userservice.dto.RegisterRequest;
import com.banking.core.userservice.dto.UserResponse;
import com.banking.core.userservice.entity.User;
import com.banking.core.userservice.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

//write unit test for RegisterService class
class RegisterServiceTest {

  @InjectMocks RegisterService registerService;

  @Mock UserRepository userRepository;
  @Mock PasswordEncoder passwordEncoder;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  private RegisterRequest createRequest() {
    RegisterRequest req = new RegisterRequest();
    req.setEmail("valid@example.com");
    req.setPassword("StrongPass123");
    req.setCitizenId("1234567890123");
    req.setThaiName("สมชาย");
    req.setEnglishName("Somchai");
    req.setPin("123456");
    return req;
  }

  //register with valid input then return status 200
  @Test
  void registerWithValidInput_thenReturnStatus200() {

   RegisterRequest request = createRequest();

    when(userRepository.existsByEmail(request.getEmail())).thenReturn(false);
    when(userRepository.existsByCitizenId(request.getCitizenId())).thenReturn(false);
    when(passwordEncoder.encode(any())).thenReturn("hashed");
    User savedUser = User.builder()
        .id(1L)
        .email(request.getEmail())
        .role("CUSTOMER")
        .build();

    when(userRepository.save(any(User.class))).thenReturn(savedUser);

    UserResponse response = registerService.register(request);

    Assertions.assertThat(response).isNotNull();
    Assertions.assertThat(response.getId()).isEqualTo(1L);
    Assertions.assertThat(response.getRole()).contains("CUSTOMER");

    verify(userRepository).save(any(User.class));
  }

  //register with duplicate email then throw EmailAlreadyExistsException
  @Test
  void register_EmailExists_ThrowsException() {
    RegisterRequest request = createRequest();

    when(userRepository.existsByEmail(request.getEmail())).thenReturn(true);

    RuntimeException exception = assertThrows(RuntimeException.class, () -> {
      registerService.register(request);
    });

    Assertions.assertThat(exception.getMessage()).isEqualTo("Email already exists");
  }


  //register with duplicate citizen then throw CitizenIdAlreadyExistsException
  @Test
  void register_CitizenIdExists_ThrowsException() {
    RegisterRequest request = createRequest();

    when(userRepository.existsByEmail(request.getEmail())).thenReturn(false);
    when(userRepository.existsByCitizenId(request.getCitizenId())).thenReturn(true);

    RuntimeException exception = assertThrows(RuntimeException.class, () -> {
      registerService.register(request);
    });

    Assertions.assertThat(exception.getMessage()).isEqualTo("Citizen ID already exists", exception.getMessage());
  }
}