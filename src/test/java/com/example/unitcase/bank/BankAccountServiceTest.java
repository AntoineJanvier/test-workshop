package com.example.unitcase.bank;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.fail;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class BankAccountServiceTest {

    @InjectMocks
    BankAccountService bankAccountService;

    @Mock
    AuthorizationService mockAuthorizationService;

    @Captor
    ArgumentCaptor<Account> accountCaptor;

    @Before
    public void init() {
        when( mockAuthorizationService.isAuthorized(any()) ).thenReturn(true);
    }

    @Test
    public void should_add_money_to_account() {
        int amount = 30;
        Account account = new Account();
        account.setBalance(10);

        bankAccountService.updateMoney(account, amount);

        assertThat(account.getBalance()).isEqualTo(40);
    }

    @Test
    public void should_remove_money_from_account() {
        int amount = -30;
        Account account = new Account();
        account.setBalance(100);

        bankAccountService.updateMoney(account, amount);

        assertThat(account.getBalance()).isEqualTo(70);
    }

    @Test(expected = IllegalArgumentException.class)
    public void should_throw_illegalArgumentException_when_account_is_null() {
        bankAccountService.updateMoney(null, 10);
    }

    @Test(expected = CreditNotAuthorizedException.class)
    public void should_throw_creditNotAuthorizedException_when_account_is_under_zero() {
        int amount = -30;
        Account account = new Account();
        account.setBalance(10);

        bankAccountService.updateMoney(account, amount);
    }

    @Test
    public void should_throw_notAuthorizedException_when_account_is_blocked() {
        when( mockAuthorizationService.isAuthorized(any()) ).thenReturn(false);

        Account account = new Account();

        try {
            bankAccountService.updateMoney(account, 10);
            fail("Should have thrown an exception");
        } catch (NotAuthorizedException ex) {
            verify( mockAuthorizationService ).isAuthorized(any());
        }
    }

    @Test
    public void dummy_test() {
        bankAccountService.doSomething();

        verify( mockAuthorizationService ).doSomething(accountCaptor.capture());
        assertThat(accountCaptor.getValue().getBalance()).isEqualTo(99);
    }
}
