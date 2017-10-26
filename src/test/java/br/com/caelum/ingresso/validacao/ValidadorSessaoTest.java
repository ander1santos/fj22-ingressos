package br.com.caelum.ingresso.validacao;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import br.com.caelum.ingresso.model.Filme;
import br.com.caelum.ingresso.model.Sala;
import br.com.caelum.ingresso.model.Sessao;
import org.junit.Assert;

public class ValidadorSessaoTest {
	
	@Test
	public void verificaNaoDevePermitirSessaoMesmoHorario(){
		Filme filme = new Filme("RogueOne",Duration.ofMinutes(120),"SCI-FI",BigDecimal.ONE);
		
		LocalTime horario = LocalTime.parse("10:00:00");
		
		Sala sala = new Sala("Eldorado - IMAX",BigDecimal.ONE);
		
		Sessao sessao = new Sessao(horario,filme,sala);
		
		List<Sessao> sessoes = Arrays.asList(sessao);
		
		ValidadorSessao validador = new ValidadorSessao(sessoes);
		
		Assert.assertFalse(validador.cabe(sessao));
	}
	
	@Test
	public void verificaNaoDevePermitirSessoesTerminandoDentroHorarioDeUmaExistente(){
		Filme filme = new Filme("RogueOne",Duration.ofMinutes(120),"SCI-FI",new BigDecimal("10"));
		
		LocalTime horario = LocalTime.parse("10:00:00");
		
		Sala sala = new Sala("Cinermak 3D",new BigDecimal("30"));
		
		List<Sessao> sessoes = Arrays.asList(new Sessao(horario,filme,sala));
		
		Sessao sessao = new Sessao(horario.minusHours(1), filme, sala);
		
		ValidadorSessao validador = new ValidadorSessao(sessoes);
		
		Assert.assertFalse(validador.cabe(sessao));
	}
	
	@Test
	public void verificaNaoDevePermitirSessoesIniciandoDentroHorarioDeUmaExistente(){
		Filme filme = new Filme("RogueOne",Duration.ofMinutes(120),"SCI-FI",BigDecimal.ONE);
		
		LocalTime horario = LocalTime.parse("10:00:00");
		
		Sala sala = new Sala("Eldorado IMAX",BigDecimal.ONE);
		
		List<Sessao> sessoes = Arrays.asList(new Sessao(horario,filme,sala));
		
		Sessao sessao = new Sessao(horario.plusHours(1), filme, sala);
		
		ValidadorSessao validador = new ValidadorSessao(sessoes);
		
		Assert.assertFalse(validador.cabe(sessao));
	}
	
	@Test
	public void garanteDevePermitirInsercaoEntreDoisFilmes(){
		Filme filme1 = new Filme("RogueOne",Duration.ofMinutes(120),"SCI-FI",BigDecimal.ONE);
		
		Filme filme2 = new Filme("RogueOne",Duration.ofMinutes(120),"SCI-FI", new BigDecimal("10"));
		
		Filme filme3 = new Filme("RogueOne",Duration.ofMinutes(120),"SCI-FI",new BigDecimal("15"));
		
		LocalTime horDez = LocalTime.parse("10:00:00");
		
		LocalTime horDezoito = LocalTime.parse("18:00:00");
		
		Sala sala = new Sala("Cinemark XD",new BigDecimal("40"));
		
		Sessao sessaoDez = new Sessao(horDez, filme1, sala);
		
		Sessao sessaoDezoito = new Sessao(horDezoito, filme2, sala);
		
		List<Sessao> sessoes = Arrays.asList(sessaoDez,sessaoDezoito);
		
		ValidadorSessao validador = new ValidadorSessao(sessoes);
		
		Sessao sessao = new Sessao(LocalTime.parse("13:00:00"), filme3, sala);
		
		Assert.assertTrue(validador.cabe(sessao));
	}
}
