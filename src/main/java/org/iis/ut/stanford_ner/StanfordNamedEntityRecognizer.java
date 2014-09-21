/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.iis.ut.stanford_ner;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import edu.stanford.nlp.ie.AbstractSequenceClassifier;
import edu.stanford.nlp.ie.crf.CRFClassifier;
import edu.stanford.nlp.ling.CoreLabel;

/**
 *
 **      هُوَ اللَّطیف     ** 
 * @author Mostafa Dehghani
 *
 **/
public class StanfordNamedEntityRecognizer {
    public static String Classifieradd[] ={"src/main/resources/stanford-ner/classifiers/english.all.3class.distsim.crf.ser.gz"
    , "src/main/resources/stanford-ner/classifiers/english.conll.4class.distsim.crf.ser.gz"
    , "src/main/resources/stanford-ner/classifiers/english.muc.7class.distsim.crf.ser.gz"};
    public static final AbstractSequenceClassifier<CoreLabel> Classifier[] =  new AbstractSequenceClassifier[Classifieradd.length];
    
    static{
        for(int i=0; i<Classifieradd.length ;i++){
            Classifier[i]= CRFClassifier.getClassifierNoExceptions(Classifieradd[i]);
        }
    }

    
    public static List<String> NER(String Input) throws IOException {

      List<String> Output = new ArrayList<String>();
      for(int i=0; i<Classifier.length ;i++){
      String Text_NER;            
          try {
              Text_NER = Classifier[i].classifyWithInlineXML(Input);
          } catch (Exception e) {
              continue;
          }
        Matcher m = Pattern.compile("<([A-Za-z0-9]+?)>(.*?)<(/[A-Za-z0-9]+?)>").matcher(Text_NER);
        while(m.find()){
                    Output.add(m.group(2));
        }
//        System.out.println(classifier.classifyWithInlineXML(Input));
//        System.out.println(classifier.classifyToString(Input, "xml", true)); 
//        System.out.println(classifier.classifyToString(Input));
//        System.err.println("--------------------------");
      }
      return Output;
    }
    public static void main(String[] args) throws IOException {
        String input = "The fate of Lehman Brothers, the beleaguered investment bank, hung in the balance on Sunday as Federal Reserve officials and the leaders of major financial institutions continued to gather in emergency meetings trying to complete a plan to rescue the stricken bank.  Several possible plans emerged from the talks, held at the Federal Reserve Bank of New York and led by Timothy R. Geithner, the president of the New York Fed, and Treasury Secretary Henry M. Paulson Jr.";
        List<String> tmpList = NER(input); 
        String NamedEntitis ="";
        for(String tmpString : tmpList){
            NamedEntitis += "\n" + tmpString;
        }
        System.out.println(NamedEntitis);
    }
}
