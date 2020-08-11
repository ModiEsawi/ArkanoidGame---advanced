/**
 * part of the listeners package.
 */
package listeners;

/**
 * The HitNotifier interface indicate that objects that implement it send notifications when they are being hit.
 */

public interface HitNotifier {
    /**
     * Adds hl as a listener to hit events.
     *
     * @param hl : the listener the will be added to the listeners list.
     */
    void addHitListener(HitListener hl);

    /**
     * removes hl as a listener to hit events.
     *
     * @param hl : the listener the will be removed from the listeners list.
     */
    void removeHitListener(HitListener hl);
}