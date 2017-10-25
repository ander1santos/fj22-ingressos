package br.com.caelum.ingresso.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import br.com.caelum.ingresso.dao.FilmeDao;
import br.com.caelum.ingresso.dao.SalaDao;
import br.com.caelum.ingresso.dao.SessaoDao;
import br.com.caelum.ingresso.model.Sessao;
import br.com.caelum.ingresso.model.form.SessaoForm;

@Controller
public class SessaoController {
	
	@Autowired
	private FilmeDao filmeDao;
	
	@Autowired
	private SalaDao salaDao;
	
	@Autowired
	private SessaoDao sessaoDao;
	
	
	@GetMapping("/admin/sessao")
	public ModelAndView form(@RequestParam("salaId") Integer salaId, SessaoForm form){
		
		ModelAndView view = new ModelAndView("/sessao/sessao");
		
		view.addObject("filmes",filmeDao.findAll());
		view.addObject("sala",salaDao.findOne(salaId));
		view.addObject("form",form);
		
		return view;
	}
	
	@PostMapping("/admin/sessao")
	@Transactional
	public ModelAndView salva(@Valid SessaoForm form, BindingResult bd){
		if(bd.hasErrors()){
			return form(form.getSalaId(),form);
		}
		
		ModelAndView view = new ModelAndView("redirect:/admin/sala/"+form.getSalaId()+"/sessoes");
		
		Sessao sessao = form.toSessao(filmeDao,salaDao);
		
		sessaoDao.save(sessao);
		
		return view;
	}
}
