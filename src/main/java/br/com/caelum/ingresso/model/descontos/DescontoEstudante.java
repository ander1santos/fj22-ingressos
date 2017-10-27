package br.com.caelum.ingresso.model.descontos;

import java.math.BigDecimal;

public class DescontoEstudante implements Desconto{

	@Override
	public BigDecimal aplicaDescontoSobre(BigDecimal preco){
		
		return preco.divide(new BigDecimal("2"));
	}
}
