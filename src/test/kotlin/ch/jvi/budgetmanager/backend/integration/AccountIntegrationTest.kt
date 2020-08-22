package ch.jvi.budgetmanager.backend.integration

import ch.jvi.budgetmanager.backend.command.domain.account.service.AccountService
import ch.jvi.budgetmanager.backend.command.domain.api.IDProvider.nextId
import ch.jvi.budgetmanager.backend.command.domain.transfer.event.TransferEvent
import ch.jvi.budgetmanager.backend.command.domain.transfer.service.TransferService
import org.assertj.core.api.Assertions.assertThat
import org.junit.Ignore
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.junit.jupiter.DisabledIf
import org.springframework.test.context.junit4.SpringRunner
import java.math.BigDecimal.*

@RunWith(SpringRunner::class)
@SpringBootTest
@ActiveProfiles("integrationTest")
@Ignore
@DisabledIf(value = "true")
// This test has to be re-written for the new way command stores are used
internal class AccountIntegrationTest {

    @Autowired
    lateinit var accountService: AccountService

    @Autowired
    lateinit var transferService: TransferService

    @Test
    fun testAccountCreation() {
        // Given
        val id = nextId
        val balance = TEN
        val name = "Name"

        // When
        accountService.createAccount(balance, name)
        val account = accountService.find(id)

        // Then
        assertThat(account).satisfies {
            assertThat(it.name).isEqualTo(name)
            assertThat(it.balance).isEqualTo(balance)
        }
    }

    @Test
    fun testAccountUpdate() {
        // Given
        val id = nextId
        val balance = TEN
        val name = "Name"
        val newBalance = balance.add(ONE)
        val newName = "NewName"

        // When
        accountService.createAccount(balance, name)
        accountService.updateAccount(id, newBalance, newName)
        val account = accountService.find(id)

        // Then
        assertThat(account).satisfies {
            assertThat(it.name).isEqualTo(newName)
            assertThat(it.balance).isEqualTo(newBalance)
        }
    }

    @Test
    fun testAccountWithTransactions() {
        // Given
        // Create Sender Account
        val senderId = nextId
        val initialSenderBalance = TEN
        val senderAccountName = "Name"
        accountService.createAccount(initialSenderBalance, senderAccountName)

        // Create Recipient Account
        val recipientId = nextId
        val recipientAccountName = "NewName"
        val initialRecipientBalance = TEN
        accountService.createAccount(initialRecipientBalance, recipientAccountName)

        // Create Transfer Update
        val balanceChange = valueOf(5)
        transferService.createTransfer(senderId, "name", recipientId, balanceChange)

        // When
        val account = accountService.find(senderId)
        val otherAccount = accountService.find(recipientId)

        // Then
        assertThat(account).satisfies {
            assertThat(it.name).isEqualTo(senderAccountName)
            assertThat(it.balance).isEqualTo(initialSenderBalance.subtract(balanceChange))
        }
        assertThat(otherAccount).satisfies {
            assertThat(it.name).isEqualTo(recipientAccountName)
            assertThat(it.balance).isEqualTo(initialRecipientBalance.add(balanceChange))
        }
    }

    @Test
    fun testAccountWithUpdatedTransactions() {
        // Given
        // Create Sender Account
        val senderId = nextId
        val initialSenderBalance = TEN
        val senderAccountName = "Name"
        accountService.createAccount(initialSenderBalance, senderAccountName)

        // Create Recipient Account
        val recipientId = nextId
        val recipientAccountName = "NewName"
        val initialRecipientBalance = TEN
        accountService.createAccount(initialRecipientBalance, recipientAccountName)

        // Create Transfer
        val balanceChange = valueOf(5)
        val transferId = nextId
        transferService.createTransfer(senderId, "name", recipientId, balanceChange)


        // Upate Transfer - Switching recipient & sender
        val updateTransferEvent = TransferEvent.UpdateTransferEvent(
            transferId, senderId, recipientId,
            ZERO, recipientId, senderId, ZERO, senderId
        )
        transferService.updateTransfer(updateTransferEvent)

        val account = accountService.find(senderId)
        val otherAccount = accountService.find(recipientId)

        // Then
        assertThat(account).satisfies {
            assertThat(it.name).isEqualTo(senderAccountName)
            assertThat(it.balance).isEqualTo(initialSenderBalance.subtract(balanceChange))
        }
        assertThat(otherAccount).satisfies {
            assertThat(it.name).isEqualTo(recipientAccountName)
            assertThat(it.balance).isEqualTo(initialRecipientBalance.add(balanceChange))
        }
    }

}