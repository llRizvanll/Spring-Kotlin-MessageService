package com.kotlinsamples.messageservice

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.annotation.Id
import org.springframework.data.jdbc.repository.query.Query
import org.springframework.data.relational.core.mapping.Table
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@SpringBootApplication
class MessageServiceApplication

fun main(args: Array<String>) {
    runApplication<MessageServiceApplication>(*args)
}

@RestController
class MessageService (val service: MessageServiceInstance){

    @GetMapping
    fun index() : List<Messages>{
        return service.findMessages();
    }

    @PostMapping
    fun saveMessage(@RequestBody message: Messages): String{
        service.post(message);
        return "success"
    }
}

@Table("MESSAGES")
data class Messages(@Id val id : String?, val text:String)

interface MessageRepository : CrudRepository<Messages,String>{

    @Query("select * from messages")
    fun findMessages(): List<Messages>
}

@Service
class MessageServiceInstance(val db: MessageRepository) {

    fun findMessages(): List<Messages> = db.findMessages()

    fun post(message:Messages){
        db.save(message)
    }
}
