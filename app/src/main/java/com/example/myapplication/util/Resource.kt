package com.example.myapplication.util

class Resource<out T> private constructor(val status: Status, val data: T?, val message: String?) {
    companion object {
        fun <T> success(data: T?): Resource<T> {
            return Resource(
                Status.SUCCESS,
                data,
                null
            )
        }

        fun <T> error(msg: String?, data: T? = null): Resource<T> {
            return Resource(
                Status.ERROR,
                data,
                msg
            )
        }

        fun <T> loading(): Resource<T> {
            return Resource(
                Status.LOADING,
                null,
                null
            )
        }
    }
}

fun <T> handleResource(
    res: Resource<T>,
    onLoading: (data: T?) -> Unit = { _ -> },
    onSuccess: (data: T?) -> Unit = { _ -> },
    onError: (message: String?, data: T?) -> Unit = { _, _ -> }
) {
    when (res.status) {
        Status.LOADING -> onLoading(res.data)
        Status.SUCCESS -> onSuccess(res.data)
        Status.ERROR -> onError(res.message, res.data)
        else -> {}
    }
}