package ch.jvi.budgetmanager.backend.core

import org.springframework.stereotype.Service
import java.math.BigDecimal

@Service
class TransferService {

    fun getTransfer(id: String) = print(id)

    fun createTransfer(senderId: String, recipientId: String, amount: BigDecimal) = print(senderId)

    fun updateTransfer(id: String, senderId: String, recipientId: String, amount: BigDecimal) = print(id)

}