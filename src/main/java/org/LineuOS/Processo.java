package org.LineuOS;
import java.io.FileWriter;
import java.io.IOException;

public class Processo {
  private int PID;
  private int tempoTotal;
  private int tempoDeProcessamento;
  private int contadorDePrograma;
  private String estado;
  private int numeroES;
  private int numUsosCPU;
  private static final int QUANTUM = 1000;

  public Processo(int PID, int tempoTotal) {
    this.PID = PID;
    this.tempoTotal = tempoTotal;
    this.tempoDeProcessamento = 0;
    this.contadorDePrograma = tempoDeProcessamento + 1;
    this.estado = "PRONTO";
    this.numeroES = 0;
    this.numUsosCPU = 0;
  }

  public int getPID() {
    return PID;
  }

  public int getTempoDeProcessamento() {
    return tempoDeProcessamento;
  }

  public String getEstado() {
    return estado;
  }

  public void setEstado(String estado) {
    this.estado = estado;
  }

  public void incrementarTempoDeProcessamento() {
    this.tempoDeProcessamento++;
    this.tempoDeProcessamento = tempoDeProcessamento + 1;
  }

  public void incrementarNumeroES() {
    this.numeroES++;
  }

  public void incrementarNumUsosCPU() {
    this.numUsosCPU++;
  }

  public boolean isTerminado() {
    return tempoDeProcessamento >= tempoTotal;
  }

  public void resetQuantum() {
    this.tempoDeProcessamento += QUANTUM;
  }

  public void salvarDados() {
    try (FileWriter writer = new FileWriter("processos.txt", true)) {
      writer.write(this.toString() + "\n");
    } catch (IOException e) {
      System.out.println("Erro ao salvar dados do processo: " + PID);
    }
  }

  @Override
  public String toString() {
    return "PID: " + PID + ", TP: " + tempoDeProcessamento + ", CP: " + contadorDePrograma + ", Estado: " + estado
        + ", NES: " + numeroES + ", N_CPU: " + numUsosCPU;
  }

}
