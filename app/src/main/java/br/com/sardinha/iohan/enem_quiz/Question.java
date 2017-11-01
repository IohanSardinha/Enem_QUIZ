package br.com.sardinha.iohan.enem_quiz;

public class Question {

    private String id;
    private String text;
    private String a;
    private String b;
    private String c;
    private String d;
    private String e;
    private String answer;
    private String materia;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getA() {
        return a;
    }

    public void setA(String a) {
        this.a = a;
    }

    public String getB() {
        return b;
    }

    public void setB(String b) {
        this.b = b;
    }

    public String getC() {
        return c;
    }

    public void setC(String c) {
        this.c = c;
    }

    public String getD() {
        return d;
    }

    public void setD(String d) {
        this.d = d;
    }

    public String getE() {
        return e;
    }

    public void setE(String e) {
        this.e = e;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getMateria() {
        return materia;
    }

    public void setMateria(String materia) {
        this.materia = materia;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Question() {

    }

    public Question(String text, String a, String b, String c, String d, String e, String answer, String materia) {

        this.text = text;
        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;
        this.e = e;
        this.answer = answer;
        this.materia = materia;
    }



}
