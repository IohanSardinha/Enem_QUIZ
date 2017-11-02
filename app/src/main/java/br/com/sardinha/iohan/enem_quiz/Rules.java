package br.com.sardinha.iohan.enem_quiz;

import java.io.Serializable;

public class Rules implements Serializable{

    private boolean humanas;
    private boolean ciencias;
    private boolean portugues;
    private boolean matematica;
    private boolean tempoBool;
    private long tempoInMillis;
    private int numeroDeQuestoes;

    public boolean isHumanas() {
        return humanas;
    }

    public void setHumanas(boolean humanas) {
        this.humanas = humanas;
    }

    public boolean isCiencias() {
        return ciencias;
    }

    public void setCiencias(boolean ciencias) {
        this.ciencias = ciencias;
    }

    public boolean isPortugues() {
        return portugues;
    }

    public void setPortugues(boolean portugues) {
        this.portugues = portugues;
    }

    public boolean isMatematica() {
        return matematica;
    }

    public void setMatematica(boolean matematica) {
        this.matematica = matematica;
    }

    public boolean isTempoBool() {
        return tempoBool;
    }

    public void setTempoBool(boolean tempoBool) {
        this.tempoBool = tempoBool;
    }

    public long getTempoInMillis() {
        return tempoInMillis;
    }

    public void setTempoInMillis(long tempoInMillis) {
        this.tempoInMillis = tempoInMillis;
    }

    public int getNumeroDeQuestoes() {
        return numeroDeQuestoes;
    }

    public void setNumeroDeQuestoes(int numeroDeQuestoes) {
        this.numeroDeQuestoes = numeroDeQuestoes;
    }



    public Rules(boolean humanas, boolean ciencias, boolean portugues, boolean matematica, boolean tempoBool, long tempoInMillis, int numeroDeQuestoes)
    {
        this.humanas = humanas;
        this.ciencias = ciencias;
        this.portugues = portugues;
        this.matematica = matematica;
        this.tempoBool = tempoBool;
        this.tempoInMillis = tempoInMillis;
        this. numeroDeQuestoes = numeroDeQuestoes;
    }

}
