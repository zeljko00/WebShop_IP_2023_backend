package unibl.etf.ip.webshop_ip2023.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import unibl.etf.ip.webshop_ip2023.model.dto.MessageDTO;
import unibl.etf.ip.webshop_ip2023.services.MessageService;

import java.util.List;

@RestController
@RequestMapping("/messages")
public class MessageController {
    private final MessageService messageService;

    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @GetMapping
    public ResponseEntity<List<MessageDTO>> getAll(){
        return  new ResponseEntity<>(messageService.getAll(), HttpStatus.OK);
    }
    @GetMapping("/filtered")
    public ResponseEntity<List<MessageDTO>> getAllFiltered(@RequestParam("key") String key){
        return  new ResponseEntity<>(messageService.getAllFiltered(key), HttpStatus.OK);
    }
    @PutMapping("/{id}")
    public ResponseEntity<?> read(@PathVariable("id") long id){
        messageService.read(id);
        return  new ResponseEntity<>(HttpStatus.OK);
    }
    @PostMapping
    public ResponseEntity<?> add(@RequestBody MessageDTO messageDTO){
        boolean flag=messageService.add(messageDTO);
        if(flag)
            return  new ResponseEntity<>(HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
