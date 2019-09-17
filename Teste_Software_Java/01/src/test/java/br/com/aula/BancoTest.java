package br.com.aula;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;

import org.junit.Assert;
import org.junit.Test;

import br.com.aula.exception.ContaJaExistenteException;
import br.com.aula.exception.ContaNaoDestinoExistenteException;
import br.com.aula.exception.ContaNaoExistenteException;
import br.com.aula.exception.ContaTransferenciaValorNegativoException;
import br.com.aula.exception.ContaSemSaldoException;

public class BancoTest {

	@Test
	public void deveCadastrarConta() throws ContaJaExistenteException{

		// Cenario
		Cliente cliente = new Cliente("Joao");
		Conta conta = new Conta(cliente, 123, 0, TipoConta.CORRENTE);
		Banco banco = new Banco();

		// Acao
		banco.cadastrarConta(conta);

		// Verificacao
		assertEquals(1, banco.obterContas().size());
	}

	@Test(expected = ContaJaExistenteException.class)
	public void naoDeveCadastrarContaNumeroRepetido() throws ContaJaExistenteException {

		// Cenario
		Cliente cliente = new Cliente("Joao");
		Conta conta1 = new Conta(cliente, 123, 0, TipoConta.CORRENTE);

		Cliente cliente2 = new Cliente("Maria");
		Conta conta2 = new Conta(cliente2, 123, 0, TipoConta.POUPANCA);

		Banco banco = new Banco();

		// Acao
		banco.cadastrarConta(conta1);
		banco.cadastrarConta(conta2);

		// Verificacao
		Assert.fail();
	}

	// TODO: HOMEWORK
	@Test(expected = ContaJaExistenteException.class)
	public void naoDeveCadastrarContaNomeClienteRepetido() throws ContaJaExistenteException {
		
		// Cenario
		Cliente cliente = new Cliente("Saulo");
		Conta conta1 = new Conta(cliente, 1236, 0, TipoConta.CORRENTE);

		Cliente cliente2 = new Cliente("Saulo");
		Conta conta2 = new Conta(cliente2, 1234, 0, TipoConta.POUPANCA);

		Banco banco = new Banco();

		// Acao
		banco.cadastrarConta(conta1);
		banco.cadastrarConta(conta2);
		
		// Verificacao
		Assert.fail();
		
	}

	// TODO: HOMEWORK - (Alterar a implementacao para tratar esse caso)
	@Test
	public void naoDeveCadastrarContaComNumeroInvalido() 
			throws ContaJaExistenteException {
		
		//Cenario
		Cliente cliente = new Cliente("Saulo");
		Conta conta1 = new Conta(cliente, 0, 0, TipoConta.CORRENTE);
		
		Banco banco = new Banco();
		
		//Acao
		banco.cadastrarConta(conta1);
		
		// Verificacao
		assertEquals(false, banco.operacaoRealizada);
		
		
	}
	
	//AJUSTADO
	@Test
	public void deveEfetuarTransferenciaContasCorrentes()
			throws ContaJaExistenteException, ContaSemSaldoException, ContaNaoExistenteException, ContaTransferenciaValorNegativoException, ContaNaoDestinoExistenteException {

		// Cenario
		Cliente cliente = new Cliente("Joao");
		Conta contaOrigem = new Conta(cliente, 123, 0, TipoConta.CORRENTE);

		Cliente cliente2 = new Cliente("Maria");
		Conta contaDestino = new Conta(cliente2, 456, 0, TipoConta.CORRENTE);

		Banco banco = new Banco(Arrays.asList(contaOrigem, contaDestino));

		// Acao
		banco.efetuarTransferencia(123, 456, 100);

		// Verificacao
		assertEquals(true, banco.operacaoRealizada);
	}

	// TODO: HOMEWORK - (Alterar a implementacao para tratar esse caso)
	@Test(expected = ContaTransferenciaValorNegativoException.class)
	public void naoDeveEfetuarTransferenciaComValorNegativo()
			throws ContaJaExistenteException, ContaSemSaldoException, ContaNaoExistenteException, ContaTransferenciaValorNegativoException, ContaNaoDestinoExistenteException {
		
		Cliente cliente = new Cliente("Joao");
		Conta contaOrigem = new Conta(cliente, 1, 0, TipoConta.CORRENTE);

		Cliente cliente2 = new Cliente("Maria");
		Conta contaDestino = new Conta(cliente2, 2, 0, TipoConta.CORRENTE);

		Banco banco = new Banco(Arrays.asList(contaOrigem, contaDestino));
		
		// Acao
		banco.efetuarTransferencia(1, 2, -100);
		
		// Verificacao
		Assert.fail();
	}

	// Verificacao
	@Test(expected = ContaSemSaldoException.class)
	public void naoDeveEfetuarTransferenciaContaOrigemPoupancaSemSaldo()
			throws ContaJaExistenteException, ContaSemSaldoException, ContaNaoExistenteException, ContaTransferenciaValorNegativoException, ContaNaoDestinoExistenteException {

		// Cenario
		Cliente cliente = new Cliente("Joao");
		Conta contaOrigem = new Conta(cliente, 123, 99, TipoConta.POUPANCA);

		Cliente cliente2 = new Cliente("Maria");
		Conta contaDestino = new Conta(cliente2, 456, 0, TipoConta.CORRENTE);

		Banco banco = new Banco(Arrays.asList(contaOrigem, contaDestino));

		// Acao
		banco.efetuarTransferencia(123, 456, 100);

		// Verificacao
		Assert.fail();
	}

	// Verificacao
	@Test(expected = ContaNaoExistenteException.class)
	public void naoDeveEfetuarTransferenciaContaOrigemNaoExistente()
			throws ContaJaExistenteException, ContaSemSaldoException, ContaNaoExistenteException, ContaTransferenciaValorNegativoException, ContaNaoDestinoExistenteException {

		// Cenario
		Cliente cliente = new Cliente("Joao");
		Conta contaOrigem = new Conta(cliente, 123, 99, TipoConta.POUPANCA);

		Cliente cliente2 = new Cliente("Maria");
		Conta contaDestino = new Conta(cliente2, 456, 0, TipoConta.CORRENTE);

		Banco banco = new Banco(Arrays.asList(contaOrigem, contaDestino));

		// Acao
		banco.efetuarTransferencia(111, 456, 100);
		
		// Verificacao
		Assert.fail();
	}

	// TODO: HOMEWORK
	@Test(expected = ContaNaoDestinoExistenteException.class)
	public void naoDeveEfetuarTransferenciaContaDestinoNaoExistente()
			throws ContaJaExistenteException, ContaSemSaldoException, ContaNaoExistenteException, ContaTransferenciaValorNegativoException, ContaNaoDestinoExistenteException {
		
		// Cenario
		Cliente cliente = new Cliente("Joao");
		Conta contaOrigem = new Conta(cliente, 1, 99, TipoConta.POUPANCA);

		Cliente cliente2 = new Cliente("Maria");
		Conta contaDestino = new Conta(cliente2, 2, 0, TipoConta.CORRENTE);
		
		Banco banco = new Banco(Arrays.asList(contaOrigem, contaDestino));
		
		// Acao
		banco.efetuarTransferencia(1, -63, 100);
		
		// Verificacao
		Assert.fail();
		
	}
}
