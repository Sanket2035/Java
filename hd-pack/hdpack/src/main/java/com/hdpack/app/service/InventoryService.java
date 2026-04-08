package com.hdpack.app.service;

import com.hdpack.app.model.Ink;
import com.hdpack.app.model.Paper;
import com.hdpack.app.model.Punch;
import com.hdpack.app.repository.InkRepository;
import com.hdpack.app.repository.PaperRepository;
import com.hdpack.app.repository.PunchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class InventoryService {

    @Autowired private PaperRepository paperRepository;
    @Autowired private InkRepository inkRepository;
    @Autowired private PunchRepository punchRepository;

    // <--- PAPER --->
    public Paper addPaper(Paper paper) {
        if (paperRepository.existsByBrandAndWidth(paper.getBrand(), paper.getWidth())) {
            throw new RuntimeException("Paper with this Brand and Dimension already exists!");
        }
        return paperRepository.save(paper);
    }
    
    public List<Paper> getAllPapers() { return paperRepository.findAll(); }

    // <--- INK --->
    public Ink addInk(Ink ink) { return inkRepository.save(ink); }
    
    public List<Ink> getAllInks() { return inkRepository.findAll(); }

    // <--- PUNCH --->
    public Punch addPunch(Punch punch) { return punchRepository.save(punch); }
    
    public List<Punch> getAllPunches() { return punchRepository.findAll(); }
}