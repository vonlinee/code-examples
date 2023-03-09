package org.example.controller;

import org.example.bean.Pet;
import org.springframework.web.bind.annotation.*;

@RestController
public class RestfulController {

    @GetMapping("restful/owners/{ownerId}/pets/{petId}")
    public void get(@PathVariable String ownerId, @PathVariable String petId) {
        System.out.println("ownerId:" + ownerId);
        System.out.println("petId:" + petId);
    }

    @GetMapping("restful2/owners/{ownerId}/pets/{petId}")
    public void get(@ModelAttribute Pet pet) {
        System.out.println("ownerId:" + pet.getOwnerId());
        System.out.println("petId:" + pet.getPetId());
    }

}
