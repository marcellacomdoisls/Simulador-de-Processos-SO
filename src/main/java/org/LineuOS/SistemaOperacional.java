package org.LineuOS;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SistemaOperacional {
  private List<Processo> processos;
  private Random random;

  public SistemaOperacional() {
    processos = new ArrayList<>();
    random = new Random();
    inicializarProcessos();
  }

  private void inicializarProcessos() {
    int[] temposExecucao = {10000, 5000, 7000, 3000, 3000, 8000, 2000, 5000, 4000, 10000};
    for (int i = 0; i < temposExecucao.length; i++) {
      processos.add(new Processo(i, temposExecucao[i]));
    }
  }

  public void executar() {
    while (!todosProcessosTerminados()) {
      for (Processo processo : processos) {
        if ("PRONTO".equals(processo.getEstado())) {
          processo.setEstado("EXECUTANDO");
          System.out.println("Processo: " + processo.getPID() + " PRONTO >>> EXECUTANDO");
          processo.salvarDados();
          executarProcesso(processo);
        }

        if ("BLOQUEADO".equals(processo.getEstado())) {
          if (random.nextInt(100) < 30) { // 30% de chance de desbloquear o processo
            processo.setEstado("PRONTO");
            System.out.println("Processo: " + processo.getPID() + " BLOQUEADO >>> PRONTO");
            processo.salvarDados();
          }
        }
      }
    }
    System.out.println("Simulação concluída. Todos os processos foram executados.");
  }

  private void executarProcesso(Processo processo) {
    int quantumRestante = 1000;

    while (quantumRestante > 0 && !processo.isTerminado()) {
      if (random.nextInt(100) < 1) { // 1% de chance de E/S
        processo.setEstado("BLOQUEADO");
        processo.incrementarNumeroES();
        System.out.println("Processo: " + processo.getPID() + " EXECUTANDO >>> BLOQUEADO");
        processo.salvarDados();
        return;
      }
      processo.incrementarTempoDeProcessamento();
      quantumRestante--;
    }

    if (processo.isTerminado()) {
      processo.setEstado("TERMINADO");
      System.out.println("Processo: " + processo.getPID() + " terminou a execução.");
      System.out.println(processo);
      processo.salvarDados();
    } else {
      processo.incrementarNumUsosCPU();
      processo.setEstado("PRONTO");
      System.out.println("Processo: " + processo.getPID() + " EXECUTANDO >>> PRONTO");
      processo.salvarDados();
    }
  }

  private boolean todosProcessosTerminados() {
    for (Processo processo : processos) {
      if (!"TERMINADO".equals(processo.getEstado())) {
        return false;
      }
    }
    return true;
  }
}
