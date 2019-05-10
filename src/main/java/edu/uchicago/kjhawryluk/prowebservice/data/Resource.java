package edu.uchicago.kjhawryluk.prowebservice.data;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import edu.uchicago.kjhawryluk.prowebservice.data.Status;

import static edu.uchicago.kjhawryluk.prowebservice.data.Status.*;

/**
 * A generic class that holds a value with its loading status.
 * Taken from roomMovies
 * @param <T>
 */
public class Resource<T> {
    @NonNull
    public final Status status;
    @Nullable
    public final T data;
    @Nullable
    public final String message;
    private Resource(@NonNull Status status, @Nullable T data, @Nullable String message) {
        this.status = status;
        this.data = data;
        this.message = message;
    }

    public static <T> Resource<T> success(@NonNull T data) {
        return new Resource<>(SUCCESS, data, null);
    }

    public static <T> Resource<T> error(String msg, @Nullable T data) {
        return new Resource<>(ERROR, data, msg);
    }

    public static <T> Resource<T> loading(@Nullable T data) {
        return new Resource<>(LOADING, data, null);
    }
}
