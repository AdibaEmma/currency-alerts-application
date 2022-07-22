package com.aweperi.bayzatbeengineeringassignment.repository

import com.aweperi.bayzatbeengineeringassignment.model.Alert
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface AlertRepository: JpaRepository<Alert, Long>