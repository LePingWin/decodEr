package com.fougas.decoder.Service.Interface;

/**
 * Created by Jean on 15/09/2017.
 */

/**
 * Interface to implement the method executing when returning a value of a asynchronous task
 */
public interface IOnTaskCompleted {
    /**
     * Method executed when the task has callback
     * @param value Value return when task is completed
     */
    void onTaskCompleted(Object value);
}
