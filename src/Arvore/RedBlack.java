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

    public void RBInsert(RedBlack T, NodoRB z) {
        NodoRB var3 = T.nil;
        NodoRB var4 = T.raiz;

        while(var4 != T.nil) {
            var3 = var4;
            if (z.valor < var4.valor) {
                var4 = var4.esquerda;
            } else {
                var4 = var4.direita;
            }
        }

        z.pai = var3;
        if (var3 == T.nil) {
            T.raiz = z;
        } else if (z.valor < var3.valor) {
            var3.esquerda = z;
        } else {
            var3.direita = z;
        }

        z.esquerda = T.nil;
        z.direita = T.nil;
        z.cor = true;
        this.RBInsertFixup(T, z);
    }

    public void RBInsertFixup(RedBlack T, NodoRB z) {
        while(z.pai.cor) {
            if (z.pai == z.pai.pai.esquerda) {
                NodoRB y = z.pai.pai.direita;
                if (y.cor) {
                    z.pai.cor = false;
                    y.cor = false;
                    z.pai.pai.cor = true;
                    z = z.pai.pai;
                } else {
                    if (z == z.pai.direita) {
                        z = z.pai;
                        this.rotacaoEsquerda(T, z);
                    }

                    z.pai.cor = false;
                    z.pai.pai.cor = true;
                    this.rotacaoDireita(T, z.pai.pai);
                }
            } else {
                NodoRB var3 = z.pai.pai.esquerda;
                if (var3.cor) {
                    z.pai.cor = false;
                    var3.cor = false;
                    z.pai.pai.cor = true;
                    z = z.pai.pai;
                } else {
                    if (z == z.pai.esquerda) {
                        z = z.pai;
                        this.rotacaoDireita(T, z);
                    }

                    z.pai.cor = false;
                    z.pai.pai.cor = true;
                    this.rotacaoEsquerda(T, z.pai.pai);
                }
            }
        }

        T.raiz.cor = false;
    }

    public void rotacaoEsquerda(RedBlack T, NodoRB z) {
        ++this.rotacoesEsquerda;
        NodoRB var3 = z.direita;
        z.direita = var3.esquerda;
        if (var3.esquerda != T.nil) {
            var3.esquerda.pai = z;
        }

        var3.pai = z.pai;
        if (z.pai == T.nil) {
            T.raiz = var3;
        } else if (z == z.pai.esquerda) {
            z.pai.esquerda = var3;
        } else {
            z.pai.direita = var3;
        }

        var3.esquerda = z;
        z.pai = var3;
    }

    public void rotacaoDireita(RedBlack T, NodoRB z) {
        ++this.rotacoesDireita;
        NodoRB var3 = z.esquerda;
        z.esquerda = var3.direita;
        if (var3.direita != T.nil) {
            var3.direita.pai = z;
        }

        var3.pai = z.pai;
        if (z.pai == T.nil) {
            T.raiz = var3;
        } else if (z == z.pai.direita) {
            z.pai.direita = var3;
        } else {
            z.pai.esquerda = var3;
        }

        var3.direita = z;
        z.pai = var3;
    }

    public void RBTransplant(RedBlack t, NodoRB u, NodoRB v) {
        if (u.pai == t.nil) {
            t.raiz = v;
        } else if (u == u.pai.esquerda) {
            u.pai.esquerda = v;
        } else {
            u.pai.direita = v;
        }

        v.pai = u.pai;
    }

    public void RBRemove(RedBlack T, NodoRB z) {
        boolean var4 = z.cor;
        NodoRB var5;
        if (z.esquerda == T.nil) {
            var5 = z.direita;
            this.RBTransplant(T, z, z.direita);
        } else if (z.direita == T.nil) {
            var5 = z.esquerda;
            this.RBTransplant(T, z, z.esquerda);
        } else {
            NodoRB var3 = this.treeMinimum(T, z.direita);
            var4 = var3.cor;
            var5 = var3.direita;
            if (var3.pai == z) {
                var5.pai = var3;
            } else {
                this.RBTransplant(T, var3, var3.direita);
                var3.direita = z.direita;
                var3.direita.pai = var3;
            }

            this.RBTransplant(T, z, var3);
            var3.esquerda = z.esquerda;
            var3.esquerda.pai = var3;
            var3.cor = z.cor;
        }

        if (!var4) {
            this.RBRemoveFixup(T, var5);
        }

    }

    public void RBRemoveFixup(RedBlack T, NodoRB x) {
        while(x != T.raiz && !x.cor) {
            if (x == x.pai.esquerda) {
                NodoRB var4 = x.pai.direita;
                if (var4.cor) {
                    var4.cor = false;
                    x.pai.cor = true;
                    this.rotacaoEsquerda(T, x.pai);
                    var4 = x.pai.direita;
                }

                if (!var4.esquerda.cor && !var4.direita.cor) {
                    var4.cor = true;
                    x = x.pai;
                } else {
                    if (!var4.direita.cor) {
                        var4.esquerda.cor = false;
                        var4.cor = true;
                        this.rotacaoDireita(T, var4);
                        var4 = x.pai.direita;
                    }

                    var4.cor = x.pai.cor;
                    x.pai.cor = false;
                    var4.direita.cor = false;
                    this.rotacaoEsquerda(T, x.pai);
                    x = T.raiz;
                }
            } else {
                NodoRB var3 = x.pai.esquerda;
                if (var3.cor) {
                    var3.cor = false;
                    x.pai.cor = true;
                    this.rotacaoDireita(T, x.pai);
                    var3 = x.pai.esquerda;
                }

                if (!var3.direita.cor && !var3.esquerda.cor) {
                    var3.cor = true;
                    x = x.pai;
                } else {
                    if (!var3.esquerda.cor) {
                        var3.direita.cor = false;
                        var3.cor = true;
                        this.rotacaoEsquerda(T, var3);
                        var3 = x.pai.esquerda;
                    }

                    var3.cor = x.pai.cor;
                    x.pai.cor = false;
                    var3.esquerda.cor = false;
                    this.rotacaoDireita(T, x.pai);
                    x = T.raiz;
                }
            }
        }

        x.cor = false;
    }

    public NodoRB treeMinimum(RedBlack T, NodoRB z) {
        while(z.esquerda != T.nil) {
            z = z.esquerda;
        }

        return z;
    }

    public NodoRB buscarNodo(RedBlack T, int x) {
        NodoRB var3 = T.raiz;

        while (var3 != T.nil && x != var3.valor) {
            if (x < var3.valor) {
                var3 = var3.esquerda;
            } else {
                var3 = var3.direita;
            }
        }

        return var3 != T.nil ? var3 : null;
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
