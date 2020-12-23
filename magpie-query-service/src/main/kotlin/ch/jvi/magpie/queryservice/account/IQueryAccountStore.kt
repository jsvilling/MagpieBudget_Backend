package ch.jvi.magpie.queryservice.account

import ch.jvi.magpie.queryservice.QueryModelStore
import ch.jvi.querydomain.account.QueryAccount

/**
 * @author J. Villing
 */
interface IQueryAccountStore : QueryModelStore<QueryAccount, String>