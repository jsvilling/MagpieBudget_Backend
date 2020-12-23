package ch.jvi.magpie.commandservice

import ch.jvi.magpie.domain.transfer.TransferCommand

/**
 * @author J. Villing
 */
interface ITransferCommandStore : CommandStore<TransferCommand.CreateTransferCommand, TransferCommand>