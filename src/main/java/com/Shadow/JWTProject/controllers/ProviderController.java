package com.Shadow.JWTProject.controllers;

import com.Shadow.JWTProject.entities.Provider;
import com.Shadow.JWTProject.services.ProviderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/providers")
public class ProviderController {

    private final ProviderService providerService;

    @Autowired
    public ProviderController(ProviderService providerService) {
        this.providerService = providerService;
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("providerForm", new Provider());
        return "providers/provider_form";
    }

    @PostMapping("/save")
    public String saveProvider(@ModelAttribute("providerForm") Provider provider) {
        providerService.create(provider);
        return "redirect:/providers/all";
    }

    @GetMapping("/all")
    public String listProviders(Model model) {
        model.addAttribute("listProviders", providerService.getAll());
        return "providers/providers_list";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Provider provider = providerService.getById(id);
        model.addAttribute("providerForm", provider);
        return "providers/provider_edit_form";
    }

    @PostMapping("/update")
    public String updateProvider(@ModelAttribute("providerForm") Provider provider) {
        providerService.update(provider.getId(), provider);
        return "redirect:/providers/all";
    }

    @GetMapping("/delete/{id}")
    public String deleteProvider(@PathVariable Long id) {
        providerService.delete(id);
        return "redirect:/providers/all";
    }
}
