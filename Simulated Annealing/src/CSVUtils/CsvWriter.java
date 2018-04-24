package CSVUtils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CsvWriter {

    PrintWriter pw;
    String fileName;
    StringBuffer csvHeader;
    StringBuffer csvData;

    public CsvWriter(String nomeArquivo) {
        this.fileName = nomeArquivo + ".csv";
       
        try {
            BufferedWriter bufferWrite = new BufferedWriter(new FileWriter(this.fileName,true));

            csvHeader = new StringBuffer("");
            csvHeader.append("x1,x2,Funcao objetivo, Iteracoes\n");

            bufferWrite.append(csvHeader.toString());
            bufferWrite.close();

        } catch (IOException ex) {
            System.out.println("Erro ao abrir arquivo!");
        }

//        try {
//            this.fileName = nomeArquivo;
//            
//            pw = new PrintWriter(new File(nomeArquivo+".csv"));
//            csvHeader = new StringBuffer("");
//            csvData = new StringBuffer("");
//            
//            csvHeader.append("x1,x2,Funcao objetivo, Iteracoes\n");
//            
//            pw.write(csvHeader.toString());
//            
//            pw.close();
//            
//        } catch (FileNotFoundException ex) {
//            System.out.println("Erro ao criar arquivo");
//        }
    }

    public void write(double x1, double x2, double FS, int iteracoes) {

        try {
            BufferedWriter bufferWrite = new BufferedWriter(new FileWriter(this.fileName,true));

            csvData = new StringBuffer("");
            csvData.append(x1 + ",");
            csvData.append(x2 + ",");
            csvData.append(FS + ",");
            csvData.append(iteracoes);
            csvData.append("\n");

            bufferWrite.append(csvData.toString());
            bufferWrite.close();

        } catch (IOException ex) {
            System.out.println("Erro ao abrir arquivo!");
        }
//        
//        try {
//            pw = new PrintWriter(new File(this.fileName + ".csv"));
//        } catch (FileNotFoundException ex) {
//            System.out.println("Erro ao abrir arquivo");
//        }
//        csvData.append(x1 + ",");
//        csvData.append(x2 + ",");
//        csvData.append(FS + ",");
//        csvData.append(iteracoes);
//        csvData.append("\n");
//
//        pw.append(csvData.toString());
//        pw.close();
    }

//    public void close(){
//        pw.close();
//    }
}
