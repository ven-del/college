package br.com.aula;

import java.util.ArrayList;
import java.util.List;

import br.com.aula.exception.ContaJaExistenteException;
import br.com.aula.exception.ContaNaoDestinoExistenteException;
import br.com.aula.exception.ContaNaoExistenteException;
import br.com.aula.exception.ContaSemSaldoException;
import br.com.aula.exception.ContaTransferenciaValorNegativoException;

public class Banco {

	private List<Conta> contas = new ArrayList<Conta>();
	boolean operacaoRealizada;
	
	public Banco() {
	}

	public Banco(List<Conta> contas) {
		this.contas = contas;
	}

	public void cadastrarConta(Conta conta) 
			throws ContaJaExistenteException {

		for (Conta c : contas) {
			
			boolean isNumeroContaIncorreto = c.getNumeroConta() <= 0;
			
			
			
			boolean isNomeClienteIgual = c.getCliente().getNome().equals(conta.getCliente().getNome());
			boolean isNumeroContaIgual = c.getNumeroConta() == conta.getNumeroConta();

			if (isNomeClienteIgual || isNumeroContaIgual) {
				throw new ContaJaExistenteException();
				
			} else if (isNumeroContaIncorreto) {
				this.operacaoRealizada = false;
			}
		}

		this.contas.add(conta);
	}

	public boolean efetuarTransferencia(int numeroContaOrigem, int numeroContaDestino, int valor)
			throws ContaNaoExistenteException, ContaSemSaldoException, ContaTransferenciaValorNegativoException, ContaNaoDestinoExistenteException {

		Conta contaOrigem = this.obterContaPorNumero(numeroContaOrigem);
		Conta contaDestino = this.obterContaPorNumero(numeroContaDestino);
		
		boolean isContaOrigemExistente = contaOrigem != null;
		boolean isContaDestinoExistente = contaDestino != null;
		
		boolean isValorNegativo = valor <= 0;

		if (isContaOrigemExistente && isContaDestinoExistente) {

			boolean isContaOrigemPoupanca = contaOrigem.getTipoConta().equals(TipoConta.POUPANCA);
			boolean isSaldoContaOrigemNegativo = contaOrigem.getSaldo() - valor < 0;

			if (isContaOrigemPoupanca && isSaldoContaOrigemNegativo) {
				throw new ContaSemSaldoException();
			} else if (isValorNegativo) {
				throw new ContaTransferenciaValorNegativoException();
			}

			contaOrigem.debitar(valor);
			contaDestino.creditar(valor);
			return this.operacaoRealizada = true;

		} else if (isValorNegativo) {
			throw new ContaTransferenciaValorNegativoException();
			
		} else if (!isContaDestinoExistente) {
			throw new ContaNaoDestinoExistenteException();
			
		} else {
			throw new ContaNaoExistenteException();
		}
	}

	public Conta obterContaPorNumero(int numeroConta) {

		for (Conta c : contas) {
			if (c.getNumeroConta() == numeroConta) {
				return c;
			}
		}

		return null;
	}

	public List<Conta> obterContas() {
		return this.contas;
	}
}
