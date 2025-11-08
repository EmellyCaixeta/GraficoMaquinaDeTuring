import javax.swing.JFrame;
import org.jfree.data.xy.XYSeries; 


public class questao3 {

    public static void main(String[] args) {
        
        //Teste do Simulador da Máquina de Turing
        
        MaquinaDeTuring mt = new MaquinaDeTuring();
        System.out.println("--- Teste do Simulador da Máquina de Turing ---");

        String entradaAceita = "aabbbbcccccc";
        boolean resultado1 = mt.aceitar(entradaAceita);
        System.out.println("Entrada: " + entradaAceita);
        System.out.println("Resultado: " + (resultado1 ? "ACEITO" : "REJEITADO"));
        System.out.println("Passos (Simulados): " + mt.obterPassos());
        System.out.println();
        
        //Iniciar o Gráfico de Complexidade
        
        System.out.println("Iniciando a janela do gráfico de complexidade...");
        
        XYSeries serieDeDados = criarConjuntoDeDados();
        new Grafico(serieDeDados, "Gráfico de Complexidade da Máquina de Turing");
    }
    
    //Cria o conjunto de dados para o gráfico
    private static XYSeries criarConjuntoDeDados() {
        XYSeries serie = new XYSeries("T(n) = O(n²)");
        for (int n = 1; n <= 5000; n++) {
            long T_n = (long) n * n;
            serie.add((double) n, (double) T_n);
        }
        return serie;
    }

} 


// Simula a Máquina de Turing para a linguagem L = {a^n b^2n c^3n}
class MaquinaDeTuring {

    private StringBuilder fita;
    private String estado;
    private int cabecote;
    private long passos;
    private static final char BRANCO = 'B';

    public MaquinaDeTuring() {
        this.fita = new StringBuilder();
        this.estado = "q0";
        this.cabecote = 0;
        this.passos = 0;
    }

    private char obterSimbolo() {
        if (cabecote >= fita.length()) {
            fita.append(BRANCO);
        }
        return fita.charAt(cabecote);
    }

    private void escreverSimbolo(char simbolo) {
        if(cabecote < fita.length()) {
           fita.setCharAt(cabecote, simbolo);
        } else {
           fita.append(simbolo); 
        }
    }

    private void moverDireita() {
        cabecote++;
    }

    private void moverEsquerda() {
        if (cabecote > 0) {
            cabecote--;
        }
    }

	public boolean aceitar(String entrada) {
        this.fita = new StringBuilder(entrada);
        this.estado = "q0";
        this.cabecote = 0;
        this.passos = 0;
        long maxPassos = (long)Math.max(100, (long)entrada.length() * entrada.length() * 20); 

        while (!estado.equals("q_aceita") && !estado.equals("q_rejeita")) {
            if (passos > maxPassos && entrada.length() > 0) {
                System.out.println("Atingiu maxPassos (possível loop infinito ou bug)");
                estado = "q_rejeita"; break;
            }
            char simboloAtual = obterSimbolo();
            passos++;

            switch (estado) {
       
                case "q0":
                    if (simboloAtual == 'a') { escreverSimbolo('X'); moverDireita(); estado = "q1"; }
                    else if (simboloAtual == 'Y') { moverDireita(); estado = "q_verifica"; } 
                    else { estado = "q_rejeita"; }
                    break;
                case "q1":
                    if (simboloAtual == 'a' || simboloAtual == 'Y') { moverDireita(); }
                    else if (simboloAtual == 'b') { escreverSimbolo('Y'); moverDireita(); estado = "q2"; }
                    else { estado = "q_rejeita"; }
                    break;
                case "q2":
                    if (simboloAtual == 'Y') { moverDireita(); } 
                    else if (simboloAtual == 'b') { escreverSimbolo('Y'); moverDireita(); estado = "q3"; }
                    else { estado = "q_rejeita"; }
                    break;
                case "q3":
                    if (simboloAtual == 'b' || simboloAtual == 'Y' || simboloAtual == 'Z') { moverDireita(); } 
                    else if (simboloAtual == 'c') { escreverSimbolo('Z'); moverDireita(); estado = "q4"; }
                    else { estado = "q_rejeita"; }
                    break;
                case "q4":
                    if (simboloAtual == 'Z') { moverDireita(); } 
                    else if (simboloAtual == 'c') { escreverSimbolo('Z'); moverDireita(); estado = "q5"; }
                    else { estado = "q_rejeita"; }
                    break;
                case "q5":
                    if (simboloAtual == 'Z') { moverDireita(); } 
                    else if (simboloAtual == 'c') { escreverSimbolo('Z'); moverEsquerda(); estado = "q_rebobina"; }
                    else { estado = "q_rejeita"; }
                    break;
                
              
                // q_rebobina: Rebobina até o primeiro 'X'
                case "q_rebobina":
                    // A verificação do 'X' DEVE vir primeiro, mesmo se o cabecote for 0.
                    if (simboloAtual == 'X') { 
                        moverDireita(); // Move 1 para a direita do 'X'
                        estado = "q0";  // Volta ao início do loop
                    } 
                    // Se não for 'X' E estivermos no início, algo deu errado
                    else if (cabecote == 0) { 
                         estado = "q_rejeita";
                    }
                    // Se não for 'X', continua movendo para a esquerda
                    else { 
                        moverEsquerda(); 
                    }
                    break;
                
                // q_verifica: Verifica se não sobraram 'b's ou 'c's
                case "q_verifica":
                    if (simboloAtual == 'Y' || simboloAtual == 'Z') { moverDireita(); }
                    else if (simboloAtual == BRANCO) { estado = "q_aceita"; }
                    else { estado = "q_rejeita"; } // Sobrou lixo (b ou c)
                    break;
            }
        }
        return estado.equals("q_aceita");
    }

    public long obterPassos() {
        return passos;
    }
}