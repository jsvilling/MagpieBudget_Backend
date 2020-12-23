package ch.jvi.magpie.domain.event.api

/**
 * Marker interface for Events to be sent over a EventBus
 *
 * Events are generated from validated user input. All events are domain specific and must contain generally valid
 * and well formatted values.
 *
 * Events themselfs are domain but not entity specific. They must be used to generate entity specific commands.
 *
 * @author J. Villing
 */
interface Event