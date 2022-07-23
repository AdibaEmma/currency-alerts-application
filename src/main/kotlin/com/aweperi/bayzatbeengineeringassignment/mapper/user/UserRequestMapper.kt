package com.aweperi.bayzatbeengineeringassignment.mapper.user

import com.aweperi.bayzatbeengineeringassignment.dto.UserRequest
import com.aweperi.bayzatbeengineeringassignment.mapper.Mapper
import com.aweperi.bayzatbeengineeringassignment.model.User
import com.benasher44.uuid.Uuid

class UserRequestMapper: Mapper<UserRequest, User> {
    override fun transform(source: UserRequest): User {
        return User(
            null,
            Uuid.randomUUID().toString(),
            source.firstName,
            source.lastName,
            source.email,
            source.password
        )
    }
}