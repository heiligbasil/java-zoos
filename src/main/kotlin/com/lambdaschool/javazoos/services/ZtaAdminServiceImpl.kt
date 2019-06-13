package com.lambdaschool.javazoos.services

import com.lambdaschool.javazoos.models.Zoo
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import javax.persistence.EntityNotFoundException

@Service(value = "ztaService")
class ZtaServiceImpl : ZtaService
{
    @Autowired
    lateinit var ztaRepository: Repository

    override fun findAll(): MutableList<Zoo>
    {
        val mutableList: MutableList<Zoo> = mutableListOf<Zoo>()
        ztaRepository.findAll().toCollection(mutableList)

        return mutableList
    }

    override fun findZooById(zooid: Long): Zoo
    {
        ztaRepository.findAll().forEach {
            if (it.zooid == zooid)
            {
                return@findZooById it
            }
        }

        throw EntityNotFoundException("Zoo $zooid not found!")
    }

    override fun getCountOfAnimalsInZoos(): MutableList<CountOfAnimalsInZoos>
    {
        return ztaRepository.getCountOfAnimalsInZoos()
    }
}

@Service(value = "adminService")
class AdminServiceImpl : AdminService
{
    @Autowired
    lateinit var adminRepository: Repository

    override fun delete(zooid: Long)
    {
        adminRepository.findById(zooid).orElseThrow { EntityNotFoundException(zooid.toString()) }

        adminRepository.deleteById(zooid)
    }

    override fun update(zooData: Zoo, zooid: Long): Zoo
    {
        var zooReplacement: Zoo = adminRepository.findById(zooid).orElseThrow { EntityNotFoundException(zooid.toString()) }

        zooReplacement = zooData.copy()

        return adminRepository.save(zooReplacement)
    }

    override fun create(zooData: Zoo): Zoo
    {
        val newZoo = Zoo(zooname = zooData.zooname, telephones = zooData.telephones, animals = zooData.animals)

        return adminRepository.save(newZoo)
    }
}