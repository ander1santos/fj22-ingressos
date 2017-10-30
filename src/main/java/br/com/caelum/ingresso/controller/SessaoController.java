package br.com.caelum.ingresso.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import br.com.caelum.ingresso.dao.FilmeDao;
import br.com.caelum.ingresso.dao.SalaDao;
import br.com.caelum.ingresso.dao.SessaoDao;
import br.com.caelum.ingresso.model.Carrinho;
import br.com.caelum.ingresso.model.ImagemCapa;
import br.com.caelum.ingresso.model.Sessao;
import br.com.caelum.ingresso.model.TipoDeIngresso;
import br.com.caelum.ingresso.model.form.SessaoForm;
import br.com.caelum.ingresso.rest.ImdbClient;
import br.com.caelum.ingresso.validacao.ValidadorSessao;

@Controller
public class SessaoController {
	
	@Autowired
	private FilmeDao filmeDao;
	
	@Autowired
	private SalaDao salaDao;
	
	@Autowired
	private SessaoDao sessaoDao;
	
	@Autowired
	private ImdbClient client;
	
	@Autowired
	private Carrinho carrinho;
	
	
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
		
		Sessao sessao = form.toSessao(filmeDao,salaDao);
		
		List<Sessao> sessoes = sessaoDao.buscaSessaoDaSala(sessao.getSala());
		
		ValidadorSessao validador = new ValidadorSessao(sessoes);
		
		if(validador.cabe(sessao)){
			sessaoDao.save(sessao);
			return new ModelAndView("redirect:/admin/sala/"+form.getSalaId()+"/sessoes");
		}
		else{
			return form(form.getSalaId(),form);
		}
	}
	
	@GetMapping("/sessao/{id}/lugares")
	public ModelAndView lugaresNaSessao(@PathVariable("id") Integer sessaoId){
		
		ModelAndView view = new ModelAndView("/sessao/lugares");
		
		Sessao sessao = sessaoDao.findOne(sessaoId);
		
		Optional<ImagemCapa> imagem = client.request(sessao.getFilme(),ImagemCapa.class);
		
		view.addObject("sessao",sessao);
		view.addObject("imagemCapa",imagem.orElse(new ImagemCapa()));
		view.addObject("tiposDeIngressos", TipoDeIngresso.values());
		view.addObject("carrinho", carrinho);
		
		return view;
	}
}
