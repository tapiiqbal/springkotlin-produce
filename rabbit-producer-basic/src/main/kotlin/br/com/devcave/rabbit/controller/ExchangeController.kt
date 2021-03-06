package br.com.devcave.rabbit.controller

import br.com.devcave.rabbit.domain.Person
import org.slf4j.LoggerFactory
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.http.HttpEntity
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("exchanges")
class ExchangeController(
    private val rabbitTemplate: RabbitTemplate
) {

    private val log = LoggerFactory.getLogger(javaClass)

    @PostMapping("{exchange}/{routingKey}")
    fun postOnExchange(
        @PathVariable exchange: String,
        @PathVariable routingKey: String,
        @RequestBody message: Object
    ): HttpEntity<Any?> {
//        for(x in 0..500){
            log.info("sending message $message")
            rabbitTemplate.convertAndSend(exchange, routingKey, message);
//        }
        return ResponseEntity.ok().build()
    }

    @PostMapping("json/{exchange}/{routingKey}")
    fun postJsonOnExchange(
        @PathVariable exchange: String,
        @PathVariable routingKey: String,
        @RequestBody message: Person
    ): HttpEntity<Any?> {
        log.info("sending message ${message.active}")
        rabbitTemplate.convertAndSend(exchange, routingKey, message)
        return ResponseEntity.ok().build()
    }
}
