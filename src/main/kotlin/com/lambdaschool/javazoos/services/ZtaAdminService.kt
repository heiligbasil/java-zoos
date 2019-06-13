package com.lambdaschool.javazoos.services

import com.lambdaschool.javazoos.models.Animal
import com.lambdaschool.javazoos.models.Zoo
import com.lambdaschool.javazoos.views.CountOfAnimalsInZoos

interface ZtaService
{
    fun findAll(): MutableList<Zoo>

    fun findZooById(zooid: Long): Zoo

    fun findAnimalByType(animaltype: String): Animal

    fun getCountOfAnimalsInZoos(): MutableList<CountOfAnimalsInZoos>
}

interface AdminService
{
    fun delete(zooid: Long)

    fun update(zooData: Zoo, zooid: Long): Zoo

    fun create(zooData: Zoo): Zoo
}