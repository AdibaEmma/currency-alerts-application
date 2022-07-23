package com.aweperi.bayzatbeengineeringassignment.mapper.user

import com.aweperi.bayzatbeengineeringassignment.dto.UserResponse
import com.aweperi.bayzatbeengineeringassignment.mapper.Mapper
import com.aweperi.bayzatbeengineeringassignment.model.User

class UserResponseMapper: Mapper<User, UserResponse> {
    override fun transform(source: User): UserResponse {
        return UserResponse(
            source.uuid,
            source.firstName,
            source.lastName,
            source.username,
            source.username,
            source.password,
            source.authorities
        )
    }
}