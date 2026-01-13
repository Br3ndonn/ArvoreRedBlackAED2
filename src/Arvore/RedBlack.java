package Arvore;

public class RedBlack {
    public NodoRB raiz;
    public final NodoRB nil = new NodoRB(0, false);
    public int rotacoesEsquerda;
    public int rotacoesDireita;

    public RedBlack() {
        this.nil.esquerda = this.nil.direita = this.nil.pai = this.nil;
        this.raiz = this.nil;
        this.rotacoesEsquerda = 0;
        this.rotacoesDireita = 0;
    }

    public void RBInsert(RedBlack var1, NodoRB var2) {
        NodoRB var3 = var1.nil;
        NodoRB var4 = var1.raiz;

        while(var4 != var1.nil) {
            var3 = var4;
            if (var2.valor < var4.valor) {
                var4 = var4.esquerda;
            } else {
                var4 = var4.direita;
            }
        }

        var2.pai = var3;
        if (var3 == var1.nil) {
            var1.raiz = var2;
        } else if (var2.valor < var3.valor) {
            var3.esquerda = var2;
        } else {
            var3.direita = var2;
        }

        var2.esquerda = var1.nil;
        var2.direita = var1.nil;
        var2.cor = true;
        this.RBInsertFixup(var1, var2);
    }

    public void RBInsertFixup(RedBlack var1, NodoRB var2) {
        while(var2.pai.cor) {
            if (var2.pai == var2.pai.pai.esquerda) {
                NodoRB var4 = var2.pai.pai.direita;
                if (var4.cor) {
                    var2.pai.cor = false;
                    var4.cor = false;
                    var2.pai.pai.cor = true;
                    var2 = var2.pai.pai;
                } else {
                    if (var2 == var2.pai.direita) {
                        var2 = var2.pai;
                        this.rotacaoEsquerda(var1, var2);
                    }

                    var2.pai.cor = false;
                    var2.pai.pai.cor = true;
                    this.rotacaoDireita(var1, var2.pai.pai);
                }
            } else {
                NodoRB var3 = var2.pai.pai.esquerda;
                if (var3.cor) {
                    var2.pai.cor = false;
                    var3.cor = false;
                    var2.pai.pai.cor = true;
                    var2 = var2.pai.pai;
                } else {
                    if (var2 == var2.pai.esquerda) {
                        var2 = var2.pai;
                        this.rotacaoDireita(var1, var2);
                    }

                    var2.pai.cor = false;
                    var2.pai.pai.cor = true;
                    this.rotacaoEsquerda(var1, var2.pai.pai);
                }
            }
        }

        var1.raiz.cor = false;
    }

    public void rotacaoEsquerda(RedBlack var1, NodoRB var2) {
        ++this.rotacoesEsquerda;
        NodoRB var3 = var2.direita;
        var2.direita = var3.esquerda;
        if (var3.esquerda != var1.nil) {
            var3.esquerda.pai = var2;
        }

        var3.pai = var2.pai;
        if (var2.pai == var1.nil) {
            var1.raiz = var3;
        } else if (var2 == var2.pai.esquerda) {
            var2.pai.esquerda = var3;
        } else {
            var2.pai.direita = var3;
        }

        var3.esquerda = var2;
        var2.pai = var3;
    }

    public void rotacaoDireita(RedBlack var1, NodoRB var2) {
        ++this.rotacoesDireita;
        NodoRB var3 = var2.esquerda;
        var2.esquerda = var3.direita;
        if (var3.direita != var1.nil) {
            var3.direita.pai = var2;
        }

        var3.pai = var2.pai;
        if (var2.pai == var1.nil) {
            var1.raiz = var3;
        } else if (var2 == var2.pai.direita) {
            var2.pai.direita = var3;
        } else {
            var2.pai.esquerda = var3;
        }

        var3.direita = var2;
        var2.pai = var3;
    }

    public void RBTransplant(RedBlack var1, NodoRB var2, NodoRB var3) {
        if (var2.pai == var1.nil) {
            var1.raiz = var3;
        } else if (var2 == var2.pai.esquerda) {
            var2.pai.esquerda = var3;
        } else {
            var2.pai.direita = var3;
        }

        var3.pai = var2.pai;
    }

    public void RBRemove(RedBlack var1, NodoRB var2) {
        boolean var4 = var2.cor;
        NodoRB var5;
        if (var2.esquerda == var1.nil) {
            var5 = var2.direita;
            this.RBTransplant(var1, var2, var2.direita);
        } else if (var2.direita == var1.nil) {
            var5 = var2.esquerda;
            this.RBTransplant(var1, var2, var2.esquerda);
        } else {
            NodoRB var3 = this.treeMinimum(var1, var2.direita);
            var4 = var3.cor;
            var5 = var3.direita;
            if (var3.pai == var2) {
                var5.pai = var3;
            } else {
                this.RBTransplant(var1, var3, var3.direita);
                var3.direita = var2.direita;
                var3.direita.pai = var3;
            }

            this.RBTransplant(var1, var2, var3);
            var3.esquerda = var2.esquerda;
            var3.esquerda.pai = var3;
            var3.cor = var2.cor;
        }

        if (!var4) {
            this.RBRemoveFixup(var1, var5);
        }

    }

    public void RBRemoveFixup(RedBlack var1, NodoRB var2) {
        while(var2 != var1.raiz && !var2.cor) {
            if (var2 == var2.pai.esquerda) {
                NodoRB var4 = var2.pai.direita;
                if (var4.cor) {
                    var4.cor = false;
                    var2.pai.cor = true;
                    this.rotacaoEsquerda(var1, var2.pai);
                    var4 = var2.pai.direita;
                }

                if (!var4.esquerda.cor && !var4.direita.cor) {
                    var4.cor = true;
                    var2 = var2.pai;
                } else {
                    if (!var4.direita.cor) {
                        var4.esquerda.cor = false;
                        var4.cor = true;
                        this.rotacaoDireita(var1, var4);
                        var4 = var2.pai.direita;
                    }

                    var4.cor = var2.pai.cor;
                    var2.pai.cor = false;
                    var4.direita.cor = false;
                    this.rotacaoEsquerda(var1, var2.pai);
                    var2 = var1.raiz;
                }
            } else {
                NodoRB var3 = var2.pai.esquerda;
                if (var3.cor) {
                    var3.cor = false;
                    var2.pai.cor = true;
                    this.rotacaoDireita(var1, var2.pai);
                    var3 = var2.pai.esquerda;
                }

                if (!var3.direita.cor && !var3.esquerda.cor) {
                    var3.cor = true;
                    var2 = var2.pai;
                } else {
                    if (!var3.esquerda.cor) {
                        var3.direita.cor = false;
                        var3.cor = true;
                        this.rotacaoEsquerda(var1, var3);
                        var3 = var2.pai.esquerda;
                    }

                    var3.cor = var2.pai.cor;
                    var2.pai.cor = false;
                    var3.esquerda.cor = false;
                    this.rotacaoDireita(var1, var2.pai);
                    var2 = var1.raiz;
                }
            }
        }

        var2.cor = false;
    }

    public NodoRB treeMinimum(RedBlack var1, NodoRB var2) {
        while(var2.esquerda != var1.nil) {
            var2 = var2.esquerda;
        }

        return var2;
    }

    public void imprimirArvore(NodoRB var1, String var2, boolean var3) {
        if (var1 != null && var1 != this.nil) {
            System.out.print(var2 + (var3 ? "├── " : "└── "));
            int var10001 = var1.valor;
            System.out.println(var10001 + (var1.cor ? "(R)" : "(B)"));
            this.imprimirArvore(var1.esquerda, var2 + (var3 ? "│   " : "    "), true);
            this.imprimirArvore(var1.direita, var2 + (var3 ? "│   " : "    "), false);
        }

    }

    public void preOrder(NodoRB var1) {
        if (var1 != null && var1 != this.nil) {
            String var2 = var1.cor ? "V" : "P";
            System.out.print(var1.valor + "(" + var2 + ") ");
            this.preOrder(var1.esquerda);
            this.preOrder(var1.direita);
        }

    }
}
