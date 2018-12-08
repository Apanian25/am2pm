package com.dawson.jonat.stockers.APIUtil;

/**
 * Interface used for handling Completed task events
 *
 * @author Danny
 */
public interface OnCompleted {
    /**
     *  Method called when task is completed
     * @param response
     */
    void OnTaskCompleted(SimpleAPIResponse response);
}
