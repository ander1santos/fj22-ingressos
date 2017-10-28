package br.com.caelum.ingresso.model.descontos;

import java.math.BigDecimal;

public class DescontoTrintaPorcentoBancos implements Desconto{
	
	@Override
	public BigDecimal aplicaDescontoSobre(BigDecimal preco){
		return preco.subtract(preco.multiply(new BigDecimal("0.3")));
	}
	
	@Override
	public String getDescricao(){
		return "Desconto Banco";
	}

}
