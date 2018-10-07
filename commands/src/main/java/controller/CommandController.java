package Uberoo.controller;

import  Uberoo.entity.Command;
import  Uberoo.CommandRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CommandController {

    @Autowired
    private CommandRepository commandRepository;


    @PostMapping("/commands")
    public Command create(@RequestBody Command command)
    {
        return commandRepository.save(command);
    }


    @GetMapping("/commands")
    public List<Command> findAll()
    {
        return commandRepository.findAll();
    }


    @PutMapping("/commands/{command_id}")
    public Command update(@PathVariable("command_id") Long commandId, @RequestBody Command commandObject)
    {
        Command command = commandRepository.findOne(commandId);
        command.setName(commandObject.getName());
        command.setFood_id(commandObject.getFood_id())
        return userRepository.save(user);
    }



    @DeleteMapping("/commands/{command_id}")
    public List<Command> delete(@PathVariable("command_id") Long commandId)
    {
        commandRepository.delete(commandId);
        return commandRepository.findAll();
    }



    @GetMapping("/commands/{command_id}")
    @ResponseBody
    public User findByCommandId(@PathVariable("command_id") Long commandId)
    {
        return commandRepository.findOne(commandId);
    }
}