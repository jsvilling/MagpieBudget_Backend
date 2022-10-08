package ch.jvi.magpie.core.domain.transfer

import ch.jvi.magpie.core.api.CommandStore

/**
 * @author J. Villing
 */
interface ITransferCommandStore : CommandStore<TransferCommand.CreateTransferCommand, TransferCommand>
