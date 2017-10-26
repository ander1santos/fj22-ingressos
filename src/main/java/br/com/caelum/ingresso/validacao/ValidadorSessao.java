package br.com.caelum.ingresso.validacao;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import br.com.caelum.ingresso.model.Sessao;

public class ValidadorSessao {
	
	private List<Sessao> sessoes;
	
	public ValidadorSessao(List<Sessao> sessoes){
		this.sessoes = sessoes;
	}
	
	private Boolean validaHorario(Sessao sessaoExistente, Sessao sessaoAtual){
		
		Boolean ehAntes = sessaoAtual.getHorario().isBefore(sessaoExistente.getHorario());
		
		LocalDateTime horarioSessao = sessaoExistente.getHorario().atDate(LocalDate.now());
		LocalDateTime horarioAtual = sessaoAtual.getHorario().atDate(LocalDate.now());
		
		if(ehAntes){
			return sessaoAtual.getHorarioTermino().isBefore(horarioSessao);
		}
		else{
		
			return sessaoExistente.getHorarioTermino().isBefore(horarioAtual);
		}
	}
	
	public Boolean cabe(Sessao sessaoAtual){
		return sessoes.stream().map(sessaoExistente -> validaHorario(sessaoAtual,sessaoExistente)).reduce(Boolean::logicalAnd).orElse(true);
	}
 
}
