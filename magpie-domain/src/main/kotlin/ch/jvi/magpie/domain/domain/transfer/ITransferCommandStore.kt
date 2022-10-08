package ch.jvi.magpie.domain.domain.transfer

import ch.jvi.magpie.domain.api.CommandStore

/**
 * @author J. Villing
 */
interface ITransferCommandStore : CommandStore<TransferCommand.CreateTransferCommand, TransferCommand>
