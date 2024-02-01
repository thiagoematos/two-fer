package br.bees.twofer.infrastructure

import br.bees.twofer.infrastructure.TwoFer
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface TwoFerRepository : JpaRepository<TwoFer, UUID> {

    @Query("SELECT t.message FROM TwoFer t WHERE t.name = :name")
    fun getMessageByName(name: String): String?
}
