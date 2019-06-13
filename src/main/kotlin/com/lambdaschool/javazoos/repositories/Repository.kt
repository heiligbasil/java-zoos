package com.lambdaschool.javazoos.repositories

import com.lambdaschool.javazoos.models.Animal
import com.lambdaschool.javazoos.models.Zoo
import com.lambdaschool.javazoos.views.CountOfAnimalsInZoos
import com.lambdaschool.javazoos.views.CountThem
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import java.util.ArrayList

interface ZooRepository : CrudRepository<Zoo, Long>
{
//    SELECT a.animaltype, COUNT(z.zooid) as zoocount
//    FROM zooanimals z
//    INNER JOIN animal a
//    ON z.animalid = a.animalid
//    GROUP BY a.animalid, a.animaltype
//    ORDER BY a.animaltype, a.animalid

    @Query(value = "SELECT a.animaltype, COUNT(z.zooid) as zoocount FROM zooanimals z INNER JOIN animal a ON z.animalid = a.animalid GROUP BY a.animalid, a.animaltype ORDER BY a.animaltype, a.animalid", nativeQuery = true)
    fun getCountOfAnimalsInZoos(): ArrayList<CountOfAnimalsInZoos>

    fun findByZooname(name: String): Zoo

    @Modifying
    @Query(value = "DELETE FROM zooanimals WHERE zooid = :zooid", nativeQuery = true)
    fun deleteZooFromZooAnimals(zooid: Long)

    @Modifying
    @Query(value = "DELETE FROM zooanimals WHERE zooid = :zooid AND animalid = :animalid", nativeQuery = true)
    fun deleteZooAnimalCombo(zooid: Long, animalid: Long)

    @Query(value = "SELECT COUNT(*) as count FROM zooanimals WHERE zooid = :zooid AND animalid = :animalid", nativeQuery = true)
    fun checkZooAnimalCombo(zooid: Long, animalid: Long): CountThem

    @Modifying
    @Query(value = "INSERT INTO zooanimals (zooid, animalid) VALUES (:zooid, :animalid)", nativeQuery = true)
    fun saveZooAnimalCombo(zooid: Long, animalid: Long)
}

interface AnimalRepository : CrudRepository<Animal, Long>
{
    fun findByAnimaltype(animaltype: String): Animal
}