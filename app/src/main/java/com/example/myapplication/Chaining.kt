package com.example.myapplication

typealias ChainOperationCompletion<T> = (Result<T>) -> Unit
typealias ChainOperation<T, U> = (T, ChainOperationCompletion<U>) -> Unit

infix fun <T, U, V> ChainOperation<T,U>.`ß`(other: ChainOperation<U,V>): ChainOperation<T,V> {

    return { input, combineCompletion ->
        this(input) { result ->
            result.onSuccess { output ->
                other(output, combineCompletion)
            }
            result.onFailure { error ->
                combineCompletion(Result.failure(error))
            }
        }
    }
}