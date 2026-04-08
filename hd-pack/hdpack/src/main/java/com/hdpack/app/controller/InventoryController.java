package com.hdpack.app.controller;

import com.hdpack.app.model.Ink;
import com.hdpack.app.model.Paper;
import com.hdpack.app.model.Punch;
import com.hdpack.app.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/inventory")
public class InventoryController {

    @Autowired
    private InventoryService inventoryService;

    // <--- PAPER ENDPOINTS --->
    @PostMapping("/paper")
    public Paper addPaper(@RequestBody Paper paper) {
        return inventoryService.addPaper(paper);
    }
    
    @GetMapping("/paper")
    public List<Paper> getPapers() {
        return inventoryService.getAllPapers();
    }

    // <--- INK ENDPOINTS --->
    @PostMapping("/ink")
    public Ink addInk(@RequestBody Ink ink) {
        return inventoryService.addInk(ink);
    }
    
    @GetMapping("/ink")
    public List<Ink> getInks() {
        return inventoryService.getAllInks();
    }

    // <--- PUNCH ENDPOINTS --->
    @PostMapping("/punch")
    public Punch addPunch(@RequestBody Punch punch) {
        return inventoryService.addPunch(punch);
    }
    
    @GetMapping("/punch")
    public List<Punch> getPunches() {
        return inventoryService.getAllPunches();
    }
}