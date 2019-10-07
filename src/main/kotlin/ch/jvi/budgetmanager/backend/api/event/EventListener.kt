package ch.jvi.budgetmanager.core.api

import org.springframework.context.event.EventListener
import org.springframework.scheduling.annotation.Async

/**
 * Annotation for a EventListener.
 *
 * @author J. Villing
 */
@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
@EventListener
@Async
annotation class EventListener