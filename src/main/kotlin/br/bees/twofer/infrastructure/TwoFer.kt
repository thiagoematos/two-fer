package br.bees.twofer.infrastructure

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.util.*

@Table(name = "two_fer")
@Entity
class TwoFer(
    @Id val id: UUID,
    val name: String,
    val message: String,
) {
    // generate a default constructor for JPA
    constructor() : this(UUID.randomUUID(), "", "")
}
