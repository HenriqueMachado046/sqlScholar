package br.com.sqlScholar.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import br.com.sqlScholar.model.Tag;
import br.com.sqlScholar.repository.TagRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;




@RestController
@RequestMapping("/tag")
public class TagController {
    @Autowired
    private TagRepository tagRepository;

    @GetMapping("/index")
    public ModelAndView index() {
        Map<String, Object> template = new HashMap<>();
        template.put("arrTag", this.tagRepository.listAll());
        return new ModelAndView("tag/index", template);
    }

    @GetMapping("/tela_adicionar")
    public ModelAndView tela_adicionar(){      
        return new ModelAndView("tag/tela_adicionar", new HashMap<>());
    }

    @PostMapping("/adicionar")
    public ModelAndView adicionar(@ModelAttribute Tag tag){
        this.tagRepository.save(tag);
        Map<String, Object> template = new HashMap<>();
        template.put("message", "Tag cadastrada com sucesso");                
        return new ModelAndView("tag/message", template);
    }

    // pegar este exemplo e fazer para os demais updates
    @RequestMapping("/editar")
    public ModelAndView editar(@RequestParam String name, @RequestParam UUID id){        
        Optional<Tag> tag = this.tagRepository.findById(id);
        tag.get().setId(id);
        tag.get().setName(name);
        this.tagRepository.save(tag.get());
        Map<String, Object> template = new HashMap<>();
        template.put("message", "Tag editada com sucesso!"+name);
        return new ModelAndView("tag/message", template);
    }

    @GetMapping("/tela_editar/{id}")
    public ModelAndView tela_editar(@PathVariable UUID id){
        Map<String, Object> template = new HashMap<>();
        Optional<Tag> tag = this.tagRepository.findById(id);
        template.put("tag", tag.get());
        return new ModelAndView("tag/tela_editar", template);
    }
    
    @GetMapping("/deletar/{id}")
    public ModelAndView deletar(@PathVariable UUID id) {
        this.tagRepository.deleteById(id);
        Map<String, Object> template = new HashMap<>();
        template.put("message", "Tag criada com sucesso!");
        return new ModelAndView("tag/message", template);
    }
    
    @GetMapping("/mostrar_tag/{id}")
    public ModelAndView mostrarTag(@PathVariable UUID id) {
        Map<String, Object> template = new HashMap<>();
        Optional<Tag> tag = this.tagRepository.findById(id);
        template.put("tag", tag.get());
        return new ModelAndView("tag/mostrar_tag", template);
    }    
}