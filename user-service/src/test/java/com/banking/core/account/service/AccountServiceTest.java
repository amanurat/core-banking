
package com.banking.core.account.service;

import com.banking.core.account.dto.AccountResponse;
import com.banking.core.account.dto.CreateAccountRequest;
import com.banking.core.account.entity.Account;
import com.banking.core.account.repository.AccountRepository;
import com.banking.core.userservice.dto.UserDetail;
import java.math.BigDecimal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

class AccountServiceTest {

    @InjectMocks
    private AccountService accountService;

    @Mock
    private AccountRepository accountRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    private static final UserDetail userDetailTeller = UserDetail.builder()
        .role("TELLER")
        .build();

    private CreateAccountRequest sampleRequest() {
        CreateAccountRequest req = new CreateAccountRequest();
        req.setCitizenId("1234567890123");
        req.setThaiName("สมชาย ใจดี");
        req.setEnglishName("Somchai Jaidee");

        return req;
    }

    @Test
    void createAccount_withValidInput_shouldReturnAccountResponse() {
        CreateAccountRequest req = sampleRequest();


        when(accountRepository.existsByAccountNumber(anyString())).thenReturn(false);

        Account savedAccount = Account.builder()
                .id(1L)
                .accountNumber("1234567")
                .citizenId(req.getCitizenId())
                .build();

        when(accountRepository.save(any(Account.class))).thenReturn(savedAccount);

        AccountResponse response = accountService.createAccount(req, userDetailTeller);

        assertThat(response).isNotNull();
        assertThat(response.getAccountNumber()).isEqualTo("1234567");
        assertThat(response.getBalance()).isEqualTo(BigDecimal.ZERO);

    }    @Test
    void createAccount_shouldGenerateUniqueAccountNumber() {
        CreateAccountRequest req = sampleRequest();

        // simulate that first generated account number already exists
        when(accountRepository.existsByAccountNumber("0000001")).thenReturn(true);
        when(accountRepository.existsByAccountNumber("0000002")).thenReturn(false);

        Account savedAccount = Account.builder()
                .id(2L)
                .accountNumber("0000002")
                .citizenId(req.getCitizenId())
                .build();

        when(accountRepository.save(any(Account.class))).thenReturn(savedAccount);

        AccountResponse response = accountService.createAccount(req, userDetailTeller);

        assertThat(response.getAccountNumber()).isNotNull();
        assertThat(response.getAccountNumber()).isEqualTo("0000002");
    }

    @Test
    void createAccount_withDuplicateCitizenId_shouldThrowException() {
        CreateAccountRequest req = sampleRequest();

        when(accountRepository.existsByAccountNumber(anyString())).thenReturn(false);

        // Simulate duplicate citizenId logic – might have your own check in real service
        doThrow(new RuntimeException("Citizen ID already used")).when(accountRepository)
            .save(argThat(account -> "1234567890123".equals(account.getCitizenId())));

        assertThatThrownBy(() -> {
            accountService.createAccount(req, userDetailTeller);
        })
            .isInstanceOf(RuntimeException.class)
            .hasMessage("Citizen ID already used");
    }

   /* @Test
    void createAccount_withNullInitialDeposit_shouldDefaultToZero() {
        CreateAccountRequest req = sampleRequest();
        req.setInitialDeposit(null);

        when(accountRepository.existsByAccountNumber(anyString())).thenReturn(false);

        Account savedAccount = Account.builder()
            .id(3L)
            .accountNumber("0000003")
            .citizenId(req.getCitizenId())
            .balance(0L)
            .build();

        when(accountRepository.save(any(Account.class))).thenReturn(savedAccount);

        AccountResponse response = accountService.createAccount(req, "TELLER");

        assertThat(response.getBalance()).isEqualTo(0L);
    }*/

}
