package br.edu.sgpd.web;

import br.edu.sgpd.model.AppUser;
import br.edu.sgpd.model.Collaborator;
import br.edu.sgpd.repository.AppUserRepository;
import br.edu.sgpd.repository.CollaboratorRepository;
import jakarta.validation.Valid;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/colaboradores")
public class CollaboratorController {
    private final CollaboratorRepository records;
    private final AppUserRepository users;
    public CollaboratorController(CollaboratorRepository records, AppUserRepository users) {
        this.records = records; this.users = users;
    }
    @GetMapping public String list(@RequestParam(defaultValue = "") String q, Model model) {
        model.addAttribute("query", q);
        model.addAttribute("collaborators", q.isBlank() ? records.findAll()
                : records.findTop100ByNameContainingIgnoreCaseOrEmailContainingIgnoreCaseOrderByNameAsc(q, q));
        return "collaborators/list";
    }
    @GetMapping("/novo") public String createForm(Model model) {
        model.addAttribute("form", new CollaboratorForm()); model.addAttribute("pageTitle", "Novo colaborador");
        model.addAttribute("action", "/colaboradores"); return "collaborators/form";
    }
    @PostMapping public String create(@Valid @ModelAttribute("form") CollaboratorForm form, BindingResult errors,
            @AuthenticationPrincipal UserDetails principal, Model model, RedirectAttributes redirect) {
        if (form.getEmail() != null && records.existsByEmailIgnoreCase(form.getEmail()))
            errors.rejectValue("email", "duplicate", "Este e-mail já está cadastrado.");
        if (errors.hasErrors()) {
            model.addAttribute("pageTitle", "Novo colaborador"); model.addAttribute("action", "/colaboradores");
            return "collaborators/form";
        }
        AppUser creator = users.findByEmailIgnoreCase(principal.getUsername()).orElseThrow();
        records.save(Collaborator.create(form.getName(), form.getEmail(), form.getPhone(), form.getJobTitle(),
                form.getHireDate(), form.isActive(), creator));
        redirect.addFlashAttribute("success", "Colaborador cadastrado com sucesso.");
        return "redirect:/colaboradores";
    }
    @GetMapping("/{id}") public String detail(@PathVariable Long id, Model model) {
        model.addAttribute("collaborator", records.findById(id).orElseThrow()); return "collaborators/detail";
    }
    @GetMapping("/{id}/editar") public String editForm(@PathVariable Long id, Model model) {
        Collaborator item = records.findById(id).orElseThrow(); CollaboratorForm form = new CollaboratorForm();
        form.setName(item.getName()); form.setEmail(item.getEmail()); form.setPhone(item.getPhone());
        form.setJobTitle(item.getJobTitle()); form.setHireDate(item.getHireDate()); form.setActive(item.isActive());
        model.addAttribute("form", form); model.addAttribute("pageTitle", "Editar colaborador");
        model.addAttribute("action", "/colaboradores/" + id); return "collaborators/form";
    }
    @PostMapping("/{id}") public String update(@PathVariable Long id,
            @Valid @ModelAttribute("form") CollaboratorForm form, BindingResult errors,
            Model model, RedirectAttributes redirect) {
        Collaborator item = records.findById(id).orElseThrow();
        if (form.getEmail() != null && records.existsByEmailIgnoreCaseAndIdNot(form.getEmail(), id))
            errors.rejectValue("email", "duplicate", "E-mail já usado por outro cadastro.");
        if (errors.hasErrors()) {
            model.addAttribute("pageTitle", "Editar colaborador"); model.addAttribute("action", "/colaboradores/" + id);
            return "collaborators/form";
        }
        item.update(form.getName(), form.getEmail(), form.getPhone(), form.getJobTitle(), form.getHireDate(), form.isActive());
        records.save(item); redirect.addFlashAttribute("success", "Dados do colaborador atualizados.");
        return "redirect:/colaboradores/" + id;
    }
}
