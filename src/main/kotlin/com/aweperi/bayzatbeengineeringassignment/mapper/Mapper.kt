package com.aweperi.bayzatbeengineeringassignment.mapper

interface Mapper<S, R> {
    fun transform(source: S): R
}