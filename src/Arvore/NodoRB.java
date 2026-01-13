package Arvore;

public class NodoRB {
    public static final boolean VERMELHO = true;
    public static final boolean PRETO = false;
    public int valor;
    public NodoRB esquerda;
    public NodoRB direita;
    public NodoRB pai;
    public boolean cor;

    public NodoRB(int var1, boolean var2) {
        this.valor = var1;
        this.cor = var2;
        this.esquerda = this.direita = this.pai = null;
    }

    public String toString() {
        String var1 = this.cor ? "VERMELHO" : "PRETO";
        String var2 = this.esquerda != null ? String.valueOf(this.esquerda.valor) : "null";
        String var3 = this.direita != null ? String.valueOf(this.direita.valor) : "null";
        String var4 = this.pai != null ? String.valueOf(this.pai.valor) : "null";
        return "NodoRB{valor=" + this.valor + ", cor=" + var1 + ", esquerda=" + var2 + ", direita=" + var3 + ", pai=" + var4 + "}";
    }
}
