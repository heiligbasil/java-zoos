package com.lambdaschool.javazoos.repositories

import com.lambdaschool.javazoos.models.Zoo
import com.lambdaschool.javazoos.views.CountOfAnimalsInZoos
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import java.util.ArrayList

interface Repository : CrudRepository<Zoo, Long>
{
//    SELECT a.animaltype, COUNT(z.zooid) as zoocount
//    FROM zooanimals z
//    INNER JOIN animal a
//    ON z.animalid = a.animalid
//    GROUP BY a.animalid, a.animaltype
//    ORDER BY a.animaltype, a.animalid

    @Query(value = "SELECT a.animaltype, COUNT(z.zooid) as zoocount FROM zooanimals z INNER JOIN animal a ON z.animalid = a.animalid GROUP BY a.animalid, a.animaltype ORDER BY a.animaltype, a.animalid", nativeQuery = true)
    fun getCountOfAnimalsInZoos(): ArrayList<CountOfAnimalsInZoos>
}