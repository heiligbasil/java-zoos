package com.lambdaschool.javazoos.services

import com.lambdaschool.javazoos.models.Animal
import com.lambdaschool.javazoos.models.Zoo
import com.lambdaschool.javazoos.repositories.AnimalRepository
import com.lambdaschool.javazoos.repositories.ZooRepository
import com.lambdaschool.javazoos.views.CountOfAnimalsInZoos
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import javax.persistence.EntityNotFoundException
import org.springframework.transaction.annotation.Transactional

@Service(value = "ztaService")
class ZtaServiceImpl : ZtaService
{
    @Autowired
    lateinit var zooRepository: ZooRepository
    @Autowired
    lateinit var animalRepository: AnimalRepository

    override fun findAll(): MutableList<Zoo>
    {
        val mutableList: MutableList<Zoo> = mutableListOf<Zoo>()
        zooRepository.findAll().toCollection(mutableList)

        return mutableList
    }

    override fun findZooById(zooid: Long): Zoo
    {
        zooRepository.findAll().forEach {

            if (it.zooid == zooid)
            {
                return@findZooById it
            }
        }

        throw EntityNotFoundException("Zoo $zooid not found!")
    }

    override fun findAnimalByType(animaltype: String): Animal
    {

        return animalRepository.findByAnimaltype(animaltype) ?: throw EntityNotFoundException("Animal $animaltype not found!")

        /*animalRepository.findAll().forEach {

            if (it.animaltype == animaltype)
            {
                return@findAnimalByType it
            }
        }

        throw EntityNotFoundException("Animal $animaltype not found!")*/
    }

    @Throws(EntityNotFoundException::class)
    override fun findZooByName(zooname: String): Zoo
    {
        return zooRepository.findByZooname(zooname) ?: throw EntityNotFoundException("Zoo $zooname not found!")
    }


    override fun getCountOfAnimalsInZoos(): MutableList<CountOfAnimalsInZoos>
    {
        return zooRepository.getCountOfAnimalsInZoos()
    }
}

@Service(value = "adminService")
class AdminServiceImpl : AdminService
{
    @Autowired
    lateinit var adminRepository: ZooRepository

    @Transactional
    override fun delete(zooid: Long)
    {
        if(adminRepository.findById(zooid).isPresent)
        {
            adminRepository.deleteZooFromZooAnimals(zooid)
            adminRepository.deleteById(zooid)
        }
        else
        {
            throw EntityNotFoundException(zooid.toString())
        }
    }

    @Transactional
    override fun update(zooData: Zoo, zooid: Long): Zoo
    {
        var zooReplacement: Zoo = adminRepository.findById(zooid).orElseThrow { EntityNotFoundException(zooid.toString()) }

        zooReplacement = zooData.copy()

        return adminRepository.save(zooReplacement)
    }

    @Transactional
    override fun create(zooData: Zoo): Zoo
    {
        val newZoo = Zoo(zooname = zooData.zooname, telephones = zooData.telephones, animals = zooData.animals)

        return adminRepository.save(newZoo)
    }
}