import javax.swing.JFrame;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;


public class Grafico extends JFrame {

	
    public Grafico(XYSeries series, String titulo) {
        super(titulo); //titulo da janela

        //Cria a coleção de dados a partir da série recebida
        XYSeriesCollection conjuntoDeDados = new XYSeriesCollection();
        conjuntoDeDados.addSeries(series);

        //Cria o gráfico JFreeChart
        JFreeChart graficoJFree = ChartFactory.createXYLineChart(
            "Complexidade Computacional O(n^2)", // Título do Gráfico
            "Tamanho da Entrada (n)",          // Eixo X
            "Complexidade de Tempo (O(n²))", // Rótulo do Eixo Y
            conjuntoDeDados,
            PlotOrientation.VERTICAL,
            true,  // Legenda
            true,  // Tooltips
            false  // URLs
        );

        //Cria o painel do gráfico
        ChartPanel painelGrafico = new ChartPanel(graficoJFree);
        //Define o painel como o conteúdo da janela
        setContentPane(painelGrafico);

        //Configuração da Janela
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }
    
}