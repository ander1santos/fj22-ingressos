package br.com.caelum.ingresso.model.descontos;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalTime;

import org.junit.Assert;
import org.junit.Test;

import br.com.caelum.ingresso.model.Filme;
import br.com.caelum.ingresso.model.Ingresso;
import br.com.caelum.ingresso.model.Lugar;
import br.com.caelum.ingresso.model.Sala;
import br.com.caelum.ingresso.model.Sessao;
import br.com.caelum.ingresso.model.TipoDeIngresso;

public class DescontoTest {

	@Test
	public void deveConcederDescontoTrintaPorcentoBancos(){
		Sala sala = new Sala("Eldorado",new BigDecimal("20.5"));
		Filme filme = new Filme("Matrix",Duration.ofMinutes(120),"SCI-FI",new BigDecimal("12"));
		
		Sessao sessao = new Sessao (LocalTime.parse("18:00:00"),filme,sala);
		
		Lugar lugar = new Lugar("A",1);
		
		Ingresso ingresso = new Ingresso(sessao, TipoDeIngresso.BANCO, lugar);
		
		BigDecimal preco = new BigDecimal("22.75");
		
		Assert.assertEquals(preco, ingresso.getPreco());
	}
	
	@Test
	public void deveConcederDescontoCinquentaPorcentoEstudante(){
		Sala sala = new Sala("Eldorado",new BigDecimal("20.5"));
		Filme filme = new Filme("Matrix",Duration.ofMinutes(120),"SCI-FI",new BigDecimal("12"));
		
		Sessao sessao = new Sessao (LocalTime.parse("10:00:00"),filme,sala);
		
		Lugar lugar = new Lugar("A",1);
		
		Ingresso ingresso = new Ingresso(sessao, TipoDeIngresso.ESTUDANTE,lugar);
		
		BigDecimal preco = new BigDecimal("16.25");
		
		Assert.assertEquals(preco, ingresso.getPreco());
	}
	
	@Test
	public void naoDeveConcederDescontoParaIngressoNormal(){
		Sala sala = new Sala("Eldorado",new BigDecimal("20.5"));
		Filme filme = new Filme("Matrix",Duration.ofMinutes(120),"SCI-FI",new BigDecimal("12"));
		
		Sessao sessao = new Sessao (LocalTime.parse("10:00:00"),filme,sala);
		
		Lugar lugar = new Lugar("A",1);
		
		Ingresso ingresso = new Ingresso(sessao, TipoDeIngresso.INTEIRO,lugar);
		
		BigDecimal preco = new BigDecimal("32.5");
		
		Assert.assertEquals(preco, ingresso.getPreco());
	}
}