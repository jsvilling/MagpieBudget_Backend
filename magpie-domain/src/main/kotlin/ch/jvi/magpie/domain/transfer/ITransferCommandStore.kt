package ch.jvi.magpie.domain.transfer

import ch.jvi.magpie.domain.CommandStore

/**
 * @author J. Villing
 */
interface ITransferCommandStore : CommandStore<TransferCommand.CreateTransferCommand, TransferCommand>
