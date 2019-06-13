package com.lambdaschool.javazoos.models

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import javax.persistence.*

@Entity
@Table(name = "zoo")
data class Zoo(
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        val zooid: Long = 0,
        var zooname: String = "",
        @OneToMany(mappedBy = "zoo")
        val telephones: MutableList<Telephone> = mutableListOf(),
        @ManyToMany
        @JoinTable(name = "zooanimals", joinColumns = [JoinColumn(name = "zooid")], inverseJoinColumns = [JoinColumn(name = "animalid")])
        @JsonIgnoreProperties(value = ["zoos"])
        val animals: MutableList<Animal> = mutableListOf()
)

@Entity
@Table(name = "telephone")
data class Telephone(
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        val phoneid: Long,
        var phonetype: String,
        var phonenumber: String,
        @ManyToOne
        @JoinColumn(name = "zooid", nullable = false)
        @JsonIgnoreProperties(value = ["telephones"])
        var zoo: Zoo? = null
)

@Entity
@Table(name = "animal")
data class Animal(
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        val animalid: Long,
        var animaltype: String,
        @ManyToMany(mappedBy = "animals")
        @JsonIgnoreProperties(value = ["animals"])
        val zoos: MutableList<Zoo> = mutableListOf()
)